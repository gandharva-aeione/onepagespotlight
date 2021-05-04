package com.aeione.ops.pageactions;
import com.aeione.ops.generic.DriverManager;
import com.aeione.ops.generic.ExtentTestManager;
import com.aeione.ops.generic.GenericFunctions;
import com.aeione.ops.pageobjects.HomePageObjects;
import com.aeione.ops.pageobjects.BoostConsolePageObject;
import com.aeione.ops.pageobjects.LoginPageObjects;
import com.aeione.ops.pageobjects.PostCardObjects;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;


public class BoostConsoleActions {
    GenericFunctions genericfunctions;
    LoginPageObjects loginpageobjects = new LoginPageObjects();
    HomePageObjects homepageobjects = new HomePageObjects();
    PostCardObjects postcardobjects = new PostCardObjects();
    BoostConsolePageObject boostConsolePageObjects = new BoostConsolePageObject();
    Actions actions = null;

    public BoostConsoleActions() throws IOException {
        genericfunctions = new GenericFunctions(DriverManager.getDriver());
        PageFactory.initElements(DriverManager.getDriver(), this);
        PageFactory.initElements(DriverManager.getDriver(), loginpageobjects);
        PageFactory.initElements(DriverManager.getDriver(), homepageobjects);
        PageFactory.initElements(DriverManager.getDriver(), postcardobjects);
        PageFactory.initElements(DriverManager.getDriver(), boostConsolePageObjects);

        actions = new Actions(DriverManager.getDriver());

    }
    /////////////////////////// Page Actions ////////////////////////////////

