package jwt.sec.auth.controllers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jwt.sec.auth.jmappers.user.MapperUser;
import jwt.sec.auth.payload.request.LoginRequest;
import jwt.sec.auth.payload.response.JwtResponse;
import jwt.sec.auth.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MapperUser mapperUser;

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

        //	return ResponseEntity.ok(
        //			new JwtResponse(jwt, 0L, userDetails.getUsername(), "mail", roles));

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Access-Control-Expose-Headers", "X-Authorization");
        headers.add("X-Authorization", "Bearer " + jwt);


        //	DbUser dbuser = mapperUser.findByUsername(userDetails.getUsername());

        String[] arrOfStr = userDetails.getUsername().split(":");

        logger.info("username " + arrOfStr[0]);
        logger.info("imie " + arrOfStr[1]);
        logger.info("nazwisko " + arrOfStr[2]);
        logger.info("e-mail " + arrOfStr[3]);
        return new
                ResponseEntity<JwtResponse>(new JwtResponse(arrOfStr[0], arrOfStr[2], arrOfStr[1], arrOfStr[3], roles), headers, HttpStatus.OK);
    }
//
//	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Username is already taken!"));
//		}
//
//		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Email is already in use!"));
//		}
//
//		// Create new user's account
//		DbUser user = new DbUser(signUpRequest.getUsername(), 
//							 signUpRequest.getEmail(),
//							 encoder.encode(signUpRequest.getPassword()));
//
//		Set<String> strRoles = signUpRequest.getRole();
//		Set<Role> roles = new HashSet<>();
//
//		if (strRoles == null) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			strRoles.forEach(role -> {
//				switch (role) {
//				case "admin":
//					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(adminRole);
//
//					break;
//				case "mod":
//					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(modRole);
//
//					break;
//				default:
//					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(userRole);
//				}
//			});
//		}
//
//		user.setRoles(roles);
//		userRepository.save(user);
//
//		return ResponseEntity.ok(new MessageResponse("DbUser registered successfully!"));
//	}
}
