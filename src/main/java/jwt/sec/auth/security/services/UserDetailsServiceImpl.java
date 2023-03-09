package jwt.sec.auth.security.services;

import jwt.sec.auth.domain.user.DbUser;
import jwt.sec.auth.jmappers.trade.MapperTrade;
import jwt.sec.auth.jmappers.user.MapperUser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.query.JsonQLQueryExecuterFactory;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.*;

/*
https://github.com/tanerinal/spring-security-ldap-jwt-mvc
https://dzone.com/articles/user-authentication-remote-ldap-server
 */


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @Autowired
    MapperUser mapperUser;

    @Autowired
    MapperTrade mapperTrade;


    @Override
    public UserDetails loadUserByUsername(String username) {
        logger.warn("AqUserDetailService.loadUserByUsername - start");


        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = true;
        logger.info("loadUserByUsername - start");
        logger.warn("username: " + username);

        DbUser dbuser = mapperUser.findByUsername(username);

        String test = mapperTrade.findByCustname(2);
        logger.warn("Customer: " + test);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (dbuser == null) {
            logger.info("Not found");
            throw new UsernameNotFoundException("Nie ma użytkownika o loginie: " + username);
        }

        logger.warn("dbuser found password: " + dbuser.getPassword());

        List<String> roles = mapperUser.getUsrRole(dbuser.getId());
        logger.warn("AqUserDetailService 4");

        for (String item : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(item));
        }

        long millis = System.currentTimeMillis();
        Date now = new Date(millis);
        logger.info("now: " + now.toString());

        if (dbuser.getData_do() == null) {
            dbuser.setData_do(new Timestamp(now.getTime() + 1000 * 60 * 60 * 24 * 1));
        //    dbuser.setData_do(new Date(now.getTime() + 1000 * 60 * 60 * 24 * 1));
        }
        logger.info("dbuser data_do: " + dbuser.getData_do().toString());

        if (now.before(dbuser.getData_od()) || now.after(dbuser.getData_do())) {
            logger.info("Out period");
            accountNonExpired = false;

        }

        if (now.after(dbuser.getData_hasla())) {
            logger.info("Password expired");
          //  credentialsNonExpired = false;
            grantedAuthorities.add(new SimpleGrantedAuthority("PASSW_EXPIRED"));
        }

        logger.warn("dbuser grantedAuthorities: " + grantedAuthorities.toString());
        var naz = dbuser.getUsername() + ":" + dbuser.getImie() + ":" + dbuser.getNazwisko() + ":" + dbuser.getEmail();

        User user = new User(naz, dbuser.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, grantedAuthorities);
        logger.info("dbuser username : " + user.getUsername());
        logger.info("dbuser password : " + user.getPassword());

        return user;
    }

    public void makeReport() {

        logger.info("makeReport - start");
        try {
//			File jsonFile = new File("json/northwind.json");
//			logger.info("jsonFile - " + jsonFile.length());


            Map<String, Object> params = new HashMap<String, Object>();
            params.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, "yyyy-MM-dd");
            params.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "#,##0.##");
            params.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
            params.put(JRParameter.REPORT_LOCALE, Locale.US);
//
//			JsonDataSource jsonDataSource = new JsonDataSource(jsonFile);
//			jsonDataSource.setDatePattern("yyyy-MM-dd");
//			jsonDataSource.setNumberPattern("#,##0.##");
//
//			logger.info("jsonDataSource - " + jsonDataSource.toString());
//			logger.info("jsonDataSource - " + jsonDataSource.recordCount());
//
//			JasperReport jasperReport = JasperCompileManager.compileReport("reports/northwind.jrxml");
//			logger.info("jasperReport - " + jasperReport.getName());
//
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jsonDataSource);
//			logger.info("jasperPrint - " + jasperPrint.getPages().size());

            String jasperFile = JasperCompileManager.compileReportToFile("reports/northwind.jrxml");
            logger.info("jasperFile - " + jasperFile);

            JasperFillManager.fillReportToFile(jasperFile, params);

            JasperExportManager.exportReportToPdfFile("reports/northwind2.jrprint");

            JasperExportManager.exportReportToPdfFile("reports/northwind2.jrprint", "c:/ANDRZEJ/test.pdf");

        } catch (Exception e) {
            logger.info("makeReport - error: " + e.getMessage());
        }
        logger.info("makeReport - stop");
    }


    private void getJasper() throws JRException, IOException {


        JasperReport jasperReport = JasperCompileManager.compileReport("reports/foton.jrxml");
        logger.info("jasperReport - " + jasperReport.getName());

        InputStream jsonStream = new FileInputStream("json/example.json");
        logger.info("jsonStream length - " + jsonStream.available());

        Map<String, Object> parameters = new HashMap<>();

        parameters.put(JsonQLQueryExecuterFactory.JSON_INPUT_STREAM, jsonStream);
//        parameters.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, "yyyy-MM-dd");
//        parameters.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "#,##0.##");
//        parameters.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
//        parameters.put(JRParameter.REPORT_LOCALE, Locale.US);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "c:/ANDRZEJ/test.pdf");
    }


    private File[] getFiles(File parentFile, String extension) {
        List<File> fileList = new ArrayList<>();
        String[] files = parentFile.list();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String reportFile = files[i];
                if (reportFile.endsWith("." + extension)) {
                    fileList.add(new File(parentFile, reportFile));
                }
            }
        }
        return fileList.toArray(new File[fileList.size()]);
    }


    public void makeReport2() {

        logger.info("makeReport - start");
        try {

            Map<String, Object> params = new HashMap<String, Object>();

            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:aq/warszawa@localhost:1521/XEPDB1");

            JasperReport jasperReport = JasperCompileManager
                    .compileReport("c:/ANDRZEJ/JAVA/EXAMPLES/jwt-sec-auth/src/main/resources/report1.jrxml");
            logger.info("jasperReport - " + jasperReport.getName());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
            logger.info("jasperPrint - " + jasperPrint.getPages().size());

            JasperExportManager.exportReportToPdfFile(jasperPrint, "c:/ANDRZEJ/test2.pdf");

        } catch (Exception e) {
            logger.info("makeReport - error: " + e.getMessage());
        }
        logger.info("makeReport - stop");

    }


    private void fill() throws JRException {
        logger.info("fill - start");
        long start = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, "yyyy-MM-dd");
        params.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "#,##0.##");
        params.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
        params.put(JRParameter.REPORT_LOCALE, Locale.US);
        try {
            params.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM, new FileInputStream("json/northwind.json"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.info("error : " + e.getMessage());
            e.printStackTrace();
        }

        JasperCompileManager.compileReportToFile("reports/northwind.jrxml");
        logger.info("fillReportToFile - start");
        JasperFillManager.fillReportToFile("reports/northwind2.jasper", params);
        logger.info("Filling time : " + (System.currentTimeMillis() - start));
    }

    private void pdf() throws JRException {
        long start = System.currentTimeMillis();
        JasperExportManager.exportReportToPdfFile("reports/northwind2.jrprint");
        logger.info("PDF creation time : " + (System.currentTimeMillis() - start));
    }

}
