
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;

public class DemoTest extends BaseTest {
    
    private final HomePage homePage = new HomePage(getDriver());

    /** TODO : Uncomment when the bug is fixed**/ // @Test(priority = 1)
    public void createAnItem(){
        homePage.setItemPhoto("strangerThingsPhoto.jpeg");
        homePage.writeDescriptionField(env.NEW_VALID_ITEM_DESCRIPTION);
        homePage.clickCreateItemButton();
        Assert.assertTrue(homePage.isElementInList(env.NEW_VALID_ITEM_DESCRIPTION));
    }

    @Test(priority = 2)
    public void editAnItem(){
        String currentDescription = homePage.getCurrentItemDescription("7");
        homePage.clickEditButton("7");
        homePage.writeDescriptionField(env.NEW_VALID_ITEM_DESCRIPTION);
        homePage.clickUpdateItem();
        String newDescription = homePage.getCurrentItemDescription("7");
        Assert.assertNotEquals(currentDescription,newDescription);
    }

    @Test(priority = 3)
    public void deleteAnItem(){
        int initialNumberItems = homePage.getNumberOfItemsPresent();
        homePage.clickDeleteButton("2");
        homePage.clickConfirmDelete();

        int newNumberItems = homePage.getNumberOfItemsPresent();
        Assert.assertNotEquals(initialNumberItems, newNumberItems);
    }

    @Test(priority = 4)
    public void verifyDescriptionFieldMaxLength(){
        homePage.writeDescriptionField(env.ACCEPTED_LENGTH_DESCRIPTION);
        Assert.assertTrue(homePage.isCreateItemEnabled());
        homePage.writeDescriptionField(env.NOT_ACCEPTED_LENGTH_DESCRIPTION);
        Assert.assertFalse(homePage.isCreateItemEnabled());
    }

    @Test(priority = 5)
    public void checkElementInListAvailable() {
        Assert.assertTrue(homePage.isElementInList(env.TITLE_TO_VERIFY));
    }

}
