package jwt.sec.auth.controllers;

import jwt.sec.auth.domain.user.DbUser;
import jwt.sec.auth.jmappers.user.MapperUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author AQ
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")  // api/user/usrChgPasswd
public class UserController {

    @Autowired
    private final MapperUser mapperUser;

    @Inject
    public UserController(MapperUser mapperUser) {
        Assert.notNull(mapperUser, "MyCollaborator must not be null!");
        this.mapperUser = mapperUser;
    }

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PreAuthorize("hasRole('ADMIN') or hasRole('VIEW') or hasRole('WRT')")
    @GetMapping(value = "/usrChgPasswd", produces = "text/plain")
    public String usrChgPasswd( @RequestParam String username,
                                @RequestParam String oldpasswd,
                                @RequestParam String newpasswd   ) throws ParseException {


        logger.info("usrChgPasswd username: " + username );
        DbUser dbuser = mapperUser.findByUsername(username);

        if( dbuser == null) {
            return "User not found";
        }

        if ( !passwordEncoder.matches(oldpasswd,
                dbuser.getPassword() ) )  {
            return "Bad old password";
        }
        dbuser.setPassword(passwordEncoder.encode( newpasswd ));

        Calendar cal = Calendar.getInstance();
        cal.setTime( new Date() );
        cal.add(Calendar.DATE, 30);

        Date dt = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(dt);
        dt = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
        Timestamp ts = new Timestamp(dt.getTime());

        dbuser.setData_hasla( ts );
        mapperUser.saveUser(dbuser);

        return "Zapisano";
    }

}
