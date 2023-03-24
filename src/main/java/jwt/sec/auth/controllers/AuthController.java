package jwt.sec.auth.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jwt.sec.auth.jmappers.user.MapperUser;
import jwt.sec.auth.payload.request.LoginRequest;
import jwt.sec.auth.payload.response.JwtResponse;
import jwt.sec.auth.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final MapperUser mapperUser;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Inject
    public AuthController(MapperUser mapperUser, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        Assert.notNull(mapperUser, "mapperUser must not be null!");
        this.mapperUser = mapperUser;
        Assert.notNull(jwtUtils, "jwtUtils must not be null!");
        this.jwtUtils = jwtUtils;
        Assert.notNull(authenticationManager, "authenticationManager must not be null!");
        this.authenticationManager = authenticationManager;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        logger.info(loginRequest.getUsername());
        logger.info(loginRequest.getPassword());
        logger.info(passwordEncoder.encode(loginRequest.getPassword()));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


        logger.info("authenticateUser name " + authentication.getName());
        logger.info("authenticateUser principal " + authentication.getPrincipal().toString());
        logger.info("authenticateUser authentication " + authentication.toString());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("authenticateUser 4 ");
        String jwt = jwtUtils.generateJwtToken(authentication);
        logger.info("authenticateUser jwt: " + jwt);

        User userDetails = (User) authentication.getPrincipal();

        logger.info("authenticateUser " + userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Access-Control-Expose-Headers", "X-Authorization");
        headers.add("X-Authorization", "Bearer " + jwt);

        String[] arrOfStr = userDetails.getUsername().split(":");

        logger.info("username " + arrOfStr[0]);
        logger.info("imie " + arrOfStr[1]);
        logger.info("nazwisko " + arrOfStr[2]);
        logger.info("e-mail " + arrOfStr[3]);
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
        return new
                ResponseEntity<>(new JwtResponse(arrOfStr[0], arrOfStr[2], arrOfStr[1], arrOfStr[3], roles), headers, HttpStatus.OK);
    }
}
