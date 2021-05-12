package com.aeione.ops.test;

import com.aeione.ops.generic.GoogleDriveAPI;
import com.aeione.ops.generic.GoogleSheetAPI;
import com.aeione.ops.generic.MyTestNGAnnotation;
import com.aeione.ops.generic.TestSetUp;
import com.aeione.ops.pageactions.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class CommunityPageTest extends TestSetUp
{
	public LoginPageActions getLoginPage() throws IOException {
		return new LoginPageActions();
	}

	public HomePageActions getHomePageActions() throws IOException {
		return new HomePageActions();
	}

	public EventPageActions getEventPageActions() throws IOException {
		return new EventPageActions();
	}

	public CommunityPageActions getCommunityPageActions() throws IOException {
		return new CommunityPageActions();
	}

	public ProfilePageActions getProfilePageActions() throws IOException {
		return new ProfilePageActions();
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

	/*
	 * @author:Aishwarya
	 * Description: Verify creating a public community
	 * Created on : 05-05-2020
	 */
	@MyTestNGAnnotation(name = "Aishwarya")
	@Test(priority =139,enabled = true, alwaysRun = true, description = "Verify Create Public Community")
	public void TC_CM_02_P1_VerifyCreatingPublicCommunityTest() throws Exception
	{

		String LogInRange = "Login!A15:C15";
		String username = null;
		String password = null;
		String fullname = null;

		dsriveAPI().downloadFileFromGoogleDrive(TEST_PUBLIC_COMMUNITY_IMAGE_ID);
		String imageFile = userDirPath + IMAGE_PUBLIC_COMMUNITY_TEST_FILE;

		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
		username=values.get("UserName / Email / PhoneNumber");
		password=values.get("Password");
		fullname=values.get("FullName");

		getLoginPage().logIn("Action Step" ,fullname , "valid registered Username & password",username, password);

		String CommunityRange = "Community!A2:C2";
		String communityName = null;
		String description = null;
		String hashtag = null;

		values = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, CommunityRange);
		communityName = values.get("Community_Name");
		description = values.get("Description");
		hashtag = values.get("Hashtag");

		getEventPageActions().clickOnGroupIconFromHeader("Action Step");
		getCommunityPageActions().clickOnCreateCommunityFromGroupLink("Action Step");
		getCommunityPageActions().enterCommunityName("Action Step", communityName);
		getCommunityPageActions().enterCommunityDescription("Action Step", description);
		getCommunityPageActions().enterCommunityHashtag("Action Step", hashtag);
		getCommunityPageActions().attachFile("Action Step",imageFile);
		getProfilePageActions().clickOnApplyButtonOfEditPicturePopUp("Action Step");
		getCommunityPageActions().clickOnCreatebutton("Verify Step");
		getCommunityPageActions().verifyCommunityInnerPage("Verify Step", communityName );
	}

	/*
	 * @author:Aishwarya
	 * Description: Verify Invite members by creating private community
	 * Created on : 05-05-2020
	 */
	@MyTestNGAnnotation(name = "Aishwarya")
	@Test(priority = 140,enabled = true, alwaysRun = true, description = "Verify Invite members by creating private community")
	public void TC_CM_03_P1_InviteMembersInPrivateCommunityTest() throws Exception
	{
		String CommunityRange = "Community!A3:E3";
		String communityName=null;
		String description=null;
		String hashtag=null;
		String userName_Invite=null;

		Map<String, String> val1 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, CommunityRange);
		communityName=val1.get("Community_Name");
		description=val1.get("Description");
		hashtag=val1.get("Hashtag");
		userName_Invite=val1.get("Invite_Member");

		dsriveAPI().downloadFileFromGoogleDrive(TEST_PRIVATE_COMMUNITY_IMAGE_ID);
		String imageFile = userDirPath + IMAGE_PRIVATE_COMMUNITY_TEST_FILE;

		getEventPageActions().clickOnGroupIconFromHeader("Action Step");
		getCommunityPageActions().clickOnCreateCommunityFromGroupLink("Action Step");
		getCommunityPageActions().clickOnPrivateCommunityType("Action Type");
		getCommunityPageActions().enterCommunityName("Action Step", communityName);
		getCommunityPageActions().enterCommunityDescription("Action Step", description);
		//getCommunityPageActions().enterCommunityHashtag("Action Step", hashtag);
		getCommunityPageActions().attachFile("Action Step",imageFile);
		getProfilePageActions().clickOnApplyButtonOfEditPicturePopUp("Action Step");
		getCommunityPageActions().clickOnCreatebutton("Verify Step");
		getCommunityPageActions().verifyCommunityInnerPage("Verify Step", communityName );
		getCommunityPageActions().clickOnPlusIconToInviteMembers("Action Step");
		getCommunityPageActions().clickOnSearchMembersTextField("Action Step");
		getCommunityPageActions().enterUsernameInSearchField("Action Step", userName_Invite);
		getCommunityPageActions().clickOnInvitebutton("Verify Step");
	}

	/*
	 * @author:Aishwarya
	 * Description: Verify converting a public community to private community 
	 * Created on : 05-05-2020
	 */

	@MyTestNGAnnotation(name = "Aishwarya")
	@Test(priority = 141,enabled = true, alwaysRun = true, description = "Verify converting a public community to private community")
	public void TC_CM_04_P1_VerifyConvertingPublicCommunityToPrivateTest() throws Exception
	{
		String CommunityRange = "Community!A5:E5";

		dsriveAPI().downloadFileFromGoogleDrive(TEST_PUBLIC_COMMUNITY_IMAGE_ID);
		String imageFile = userDirPath + IMAGE_PUBLIC_COMMUNITY_TEST_FILE;

		Map<String, String> val1 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, CommunityRange);
		String communityName=val1.get("Community_Name");
		String description=val1.get("Description");
		String hashtag=val1.get("Hashtag");

		getEventPageActions().clickOnGroupIconFromHeader("Action Step");
		getCommunityPageActions().clickOnCreateCommunityFromGroupLink("Action Step");
		getCommunityPageActions().enterCommunityName("Action Step", communityName);
		getCommunityPageActions().enterCommunityDescription("Action Step", description);
		getCommunityPageActions().enterCommunityHashtag("Action Step", hashtag);
		getCommunityPageActions().attachFile("Action Step",imageFile);
		getProfilePageActions().clickOnApplyButtonOfEditPicturePopUp("Action Step");
		getCommunityPageActions().clickOnCreatebutton("Verify Step");
		getCommunityPageActions().verifyCommunityInnerPage("Verify Step", communityName );

		/*getCommunityPageActions().clickOnCommunityTabFromHomePage("Action Step");
		getCommunityPageActions().clickOnCommunityFromCommunitiesYouManageList("Action Step");*/
		
		getCommunityPageActions().clickOnSettingsActionButton("Action Step");
		getCommunityPageActions().clickOnMakeClosedOption("Action Step");
		getCommunityPageActions().verifyConvertingCommunityFromPublicToPrivate("verify Step");
	}




	/*
	 * @author:Aishwarya
	 * Description: Verify deleting a community
	 * Created on : 01-06-2020
	 */

	@MyTestNGAnnotation(name = "Aishwarya")
	@Test(priority = 142,enabled = true, alwaysRun = true, description = "Verify \"Deleting\" the community as Admin")
	public void tc_CM_05_P1_VerifyDeletingCommunityAsAdminTest() throws Exception
	{
		getCommunityPageActions().clickOnCommunityTabFromHomePage("Action Step");
		getCommunityPageActions().clickOnCommunityFromCommunitiesYouManageList("Action Step");
		String community=getCommunityPageActions().getCommunityName("Action Step");
		getCommunityPageActions().clickOnSettingsActionButton("Action Step");
		getCommunityPageActions().verifyDeleteCommunityOption("Verify Step");
		getCommunityPageActions().clickOnDeleteCommunity("Action Step");
		getCommunityPageActions().clickOnConfirmDeleteCommunity("Verify step");
		getCommunityPageActions().verifyDeletedCommunityInCommunityList("Verify Step", community);
	}

	/*
	 * @author:Aishwarya
	 * Description: Verify creating a post in community as admin
	 * Created on : 01-06-2020
	 */
	@MyTestNGAnnotation(name = "Aishwarya")
	@Test(priority = 143,enabled = true, alwaysRun = true, description = "Verify \"Create Post\" in community as an admin")
	public void tc_CM_06_P1_VerifyCreatingPostInCommunityAsAdminTest() throws Exception
	{
		String CommunityRange = "Community!D2:E2";
		String textPostVal = null;

		Map<String, String> val = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, CommunityRange);
		textPostVal= val.get("Text_Post");

		getCommunityPageActions().clickOnCommunityTabFromHomePage("Action Step");
		getCommunityPageActions().clickOnCommunityFromCommunitiesYouManageList("Action Step");

		getHomePageActions().clickOnPosterTextArea("Action Step");
		getHomePageActions().enterTextOnMessageTextArea("Action Step", textPostVal);
		/*getHomePageActions().attachFile("Action Step",imageFile);
		getHomePageActions().verifyDisplayOfUploadedThumbnail("Verify Step");*/
		getHomePageActions().clickOnPostButton("Action step");
		getProfilePageActions().verifyDisplayOfPostedText("Verify Step" , textPostVal);
	}

	/*
	 * @author:Aishwarya
	 * Description: Verify changing community Settings
	 * Created on : 02-06-2020
	 */
	@MyTestNGAnnotation(name = "Aishwarya")
	@Test(priority = 144,enabled = true, alwaysRun = true, description = "Verify changing \"Community Settings \" as admin")
	public void tc_CM_07_P1_VerifyChangingCommunitySettingsTest() throws Exception
	{
		String CommunityRange = "Community!A7:B7";

		dsriveAPI().downloadFileFromGoogleDrive(TEST_COMMUNITY_POST_IMAGE_ID);
		String imageFile = userDirPath + IMAGE_COMMUNITY_POST_TEST_FILE;

		Map<String, String> val1 = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, CommunityRange);
		String communityName=val1.get("Community_Name");
		String description=val1.get("Description");
		String hashtag=val1.get("Hashtag");

		getEventPageActions().clickOnGroupIconFromHeader("Action Step");
		getCommunityPageActions().clickOnCreateCommunityFromGroupLink("Action Step");
		getCommunityPageActions().enterCommunityName("Action Step", communityName);
		getCommunityPageActions().enterCommunityDescription("Action Step", description);
		getCommunityPageActions().enterCommunityHashtag("Action Step", hashtag);
		getCommunityPageActions().attachFile("Action Step",imageFile);
		getProfilePageActions().clickOnApplyButtonOfEditPicturePopUp("Action Step");
		getCommunityPageActions().clickOnCreatebutton("Verify Step");
		getCommunityPageActions().verifyCommunityInnerPage("Verify Step", communityName );

		/*getCommunityPageActions().clickOnCommunityTabFromHomePage("Action Step");
		getCommunityPageActions().clickOnCommunityFromCommunitiesYouManageList("Action Step");*/

		getCommunityPageActions().clickOnSettingsActionButton("Action Step");
		getCommunityPageActions().clickOnDisableGathering("Verify Step");
		getCommunityPageActions().clickOnMakeClosedOption("Action Step");
		getCommunityPageActions().clickOnDisableNotifications("Verify Step");
	}

	/*
	 * @author:Aishwarya
	 * Description: Verify Updated Settings of community as admin
	 * Created on : 02-06-2020
	 */
	@MyTestNGAnnotation(name = "Aishwarya")
	@Test(priority = 145,enabled = true, alwaysRun = true, description = "Verify \" Edit \"  community as admin")
	public void tc_CM_08_P1_VerifyEditCommunityTest() throws Exception
	{
		String CommunityRange = "Community!A2:B2";

		dsriveAPI().downloadFileFromGoogleDrive(TEST_COMMUNITY_POST_IMAGE_ID);
		String imageFile = userDirPath + IMAGE_COMMUNITY_POST_TEST_FILE;

		Map<String, String> values = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, CommunityRange);
		String communityName=values.get("Community_Name");
		String description=values.get("Description");
		String hashtag=values.get("Hashtag");

		getEventPageActions().clickOnGroupIconFromHeader("Action Step");
		getCommunityPageActions().clickOnCreateCommunityFromGroupLink("Action Step");
		getCommunityPageActions().enterCommunityName("Action Step", communityName);
		getCommunityPageActions().enterCommunityDescription("Action Step", description);
		getCommunityPageActions().enterCommunityHashtag("Action Step", hashtag);
		getCommunityPageActions().attachFile("Action Step",imageFile);
		getProfilePageActions().clickOnApplyButtonOfEditPicturePopUp("Action Step");
		getCommunityPageActions().clickOnCreatebutton("Verify Step");
		getCommunityPageActions().verifyCommunityInnerPage("Verify Step", communityName );

		String CommunityUpdateRange = "Community!A6:B6";

		values = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, CommunityUpdateRange);
		communityName=values.get("Community_Name");
		description=values.get("Description");

		/*getCommunityPageActions().clickOnCommunityTabFromHomePage("Action Step");
		getCommunityPageActions().clickOnCommunityFromCommunitiesYouManageList("Action Step");*/

		getCommunityPageActions().clickOnSettingsActionButton("Action Step");
		//getCommunityPageActions().verifyConvertingCommunityFromPublicToPrivate("Verify Step");
		getCommunityPageActions().clickOnEditCommunity("Action Step");
		getCommunityPageActions().enterCommunityName("Action Step", communityName);
		getCommunityPageActions().enterCommunityDescription("Action Step", description);
		getCommunityPageActions().clickOnCreatebutton("Verify Step");
		getCommunityPageActions().verifyCommunityInnerPage("Verify Step", communityName );
	}


	/*
	 * @author:Aishwarya
	 * Description: Verify joining a community
	 * Created on : 02-06-2020
	*/
	@MyTestNGAnnotation(name = "Aishwarya")
	@Test(priority = 146,enabled = true, alwaysRun = true, description = "Verify \"Join community\" in community ")
	public void tc_CM_09_P1_VerifyJoiningCommunityTest() throws Exception
	{
		getHomePageActions().clickOnTopBarDropdown("Action Step");
		getHomePageActions().clickOnSignOut("Action Step");

		String LogInRange = "Login!A14:C14";
		String username = null;
		String password = null;
		String fullname = null;

		Map<String, String> values= sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
		username=values.get("UserName / Email / PhoneNumber");
		password=values.get("Password");
		fullname=values.get("FullName");

		getLoginPage().logIn("Action Step" ,fullname , "valid registered Username & password",username, password);

		getCommunityPageActions().clickOnCommunityTabFromHomePage("Action Step");
		getCommunityPageActions().verifyJoiningCommunity("Verify Step");
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
