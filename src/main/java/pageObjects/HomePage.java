package pageObjects;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.CommonUtilities;
import utilities.Log;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    /**
     * Home Page object extended from Base Page
     * @author victor.carrillo
     **/

    WebElement ele;
    List<WebElement> list ;
    By byItemList = By.xpath("//ul[@ui-sortable='strangerlist.sortableOptions']/li/div/div/div[2]/p");
    By byDescriptionField = By.cssSelector("textarea[name='text']");
    By byCreateItemButton = By.xpath("//button[contains(text(),'Create Item')]");
    By byItemPhotoUpload = By.cssSelector("input#inputImage");
    By byConfirmDeleteButton = By.xpath("//button[@ng-click='submit()']");
    By byUpdateItemButton = By.xpath("//button[contains(text(),'Update Item')]");


    String strItemTable = "//li[";
    String strEditButton = "]/div/div/div[1]/button[contains(text(),'Edit')]";
    String strDeleteButton= "]/div/div/div[1]/button[contains(text(),'Delete')]";
    String strDescription = "]/div/div/div[2]/p";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /** Actions **/
    public void setItemPhoto(String imgFile) {
        Log.info("Upload item image file");
        ele = this.getElementPresenceOfElementLocated(byItemPhotoUpload, 5);
        ele.sendKeys(env.IMAGE_FOLDER + imgFile);
    }

    public boolean isElementInList(String searchElement) {
        Log.info("Validate if "+ searchElement +" is present on the page");
        list = getListElements(byItemList, 5);
        List<String> text = list.stream().map(WebElement::getText).collect(Collectors.toList());
        return text.stream().anyMatch(x -> x.contains(searchElement));
    }

    public int getNumberOfItemsPresent(){
        implicitWaitFor(4);
        list = getListElements(byItemList, 5);
        return list.size();

    }

    public void clickEditButton(String itemIndex){
        Log.info("Click on Edit Button");
        By byEditButton= byXpathLocator(strItemTable,strEditButton,itemIndex);
        ele = getElementPresenceOfElementLocated(byEditButton,5);
        ele.click();
    }

    public String getCurrentItemDescription(String itemIndex){
        Log.info("Get current description of item");
        By byItemDescription = byXpathLocator(strItemTable,strDescription,itemIndex);
        ele = getElementPresenceOfElementLocated(byItemDescription,5);
        scroll2Element(ele);
        return returnText(ele);
    }

    public void clickDeleteButton(String itemIndex){
        Log.info("Click on Delete Button");
        By byDeleteButton = byXpathLocator(strItemTable,strDeleteButton,itemIndex);
        implicitWaitFor(5);
        ele = getElementPresenceOfElementLocated(byDeleteButton,5);
        scroll2Element(ele);
        ele.click();
    }

    public void clickConfirmDelete(){
        Log.info("Click on Confirm Delete Button");
        ele = getElementPresenceOfElementLocated(byConfirmDeleteButton,5);
        scroll2Element(ele);
        ele.click();
        waitForNSeconds(2);

    }

    public void writeInvalidDescriptionField(String description){
        Log.info("Write Description in Field");
        ele = getElementPresenceOfElementLocated(byDescriptionField,5);
        ele.clear();
        ele.sendKeys(description + "  " + generateRandomString() );
    }

    public void writeDescriptionField(String description){
        Log.info("Write Description in Field");
        ele = getElementPresenceOfElementLocated(byDescriptionField,5);
        ele.clear();
        ele.sendKeys(description);
    }

    public String generateRandomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public void clickUpdateItem(){
        Log.info("Click Update Item");
        ele = getElementPresenceOfElementLocated(byUpdateItemButton,5);
        implicitWaitFor(3);
        ele.click();
    }

    public boolean isCreateItemEnabled(){
        Log.info("Validate if Create Item is Enabled");
        ele = getElementPresenceOfElementLocated(byCreateItemButton,5);
        String disableAttribute = ele.getAttribute("disabled");
        if(disableAttribute==null || disableAttribute.equals("")){
            return true ;
        } else {
            return false;
        }
    }

    public void clickCreateItemButton(){
        Log.info("Click Create Item Button");
        ele = getElementPresenceOfElementLocated(byCreateItemButton,5);
        ele.click();
    }

    public void takeScreenshot(String scenario)  {
        Log.info("Taking a screenshot current page");
            CommonUtilities.takeScreenshot(driver, this.pathScreenshots, scenario);
    }
}

