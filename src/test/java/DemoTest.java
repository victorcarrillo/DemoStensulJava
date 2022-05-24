
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import utilities.Log;

public class DemoTest extends BaseTest {
    
    private final HomePage homePage = new HomePage(getDriver());

    /** TODO : Uncomment when the bug is fixed**/ // @Test(priority = 1)
    public void createAnItem(){
        Log.info("Executing TEST CASE 1");
        homePage.setItemPhoto("strangerThingsPhoto.jpeg");
        homePage.writeDescriptionField(env.NEW_VALID_ITEM_DESCRIPTION);
        homePage.clickCreateItemButton();
        Assert.assertTrue(homePage.isElementInList(env.NEW_VALID_ITEM_DESCRIPTION));
    }

    @Test(priority = 2)
    public void editAnItem(){
        Log.info("Executing TEST CASE 2");
        String currentDescription = homePage.getCurrentItemDescription("7");
        homePage.clickEditButton("7");
        homePage.writeDescriptionField(env.NEW_VALID_ITEM_DESCRIPTION);
        homePage.clickUpdateItem();
        String newDescription = homePage.getCurrentItemDescription("7");
        Assert.assertNotEquals(currentDescription,newDescription);
    }

    @Test(priority = 3)
    public void deleteAnItem(){
        Log.info("Executing TEST CASE 3");
        int initialNumberItems = homePage.getNumberOfItemsPresent();
        homePage.clickDeleteButton("2");
        homePage.clickConfirmDelete();

        int newNumberItems = homePage.getNumberOfItemsPresent();
        Assert.assertNotEquals(initialNumberItems, newNumberItems);
    }

    @Test(priority = 4)
    public void verifyDescriptionFieldMaxLength(){
        Log.info("Executing TEST CASE 4");
        homePage.writeDescriptionField(env.ACCEPTED_LENGTH_DESCRIPTION);
        Assert.assertTrue(homePage.isCreateItemEnabled());
        homePage.writeDescriptionField(env.NOT_ACCEPTED_LENGTH_DESCRIPTION);
        Assert.assertFalse(homePage.isCreateItemEnabled());
    }

    @Test(priority = 5)
    public void checkElementInListAvailable() {
        Log.info("Executing TEST CASE 5");
        Assert.assertTrue(homePage.isElementInList(env.TITLE_TO_VERIFY));
    }

}
