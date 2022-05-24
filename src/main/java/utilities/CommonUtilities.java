package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CommonUtilities {

    public static void sleepByNSeconds(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Map<String, String> putSysProps(Enumeration e, Properties p) {
        Map<String, String> hash = new HashMap<String, String>();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = p.getProperty(key);
            hash.put(key, value);
        }

        return hash;
    }

    public static void takeScreenshot(WebDriver driver, String filePath, String scenario) {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driver);

            File fin = new File(filePath
                    + "Screenshot_"
                    + scenario
                    + ".png");

            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);

            File destFile = fin;

            FileUtils.copyFile(srcFile, destFile);
        } catch (Exception ioe) {
        }
    }

    public static Properties loadProperties(String path) {
        try (InputStream input = new FileInputStream(path)) {
            Properties prop = new Properties();
            prop.load(input);

            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}