package jwt.sec.auth.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MybatisConfig {

    @MapperScan(basePackages = "jwt.sec.auth.jmappers.user", sqlSessionTemplateRef = "userSqlSessionTemplate")
    private static class userMapperScan {
    }

    @MapperScan(basePackages = "jwt.sec.auth.jmappers.trade", sqlSessionTemplateRef = "tradeSqlSessionTemplate")
    private class tradeMapperScan {
    }

    @Bean("userSqlSessionTemplate")
    public SqlSessionTemplate userSqlSessionTemplate(
            @Qualifier("userSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("tradeSqlSessionTemplate")
    public SqlSessionTemplate tradeSqlSessionTemplate(
            @Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }



    @Bean("userSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactory(
            @Qualifier("UserDS") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("jwt.sec.auth.domain.user");
        String[] mapperLocations = {"classpath:mbmappers/*User.xml"};
        factory.setMapperLocations(resolveMapperLocations(mapperLocations));
        return factory.getObject();
    }

    @Bean("tradeSqlSessionFactory")
    public SqlSessionFactory tradeSqlSessionFactory(
            @Qualifier("TradeDS") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("jwt.sec.auth.domain.trade");
        String[] mapperLocations = {"classpath:mbmappers/*Trade.xml"};
        factory.setMapperLocations(resolveMapperLocations(mapperLocations));
        return factory.getObject();
    }

    public static final String PRIMARY_DATASOURCE = "UserDS";
    public static final String SECONDARY_DATASOURCE = "TradeDS";

    @Bean(name = PRIMARY_DATASOURCE, destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.user")
    @Primary
    public DataSource dataSourceOne() {
        return new HikariDataSource();
    }

    @Bean(name = SECONDARY_DATASOURCE, destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.trade")
    public DataSource dataSourceAnother() {
        return new HikariDataSource();
    }

    private Resource[] resolveMapperLocations(String[] mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }
}

