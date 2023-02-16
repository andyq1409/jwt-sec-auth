package jwt.sec.auth.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
