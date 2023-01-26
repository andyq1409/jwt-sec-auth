package jwt.sec.auth.security.services;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

public class JrRaport {

    public void makeReport() {

        try {

            Map<String, Object> params = new HashMap<String, Object>();

            params.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, "yyyy-MM-dd");
            params.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "#,##0.##");
            params.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
            params.put(JRParameter.REPORT_LOCALE, Locale.US);

            File jsonFile = new File("classpath:mbmappers/northwind.json");

            JRDataSource jsonDataSource = new JsonDataSource(jsonFile);

            JasperReport jasperReport = JasperCompileManager.compileReport("classpath:mbmappers/northwind.jrxml");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jsonDataSource);

            JasperExportManager.exportReportToPdfFile(jasperPrint, "classpath:mbmappers/test.pdf");

        } catch (Exception e) {
        }
    }
}
