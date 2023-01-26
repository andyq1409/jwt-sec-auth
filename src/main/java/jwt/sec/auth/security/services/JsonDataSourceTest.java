package jwt.sec.auth.security.services;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;


/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class JsonDataSourceTest {

    public void test() throws JRException {
        fill();
        pdf();
    }

    private void fill() throws JRException {
        long start = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, "yyyy-MM-dd");
        params.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "#,##0.##");
        params.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
        params.put(JRParameter.REPORT_LOCALE, Locale.US);

        JasperCompileManager.compileReportToFile("reports/northwind.jrxml");
        JasperFillManager.fillReportToFile("reports/northwind2.jasper", params);
        System.err.println("Filling time : " + (System.currentTimeMillis() - start));
    }

    private void pdf() throws JRException {
        long start = System.currentTimeMillis();
        JasperExportManager.exportReportToPdfFile("reports/northwind2.jrprint");
        System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
    }

}
