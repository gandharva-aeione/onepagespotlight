package com.aeione.ops.test;

import com.aeione.ops.generic.GoogleDriveAPI;
import com.aeione.ops.generic.GoogleSheetAPI;
import com.aeione.ops.generic.MyTestNGAnnotation;
import com.aeione.ops.generic.TestSetUp;
import com.aeione.ops.pageactions.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PostCardTest extends TestSetUp {
    public LoginPageActions getLoginPage() throws IOException {
        return new LoginPageActions();
    }
    public HomePageActions getHomePageActions() throws IOException {
        return new HomePageActions();
    }
    public PostCardActions getPostCardActions() throws IOException {
        return new PostCardActions();
    }
    public PostWithHashTagActions getPostWithHashTagActions() throws IOException {
        return new PostWithHashTagActions();
    }

    public SearchActions getSearchActions() throws IOException {
        return new SearchActions();

    }

    public SettingsPageActions getSettingsActions() throws IOException {
        return new SettingsPageActions();
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
    public RegistrationPageActions getRegistrationPage() throws IOException {
        return new RegistrationPageActions();
    }


    /**
     * Test_Cases_For_Contents_of_PostCard
     * Author:- Smita Sahoo
     */


    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 52, enabled = true, alwaysRun = true, description = "Verify content of Post Card")
    public void tc_PC_1_P1_VerifyContentsOfPostCardTest() throws Exception {

        String range = "Login!A7:C7";
        String range1 = "Home page!A2:B2";
        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
        String username=val.get("UserName / Email / PhoneNumber");
        String password=val.get("Password");
        String fullname =val.get("FullName");
        val=sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range1);
        String textPostVal = val.get("Post_Text");

        getLoginPage().logIn("Action Step", fullname, "valid username, password", username, password);
        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", textPostVal);

    }

    /**
     * Test_Cases_For_Verification of the functionality of "Buy coins button" under Contribution popup
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 53, enabled = true, alwaysRun = true, description = "Verify the functionality of Buy coins button under Contribution popup")
    public void tc_PC_02_P1_VerifyBuyCoinsButtonInContributionPopupTest() throws Exception {

        String range = "Login!A9:C9";
        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
        String mobile_number = val.get("UserName / Email / PhoneNumber");
        String password = val.get("Password");
        String fullname = val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);
        getPostCardActions().clickOnContributeIcon("Action Step");
        getPostCardActions().verifyDisplayOfContributePopup("Verify Step");
        getPostCardActions().clickOnBuyCoinsButtonInContributePopup("Action Step");
        getPostCardActions().verifyDisplayOfBuyCoinsPopup("Verify Step");
        getPostCardActions().clickOnCancelButtonInBuyCoinsPopup("Action Step");

    }

    /**
     * Test_Cases_For_Verification of "yes button" is deleting the post or not in Delete page in own postcard.
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 54, enabled = true, alwaysRun = true, description = "Verify that yes button is delete the post in Delete page")
    public void tc_PC_03_P1_VerifyDeletePostCardWithYesButtonTest() throws Exception {

        String range = "Login!A9:C9";
        String range1 = "Home page!A2:B2";

        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
        String mobile_number=val.get("UserName / Email / PhoneNumber");
        String password=val.get("Password");
        String fullname=val.get("FullName");
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range1);
        String textPostVal = val.get("Post_Text");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);
        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", textPostVal);
        String time = getPostCardActions().getPostActivityTime("Action Step");
        getPostCardActions().clickOnMeatBallMenu("Action Step");
        getPostCardActions().clickOnDeleteOption("ActionStep");
        getPostCardActions().clickOnYesButton("Action Step");
        getPostCardActions().verifyPostCardIsDeleted("Verify Step", time);

    }

    /**
     * Test_Cases_For_Verification of "boost button" in own post card
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 55, enabled = true, alwaysRun = true, description = "Verify the functionality of boost button in post card")
    public void tc_PC_04_P1_VerifyBoostButtonInPostCardTest() throws Exception {
        String range = "Login!A9:C9";
        String range1 = "Home page!A2:B2";
        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
        String mobile_number=val.get("UserName / Email / PhoneNumber");
        String password=val.get("Password");
        String fullname =val.get("FullName");
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range1);
        String textPostVal = val.get("Post_Text");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);
        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", textPostVal);
        getPostCardActions().clickOnBoostButtonOnPostCard("Action Step");
        getPostCardActions().verifyDisplayOfBoostPopup("Verify Step");
        getPostCardActions().clickOnCancelButtonInBoostPostPopup("Action Step");

    }

    /**
     * Test_Cases_For_Verification of Like List in post card
     * Author:- Gandharva
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 56, enabled = true, alwaysRun = true, description = "Verify the Like List in post card")
    public void tc_PC_05_P1_VerifyLikeListInPostCardTest() throws Exception
    {
        String LogInRange = "Login!A9:C9";
        String LogInRange2 = "Login!A6:C6";
        String HomePageRange = "Home page!A10:A10";
        String TopBarDropDownRange = "TopBarDropDownActions!A8:B8";
        String PostCardRange = "Postcard!A2:B2";

        dsriveAPI().downloadFileFromGoogleDrive(TEST_IMAGE_ID1);
        String imageFile = userDirPath + IMAGE_TEST_FILE1;

        String username = null;
        String password = null;
        String fullname =null;
        String textPostVal = null;
        String action = null;

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomePageRange);
        textPostVal = val.get("Post_Text");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().attachFile("Action Step", imageFile);
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action step");
        getHomePageActions().verifyDispayOfCreatedImagePost("Verify Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step",textPostVal);

        getHomePageActions().clickOnTopBarDropdown("Action Step");
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, TopBarDropDownRange);
        action = val.get("Actions");
        getSettingsActions().clickOnDropDownActions("Action Step",action);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange2);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);
        String SearchUserRange = "Login!A9:B9";
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, SearchUserRange);
        String searchUserName = val.get("UserName / Email / PhoneNumber");

        getSearchActions().enterUsernameOnSearchTextFieldAndSelectUserName("Action Step", searchUserName);
        getSearchActions().clickOnProfileTab("Action Step");
        getSettingsActions().clickOnUserFullName("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step",textPostVal);
        getPostCardActions().clickOnLikeButton("Action Step");

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, PostCardRange);
        action = val.get("Actions");
        getPostCardActions().clickOnPostCardActvityAction("Verify Step",action);

        getHomePageActions().clickOnTopBarDropdown("Action Step");
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, TopBarDropDownRange);
        action = val.get("Actions");
        getSettingsActions().clickOnDropDownActions("Action Step",action);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val.get("UserName / Email / PhoneNumber");
        password = val.get("Password");
        fullname = val.get("FullName");

        getLoginPage().logIn("Action Step",fullname,  "valid username, password",username, password);
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step",textPostVal);
        getPostCardActions().clickOnLikeCountDetailsOnPostCard("Action Step");
        getPostCardActions().verifyDisplayOfLikePopUp("Verify Step");
        getPostCardActions().verifyDisplayOfPostLikedUserList("Verify Step");

    }

    /**
     * Test_Cases_For_Verification  of "share button" in own post card
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 57, enabled = true, alwaysRun = true, description = "Verify that functionality of share button in post card")
    public void tc_PC_08_P2_VerifyShareButtonInPostCardTest() throws Exception {
        String range = "Login!A9:C9";
        String range1 = "Home page!A2:B2";

        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
        String mobile_number=val.get("UserName / Email / PhoneNumber");
        String password=val.get("Password");
        String fullname =val.get("FullName");
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range1);
        String textPostVal = val.get("Post_Text");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);
        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", textPostVal);
        getPostCardActions().clickOnShareButton("Action Step");
        getPostCardActions().clickOnRepostButton("Action Step");
        getPostCardActions().verifyDisplayOfRepostPopup("Verify Step");
        getPostCardActions().clickOnCancelButtonInRepostPopup("Action Step");
        getPostCardActions().clickOnLikeButton("Action Step");


    }

    /**
     * Test_Cases_For_Verification  of "like button" in own post card
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 58, enabled = true, alwaysRun = true, description = "Verify the like button function in post card")
    public void tc_PC_09_P2_VerifyLikeButtonInPostcardTest() throws Exception {
        String range = "Login!A9:C9";
        String range1 = "Home page!A2:B2";

        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
        String mobile_number=val.get("UserName / Email / PhoneNumber");
        String password=val.get("Password");
        String fullname =val.get("FullName");
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range1);
        String textPostVal = val.get("Post_Text");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);
        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", textPostVal);
        String before_like_count = getPostCardActions().verifyBeforePostLikeCount("Verify Step");
        getPostCardActions().clickOnLikeButton("Action Step");
        getPostCardActions().verifyAfterPostLikeCount("verify step" , before_like_count);

    }

    /**
     * Test_Cases_For_Verification  of "comment button" in own post card
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 59, enabled = true, alwaysRun = true, description = "Verify commenting on post")
    public void tc_PC_10_P2_VerifyCommentOnPostTest() throws Exception {
        String range = "Login!A9:C9";
        String range1 = "Home page!A11:A11";
        String range2 = "Home page!A12:A12";

        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
        String mobile_number=val.get("UserName / Email / PhoneNumber");
        String password=val.get("Password");
        String fullname =val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range1);
        String textPostVal = val.get("Post_Text");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", textPostVal);
        getPostCardActions().clickOnCommentButton("Action Step");
        getPostCardActions().verifyDisplayOfPostCardCommentSection("Verify Step");

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range2);
        String textCommentVal = val.get("Post_Text");

        getPostCardActions().enterTextOnCommentTextArea("Action Step", textCommentVal);
        getPostCardActions().clickOnCommentSectionPostButton("Action Step");
        getPostCardActions().verifyDisplayOfAddedCommentToPost("Verify Step", textCommentVal);

    }


    /**
     * Test_Cases_For_Verification  of like, Comment, Share, view and contribute count on the post card of follower user
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
   @Test(priority = 60, enabled = true, alwaysRun = true, description = "Verify Contents Of Followed User Postcard ")
    public void tc_PC_11_P1_VerifyContentsOfFollowedUserPostcardTest() throws Exception {

        String range = "Login!A7:C7";
        String range1 = "Login!A9:C9";
        String range2 = "Home page!A2:B2";
        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
        String username_A=val.get("UserName / Email / PhoneNumber");
        String password_A=val.get("Password");
        String fullname =val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "valid mobile_no, password", username_A, password_A);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range2);
        String textPostVal = val.get("Post_Text");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action step");
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range1);
        String mobile_number=val.get("UserName / Email / PhoneNumber");
        String password=val.get("Password");
        fullname =val.get("FullName");
        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);

        getHomePageActions().verifyDisplayOfFollowedUserPost("Verify Step", textPostVal);
        getPostCardActions().verifyContentsOfFollowedUserPostCard("Verify Step");
        String before_like_count = getPostCardActions().verifyBeforePostLikeCount("Action Step");
        getPostCardActions().clickOnLikeButton("Action Step");
        getPostCardActions().verifyAfterPostLikeCount("verify step", before_like_count);
        getPostCardActions().clickOnCommentButton("Action Step");
        getPostCardActions().verifyDisplayOfPostCardCommentSection("Verify Step");
        getPostCardActions().enterTextOnCommentTextArea("Action Step", textPostVal);
        getPostCardActions().clickOnCommentSectionPostButton("Action Step");
        getPostCardActions().verifyDisplayOfAddedCommentToPost("Verify Step", textPostVal);
        String before_share_count = getPostCardActions().verifyBeforePostShareCount("verify Step");
        getPostCardActions().clickOnShareButton("Action Step");
        getPostCardActions().clickOnRepostButton("Action Step");
        getPostCardActions().verifyDisplayOfRepostPopup("Verify Step");
        getPostCardActions().clickOnShareButtonInRepostPopup("Action Step");
        getPostCardActions().verifyAfterPostShareCount("verify step", before_share_count);

    }

    /**
     * Test_Cases_For_Verification of "the functionality of boost from post card"
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 61, enabled = true, alwaysRun = true, description = "Verify boost from post card")
    public void tc_PC_13_P1_VerifyBoostPostCardTest() throws Exception {

        String HomeRange = "Home page!A13:A13";
        String PostcardRange = "Postcard!A2:B2";
        String RegistrationRange = "Registration!A4:H";
        String LogInRange = "Login!A22:C22";

        String fullName = null;
        String username = null;
        String password = null;
        ArrayList<String> responseinfo = null;
        String response = null;

        dsriveAPI().downloadFileFromGoogleDrive(TEST_IMAGE_ID1);
        String imageFile = userDirPath + IMAGE_TEST_FILE1;

        Map<String, String> val1 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, RegistrationRange);
       // fullName = getRegistrationPage().getFullName();
        fullName = val1.get("FullName");
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

        response= getRegistrationPage().registerApi("Action & verify", fullName,userName,phoneNumber,countryCode,secret, emailAddress,dateOfBirth,createPassword,skipOtp);
        getRegistrationPage().verifyRegisterApi("Verify Step", response);

        //Update In Registration Page
        List<List<Object>> values1 = Arrays.asList(Arrays.asList(fullName,userName,emailAddress, countryCode,phoneNumber,dateOfBirth,createPassword,skipOtp));
        sheetAPI().appendRowData(TEST_DATA_GOOGLESHEET, CONSTANT_ROW, "USER_ENTERED", values1);

        //Update In LogIn Page
        List<List<Object>> values = Arrays.asList(Arrays.asList(userName, createPassword, fullName));
        sheetAPI().updateMultipleCellValues(TEST_DATA_GOOGLESHEET, LogInRange, "USER_ENTERED", values);

        val1 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = val1.get("UserName / Email / PhoneNumber");
        password = val1.get("Password");
        fullName = val1.get("FullName");

        Map<String, String> val=sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, HomeRange);
        String textPostVal = val.get("Post_Text");

        val=sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, PostcardRange);
        String impressiontextVal = val.get("Impressions");

        getLoginPage().logIn("Action Step", fullName, "valid username, password", username, password);
        getLoginPage().clickOnAddSkillsPopupCloseButton("Action Step");
        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().attachFile("Action Step",imageFile);
        getHomePageActions().verifyDisplayOfUploadedThumbnail("Verify Step");
        getHomePageActions().clickOnPostButton("Action Step");

        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", textPostVal);
        getPostCardActions().clickOnBoostButtonOnPostCard("Action Step");
        getPostCardActions().clickOnBoostImpressionTextArea("Action Step");
        getPostCardActions().enterImpressionOnBoostImpressionTextArea("Action Step", impressiontextVal);
        getPostCardActions().clickOnBoostButtonOnPopUp("Action Step");
        getPostWithHashTagActions().closePaywallWindow("Action Step");
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getPostCardActions().clickOnBoostConsole("Action Step");
        getPostCardActions().verifyDisplayOfBoostedTextPost("Verify Step", textPostVal);
        getPostCardActions().verifyContentsOfBoostedPost("Verify Step");

    }

    /**
     * Test_Cases_For_Verification of the Repost- post functionality
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 62, enabled = true, alwaysRun = true, description = "Verify the Repost- post functionality")
    public void tc_PC_14_P1_VerifyRepostPostTest() throws Exception {

        String range = "Login!A9:C9";
        String range1 = "Home page!A1:A1";
        String range2 = "Home page!A8:A8";

        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
        String mobile_number=val.get("UserName / Email / PhoneNumber");
        String password=val.get("Password");
        String fullname =val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range1);
        String textPostVal = val.get("Post_Text");

        getHomePageActions().clickOnPosterTextArea("Action Step");
        getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
        getHomePageActions().clickOnPostButton("Action Step");
        getHomePageActions().verifyDisplayOfCreatedTextPost("Verify Step", textPostVal);

        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range2);
        String textRepostVal = val.get("Post_Text");

        getPostCardActions().clickOnShareButton("Action Step");
        getPostCardActions().clickOnRepostButton("Action Step");
        getPostCardActions().verifyDisplayOfRepostPopup("Verify Step");
        getPostCardActions().enterTextOnRepostTextArea("Action Step", textRepostVal);
        getPostCardActions().clickOnShareButtonInRepostPopup("Action Step");
        getPostCardActions().verifyDisplayOfRepostedTextPost("Verify Step",textRepostVal);
    }




    @AfterMethod(dependsOnMethods = {"com.aeione.ops.generic.TestSetUp.afterMethod"})
    public void after() throws IOException {
        getHomePageActions().navigateHomePage();
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");

    }
}
