package com.aeione.ops.test;
import com.aeione.ops.generic.DriverManager;
import com.aeione.ops.generic.GoogleDriveAPI;
import com.aeione.ops.generic.GoogleSheetAPI;
import com.aeione.ops.generic.TestSetUp;
import com.aeione.ops.pageactions.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BoostConsolePageTest extends TestSetUp {
    public LoginPageActions getLoginPage() throws IOException {
        return new LoginPageActions();
    }

    public HomePageActions getHomePageActions() throws IOException {
        return new HomePageActions();
    }

    public BoostConsoleActions getBoostConsoleActions() throws IOException {
        return new BoostConsoleActions();
    }

    public PostCardActions getPostCardActions() throws IOException {
        return new PostCardActions();
    }
    public PostWithHashTagActions getPostWithHashTagActions() throws IOException {
        return new PostWithHashTagActions();
    }


    public RegistrationPageActions getRegistrationPage() throws IOException {
        return new RegistrationPageActions();
    }


    public GoogleSheetAPI sheetAPI() throws IOException {
        return new GoogleSheetAPI();
    }

    public GoogleDriveAPI dsriveAPI() throws IOException {
        return new GoogleDriveAPI();
    }



    /**
     * Test_Cases_To_Verify the boost Console under profile drop down
     * Author:- Smita Sahoo
     */

    @Test(priority = 149, enabled = true, alwaysRun = true, description = "Verify the boost Console under profile drop down")
    public void TC_BC_01_P1_verifyBoostConsoleIconInProfileDropdownTest () throws Exception
    {
        String LogInRange = "Login!A9:C9";

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        String mobile_number = val.get("UserName / Email / PhoneNumber");
        String password = val.get("Password");
        String fullname = val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getBoostConsoleActions().verifyDisplayOfBoostConsoleOption("Verify Step");
    }

    /**
     * Test_Cases_To_Verify the boosted post in the Boosted Console
     * Author:- Smita Sahoo
     */

    @Test(priority = 150, enabled = true, alwaysRun = true, description = "Verify the Boosted Post under Boosted Console")
    public void TC_BC_02_P1_verifyBoostedPostTest() throws Exception {
        String RegistrationRange = "Registration!A4:H";
        String LogInRange = "Login!A20:C20";
        String range1 = "Home page!A16:A16";
        String PostcardRange = "Postcard!A2:B2";

        String fullName = null;
        String username_B = null;
        String password_B = null;
        ArrayList<String> responseinfo = null;
        String response = null;

        Map<String, String> val1 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, RegistrationRange);
        fullName = getRegistrationPage().getFullName();
        String userName = getRegistrationPage().getUserName(val1.get("UserName"));
        String emailAddress = getRegistrationPage().getEmail(val1.get("Email Address"));
        String countryCode = val1.get("Country Code");
        String dateOfBirth = val1.get("Date of birth");
        String createPassword = getRegistrationPage().getRandomValidPassword(val1.get("Create Password"));
        String skipOtp = val1.get("Skip OTP");
        String phoneNumber = getRegistrationPage().getPhoneNumber(val1.get("Phone Number"));

        responseinfo = getRegistrationPage().mobileVerifyApi("Action Step", phoneNumber, countryCode, skipOtp);
        response = responseinfo.get(0);
        String secret = responseinfo.get(1);

        getRegistrationPage().verifyMobileApi("Verify Step", response);
        response = getRegistrationPage().mobileConfirmApi("Action Step", phoneNumber, secret, skipOtp, countryCode);
        getRegistrationPage().verifyMobileConfirmApi("Verify Step", response);

        response = getRegistrationPage().registerApi("Action & verify", fullName, userName, phoneNumber, countryCode, secret, emailAddress, dateOfBirth, createPassword, skipOtp);
        getRegistrationPage().verifyRegisterApi("Verify Step", response);

        //Update In Registration Page
        List<List<Object>> values1 = Arrays.asList(Arrays.asList(fullName,userName,emailAddress, countryCode,phoneNumber,dateOfBirth,createPassword,skipOtp));
        sheetAPI().appendRowData(TEST_DATA_GOOGLESHEET, CONSTANT_ROW, "USER_ENTERED", values1);

        //Update In LogIn Page
        List<List<Object>> values = Arrays.asList(Arrays.asList(userName, createPassword, fullName));
        sheetAPI().updateMultipleCellValues(TEST_DATA_GOOGLESHEET, LogInRange, "USER_ENTERED", values);

        val1 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username_B = val1.get("UserName / Email / PhoneNumber");
        password_B = val1.get("Password");
        fullName = val1.get("FullName");

        getLoginPage().logIn("Action Step", fullName, "valid username, password", username_B, password_B);
        getLoginPage().clickOnAddSkillsPopupCloseButton("Action Step");

        Map<String, String> val2 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range1);
        String PostVal = val2.get("Post_Text");
        val2 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, PostcardRange);
        String impressiontextVal = val2.get("Impressions");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", PostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", PostVal);
        getPostCardActions().clickOnBoostButton("Action Step");
        getPostCardActions().clickOnBoostImpressionTextArea("Action Step");
        getPostCardActions().enterImpressionOnBoostImpressionTextArea("Action Step", impressiontextVal);
        getPostCardActions().clickOnBoostButtonInBoostPopup("Action Step");
        getPostWithHashTagActions().closePaywallWindow("Action Step");
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getBoostConsoleActions().verifyDisplayOfBoostConsoleOption("Verify Step");
        getPostCardActions().clickOnBoostConsole("Action Step");
        getBoostConsoleActions().verifyDisplayOfBoostedTextPost("Verify Step", PostVal);
        //getPostCardActions().verifyContentsOfBoostedPost("Verify Step");

    }

    /**
     * Test_Cases_To_ Verify the Functionality of "Boost" Button Under Boost console
     * Author:- Smita Sahoo
     */


    @Test(priority = 151, enabled = true, alwaysRun = true, description = "Verify Boost functionality for My Posts under Boost Console")
    public void TC_BC_03_P1_verifyBoostButtonInBoostConsoleTest() throws Exception {

        String LogInRange = "Login!A20:C20";
        String HomeRange = "Home page!A16:A16";
        String PostcardRange = "Postcard!A2:B2";

        String fullName = null;
        String username_B = null;
        String password_B = null;
        dsriveAPI().downloadFileFromGoogleDrive(TEST_IMAGE_ID1);
        String imageFile = userDirPath + IMAGE_TEST_FILE1;


        Map<String, String> value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username_B = value.get("UserName / Email / PhoneNumber");
        password_B = value.get("Password");
        fullName = value.get("FullName");

        getLoginPage().logIn("Action Step", fullName, "valid username, password", username_B, password_B);

        value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomeRange);
        String PostVal = value.get("Post_Text");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().attachFile("Action Step",imageFile);
        getHomePageActions().enterTextOnMessageTextArea("Action Step", PostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", PostVal);
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getBoostConsoleActions().verifyDisplayOfBoostConsoleOption("Verify Step");

        value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, PostcardRange);
        String impressiontextVal = value.get("Impressions");

        getBoostConsoleActions().clickOnBoostConsoleOption("Action Step");
        getBoostConsoleActions().clickOnBoostConsoleMyPostsOption("Action Step");
        getBoostConsoleActions().clickOnBoostbutton("Action Step");
        getPostCardActions().clickOnBoostImpressionTextArea("Action Step");
        getPostCardActions().enterImpressionOnBoostImpressionTextArea("Action Step", impressiontextVal);
        getPostCardActions().clickOnBoostButtonInBoostPopup("Action Step");
        getPostWithHashTagActions().closePaywallWindow("Action Step");
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getPostCardActions().clickOnBoostConsole("Action Step");
        getPostCardActions().verifyDisplayOfBoostedTextPost("Verify Step", PostVal);
        getPostCardActions().verifyContentsOfBoostedPost("Verify Step");
    }

    /**
     * Test_Cases_To_Verify all the contents in a Boosted Console
     * Author:- Smita Sahoo
     */


    @Test(priority = 152, enabled = true, alwaysRun = true, description = "Verify the contents of boost Console postcards")
    public void TC_BC_04_P1_verifyBoostConsoleContentsTest() throws Exception {
        String loginrange = "Login!A20:C20";

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, loginrange);
        String mobile_number = val.get("UserName / Email / PhoneNumber");
        String password = val.get("Password");
        String fullname = val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getPostCardActions().clickOnBoostConsole("Action Step");
        getBoostConsoleActions().verifyContentsOfBoostedPost("Verify Step");

    }

    /**
     * Test_Cases_To_ Verify the minimum view(Impression) for a post from Boost Post window
     * Author:- Smita Sahoo
     */
    @Test(priority = 153, enabled = true, alwaysRun = true, description = "Verify Impression for post")
    public void TC_BC_05_P1_verifyMinimumViewForAPostFromBoostPostWindowTest() throws Exception
    {
        String loginrange = "Login!A20:C20";
        String homepagerange = "Home page!A2:B2";
        String PostcardRange = "Postcard!A8:A8";

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, loginrange);
        String mobile_number = val.get("UserName / Email / PhoneNumber");
        String password = val.get("Password");
        String fullname = val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, homepagerange);
        String textPostVal = val.get("Post_Text");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", textPostVal);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, PostcardRange);
        String impressiontextVal = val.get("Impressions");

        getPostCardActions().clickOnBoostButton("Action Step");
        getPostCardActions().clickOnBoostImpressionTextArea("Action Step");
        getBoostConsoleActions().enterImpressionOnBoostImpressionTextArea("Action Step", impressiontextVal);
        getBoostConsoleActions().clickOnBodyField("Action Step");
        getBoostConsoleActions().verifyDisplayOfSuggestionMessageInBoostPopup("Verify Step");
    }

    /**
     * Test_Cases_To_Verify the functionality of approving a Boosted post from Admin point of view
     * Author:- Smita Sahoo
     */
    @Test(priority = 154, enabled = true, alwaysRun = true, description = "Verify the approved posts in the Admin Boosts Newsfeed")
    public void TC_BC_07_P1_verifyApprovePostInAdminBoostsNewsfeedTest() throws Exception
    {
        String RegistrationRange = "Registration!A4:H";
        String LogInRange = "Login!A20:C20";
        String HomeRange = "Home page!A16:A16";
        String PostcardRange = "Postcard!A2:B2";
        String BoostedUsernameRange= "Login!A20:C20";

        String fullName = null;
        String username = null;
        String password = null;
        dsriveAPI().downloadFileFromGoogleDrive(TEST_IMAGE_ID);
        String imageFile = userDirPath + IMAGE_TEST_FILE;

        ArrayList<String> responseinfo = null;
        String response = null;

        Map<String, String> val1 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, RegistrationRange);
        fullName = getRegistrationPage().getFullName();
        String userName = getRegistrationPage().getUserName(val1.get("UserName"));
        String emailAddress = getRegistrationPage().getEmail(val1.get("Email Address"));
        String countryCode = val1.get("Country Code");
        String dateOfBirth = val1.get("Date of birth");
        String createPassword = getRegistrationPage().getRandomValidPassword(val1.get("Create Password"));
        String skipOtp = val1.get("Skip OTP");
        String phoneNumber = getRegistrationPage().getPhoneNumber(val1.get("Phone Number"));

        responseinfo = getRegistrationPage().mobileVerifyApi("Action Step", phoneNumber, countryCode, skipOtp);
        response = responseinfo.get(0);
        String secret = responseinfo.get(1);

        getRegistrationPage().verifyMobileApi("Verify Step", response);
        response = getRegistrationPage().mobileConfirmApi("Action Step", phoneNumber, secret, skipOtp, countryCode);
        getRegistrationPage().verifyMobileConfirmApi("Verify Step", response);

        response = getRegistrationPage().registerApi("Action & verify", fullName, userName, phoneNumber, countryCode, secret, emailAddress, dateOfBirth, createPassword, skipOtp);
        getRegistrationPage().verifyRegisterApi("Verify Step", response);

        //Update the credentials in Registration Page
        List<List<Object>> values1 = Arrays.asList(Arrays.asList(fullName,userName,emailAddress, countryCode,phoneNumber,dateOfBirth,createPassword,skipOtp));
        sheetAPI().appendRowData(TEST_DATA_GOOGLESHEET, CONSTANT_ROW, "USER_ENTERED", values1);

        //Update the credentials in LogIn Page
        List<List<Object>> values = Arrays.asList(Arrays.asList(userName, createPassword, fullName));
        sheetAPI().updateMultipleCellValues(TEST_DATA_GOOGLESHEET, LogInRange, "USER_ENTERED", values);


        val1 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val1.get("UserName / Email / PhoneNumber");
        password = val1.get("Password");
        fullName = val1.get("FullName");

        Map<String, String> val2 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomeRange);
        String PostVal = val2.get("Post_Text");
        val2 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, PostcardRange);
        String impressiontextVal = val2.get("Impressions");

        ///login as userA
        getLoginPage().logIn("Action Step", fullName, "valid username, password", username, password);
        getLoginPage().clickOnAddSkillsPopupCloseButton("Action Step");
        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().attachFile("Action Step",imageFile);
        getHomePageActions().enterTextOnMessageTextArea("Action Step", PostVal);
        getHomePageActions().clickOnPostButton("Action Step");

        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", PostVal);
        getPostCardActions().clickOnBoostButton("Action Step");
        getPostCardActions().clickOnBoostImpressionTextArea("Action Step");
        getPostCardActions().enterImpressionOnBoostImpressionTextArea("Action Step", impressiontextVal);
        getPostCardActions().clickOnBoostButtonInBoostPopup("Action Step");
        getPostWithHashTagActions().closePaywallWindow("Action Step");
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getPostCardActions().clickOnBoostConsole("Action Step");


        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getPostCardActions().clickOnBoostConsole("Action Step");
        getBoostConsoleActions().verifyDisplayOfBoostedTextPost("Verify Step", PostVal);
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");

        //Log_In as an Admin

        String AdminRange = "Login!A11:C11";

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, AdminRange);
        String username_B = val.get("UserName / Email / PhoneNumber");
        String password_B = val.get("Password");
        String fullname = val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", username_B, password_B);
        getHomePageActions().navigateHomePage();
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getBoostConsoleActions().verifyDisplayOfAdminOption("Verify Step");
        getBoostConsoleActions().clickOnAdminOption("Action Step");
        getBoostConsoleActions().clickOnBoostsOption("Action Step");

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, BoostedUsernameRange);
        String  boosted_post_fullname = val.get("FullName");
        System.out.println("Boosted Username:-"+boosted_post_fullname);
        getBoostConsoleActions().verifyUserNameInNewsfeed("Verify Step", boosted_post_fullname);
        getBoostConsoleActions().clickOnApproveButtonInNewsfeed("Action Step");
    }

    /**
     * Test_Cases_To_ Verify the functionality of rejecting a Boosted post from Admin point of view
     * Author:- Smita Sahoo
     */
    @Test(priority = 155, enabled = true, alwaysRun = true, description = "Verify the rejected posts in the Admin Newsfeed")
    public void TC_BC_08_P1_verifyRejectedPostInAdminNewsfeedTest() throws Exception
    {
        String LogInRange = "Login!A20:C20";
        String HomeRange = "Home page!A17:A17";
        String PostcardRange = "Postcard!A2:B2";
        String BoostedUsernameRange= "Login!A20:C20";

        String fullName = null;
        String username = null;
        String password = null;
        dsriveAPI().downloadFileFromGoogleDrive(TEST_IMAGE_ID);
        String imageFile = userDirPath + IMAGE_TEST_FILE;

        Map<String,String> value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = value.get("UserName / Email / PhoneNumber");
        password = value.get("Password");
        fullName = value.get("FullName");

        //login as user A
        getLoginPage().logIn("Action Step", fullName, "valid username, password", username, password);

        value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomeRange);
        String PostVal = value.get("Post_Text");
        value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, PostcardRange);
        String impressiontextVal = value.get("Impressions");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().attachFile("Action Step",imageFile);
        getHomePageActions().enterTextOnMessageTextArea("Action Step", PostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", PostVal);

        getPostCardActions().clickOnBoostButton("Action Step");
        getPostCardActions().clickOnBoostImpressionTextArea("Action Step");
        getPostCardActions().enterImpressionOnBoostImpressionTextArea("Action Step", impressiontextVal);
        getPostCardActions().clickOnBoostButtonInBoostPopup("Action Step");
        getPostWithHashTagActions().closePaywallWindow("Action Step");

        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getPostCardActions().clickOnBoostConsole("Action Step");
        getBoostConsoleActions().verifyDisplayOfBoostedTextPost("Verify Step", PostVal);
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");

        //Log_In as an Admin

        String AdminRange = "Login!A11:C11";
        //String BoostedUsernameRange= "Login!A20:C20";

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, AdminRange);
        String username_B = val.get("UserName / Email / PhoneNumber");
        String password_B = val.get("Password");
        String fullname = val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "valid username, password", username_B, password_B);
        getHomePageActions().navigateHomePage();
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getBoostConsoleActions().verifyDisplayOfAdminOption("Verify Step");
        getBoostConsoleActions().clickOnAdminOption("Action Step");
        getBoostConsoleActions().clickOnBoostsOption("Action Step");


        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, BoostedUsernameRange);
        String  boosted_post_fullname = val.get("FullName");
        System.out.println("Boosted Username:-"+boosted_post_fullname);
        getBoostConsoleActions().verifyUserNameInNewsfeed("Verify Step", boosted_post_fullname);

        getBoostConsoleActions().clickOnRejectButtonInNewsfeed("Action Step");
        getBoostConsoleActions().verifyDisplayOfRejectionReasonInAdminconsole("Verify Step");
        getBoostConsoleActions().clickOnReasonInRejectionPopup("Action Step");
        getBoostConsoleActions().clickOnRejectBoostButtonInRejectionPopup("Action Step");
        //getBoostConsoleActions().clickOnCancelButtonInRejectionPopup("Action Step");

        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");
