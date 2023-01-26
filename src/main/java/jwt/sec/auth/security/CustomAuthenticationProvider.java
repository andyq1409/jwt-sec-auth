package jwt.sec.auth.security;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jwt.sec.auth.security.services.UserDetailsServiceImpl;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Resource
    UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {

        logger.warn("CustomAuthenticationProvider.authenticate - start");
        String name = authentication.getName();
        logger.warn("request user: " + name);
        Object credentials = authentication.getCredentials();
        logger.warn("credentials class: " + credentials.getClass());
        if (!(credentials instanceof String)) {
            return null;
        }
        String password = credentials.toString();
        logger.warn("user password: " + password);


        UserDetails user = null;
        try {
            user = userDetailsService.loadUserByUsername(name);
            logger.warn("user: " + user.toString());

        } catch (UsernameNotFoundException exception) {
            logger.warn("DbUser not found");
            throw new BadCredentialsException(exception.getMessage());
        }

        if (!user.isAccountNonExpired()) {
            logger.warn("DbUser account expired");
            throw new DisabledException("DbUser account expired");
        }

        if (!user.isCredentialsNonExpired()) {
            logger.warn("DbUser password expired");
            throw new DisabledException("DbUser password expired");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException("Bad credentials");
        }

        logger.info("Authentication success");

        return createSuccessfulAuthentication(authentication, user);
    }

    private Authentication createSuccessfulAuthentication(final Authentication authentication, final UserDetails user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        logger.warn("CustomAuthenticationProvider.supports - start");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
