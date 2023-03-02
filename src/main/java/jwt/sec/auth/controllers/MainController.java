package jwt.sec.auth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jwt.sec.auth.domain.user.DbUser;
import jwt.sec.auth.domain.user.DbUsrRoles;
import jwt.sec.auth.jmappers.user.MapperUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author AQ
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/main")
public class MainController {

    private final MapperUser mapperUser;

    @Inject
    public MainController(MapperUser mapperUser) {
        Assert.notNull(mapperUser, "MyCollaborator must not be null!");
        this.mapperUser = mapperUser;
    }

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    String jsonStr;
    String param;

    @PreAuthorize("hasRole('ADMIN')")
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
            logger.info("usersList json: " + jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
//=================================================================================================
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

        if (user.getId() != 0L) {
            if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().isBlank()) {
                DbUser xx = mapperUser.getUser(user.getId());
                user.setPassword(xx.getPassword());
                logger.info("saveUser database password: " + user.getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                logger.info("saveUser encode password: " + user.getPassword());
            }
            mapperUser.saveUser(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            mapperUser.insUser(user);
        }
        return "Zapisano.";
    }
//=================================================================================================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/usrRolList", produces = "application/json")
    public String userRoleList(@RequestParam Long idUser) {
        List<DbUsrRoles> list = mapperUser.getUserRoles(idUser);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        try {
            jsonStr = mapper.writeValueAsString(list);
            logger.info("usersList userRoleList json: " + jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
    //=================================================================================================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/user", produces = "application/json")
    public String userData(@RequestParam Long idUser) {
        DbUser usr = mapperUser.getUser(idUser);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        try {
            jsonStr = mapper.writeValueAsString(usr);
            logger.info("usersList json: " + jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
    //=================================================================================================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/updUserRole", produces = "text/plain")
    public String updUserRole(@RequestBody DbUsrRoles role) {
        if (role.getId() != 0L) {
            mapperUser.updUserRole(role);
        } else {
            mapperUser.insUserRole(role);
        }
        return "Zapisano.";
    }

}
