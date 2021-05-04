package com.aeione.ops.test;

import com.aeione.ops.generic.*;
import com.aeione.ops.pageactions.*;
import com.aeione.ops.pageobjects.HomePageObjects;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OrganizationPageTest extends TestSetUp
{
	GenericFunctions genericfunctions;
	HomePageObjects homepageobjects = new HomePageObjects();
	HomePageObjects organizationpageobjects = new HomePageObjects();
	Actions actions = null;


	public void ProfilePageActions() throws IOException {
		genericfunctions = new GenericFunctions(DriverManager.getDriver());
		PageFactory.initElements(DriverManager.getDriver(), this);
		PageFactory.initElements(DriverManager.getDriver(), homepageobjects);
		PageFactory.initElements(DriverManager.getDriver(), organizationpageobjects);
		actions = new Actions(DriverManager.getDriver());
	}

	public LoginPageActions getLoginPage() throws IOException {
		return new LoginPageActions();
	}

	public HomePageActions getHomePageActions() throws IOException {
		return new HomePageActions();
	}

	public ProfilePageActions getProfilePageActions() throws IOException {
		return new ProfilePageActions();
	}
	public ProfileCompletionPageActions getProfileCompletionPageActions() throws IOException {
		return new ProfileCompletionPageActions();
	}

	public OrganizationPageActions getOrganizationPageActions() throws IOException {
		return new OrganizationPageActions();
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

	public PostCardActions getPostCardActions() throws IOException {
		return new PostCardActions();
	}

	/*
	 * @author:Aishwarya
	 * Description: Verify functionality of Create organization button
	 * Created on : 05-05-2020
	 */

	@Test(priority = 115, enabled = true, alwaysRun = true, description = "Verify functionality of Create organization button")
	public void TC_ORG_01_P1_VerifyFunctionalityOfCreateOrganizationButtonTest() throws Exception
	{

		String LogInRange = "Login!A15:C15";
		String RegistrationRange = "Registration!A4:H";
		String username = null;
		String password = null;
		String fullName = null;
		ArrayList<String> responseinfo = null;
		String response = null;

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

		Map<String, String> loginvalues = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
		username=loginvalues.get("UserName / Email / PhoneNumber");
		password=loginvalues.get("Password");
		fullName=loginvalues.get("FullName");

		getLoginPage().logIn("Action Step", fullName, "valid username, password", username, password);

		getLoginPage().clickOnAddSkillsPopupCloseButton("Action Step");

		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreateOrganizationButton("Action Step");
		getOrganizationPageActions().verifyNavigationToCreateOrganizationPage("Verify Step");
	}


	/*
	 * @author:Aishwarya / Gandharva
	 * Description: Verify functionality of Create organization button
	 * Created on : 05-05-2020
	 */

	@Test(priority = 116, enabled = true, alwaysRun = true, description = "Verify functionality of Create organization button")
	public void TC_ORG_02_P1_CreateAndVerifyOrganizationTest() throws Exception
	{

		String range = "Organization!A2:E2";
		String name = null;
		String type = null;
		String addSkills = null;
		String description = null;
		String location = null;

		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
		name = values.get("Name");
		type = values.get("Type");
		addSkills = values.get("Add_Skills");
		description = values.get("Description");
		location = values.get("Location");

		dsriveAPI().downloadFileFromGoogleDrive(TEST_IMAGE_ID1);
		String imageFile = userDirPath + IMAGE_TEST_FILE1;

		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreateOrganizationButton("Action Step");
		getOrganizationPageActions().enterOrganizationName("Action Step", name );
		getOrganizationPageActions().selectOrganizationType("Action Step", type);
		//getOrganizationPageActions().addSkillsToOrganization("Action Step", addSkills);
		//getOrganizationPageActions().enterSkillsTag("Action Step", addSkills.split(",")[1]);
		getOrganizationPageActions().enterOrganizationDescription("Action Step",description);
		getOrganizationPageActions().enterOrganizationLocation("Action Step", location);
		//getOrganizationPageActions().attachFile("Action Step", imageFile);
		//getProfilePageActions().clickOnApplyButtonOfEditPicturePopUp("Action Step");
		getOrganizationPageActions().clickOnCreateButton("Verify Step");
		getOrganizationPageActions().verifyNavigationToOrganizationInnerPage("Verify Step", name);
	}


	/*
	 * @author:Aishwarya
	 * Description: Verify deleting the organization
	 * Created on : 05-05-2020
	 */

	@Test(priority = 117, enabled = true, alwaysRun = true, description = "Verify functionality of Invite members icon")
	public void TC_ORG_03_P1_VerifyInviteMembersIconAsOwnerTest() throws Exception
	{
		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreatedOrganization("Action Step");
		getOrganizationPageActions().verifyDisplayOfInviteMembersOptionToTheOwner("Verify Step");
		getOrganizationPageActions().verifyWhetherInviteMembersIconIsClickable("Verify Step");
	}

	/*
	 * @author:Aishwarya
	 * Description: Verify creating Text post as owner
	 * Created on : 05-05-2020
	 */

	@Test(priority = 118, enabled = true, alwaysRun = true, description = "Verify creating Text post as owner")
	public void TC_ORG_04_P1_VerifyCreatingTextPostAsOwnerTest() throws Exception
	{
		String range = "Organization!E2:F2";
		String textPostVal = null;
		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
		textPostVal = values.get("TextPost");

		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreatedOrganization("Action Step");
		getHomePageActions().clickOnPosterTextArea("Action Step");
		getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
		getHomePageActions().clickOnPostButton("Action step");
		getProfilePageActions().verifyDisplayOfPostedText("Verify Step" , textPostVal);
	}

	/*
	 * @author:Aishwarya /Gandharva
	 * Description: Verify Deleting the uploaded post as owner
	 * Created on : 05-05-2020
	 */

	@Test(priority = 119, enabled = true, alwaysRun = true, description = "Verify Deleting the uploaded post as owner")
	public void TC_ORG_05_P1_VerifyDeletingPostAsOwnerTest() throws Exception
	{
		String range = "Organization!E2:F2";
		String textPostVal = null;
		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
		textPostVal = values.get("TextPost");

		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreatedOrganization("Action Step");
		String time = getPostCardActions().getPostActivityTime("Action Step");
		getOrganizationPageActions().clickOnPostMenuActionButton("Action Step");
		getOrganizationPageActions().clickOnDeletePostButton("Action Step");
		getOrganizationPageActions().clickOnConfirmDeletePost("Verify Step");
		getPostCardActions().verifyPostCardIsDeleted("Verify Step", time);
	}

	/*
	 * @author:Aishwarya
	 * Description: Verify Creating Restricted post as owner
	 * Created on : 09-06-2020
	 */

	@Test(priority = 120, enabled = true, alwaysRun = true, description = "Verify Creating Restricted post as owner")
	public void TC_ORG_06_P1_VerifyCreatingRestrictedPostTest() throws Exception
	{
		String range = "Organization!E2:F2";
		String textPostVal = null;
		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
		textPostVal = values.get("TextPost");

		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreatedOrganization("Action Step");
		getHomePageActions().clickOnPosterTextArea("Action Step");
		getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
		getOrganizationPageActions().enableRestrictedContent("Verify Step");
		getHomePageActions().clickOnPostButton("Action step");
		getOrganizationPageActions().verifyUncoverButtonForRestrictedContentPost("Verify Step");

	}

	/*
	 * @author:Aishwarya
	 * Description: Verify Create Post functionality for Owner
	 * Created on : 09-06-2020
	 */

	@Test(priority = 121, enabled = true, alwaysRun = true, description = "Verify Creating a post as owner")
	public void TC_ORG_07_P1_VerifyCreatePostFunctionalityForOwnerTest() throws Exception
	{
		String range = "Organization!E2:F2";
		String textPostVal = null;
		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
		textPostVal = values.get("TextPost");

		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreatedOrganization("Action Step");
		getOrganizationPageActions().verifyCreatePostOptionForOwner("verify Step");
		getHomePageActions().clickOnPosterTextArea("Action Step");
		getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
		getHomePageActions().clickOnPostButton("Action step");
		getProfilePageActions().verifyDisplayOfPostedText("Verify Step" , textPostVal);

	}

	/*
	 * @author:Aishwarya
	 * Description: Verify comment functionality for Created Post functionality as Owner
	 * Created on : 09-06-2020
	 */

	@Test(priority = 122, enabled = true, alwaysRun = true, description = "Verify \"Comment functionality\" for Created Post functionality as Owner")
	public void TC_ORG_08_P1_VerifyCommentFunctionalityForPostAsOwnerTest() throws Exception
	{
		String range = "Organization!F2:G2";
		String commentVal = null;
		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
		commentVal = values.get("Comment");
		String defaultCommentCount=null;
		
		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreatedOrganization("Action Step");
		defaultCommentCount=getOrganizationPageActions().getDefaultCommentCount("Action Step");
		getOrganizationPageActions().clickOnCommentIcon("Action Step");
		getOrganizationPageActions().enterComment("Action Step", commentVal);
		getOrganizationPageActions().clickOnSendCommentIcon("Verify Step");
		getOrganizationPageActions().verifyCommentCount("Verify Step", defaultCommentCount);
		
	}
	



	/*
	 * @author:Aishwarya
	 * Description: Verify Create #Showtimes Post functionality as Owner
	 * Created on : 11-06-2020
	 */

	/*@Test(priority = 52, enabled = true, alwaysRun = true, description = "Verify Create #Showtimes Post functionality as Owner")
	public void TC_ORG_13_P1_VerifyCreateShowtimezPostAsOwnerTest() throws Exception
	{
		String hashtagType = null;
		String title = null;
		String description = null;
		String location = null;
		String date = null;
		String time = null;

		String range = "ShowTimez!A2:F2";
		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);

		hashtagType = values.get("Action");
		title = values.get("Title");
		description = values.get("Description");
		location = values.get("Location");
		date  = values.get("Date");
		time = values.get("Time");

		getHomePageActions().clickOnTopBarDropdown("Action Step");
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreatedOrganization("Action Step");
		getOrganizationPageActions().verifyCreatePostOptionForOwner("verify Step");
		getProfilePageActions().clickOnSpecialHashtagDropdown("Action Step");
		getProfilePageActions().selectHashtag("Action Step", hashtagType);


	}*/



	/*
	 * @author:Aishwarya
	 * Description: Verify deleting the organization
	 * Created on : 05-05-2020
	 */

	@Test(priority = 123, enabled = true, alwaysRun = true, description = "Verify deleting the organization")
	public void TC_ORG_09_P1_VerifyDeleteOrganizationTest() throws Exception
	{
		String range = "Organization!A2:D2";
		String name = null;
		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, range);
		name = values.get("Name");

		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().clickOnCreatedOrganization("Action Step");
		getOrganizationPageActions().clickOnSettingsActionButton("Action Step");
		getOrganizationPageActions().clickOnDeleteOrganization("Action Step");
		getOrganizationPageActions().clickOnConfirmDeleteOrganization("Action Step");
		getHomePageActions().clickOnTopBarDropdown("Action Step");        
		getOrganizationPageActions().clickOnOrganizationLink("Action Step");
		getOrganizationPageActions().verifyDeletedOrganizationInOrganizationPage("Verify Step", name);
	}
	

	/*
	 * @author:Aishwarya
	 * Description: After method which works as pre-condition to the next test script execution
	 * Created on : 29-05-2020
	 */
	@AfterMethod(dependsOnMethods={"com.aeione.ops.generic.TestSetUp.afterMethod"})
	public void after() throws IOException
	{
		getHomePageActions().navigateHomePage();
		System.out.println("After method got executed ");
	}	


}
