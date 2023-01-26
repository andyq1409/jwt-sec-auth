package jwt.sec.auth.controllers;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.query.JsonQLQueryExecuterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/all")
    public String allAccess() {

/*        try {
            getJasper();
        } catch (JRException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();sdgfsgfbsgfbbs
        }*/


        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "DbUser Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }


    private void getJasper() throws JRException, IOException {


        JasperReport jasperReport = JasperCompileManager.compileReport("reports/northwind.jrxml");
        logger.info("jasperReport - " + jasperReport.getQuery().getText());

        InputStream jsonStream = new FileInputStream("json/northwind.json");
        logger.info("jsonStream length - " + jsonStream.available());
//
//        byte[] array = new byte[2500];
//
//        // Read byte from the input stream
//        jsonStream.read(array);
//        logger.info("Data read from the file: ");
//
//        // Convert byte array into string
//        String data = new String(array);
//        logger.info(data);
//        
//        byte[] bytes = new byte[35];
//        int ile = jsonStream.read(bytes, 0, 30);
//        logger.info("jsonStream ile - " + ile  );
//        String s = new String(bytes);
//        logger.info("jsonStream pocz - " + s  );	

        Map<String, Object> parameters = new HashMap<>();

        parameters.put(JsonQLQueryExecuterFactory.JSON_INPUT_STREAM, jsonStream);
//        parameters.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, "yyyy-MM-dd");
//        parameters.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "#,##0.##");
//        parameters.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
//        parameters.put(JRParameter.REPORT_LOCALE, Locale.US);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "c:/ANDRZEJ/test.pdf");
    }
}
