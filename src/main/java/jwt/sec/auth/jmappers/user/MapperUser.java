package jwt.sec.auth.jmappers.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jwt.sec.auth.domain.user.DbUser;

@Mapper
public interface MapperUser {

    DbUser findByUsername(String login);

    DbUser getUser(Long id);

    List<String> getUsrRole(Long idusr);

    List<DbUser> filteredUsers(String param);
    
    public void saveUser(DbUser user);

}
