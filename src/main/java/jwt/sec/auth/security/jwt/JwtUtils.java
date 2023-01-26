package jwt.sec.auth.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jwt.sec.auth.models.JwtPayload;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@SuppressWarnings("deprecation")
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${com.jwt.sec.jwtSecret}")
    private String jwtSecret;

    @Value("${com.jwt.sec.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) throws JsonMappingException, JsonProcessingException {

        User userPrincipal = (User) authentication.getPrincipal();

        jwtSecret = "7e65f6f2ef2298d16b15a8788b6e1aa9ae81ac205fe959d112c75bf715af9e0a";

        logger.info("generateJwtToken username " + userPrincipal.getUsername());
        logger.info("generateJwtToken jwtSecret " + jwtSecret);
        logger.info("generateJwtToken jwtExpirationMs " + jwtExpirationMs);


        String[] arrOfStr = userPrincipal.getUsername().split(":");

        var roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(3, ChronoUnit.MINUTES);

        logger.info("Issued at: {}", issuedAt);
        logger.info("Expires at: {}", expiration);

        //var signingKey = jwtSecret.getBytes();
        var token2 = Jwts.builder()
                //		.signWith(SignatureAlgorithm.HS512, Keys.hmacShaKeyFor(signingKey))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .setHeaderParam("typ", "JWT")
                .setIssuer("secure-api")
                .setAudience("secure-app")
                .setSubject(arrOfStr[0])
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", roles)
                .compact();

        logger.info("JWT token: " + token2);
        String[] chunks = token2.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        logger.info("JWT Header " + header);
        logger.info("JWT payload" + payload);

        ObjectMapper objectMapper = new ObjectMapper();
        JwtPayload jwtPayload = objectMapper.readValue(payload, JwtPayload.class);
        logger.info("JWT payload username " + jwtPayload.getSub());

        return token2;
    }

    public String getUserNameFromJwtToken(String token) {


        logger.info("JWT token: " + token);
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        logger.info("JWT Header " + header);
        logger.info("JWT payload" + payload);

        ObjectMapper objectMapper = new ObjectMapper();
        JwtPayload jwtPayload = new JwtPayload();
        try {
            jwtPayload = objectMapper.readValue(payload, JwtPayload.class);
            logger.info("JWT payload username " + jwtPayload.getSub());
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jwtPayload.getSub();

        //  return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws Exception {

//	  String[] chunks = authToken.split("\\.");
//	  String tokenWithoutSignature = chunks[0] + "." + chunks[1];
//	  String signature = chunks[2];
//	  
//	  SignatureAlgorithm sa = SignatureAlgorithm.HS512;
//	  SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(), sa.getJcaName());
//	  
//	  DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);
//
//	  if (!validator.isValid(tokenWithoutSignature, signature)) {
//	      throw new Exception("Could not verify JWT token integrity!");
//	  }

        logger.info("JWT parser start ");
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            logger.info("JWT parser ok ");
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        logger.info("JWT parser bad ");
        return false;
    }
}
