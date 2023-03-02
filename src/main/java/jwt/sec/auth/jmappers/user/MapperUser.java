package jwt.sec.auth.jmappers.user;

import java.util.List;

import jwt.sec.auth.domain.user.DbUsrRoles;
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

}
