package jwt.sec.auth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jwt.sec.auth.domain.user.DbUser;
import jwt.sec.auth.domain.user.DbUsrRoles;
import jwt.sec.auth.domains.DbCustomer;
import jwt.sec.auth.domains.DbOrder;
import jwt.sec.auth.domains.DbOrderItem;
import jwt.sec.auth.domains.DbProduct;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        Assert.notNull(mapperUser, "mapperUser must not be null!");
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
        try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
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
        try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
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
        logger.info("updUserRole user id: " + role.getId_user() );
        logger.info("updUserRole role id: " + role.getId_role() );
        logger.info("updUserRole data od: " + role.getDate_from().toString() );
        if (role.getId() != 999999999L) {
            mapperUser.updUserRole(role);
        } else {
            mapperUser.insUserRole(role);
        }
        return "Zapisano.";
    }
    //=================================================================================================
    @PreAuthorize("hasRole('ADMIN') or hasRole('VIEW') or hasRole('WRT')")
    @GetMapping(value = "/getProds", produces = "application/json")
    public String getProds( @RequestParam Long id,
                            @RequestParam String name  ) {

        logger.info("getProds id: " + id.toString() );
        logger.info("getProds name: " + name );
        String pp = "%" + name + "%";

        DbProduct param = new DbProduct();
        param.setProduct_id(id);
        param.setProduct_name( pp );
        param.setProduct_description( pp );
        logger.info("getProds param: " + param.toString());
        List<DbProduct> list = mapperUser.getProducts(param);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        ObjectMapper mapper2 = new ObjectMapper();
        mapper2.setDateFormat(df);
        try {
            jsonStr = mapper2.writeValueAsString(list);
            logger.info("usersList json: " + jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
    //=================================================================================================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/saveProduct", produces = "text/plain")
    public String saveProduct(@RequestBody DbProduct prod) {
        logger.info("saveProduct nazwa: " + prod.getProduct_name());
        logger.info("saveProduct plik: " + prod.getFilename());

        if (prod.getProduct_id() != 0L) {
            mapperUser.updProduct(prod);
        } else {
            mapperUser.insProduct(prod);
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Zapisano.";
    }
    //=================================================================================================
    @PreAuthorize("hasRole('ADMIN') or hasRole('VIEW') or hasRole('WRT')")
    @GetMapping(value = "/getOrders", produces = "application/json")
    public String getOrders( @RequestParam Long order_id,
                             @RequestParam String customer,
                             @RequestParam String order_timestamp) {

        logger.info("getOrders customer: " + customer );
        logger.info("getOrders order_timestamp: " + order_timestamp );

        DbOrder param = new DbOrder();
        param.setOrder_id(order_id);
        param.setCustomer(customer);
        if (order_timestamp.equals("")) {
            param.setOrder_timestamp(null);
        } else
            param.setOrder_timestamp(Timestamp.valueOf(order_timestamp.substring(0,18)));
        logger.info("getOrders param: " + param.toString());
        List<DbOrder> list = mapperUser.getOrders(param);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper2 = new ObjectMapper();
        mapper2.setDateFormat(df);
        try {
            jsonStr = mapper2.writeValueAsString(list);
            logger.info("usersList json: " + jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
    //=================================================================================================
    @PreAuthorize("hasRole('ADMIN') or hasRole('VIEW') or hasRole('WRT')")
    @GetMapping(value = "/getOrderItems", produces = "application/json")
    public String getOrderItems( @RequestParam Long order_id) {

        logger.info("getOrderItems order_id: " + order_id );

        DbOrderItem param = new DbOrderItem();
        param.setOrder_id(order_id);
        logger.info("getOrderItems param: " + param.toString());
        List<DbOrderItem> list = mapperUser.getOrderItems(param);

        ObjectMapper mapper2 = new ObjectMapper();
        try {
            jsonStr = mapper2.writeValueAsString(list);
            logger.info("getOrderItems json: " + jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
    //=================================================================================================
    @PreAuthorize("hasRole('ADMIN') or hasRole('VIEW') or hasRole('WRT')")
    @GetMapping(value = "/getCustomers", produces = "application/json")
    public String getCustomers( @RequestParam Long customer_id,
                                @RequestParam String cust_last_name) {

        logger.info("getOrders customer: " + cust_last_name );

        DbCustomer param = new DbCustomer();
        param.setCustomer_id(customer_id);
        param.setCust_last_name(cust_last_name);

        logger.info("getOrders param: " + param.toString());
        List<DbCustomer> list = mapperUser.getCustomers(param);

        ObjectMapper mapper2 = new ObjectMapper();
        try {
            jsonStr = mapper2.writeValueAsString(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
    //=================================================================================================

}