    public void clickOnBoostConsoleOption(String... strings)
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On BoostConsoleOption ");
        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boostconsole_link);
            boostConsolePageObjects.boostconsole_link.click();

        } catch (Exception e) {
            Assert.fail("Could not perform action on \"BoostConsole Option\"" + "&" + e.getMessage() + "");

        }
    }

    public void clickOnBoostConsoleMyPostsOption(String... strings)
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On BoostConsoleOption ");
        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boostconsole_my_post.get(0));
            boostConsolePageObjects.boostconsole_my_post.get(0).click();

        } catch (Exception e) {
            Assert.fail("Could not perform action on \"BoostConsole Option\"" + "&" + e.getMessage() + "");

        }
    }

    public void clickOnAdminOption(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On Admin Option ");
        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.admin_link);
            boostConsolePageObjects.admin_link.click();

        } catch (Exception e) {
            Assert.fail("Could not perform action on \"Admin Option\"" + "&" + e.getMessage() + "");

        }
    }

    public void clickOnBoostsOption(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On Boost Option ");
        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boosts_option);
            boostConsolePageObjects.boosts_option.click();

        } catch (Exception e) {
            Assert.fail("Could not perform action on \"Boosts Option\"" + "&" + e.getMessage() + "");

        }
    }


    public void clickOnBoostbutton(String... strings)
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On Boost button ");
        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.my_post_boost_button);
            boostConsolePageObjects.my_post_boost_button.click();

        } catch (Exception e) {
            Assert.fail("Could not perform action on \"BoostConsole Option\"" + "&" + e.getMessage() + "");

        }
    }



    public void clickOnBodyField(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On Boost popup body field ");
        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boost_popup_header);
            boostConsolePageObjects.boost_popup_header.click();

        } catch (Exception e) {
            Assert.fail("Could not perform action on \"Boost popup body field\"" + "&" + e.getMessage() + "");

        }
    }

    public void enterImpressionOnBoostImpressionTextArea(String... strings)
    {
        String impression = strings[1];
        try
        {
            ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: Enter Impression on Boost Impression TextArea as :: "+ " \"<b>" + impression +"\"   </b>");
            genericfunctions.waitTillTheElementIsVisible(postcardobjects.boost_impression_text_section);
            postcardobjects.boost_impression_text_section.sendKeys(impression);
            Thread.sleep(1000);
            postcardobjects.boost_impression_text_section.click();

        }
        catch (Exception e)
        {
            Assert.fail("Could not perform action on \"Text of Boost Impression\""+"&"+e.getMessage()+"" );
        }
    }

    public void clickOnRejectButtonInNewsfeed(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On Reject Button In Newsfeed ");
        List<WebElement> boostedPostInNewsfeed = null;
        try {
            boostedPostInNewsfeed = boostConsolePageObjects.newsfeed_postcard;
            int recentBoostPost = (boostedPostInNewsfeed.size()-1);
            genericfunctions.scrollToElement(boostedPostInNewsfeed.get(recentBoostPost));

            WebElement rejectpost = boostedPostInNewsfeed.get(recentBoostPost).findElement(By.xpath("//*[contains(@class,'reject-button')]"));
            genericfunctions.clickOnElementUsingJavascript(rejectpost);


        } catch (Exception e) {
            Assert.fail("Could not perform action on \"Reject Button In Newsfeed\"" + "&" + e.getMessage() + "");

        }
    }

    public void clickOnApproveButtonInNewsfeed(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On Approve Button In Newsfeed ");
        List<WebElement> boostedPostInNewsfeed = null;
        try {
            boostedPostInNewsfeed = boostConsolePageObjects.newsfeed_postcard;
            int recentBoostPost = boostedPostInNewsfeed.size()-1;
            genericfunctions.scrollToElement(boostedPostInNewsfeed.get(recentBoostPost));
            WebElement acceptpost = boostedPostInNewsfeed.get(recentBoostPost).findElement(By.xpath("//*[contains(@class,'accept-button')]"));
            genericfunctions.clickOnElementUsingJavascript(acceptpost);

        } catch (Exception e) {
            Assert.fail("Could not perform action on \"Reject Button In Newsfeed\"" + "&" + e.getMessage() + "");

        }
    }

    public void clickOnReasonInRejectionPopup(String... strings)
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: Click On Valid Reasons in Rejection Popup ");
        String actual_Reason = null;
        try
        {
            genericfunctions.waitWebDriver(10000);
            actual_Reason = boostConsolePageObjects.rejection_reasons.getText();
           // genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.rejection_reasons);
            actions.moveToElement(boostConsolePageObjects.rejection_reasons).doubleClick(boostConsolePageObjects.rejection_reasons).build().perform();
            //genericfunctions.waitTillTheElementIsVisibleAndClickable(boostConsolePageObjects.rejection_reasons);
           //genericfunctions.clickOnElementUsingJavascript(boostConsolePageObjects.rejection_reasons);

            ExtentTestManager.getTest().log(LogStatus.PASS, "" + " Selected Valid Reason for Boost Rejection as :: "+"\"<b>" + actual_Reason +"\" </b>");

        }
        catch (Exception e)
        {
            Assert.fail("Could not perform action on \"Valid Reasons\"" + "&" + e.getMessage() + "");

        }
    }

    public void clickOnRejectBoostButtonInRejectionPopup(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On Reject Boost Button in Rejection Popup ");
        try {

            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.rejection_boost_button);
            boostConsolePageObjects.rejection_boost_button.click();

        } catch (Exception e) {
            Assert.fail( "&" + "Could not perform action on \"Reject Boost Button\"" + "&" + e.getMessage() + "");

        }
    }


    public void clickOnCancelButtonInRejectionPopup(String... strings)
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: click On Cancel Button in Rejection Popup ");
        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.rejection_cancel_button);
            boostConsolePageObjects.rejection_cancel_button.click();

        } catch (Exception e) {
            Assert.fail("Could not perform action on \"Cancel Button\"" + "&" + e.getMessage() + "");

        }
    }

    public void clickOnRejectedPostsTab(String... strings)
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: Click on Rejected Boost Posts Tab Under Boost Console");
        try
        {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.rejected_posts_module);
            boostConsolePageObjects.rejected_posts_module.click();
        }
        catch (Exception  e)
        {
            Assert.fail("Could not perform action on \" Rejected Posts Tab\""+"&"+e.getMessage()+"" );
        }
    }

    //////////////////////////// Page Verifications /////////////////////////////////////

    public void verifyContentsOfBoostedPost(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: All the  contents in Bookmarked post section with following assertion :");

        verifyDisplayOfBoostedUserProfileName();
        verifyDispalyOfBoostedUserProfileImage();
        verifyDisplayOfBoostedButton();
        verifyDisplayOfTargetCount();
        verifyDisplayOfViews();
        verifyDisplayOfPostActivityTime();

    }

    public void verifyDispalyOfBoostedUserProfileImage(String... strings) {

        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boosted_post_userimage.get(0));
            Assert.assertTrue(boostConsolePageObjects.boosted_post_userimage.get(0).isDisplayed());
            ExtentTestManager.getTest().log(LogStatus.PASS, " " + " \" User profile Image\" is displaying");

        } catch (Exception e) {
            Assert.fail("Expected :: \" User profile Image\" should be displayed  ; Actual ::  \" User profile Image\" is not displayed" + "&" + e.getMessage() + "");
        }

    }
    public void verifyDisplayOfBoostedButton(String... strings) {

        try {
            genericfunctions.waitTillTheElementIsVisible(postcardobjects.boosted_post_boosted_button.get(0));
            Assert.assertTrue(postcardobjects.boosted_post_boosted_button.get(0).isDisplayed());
            ExtentTestManager.getTest().log(LogStatus.PASS, " " + " \" BoostedButton\" is displaying");

        } catch (Exception e) {
            Assert.fail("Expected :: \" BoostedButton\" should be displayed  ; Actual ::  \" BoostedButton\" is not displayed" + "&" + e.getMessage() + "");
        }

    }
    public String verifyDisplayOfTargetCount(String... strings) {

        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.target_option.get(0));
            Assert.assertTrue(boostConsolePageObjects.target_option.get(0).isDisplayed());
            String targetCounts = boostConsolePageObjects.target_option.get(0).getText();
            ExtentTestManager.getTest().log(LogStatus.PASS, " " + " \" Target Count\" is displaying");
            ExtentTestManager.getTest().log(LogStatus.INFO, " " + " \"Target Count are \" " + "<b>" + targetCounts  + "</b>");
            return targetCounts;

        } catch (Exception e) {
            Assert.fail("Expected :: \" Target Count\" should be displayed  ; Actual ::  \" Target Count\" is not displayed" + "&" + e.getMessage() + "");
        }
        return null;
    }

    public String verifyDisplayOfViews(String... strings) {

        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.views_so_far.get(0));
            Assert.assertTrue(boostConsolePageObjects.views_so_far.get(0).isDisplayed());
            String viewsCounts = boostConsolePageObjects.views_so_far.get(0).getText();
            ExtentTestManager.getTest().log(LogStatus.PASS, " " + " \" views Counts \" is displaying");
            ExtentTestManager.getTest().log(LogStatus.INFO, " " + " \"Target Count are \" " + "<b>" + viewsCounts + "</b>");
            return viewsCounts;
        } catch (Exception e) {
            Assert.fail("Expected :: \" User profile Image\" should be displayed  ; Actual ::  \" User profile Image\" is not displayed" + "&" + e.getMessage() + "");
        }
        return null;
    }

    public void verifyDisplayOfPostActivityTime(String... strings) {

        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boosted_post_activity_time.get(0));
            Assert.assertTrue(boostConsolePageObjects.boosted_post_activity_time.get(0).isDisplayed());
            ExtentTestManager.getTest().log(LogStatus.PASS, " " + "\" post Activity time\" is displaying");

        } catch (Exception e) {
            Assert.fail("Expected :: \" post Activity time\" should be displayed  ; Actual ::  \"post Activity time\" is not displayed" + "&" + e.getMessage() + "");
        }

    }


    public void verifyDisplayOfBoostConsoleOption(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: Boost Console Option is displaying or not :");

        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boostconsole_link);
            Assert.assertTrue(boostConsolePageObjects.boostconsole_link.isDisplayed());
            ExtentTestManager.getTest().log(LogStatus.PASS, "\"Boost Console\" is displaying");
        } catch (Exception e) {
            Assert.fail("Expected :: \"Boost Console\"  should be displayed ; Actual :: \"Boost Console\"  is not displaying" + "&" + e.getMessage() + "");
        }
    }
    public void verifyDisplayOfAdminOption(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: Boost Console Option is displaying or not :");

        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.admin_link);
            Assert.assertTrue(boostConsolePageObjects.admin_link.isDisplayed());
            ExtentTestManager.getTest().log(LogStatus.PASS, "\"Boost Console\" is displaying");
        } catch (Exception e) {
            Assert.fail("Expected :: \"Boost Console\"  should be displayed ; Actual :: \"Boost Console\"  is not displaying" + "&" + e.getMessage() + "");
        }
    }


    public void verifyDisplayOfBoostedUserProfileName(String... strings) {

        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boosted_post_username.get(0));
            Assert.assertTrue(boostConsolePageObjects.boosted_post_username.get(0).isDisplayed());
            ExtentTestManager.getTest().log(LogStatus.PASS, " " + " \" User profile Name\" is displaying");

        } catch (Exception e) {
            Assert.fail("Expected :: \" User profile Name\" should be displayed  ; Actual ::  \" User profile Name\" is not displayed" + "&" + e.getMessage() + "");
        }

    }

    public void verifyDisplayOfBoostedTextPost(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " ::  Boosted Post is Displayed or not Under Boosted Console");
        String expectedTextPost = strings[1].trim();
        String actualTextPost = null;
        genericfunctions.waitWebDriver(2000);

        try {
            genericfunctions.waitForPageToLoad(boostConsolePageObjects.boosted_text.get(0));
            actualTextPost = boostConsolePageObjects.boosted_text.get(0).getText().trim();
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boosted_text.get(0));
            Assert.assertTrue(actualTextPost.equals(expectedTextPost));
            ExtentTestManager.getTest().log(LogStatus.PASS, " Expected :: \" Boosted Created Text Post\" should be displayed as :: "
                    + " \"<b>" + expectedTextPost + "\"   </b> ; Actual :: \" Boosted Created Text Post\" is displayed as :: " + " \"<b>" + actualTextPost + "\"   </b>");
        } catch (Throwable e) {
            String actualException = e.getClass().getName();

            switch (actualException) {
                case "java.lang.NoSuchElementException":
                    Assert.fail(actualException + "Expected :: \" Boosted Created Post \" should be displayed ;" +
                            " Actual :: \"Boosted Created Post \" is not displayed" + "&" + e.getMessage() + "");
                    break;
                case "java.lang.AssertionError":
                    Assert.fail(actualException + " " + " Expected :: \" Boosted Created Text Post\" should be displayed as :: "
                            + " \"<b>" + expectedTextPost + "\"   </b> ; Actual :: \" Boosted Created Text Post\" is displayed as :: " + " \"<b>" + actualTextPost + "\"   </b>" + "&" + e.getMessage() + "");
                    break;
                default:
                    Assert.fail(actualException + "&" + e.getMessage() + "");
            }
        }
    }

    public void verifyDisplayOfSuggestionMessageInBoostPopup(String... strings) {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: Suggestion Message In BoostPopup is displaying or not ");
        String exp_msg = "You must boost between 100 and 5000 views.";
        String act_msg = null;
        try {
            Thread.sleep(2000);

            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boost_post_popup_message);
            act_msg = boostConsolePageObjects.boost_post_popup_message.getText();
            Assert.assertEquals(exp_msg, act_msg);
            Assert.assertTrue(boostConsolePageObjects.boost_post_popup_message.isDisplayed());

            ExtentTestManager.getTest().log(LogStatus.PASS, "\"Suggested Message \" is displayed as :: " + " \"<b>" + act_msg);
        } catch (Exception e) {
            Assert.fail("Expected :: \"Suggestion Message\"  should be displayed ; Actual :: \"Suggestion Message\"  is not displaying" + "&" + e.getMessage() + "");
        }
    }

    public void  verifyUserNameInNewsfeed(String... strings)
    {
        ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " :: Post with username in Newsfeed is displaying or not :");
        String expectedUserName = strings[1].trim();

        System.out.println("User:-"+expectedUserName);

        String actualUserName ;

        try {
            genericfunctions.waitWebDriver(2000);
            genericfunctions.scrollTillEndOfPage();
            actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
            int postcard= boostConsolePageObjects.boosted_postcard.size();
            String name= boostConsolePageObjects.boosted_postcard.get(postcard-1).getText();

            String name1[]=name.split("\\s");
            System.out.println("name:-"+name1[0]);
            //System.out.println("Actual:-"+name.split(" "));

            // genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.boosted_username);
            //actualUserName = boostConsolePageObjects.boosted_username.getText().trim();
            actualUserName = name1[0].trim();
            Assert.assertTrue(actualUserName.equals(expectedUserName));
            ExtentTestManager.getTest().log(LogStatus.PASS, "\"Post with username in Newsfeed\" is displaying");

        } catch (Exception e) {
            Assert.fail("Expected :: \" Post with username in Newsfeed\"  should be displayed ; Actual :: \" Post with username in Newsfeed\"  is not displaying" + "&" + e.getMessage() + "");
        }
    }

    public void verifyDisplayOfRejectionReasonInAdminconsole(String... strings)
    {

        try {
            genericfunctions.waitTillTheElementIsVisible(boostConsolePageObjects.rejection_popupdisplay);
            Assert.assertTrue(boostConsolePageObjects.rejection_popupdisplay.isDisplayed());
            ExtentTestManager.getTest().log(LogStatus.PASS, " " + " \" Rejection Reason In Adminconsole\" is displaying");

        }
        catch (Exception e)
        {
            Assert.fail("Expected :: \" Rejection Reason In Adminconsole\" should be displayed  ; Actual ::  \" Rejection Reason In Adminconsole\" is not displayed" + "&" + e.getMessage() + "");
        }

    }


}