/*
        Map<String,String> values = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = values.get("UserName / Email / PhoneNumber");
        password = values.get("Password");
        fullName = values.get("FullName");

        //login as user A
        getLoginPage().logIn("Action Step", fullName, "valid username, password", username, password);

        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getPostCardActions().clickOnBoostConsole("Action Step");
        getBoostConsoleActions().clickOnRejectedPostsTab("Action Step");
        getBoostConsoleActions().verifyDisplayOfBoostedTextPost("Verify Step", PostVal);*/

    }

    /**
     * Test_Cases_To_ Verify the rejected posts in the Boost console
     * Author:- Smita Sahoo
     */
    @Test(priority = 156, enabled = true, alwaysRun = true, description = "Verify the rejected posts in the Boost Console")
    public void TC_BC_09_P1_verifyRejectedPostInBoostConsoleTest() throws Exception
    {
        String BoostedUserLogInRange= "Login!A20:C20";
        String HomeRange = "Home page!A17:A17";

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, BoostedUserLogInRange);
        String username_B = val.get("UserName / Email / PhoneNumber");
        String password_B = val.get("Password");
        String fullname = val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "valid username, password", username_B, password_B);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, BoostedUserLogInRange);
        String  boosted_post_fullname = val.get("FullName");
        System.out.println("Boosted Username:-"+boosted_post_fullname);
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomeRange);
        String PostVal = val.get("Post_Text");
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getPostCardActions().clickOnBoostConsole("Action Step");
        getBoostConsoleActions().clickOnRejectedPostsTab("Action Step");
        getBoostConsoleActions().verifyUserNameInNewsfeed("Verify Step", boosted_post_fullname);
        getBoostConsoleActions().verifyDisplayOfBoostedTextPost("Verify Step", PostVal);

    }




    @AfterMethod(dependsOnMethods = {"com.aeione.ops.generic.TestSetUp.afterMethod"})
    public void after () throws IOException {
        getHomePageActions().navigateHomePage();
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");
    }


}
