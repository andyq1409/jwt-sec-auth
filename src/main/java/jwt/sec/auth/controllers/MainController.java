package jwt.sec.auth.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jwt.sec.auth.domain.user.DbUser;
import jwt.sec.auth.jmappers.user.MapperUser;

/**
 * @author AQ
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/main")
public class MainController {

	@Autowired(required = true)
	private MapperUser mapperUser;

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	String jsonStr;
	String param;

	@PreAuthorize("hasRole('ADMIN')")
//     /api/main/usrlist
	@GetMapping(value = "/usrlist", produces = "application/json")
	public String usersList(@RequestParam String filtrStr) {

		if (filtrStr.equals("all")) {
			param = "%%";
		} else {
			param = "%" + filtrStr + "%";
		}
		logger.info("usersList param: " + param);
		List<DbUser> usrs = mapperUser.filteredUsers(param);

		usrs.forEach(entry -> {
			List<String> roles = mapperUser.getUsrRole(entry.getId());
			entry.setRoles(new HashSet<>(roles));
		});

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(df);
		try {
			jsonStr = mapper.writeValueAsString(usrs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonStr;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/saveUser", produces = "text/plain")
	public String saveUser(@RequestBody DbUser user) {
		logger.info("saveUser password: " + user.getPassword());
		logger.info("saveUser password: " + user.getPassword());
		if (user.getData_do() == null) {
			logger.info("saveUser data_do: is null");			
		} else {
			logger.info("saveUser data_do: " + user.getData_do().toString());
		}
		logger.info("saveUser mail: " + user.getEmail());
		logger.info("saveUser data_od: " + user.getData_od().toString());
		
		if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().isBlank() ) {
			DbUser xx = mapperUser.getUser(user.getId());
			user.setPassword(xx.getPassword());
		    logger.info("saveUser database password: " + user.getPassword());
		} else {
			user.setPassword( passwordEncoder.encode(user.getPassword()) );
			logger.info("saveUser encode password: " + user.getPassword());
		}		
		mapperUser.saveUser(user);
		return "Zapisano.";
	}
}
