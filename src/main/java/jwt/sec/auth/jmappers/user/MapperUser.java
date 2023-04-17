package jwt.sec.auth.jmappers.user;

import java.util.List;

import jwt.sec.auth.domain.user.DbUsrRoles;
import jwt.sec.auth.domains.DbCustomer;
import jwt.sec.auth.domains.DbOrder;
import jwt.sec.auth.domains.DbProduct;
import org.apache.ibatis.annotations.Mapper;

import jwt.sec.auth.domain.user.DbUser;

@Mapper
public interface MapperUser {

    DbUser findByUsername(String login);

    DbUser getUser(Long id);

    List<DbUsrRoles> getUserRoles(Long idusr);

    List<String> getUsrRole(Long idusr);

    List<DbUser> filteredUsers(String param);
    
    void saveUser(DbUser user);

    void insUser(DbUser user);

    void updUserRole(DbUsrRoles role);

    void insUserRole(DbUsrRoles role);

    List<DbProduct> getProducts(DbProduct product);

    void insProduct(DbProduct prod);

    void updProduct(DbProduct prod);

    List<DbOrder> getOrders(DbOrder order);

    List<DbCustomer> getCustomers(DbCustomer customer);

}
