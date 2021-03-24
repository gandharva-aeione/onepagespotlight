package com.aeione.ops.pageactions;

import com.aeione.ops.generic.DriverManager;
import com.aeione.ops.generic.ExtentTestManager;
import com.aeione.ops.generic.GenericFunctions;
import com.aeione.ops.pageobjects.BlueStorePageObjects;
import com.aeione.ops.pageobjects.GenericPageObjects;
import com.aeione.ops.pageobjects.HomePageObjects;
import com.aeione.ops.pageobjects.LoginPageObjects;
import com.google.inject.internal.util.$SourceProvider;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class BlueStorePageActions
{
    GenericFunctions genericfunctions;
    BlueStorePageObjects blueStorePageObjects = new BlueStorePageObjects();
    GenericPageActions genericPageActions=new GenericPageActions();

    Actions actions = null;
    Select selectdropdownOption=null;

    public BlueStorePageActions() throws IOException
    {
        genericfunctions = new GenericFunctions(DriverManager.getDriver());
        PageFactory.initElements(DriverManager.getDriver(), this);
        PageFactory.initElements(DriverManager.getDriver(), blueStorePageObjects);
        PageFactory.initElements(DriverManager.getDriver(), genericPageActions);
        actions = new Actions(DriverManager.getDriver());
    }

    /////////////////////////// Page Actions////////////////////////////////////////////////////////////////////////////

    /**
     * Click On BlueStoreLink On Newsfeed
     * @author:- Gandahrva
     * Date:- 05-03-2021
     */
    public void clickOnFeatureLinksOnNewsfeed(String... strings)
    {
        String action = strings[1];
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " ::  Click on " +" \"<b>" + action.toUpperCase() +"\"   </b>"+ " Feature Link On Newsfeed" );
        try
        {
            switch (action)
            {
                case "community" :
                    DriverManager.getDriver().findElement(By.id("newsfeed-"+action)).click();
                    ExtentTestManager.getTest().log(LogStatus.PASS, " "+ " \"Clicked on Community Link \" ");
                    break;

                case "bookmark" :
                    DriverManager.getDriver().findElement(By.id("newsfeed-"+action)).click();
                    ExtentTestManager.getTest().log(LogStatus.PASS, " "+ " \"Clicked on Bookmark Link \" ");
                    break;

                case "bluestore" :
                    DriverManager.getDriver().findElement(By.id("newsfeed-"+action)).click();
                    ExtentTestManager.getTest().log(LogStatus.PASS, " "+ " \"Clicked on BlueStore Link \" ");
                    break;
            }
        }
        catch (Throwable e)
        {
            Assert.fail("Could not click on  "+ action + "feature link" + "&"+e.getMessage()+"" );
        }
    }

    /**
     * Click On Create Button On BlueStore Landing Page
     * @author:- Gandahrva
     * Date:- 05-03-2021
     */
    public void clickOnCreateAnItemForSaleOrRentButtonOnBlueStoreLandingPage(String... strings) throws IOException
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " ::  Click on Create an item for sale or rent Button on BlueStore Landing Page");

        try
        {
            genericfunctions.waitWebDriver(4000);
            genericfunctions.waitForPageToLoad(blueStorePageObjects.crete_bluestore_button);
            genericfunctions.waitTillTheElementIsVisible(blueStorePageObjects.crete_bluestore_button);
            Assert.assertTrue(blueStorePageObjects.crete_bluestore_button.isDisplayed());
            blueStorePageObjects.crete_bluestore_button.click();
        }
        catch (Exception e)
        {
            Assert.fail(" &"+"Could not perform an action on \" Create an item for sale or rent Button \" "  + " &"+e.getMessage()+"" );
        }
    }

    /**
     * Click On Create Button On BlueStore Landing Page
     * @author:- Gandahrva
     * Date:- 05-03-2021
     */
    public void clickOnMySalesBoardButton(String... strings) throws IOException
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " ::  Click on My Sales Board Button on BlueStore Landing Page");

        try
        {
            genericfunctions.waitForPageToLoad(blueStorePageObjects.landing_page_My_Sales_Board);
            genericfunctions.waitTillTheElementIsVisible(blueStorePageObjects.landing_page_My_Sales_Board);
            Assert.assertTrue(blueStorePageObjects.landing_page_My_Sales_Board.isDisplayed());
            blueStorePageObjects.landing_page_My_Sales_Board.click();
        }
        catch (Exception e)
        {
            Assert.fail(" &"+"Could not perform an action on \" My Sales Board Button \" "  + " &"+e.getMessage()+"" );
        }

    }

    /////////////////////////// Page Verifications////////////////////////////////////////////////////////////////////////////

    /**
     * Verify Create Button On BlueStore Landing Page
     * @author:- Gandahrva
     * Date:- 05-03-2021
     */
    public void verifyCreateAnItemForSaleOrRentButtonOnBlueStoreLandingPage(String... strings) throws IOException
    {
        String page = strings[1];
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: Create an item for sale or rent Button is Displayed or Not on "
                +" \"<b>" + page.toUpperCase() +"\"   </b>"+" Page");

        try
        {
            genericfunctions.waitWebDriver(4000);
            genericfunctions.waitForPageToLoad(blueStorePageObjects.crete_bluestore_button);
            genericfunctions.waitTillTheElementIsVisible(blueStorePageObjects.crete_bluestore_button);
            Assert.assertTrue(blueStorePageObjects.crete_bluestore_button.isDisplayed());
            ExtentTestManager.getTest().log(LogStatus.PASS,   "  \" Create an item for sale or rent Button \" is displayed ");
        }
        catch (Throwable e)
        {
            Assert.fail("&" +"Could not found \"Create an item for sale or rent Button\"" + "&" + e.getMessage() + "");
        }
    }

    /**
     * Verify Display of Bluestore PopUp
     * @author:- Gandahrva
     * Date:- 05-03-2021
     */
    public void verifyDisplayOfBluStorePopUp(String... strings) throws IOException
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: BlueStore form is Displayed or Not on BlueStore Page");

        try
        {
            genericfunctions.waitWebDriver(4000);
            genericfunctions.waitTillTheElementIsVisible(blueStorePageObjects.bluestore_form);
            Assert.assertTrue(blueStorePageObjects.bluestore_form.isDisplayed());
            ExtentTestManager.getTest().log(LogStatus.PASS,   "  \" BlueStore form \" is displayed ");
        }
        catch (Throwable e)
        {
            Assert.fail("&" +"Could not found \"BlueStore form \"" + "&" + e.getMessage() + "");
        }
    }

    /**
     * Verify Display of Bluestore PopUp
     * @author:- Gandahrva
     * Date:- 05-03-2021
     */
    public void verifyCreatedBlueStorePost(String... strings)
    {
        String page = strings[1];
        String expectedTitle = strings[2].trim();
        String actualTitle = null ;
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: BlueStore Post is Displayed or Not on "
                +" \"<b>" + page.toUpperCase() +"\"   </b>"+" Page");
        try
        {
            for (WebElement webElement : blueStorePageObjects.bluestore_card_title)
            {
                actualTitle = webElement.getText().trim();
                System.out.println("Title:- "+actualTitle);
                Assert.assertTrue(actualTitle.equals(expectedTitle));
                break;
            }
            ExtentTestManager.getTest().log(LogStatus.PASS,   "  \" BlueStore Post \" is displayed ");
            ExtentTestManager.getTest().log(LogStatus.PASS, " Expected :: \" BlueStore Title\" should be displayed as "
                    + ":: \"<b>" + expectedTitle +"\"   </b> ; Actual :: \" BlueStore  Title\" is displayed as" + ":: \"<b>" + actualTitle +"\"   </b>");

        }
        catch (Throwable e)
        {
            System.out.println("Title:- "+actualTitle);
            Assert.fail("&" + "Expected :: \" BlueStore Title\" should be displayed as :: "
                    + "<b>" + expectedTitle  + "</b>"  + " ;" +
                    " Actual :: \" BlueStore Title\" is displayed as :: "  +  "<b>" + actualTitle +  "</b>"  + "&" + e.getMessage() + "");
        }
    }


    /**
     * Verify Display of Bluestore Categories
     * @author:- Gandahrva
     * Date:- 17-03-2021
     */
    public void verifyCategoriesOnBluStoreLandingPage(String... strings)
    {
        List<WebElement> categories=null;
        String[] expectedCategories=strings[1].split("\\,");
        String currentExpectedCategories= null;
        String currentActualCategories = null;

        categories = blueStorePageObjects.blueStore_Categories;

        System.out.println("Categories Size:-"+categories.size());
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + "<b>"+" BlueStore Categories are :- "+"</b>");

        try
        {
            for (int i = 0; i < categories.size(); i++)
            {

                currentExpectedCategories = expectedCategories[i].trim();
                currentActualCategories = categories.get(i).getText().trim();

                genericfunctions.waitTillTheElementIsVisible(blueStorePageObjects.blueStore_Categories.get(i));
                Assert.assertTrue(currentActualCategories.equals(currentExpectedCategories));

                ExtentTestManager.getTest().log(LogStatus.PASS, " " +   "<b>" + blueStorePageObjects.blueStore_Categories.get(i).getText() + "</b>");

            }
        }
        catch (Throwable e)
        {
            Assert.fail("&"+"Expected :: " + currentExpectedCategories + " Category should be displayed  ; Actual :: "
                    + currentActualCategories + " Category is not displayed"+"&"+e.getMessage()+"");
        }

    }


}
