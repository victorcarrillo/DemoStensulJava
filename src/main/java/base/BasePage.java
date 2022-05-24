package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonUtilities;
import utilities.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {

	protected WebDriver driver;
	protected WebElement ele;
	protected List<WebElement> listEle;
	protected String projectPath = System.getProperty("user.dir");
	protected String pathScreenshots = projectPath + "/screenshots/";
	public static EnvironmentProperties env = new EnvironmentProperties();

	public BasePage(WebDriver driver) {
		//super(driver);
		this.driver=driver;
	}

	public WebDriverWait wait(int secs) {
		return new WebDriverWait(driver, secs);
	}

	protected WebElement getElement(By by, int secs) {
		try {
			ele = wait(secs)
					.ignoring(TimeoutException.class, NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(by));

			return ele;
		} catch (TimeoutException | NoSuchElementException e) {
			Log.fatal("Element is not found during test execution.. " + e);
			CommonUtilities.takeScreenshot(driver, pathScreenshots, "TimeoutExceptionGetHeaderMessage");
			//throw(e);
			return null;
		}

	}

	protected WebElement getElementPresenceOfElementLocated(By by, int secs) {
		try {
			ele = wait(secs)
					.ignoring(TimeoutException.class, NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.presenceOfElementLocated(by));
			return ele;
		} catch (TimeoutException | NoSuchElementException e) {
			Log.fatal("Element is not found during test execution.... " + e);
			CommonUtilities.takeScreenshot(driver, pathScreenshots, "TimeoutExceptionGetHeaderMessage");
			return null;
		}
	}

	protected List<WebElement> getListElements(By by, int secs) {

		try {
			listEle = wait(secs)
					.ignoring(TimeoutException.class, NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
			return listEle;
		} catch (TimeoutException | NoSuchElementException e) {
			Log.fatal("Element is not found during test execution.... " + e);
			CommonUtilities.takeScreenshot(driver, pathScreenshots, "TimeoutExceptionGetHeaderMessage");
			//throw(e);
			return null;
		}

	}

	protected void scroll2Element(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView({'block':'center','inline':'center'});", ele);

	}

	protected By byXpathLocator(String str1, String str2, String value) {
		return By.xpath(str1 + value + str2);
	}

	protected void implicitWaitFor(int seconds){
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	protected String returnText(WebElement ele) {
		return ele.getText();
	}

	protected void waitForNSeconds(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
