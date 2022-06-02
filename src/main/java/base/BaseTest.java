package base;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.HomePage;
import utilities.CommonUtilities;
import utilities.Log;

import java.util.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static EnvironmentProperties env = new EnvironmentProperties();
    protected String projectPath = System.getProperty("user.dir");
    protected String pathScreenshots = projectPath + "/screenshots/";
    protected Properties propi = EnvironmentProperties.getProperties();
    protected WebDriver driver;
    protected ChromeDriver driverChrome;
    protected HomePage homePage;

    @BeforeSuite
    public void init() {
        Log.startLog("Test Suite is starting");
    }


    @BeforeTest()
    public void setup() {
        Log.info("Initialize WebDriver instance");
        driver = getDriver();

        Log.info("Deleting Cookies");
        driver.manage().deleteAllCookies();

        Log.info("Applying the implicit wait during 10 seconds");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        homePage = new HomePage(driver);
        Log.info("Open the web site to test");
        driver.get(propi.getProperty("webapp.website"));
        Log.info("Opening : " + propi.getProperty("webapp.website"));
    }

    @AfterTest
    public void teardown() {
        try {
            Log.endLog("Test node is ending");
            if (driver != null) {
                Log.endLog("Driver close");
                driver.close();
                driver.quit();
            }
        } catch (Exception e) {
        }

    }

    @AfterSuite
    public void end() {
        try {
            Log.endLog("Test Suite is ending");
        } catch (WebDriverException we) {
            Log.fatal(we.getMessage());
        }
    }

    public WebDriver getDriver() {
        if (driver == null)
            driver = connectDriver();
        return driver;
    }

    public WebDriver connectDriver() {
        String propBrowser = propi.getProperty("webapp.browser").toLowerCase();

        Properties propSystem = System.getProperties();
        Enumeration e = propSystem.propertyNames();

        Map<String, String> propMap = new HashMap<String, String>();
        propMap = CommonUtilities.putSysProps(e, propSystem);
        projectPath = propMap.get("user.dir");
        String os = propMap.get("os.name").trim().toLowerCase();

        if (propBrowser.isEmpty() || propBrowser == null) {
            throw new IllegalArgumentException("Missing parameter value for browser");
        } else {

            if (os.indexOf("win") >= 0) {

                if (propBrowser.contentEquals("chrome")) {

                    System.setProperty("webdriver.chrome.driver",
                            projectPath + "\\drivers\\windows\\chromedriver.exe");


                    ChromeOptions op = new ChromeOptions();
                    if (propi.getProperty("webapp.headlessExecution").equals("true")) {
                        op.addArguments("--headless");
                    }
                    if (propi.getProperty("webapp.incognito").equals("true")) {
                        op.addArguments("--incognito");
                    }
                    op.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                    op.setPageLoadStrategy(PageLoadStrategy.NONE);
                    op.addArguments("enable-automation");
                    op.addArguments("--disable-gpu");
                    op.addArguments("--disable-extensions");
                    op.addArguments("--no-sandbox");
                    op.addArguments("--disable-dev-shm-usage");
                    driver = new ChromeDriver(op);

                } else if (propBrowser.contentEquals("ff") ||
                        propBrowser.contentEquals("firefox")) {
                    FirefoxOptions dc = new FirefoxOptions();
                    if (propi.getProperty("webapp.headlessExecution").equals("true")) {
                        dc.setHeadless(true);
                    }

                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setAcceptUntrustedCertificates(true);
                    profile.setAssumeUntrustedCertificateIssuer(false);
                    profile.setPreference("app.update.enabled", false);
                    profile.setPreference("browser.download.folderList", 2);
                    profile.setPreference("browser.helperApps.alwaysAsk.force", false);
                    if (propi.getProperty("webapp.incognito").equals("true")) {
                        profile.setPreference("browser.private.browsing.autostart", true);
                    }
                    //dc.setCapability(FirefoxDriver.PROFILE, profile);
                    dc.setCapability("marionette", true);
                    System.setProperty("webdriver.gecko.driver",
                            projectPath + "\\drivers\\windows\\geckodriver.exe");
                    driver = new FirefoxDriver(dc);

                }  else {
                    throw new IllegalArgumentException("Browser name is not correct or is not valid...");
                }
            }
            else if ((os.contains("mac")) || (os.contains("darwin"))) {

                if (propBrowser.trim().contentEquals("chrome")) {

                    System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/mac/chromedriver");

                    if(propi.getProperty("webapp.mobileSetup").equalsIgnoreCase("true")) {
                        driverChrome = new ChromeDriver();
                        DevTools devTools = driverChrome.getDevTools();
                        devTools.createSession();
                        devTools.send(Emulation.setDeviceMetricsOverride(
                                600,
                                1000,
                                50,
                                true,
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()));

                    } else {
                        ChromeOptions op = new ChromeOptions();
                        if (propi.getProperty("webapp.headlessExecution").equals("true")) {
                            op.addArguments("--headless");
                        }
                        if (propi.getProperty("webapp.incognito").equals("true")) {
                            op.addArguments("--incognito");
                        }
                        op.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                        op.setPageLoadStrategy(PageLoadStrategy.NONE);
                        op.addArguments("enable-automation");
                        op.addArguments("--disable-gpu");
                        op.addArguments("--disable-extensions");
                        op.addArguments("--no-sandbox");
                        op.addArguments("--disable-dev-shm-usage");

                        driverChrome = new ChromeDriver(op);
                    }
                }
                else if (propBrowser.contentEquals("ff") || propBrowser.contentEquals("firefox")) {
                    FirefoxOptions dc = new FirefoxOptions();
                    if (propi.getProperty("webapp.headlessExecution").equals("true")) {
                        dc.setHeadless(true);
                    }
                    FirefoxProfile profile = new FirefoxProfile();

                    profile.setAcceptUntrustedCertificates(true);
                    profile.setAssumeUntrustedCertificateIssuer(false);

                    profile.setPreference("browser.download.folderList", 2);
                    profile.setPreference("browser.helperApps.alwaysAsk.force", false);

                    if (propi.getProperty("webapp.incognito").equals("true")) {
                        profile.setPreference("browser.private.browsing.autostart", true);
                    }
                    //dc.setCapability(FirefoxDriver.PROFILE, profile);
                    dc.setCapability("marionette", true);

                    System.setProperty("webdriver.gecko.driver",
                            projectPath + "/drivers/mac/geckodriver");
                    driver = new FirefoxDriver(dc);
                }
                else {
                    throw new IllegalArgumentException("Browser name is not correct or is not valid...");
                }
            } else {
                throw new IllegalArgumentException("OS out of scope ...");
            }
        }

        return driverChrome;
    }


}
