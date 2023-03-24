package jwt.sec.auth.security;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomLdapAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomLdapAuthenticationProvider.class);

    private LdapTemplate ldapTemplate;

    @PostConstruct
    private void initContext() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://warta.ad.intra:389");
        contextSource.setAnonymousReadOnly(true);
        contextSource.setUserDn("CN=wafplsct,OU=ServiceAccounts,OU=_Admin,DC=warta,DC=ad,DC=intr");
        contextSource.afterPropertiesSet();
        contextSource.setPassword("SvN*43#N9$xX");
        ldapTemplate = new LdapTemplate(contextSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        logger.warn("CustomLdapAuthenticationProvider.authenticate - start");
        logger.warn("CustomLdapAuthenticationProvider.authenticate - username: " + authentication.getName());
        logger.warn("CustomLdapAuthenticationProvider.authenticate - password: " + authentication.getCredentials().toString());
        Filter filter = new EqualsFilter("uid", authentication.getName());
        boolean authenticate = ldapTemplate.authenticate(LdapUtils.emptyLdapName(), filter.encode(),
                authentication.getCredentials().toString());
        if (authenticate) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            UserDetails userDetails = new User(authentication.getName() ,authentication.getCredentials().toString()
                    ,grantedAuthorities);
            return new UsernamePasswordAuthenticationToken(userDetails,
                    authentication.getCredentials().toString() , grantedAuthorities);

        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
