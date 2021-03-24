package com.aeione.ops.test;

import com.aeione.ops.generic.GoogleDriveAPI;
import com.aeione.ops.generic.GoogleSheetAPI;
import com.aeione.ops.generic.TestSetUp;
import com.aeione.ops.pageactions.HomePageActions;
import com.aeione.ops.pageactions.LoginPageActions;
import com.aeione.ops.pageactions.PostWithHashTagActions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class HomePageTest extends TestSetUp {

    public LoginPageActions getLoginPage() throws IOException {
        return new LoginPageActions();
    }

    public HomePageActions getHomePageActions() throws IOException {
        return new HomePageActions();
    }

    public PostWithHashTagActions getPostWithHashTagActions() throws IOException {
        return new PostWithHashTagActions();
    }

    public GoogleSheetAPI sheetAPI() throws IOException
    {
        GoogleSheetAPI.getSheetsService();
        return new GoogleSheetAPI();
    }

    public GoogleDriveAPI dsriveAPI() throws IOException {

        GoogleDriveAPI.getDriveService();
        return new GoogleDriveAPI();

    }

    /**
     * Test_Caeses_For_Verify_Contents_Of_Home_Page
     * Author:- Gandahrva
     */
    @Test(priority = 11, enabled = true, alwaysRun = true, description = "Verify all the contents in Home Page")
    public void TC_HM_01_P1_ContentsOfHomePageTest() throws Exception
    {

        String LogInRange = "Login!A6:C6";

        String username = null;
        String password = null;
        String fullname =null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "valid username, password",username, password);
        getHomePageActions().verifyContentsOfHomePageTests("Verify Step");
    }

    /**
     * Test_Caeses_For_Verify_Contents_Of_Home_Page_Profile_Card
     * Author:- Gandahrva
     */
    @Test(priority = 12, enabled = true, alwaysRun = true, description = "Verify for PROFILE CARD")
    public void TC_HM_02_P1_ContentsOfProfileCardInHomePageTest() throws Exception
    {
        String LogInRange = "Login!A6:C6";
        String username = null;
        String password = null;
        String fullname =null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);
        getHomePageActions().verifyContentsOfProfileCardInHomePage("Verify Step");
    }

    /**
     * Test_Caeses_For_Verify_Contents_Of_Home_Page_Followed_User_Post
     * Author:- Gandahrva
     */
    @Test(priority = 13, enabled = true, alwaysRun = true, description = "Verify for Followed User Post")
    public void TC_HM_03_P1_VerifyFollowedUserPostTest() throws Exception
    {

        // User A
        String LogInRange = "Login!A7:C7";
        String HomePageRange = "Home page!A4:A4";
        String textPostVal = null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        String username = val.get("UserName / Email / PhoneNumber");
        String password = val.get("Password");
        String fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange);
        textPostVal = val.get("Post_Text");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action step");

        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");

        //User B
        LogInRange = "Login!A6:C6";
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password ",username, password);
        getHomePageActions().verifyDisplayOfFollowedUserPost("Verify Step",textPostVal);

    }

    /**
     * Test_Caeses_For_Verify_Contents_Of_Home_Page_Published_Blog
     * Author:- Gandahrva
     */
    @Test(priority = 14, enabled = true, alwaysRun = true, description = "Verify for Published Blog")
    public void TC_HM_04_P1_VerifyPublishedBlogTest() throws Exception
    {
        String LogInRange = "Login!A6:C6";
        String BlogRange = "Blog!A4:B4";

        dsriveAPI().downloadFileFromGoogleDrive(TEST_IMAGE_ID1);
        String imageFile = userDirPath + IMAGE_TEST_FILE1;

        String username = null;
        String password = null;
        String fullname =null;

        String blogTitleVal = null;
        String blogName= null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, BlogRange);
        blogTitleVal = val.get("Post_Text");
        blogName = val.get("Post_Text");
        String blogError = val.get("Error");

        getHomePageActions().clickOnBlogIcon("Action Step");
        getHomePageActions().enterBlogTitle("Action Step",blogTitleVal);
        getHomePageActions().clickOnPreviewPublishButton("Action Step",blogError);
        getHomePageActions().attachFile("Action Step", imageFile);
        getHomePageActions().clickOnPublishNowButton("Action Step");
        getHomePageActions().verifyPublishedBlogInnerPage("Verify Step",blogTitleVal);
        getHomePageActions().clickOnHomeTopBar("Action Step");
        getHomePageActions().verifyDisplayOfPublishedBlogOnHomeNewsfeed("Verify Step",blogName);
    }


    /**
     * Test_Caeses_For_Verify_Contents_Of_Home_Page_Suggested_List
     * Author:- Gandahrva
     */
    @Test(priority = 15, enabled = true, alwaysRun = true, description = " Verify for Suggestion List")
    public void TC_HM_05_P1_ContentsOfSuggestedListTest() throws Exception
    {
        String LogInRange = "Login!A7:C7";
        String username = null;
        String password = null;
        String fullname =null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);
        getHomePageActions().verifySuggestedWidget("Verify Step ");
        getHomePageActions().verifyContentsOfSuggestionSection("verify step");
    }

    /**
     * Test_Caeses_For_Verify_Contents_Of_Home_Page_Follow_User
     * Author:- Gandahrva
     */
    @Test(priority = 16, enabled = true, alwaysRun = true, description = "verify for Follow Button")
    public void TC_HM_06_P1_VerifyFollowUserButtonTest() throws Exception
    {
        String LogInRange = "Login!A7:C7";
        String username = null;
        String password = null;
        String fullname =null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);

        getHomePageActions().verifySuggestedWidget("Verify Step ");
        String exceptedFollowedUser=getHomePageActions().clickOnFollowButton("Action Step");
        getHomePageActions().verifyFollowedUser("Verify Step",exceptedFollowedUser);
        getHomePageActions().verifyDisplayOfUnfolloButton("Verify Step");
    }

    /**
     * Test_Caeses_For_Create_Post_And_Verification
     * Author:- Gandahrva
     */
    @Test(priority = 17, enabled = true, alwaysRun = true, description = "create a post")
    public void TC_HM_07_P1_VerifyCreatePostAreaTest() throws Exception
    {
        String LogInRange = "Login!A6:C6";
        String HomePageRange = "Home page!A7:A7";
        String username = null;
        String password = null;
        String fullname =null;
        dsriveAPI().downloadFileFromGoogleDrive(TEST_IMAGE_ID1);
        String imageFile = userDirPath + IMAGE_TEST_FILE1;
        String textPostVal = null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange);
        textPostVal= val.get("Post_Text");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().attachFile("Action Step",imageFile);
        getHomePageActions().verifyDisplayOfUploadedThumbnail("Verify Step");
        getHomePageActions().clickOnPostButton("Action step");
        getHomePageActions().verifyDispayOfCreatedImagePost("Verify Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step",textPostVal);
    }


    /**
     * Test_Caeses_For_Opportunity_Widget
     * Author:- Gandahrva
     */
    @Test(priority = 18, enabled = true, alwaysRun = true, description = "create a post")
    public void TC_HM_08_P1_VerifyOpportunityWidgetTest() throws Exception
    {
        String LogInRange = "Login!A6:C6";
        String OpportunityRange = "Opportunity!A2:D2";
        String username = null;
        String password = null;
        String fullname =null;
        String title=null;
        String description=null;
        String location=null;
        String category=null;
        dsriveAPI().downloadFileFromGoogleDrive(TEST_IMAGE_ID1);
        String imageFile = userDirPath + IMAGE_TEST_FILE1;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);

        val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, OpportunityRange);
        title=val.get("Opportunity Title");
        description=val.get("Description");
        location=val.get("Location");
        category= val.get("category");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().clickOnPostMenuDropdown("Action Step");
        getHomePageActions().clickOnOpportunityHashTag("Action Step");
        //getHomePageActions().clickOnSelectCategoryDropdown("Action Step");
        getHomePageActions().selectCategory("Action Step",category);
        getHomePageActions().enterOpportunityTitle("Action Step",title);
        getHomePageActions().enterOpportunityDescription("Action Step", description);
        getHomePageActions().enterOpportunityLocation("Action Step",location);
        getHomePageActions().attachFile("Action Step",imageFile);
        getHomePageActions().verifyDisplayOfOpportunityThumbnail("Verify Step");
        getHomePageActions().clickOnPostOpportunityButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedOpportunityOnWidget("Verify Step");

    }

    /**
     * Test_Caeses_For_Messanger_PopUp_Verification
     * Author:- Gandahrva
     */
    @Test(priority = 19, enabled = true, alwaysRun = true, description = "create a post")
    public void TC_HM_09_P1_VerifyMessangerPopUpTest() throws Exception
    {
        String LogInRange = "Login!A6:C6";
        String username = null;
        String password = null;
        String fullname =null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);
        getHomePageActions().verifyDisplayOfMessengerPopUp("Verify Step");
        getHomePageActions().clickOnMessengerPopUp("Action Step");
        getHomePageActions().verifyDispalyOfMessengerPopUpContents("Verify Step");
    }

   /* *
     * Test_Caeses_For_Home Page Filters
     * * Date:- 08-09-2020
     * Author:- Gandahrva
*/
    @Test(priority = 20, enabled = true, alwaysRun = true, description = "Home Page Filters")
    public void TC_HM_10_P1_VerifyHomePageFiltersTest() throws Exception
    {
        String LogInRange = "Login!A6:C6";
        String HomePageRange = "Home page!A2:B2";
        String username = null;
        String password = null;
        String fullname =null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);
        getHomePageActions().verifyDisplayOfHomePageFiltersWidget("Verify Step");

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange);
        String tabs= val.get("Filters");

        getHomePageActions().verifyDisplayOfHomePageFiltersTabs(tabs,"Verify Step");
    }

