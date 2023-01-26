package jwt.sec.auth.jmappers.trade;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MapperTrade {

    String findByCustname(int idcust);

}