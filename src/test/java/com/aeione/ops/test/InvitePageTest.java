package com.aeione.ops.test;

import com.aeione.ops.generic.*;
import com.aeione.ops.pageactions.GenericPageActions;
import com.aeione.ops.pageactions.HomePageActions;
import com.aeione.ops.pageactions.InvitePageActions;
import com.aeione.ops.pageactions.LoginPageActions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class InvitePageTest extends TestSetUp {
    public LoginPageActions getLoginPage() throws IOException {
        return new LoginPageActions();
    }

    public HomePageActions getHomePageActions() throws IOException {
        return new HomePageActions();
    }

    public InvitePageActions getInvitePageActions() throws IOException {
        return new InvitePageActions();
    }
    public GenericPageActions getGenericPageActions() throws IOException {
        return new GenericPageActions();
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
     * Test_Caeses_For_Check that User gets an option for "Invite Friend"
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 113, enabled = true, alwaysRun = true, description = "Display of Invite Friends option ")
    public void tc_IN_01_P1_VerifyInviteFriendOptionTest() throws Exception
    {
        String LogInRange = "Login!A9:C9";

        Map<String, String> val= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        String mobile_number=val.get("UserName / Email / PhoneNumber");
        String password=val.get("Password");
        String fullname =val.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getInvitePageActions().verifyDisplayOfInviteFriendsOption("Verify Step");
    }
    /**
     * Test_Caeses_For_"Verify user able to navigate to OnePageSpotLight Page through ""Email_Link"""
     * Author:- Smita Sahoo
     */
    @MyTestNGAnnotation(name = "Smita Sahoo")
    @Test(priority = 114, enabled = true, alwaysRun = true, description = "navigate to OnePageSpotLight Page through Email link")
    public void tc_IN_03_P1_VerifyInviteFriendsThroughEmailLinkTest() throws Exception
    {
        String LogInRange = "Login!A9:C9";
        String InviteRange = "Invite!A2:B2";
        String range2 = "Invite!A10:B10";

        Map<String, String> val1= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        String mobile_number=val1.get("UserName / Email / PhoneNumber");
        String password=val1.get("Password");
        String fullname =val1.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "mobile_number, password", mobile_number, password);

        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getInvitePageActions().verifyDisplayOfInviteFriendsOption("Verify Step");
        getInvitePageActions().clickOnInviteFriendsOption("Action Step");
        getInvitePageActions().clickOnInviteFriendsTextBox("Action Step");

        Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, InviteRange);
        val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, InviteRange);
        String emailaddress = val.get("Email_Address/URL");
        String email_password=  val.get("Password");

        getInvitePageActions().enterEmailIdOnEmailAddressTextArea("Action Step", emailaddress);
        getInvitePageActions().clickOnSendInviteButton("Action Step");
        getInvitePageActions().verifyDisplayoFInvitationSentPopup("Verify Step");
        getGenericPageActions().launchNewTabAndAccessURL("Action Step",  IAutoConst.GMAIL_URL);
        getInvitePageActions().logInToGmail("Action Step","email_address , email_password",emailaddress,email_password);
    }

    @AfterMethod(dependsOnMethods = {"com.aeione.ops.generic.TestSetUp.afterMethod"})
    public void after() throws IOException {
        getHomePageActions().navigateHomePage();
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");

    }
}