/*
   *
     * Test_Caeses_For_Home Page Filters_Posts
     * * Date:- 08-09-2020
     * Author:- Gandahrva
*/

    @Test(priority = 21, enabled = true, alwaysRun = true, description = "Home Page Bluestore Filter Posts")
    public void TC_HM_11_P1_VerifyBlueStorePostOnBlueStoreTabAndAllPostsTabTest() throws Exception
    {
        String LogInRange = "Login!A6:C6";
        String HomePageRange = "Home page!A3:C3";
        String HomePageRange1 = "Home page!A2:C2";
        String BlueStoreRange = "BlueStore!A8:I8";

        dsriveAPI().downloadFileFromGoogleDrive(TEST_STORE_IMAGE_ID);
        String imageFile = userDirPath + IMAGE_STORE_TEST_FILE;

        String username = null;
        String password = null;
        String fullname =null;
        String tab = null;
        String action = null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);
        getHomePageActions().verifyDisplayOfHomePageFiltersWidget("Verify Step");

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, BlueStoreRange);
        action = val.get("Action");
        String title = val.get("Title");
        String description = val.get("Description_Values");
        String units = val.get("No. Units");
        String regularprice = val.get("Regular_Price");
        String salePrice = val.get("Sale_Price");
        String location = val.get("Location");
        String Category = val.get("Category");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().clickOnPostMenuDropdown("Action Step");
        getPostWithHashTagActions().selectHashTagFromDropdownList("Action Step",action);
        getHomePageActions().attachFile("Action Step",imageFile);
        getPostWithHashTagActions().verifyDisplayOfUploadedBlueStoreThumbnail("Verify Step");
        getPostWithHashTagActions().enterBlueStoreTitle("Action Step",title);
        getPostWithHashTagActions().enterBlueStoreDescription("Action Step",description);
        getPostWithHashTagActions().enterBlueStoreLocation("Action Step",location);
        getPostWithHashTagActions().selectBlueStoreCategory("Action Step",Category);
        getPostWithHashTagActions().enterBlueStoreUnits("Action Step",units);
        getPostWithHashTagActions().enterBlueStoreRegularPrice("Action Step",regularprice);
        getPostWithHashTagActions().enterBlueStoreSalePrice("Action Step",salePrice);
        getPostWithHashTagActions().clickOnBluestoreSubmitButton("Action Step");

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange1);
        tab = val.get("Tab");
        getHomePageActions().clickOnTabs("Action Step",tab);
        getPostWithHashTagActions().verifyDisplayOfCreatedBluestorePostOnFeed("Verify Step",title,tab);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange);
        tab = val.get("Tab");
        getHomePageActions().clickOnTabs("Action Step",tab);
        getPostWithHashTagActions().verifyDisplayOfCreatedBluestorePostOnFeed("Verify Step",title,tab);

        /*val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange);
        tab = val.get("Tab");
        getHomePageActions().clickOnTabs("Action Step",tab);
        getHomePageActions().verifyHighlightedTab("Verify Tab",tab);
        getHomePageActions().verifyDisplayOfPostUnderRespectiveFilter("Verify Step");*/
    }

    /**
     * Test_Caeses_For_Home Page Filters_Posts
     * * Date:- 09-09-2020
     * Author:- Gandahrva
*/
    @Test(priority = 22, enabled = true, alwaysRun = true, description = "Home Page Opportunity Filter Posts")
    public void TC_HM_12_P1_VerifyOpportunityPostOnOpportunityTabAndAllPostsTabTest() throws Exception
    {
        String LogInRange = "Login!A6:C6";
        String HomePageRange = "Home page!A4:C4";
        String HomePageRange1 = "Home page!A2:C2";
        String OpportunityRange = "Opportunity!A5:D5";
        String username = null;
        String password = null;
        String fullname =null;
        String title=null;
        String description=null;
        String location=null;
        String category=null;
        String tab = null;

        dsriveAPI().downloadFileFromGoogleDrive(TEST_OPPORTUNITY_IMAGE_ID);
        String imageFile = userDirPath + IMAGE_OPPORTUNITY_TEST_FILE;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);

        val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, OpportunityRange);
        title=val.get("Opportunity Title");
        description=val.get("Description");
        location=val.get("Location");
        category= val.get("category");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().clickOnPostMenuDropdown("Action Step");
        getHomePageActions().clickOnOpportunityHashTag("Action Step");
        //getHomePageActions().clickOnSelectCategoryDropdown("Action Step");
        getHomePageActions().selectCategory("Action Step",category);
        getHomePageActions().enterOpportunityTitle("Action Step",title);
        getHomePageActions().enterOpportunityDescription("Action Step", description);
        getHomePageActions().enterOpportunityLocation("Action Step",location);
        getHomePageActions().attachFile("Action Step",imageFile);
        getHomePageActions().verifyDisplayOfOpportunityThumbnail("Verify Step");
        getHomePageActions().clickOnPostOpportunityButton("Action Step");

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange1);
        tab = val.get("Tab");
        getHomePageActions().clickOnTabs("Action Step",tab);
        getPostWithHashTagActions().verifyDisplayOfOpportunityPostCardOnFeed("Verify Step",tab);
        getPostWithHashTagActions().verifyDisplayOfCreatedOpportunityPostOnFeed("Verify Step",title,tab);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange);
        tab = val.get("Tab");
        getHomePageActions().clickOnTabs("Action Step",tab);
        getPostWithHashTagActions().verifyDisplayOfOpportunityPostCardOnFeed("Verify Step",tab);
        getPostWithHashTagActions().verifyDisplayOfCreatedOpportunityPostOnFeed("Verify Step",title,tab);

    }

  /*  *
     * Test_Caeses_For_Home Page Filters_Showtimez_Posts
     * * Date:- 09-09-2020
     * Author:- Gandahrva*/

    @Test(priority = 23, enabled = true, alwaysRun = true, description = "Home Page Showtimez Filter Posts")
    public void TC_HM_13_P1_VerifyShowtimezPostOnShowtimezTabAndAllPostsTabTest() throws Exception
    {
        String LogInRange = "Login!A6:C6";
        String HomePageRange = "Home page!A5:C5";
        String HomePageRange1 = "Home page!A2:C2";
        String ShowTimezRange = "ShowTimez!A6:F6";
        String username = null;
        String password = null;
        String fullName = null;
        String action = null;
        String tab =null;
        dsriveAPI().downloadFileFromGoogleDrive(TEST_SHOWTIMEZ_IMAGE_ID);
        String imageFile = userDirPath + IMAGE_SHOWTIMEZ_TEST_FILE;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullName = val.get("FullName");

        getLoginPage().logIn("Action Step",fullName, "Valid username,password",username, password);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, ShowTimezRange);
        action = val.get("Action");
        String title = val.get("Title");
        String description = val.get("Description");
        String location = val.get("Location");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 15);
        Date upcomingDate = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(upcomingDate);

        String time = val.get("Time");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().clickOnPostMenuDropdown("Action Step");
        getPostWithHashTagActions().selectHashTagFromDropdownList("Action Step",action);
        getHomePageActions().attachFile("Action Step",imageFile);
        getPostWithHashTagActions().verifyDisplayOfUploadedShowtimezThumbnail("Verify Step");
        getPostWithHashTagActions().enterShowTimezTitle("Action Step",title);
        getPostWithHashTagActions().enterShowTimezDescription("Action Step",description);
        getPostWithHashTagActions().enterShowTimezLocation("Action Step",location);
        getPostWithHashTagActions().enterShowTimezDate("Action Step",strDate);
        getPostWithHashTagActions().enterShowTimezTime("Action Step",time);
        getPostWithHashTagActions().clickOnShowTimezSubmitButton("Action Step");

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange1);
        tab = val.get("Tab");
        getHomePageActions().clickOnTabs("Action Step",tab);
        getPostWithHashTagActions().verifyDisplayOfCreatedShowTimezPostTitleOnFeed("Verify Step",title,tab);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange);
        tab = val.get("Tab");
        getHomePageActions().clickOnTabs("Action Step",tab);
        getPostWithHashTagActions().verifyDisplayOfCreatedShowTimezPostTitleOnFeed("Verify Step",title,tab);
    }

    @AfterMethod(dependsOnMethods = {"com.aeione.ops.generic.TestSetUp.afterMethod"})
    public void after() throws IOException
    {
        getHomePageActions().navigateHomePage();
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");

    }

}