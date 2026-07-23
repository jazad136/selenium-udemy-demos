package com.practicetestautomation.testsetup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author JonathanSaddler
 */
public class ExtentReportManager {
    private static ExtentReports extent;
    private ExtentReportManager() { } 
    
    public static ExtentReports getReporter() {
        if(extent == null) { 
            extent = new ExtentReports();
            final File CONF = new File("src/test/resources/ExtentConfigs/extentConfig.xml");
            ExtentSparkReporter spark = new ExtentSparkReporter("xml-config-report.html");
            // load xml configuration
            try {spark.loadXMLConfig(CONF);} 
            catch(IOException e) {
                throw new RuntimeException("Could not set up suite with XML configuration"
                        + " at src/main/resources/extentConfig.xml");
            }
            extent.attachReporter(spark);
        }
        return extent;
    }
    
}
