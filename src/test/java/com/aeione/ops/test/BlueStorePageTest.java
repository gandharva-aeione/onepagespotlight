package com.aeione.ops.test;

import com.aeione.ops.generic.GoogleDriveAPI;
import com.aeione.ops.generic.GoogleSheetAPI;
import com.aeione.ops.generic.TestSetUp;
import com.aeione.ops.pageactions.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BlueStorePageTest extends TestSetUp
{

    public LoginPageActions getLoginPage() throws IOException {
        return new LoginPageActions();
    }

    public RegistrationPageActions getRegistrationPage() throws IOException {
        return new RegistrationPageActions();
    }

    public BlueStorePageActions getBlueStorePageActions() throws IOException
    {
        return new BlueStorePageActions();
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

    public GoogleDriveAPI dsriveAPI() throws IOException
    {
        GoogleDriveAPI.getDriveService();
        return new GoogleDriveAPI();

    }

    /**
     * Test_Caeses_For_Verify_BlueStore_Post
     * Author:- Gandahrva
     * Date :- 05-03-2021
     */
    @Test(priority = 162, enabled = true, alwaysRun = true, description = "Create BlueStore Post")
    public void TC_BS_01_P1_VerifyCreateBlueStorePostTest() throws Exception
    {
        String RegistrationRange = "Registration!A4:H";
        String LogInRange = "Login!A21:C21";
        String featureLinkRange = "Generic!A4:B4";
        String BlueStoreRange = "BlueStore!A9:J9";

        dsriveAPI().downloadFileFromGoogleDrive(TEST_STORE_IMAGE_ID);
        String imageFile = userDirPath + IMAGE_STORE_TEST_FILE;

        ArrayList<String> responseinfo=null;
        String response=null;
        String username = null;
        String password = null;
        String fullname = null;
        String action = null;
        ArrayList<String> val = sheetAPI().getSpreadSheetValuesOfSpecificRow(TEST_DATA_GOOGLESHEET, RegistrationRange);
        String fullName= val.get(0);
        String userName=getRegistrationPage().getUserName(val.get(1));
        String emailAddress=getRegistrationPage().getEmail(val.get(2));
        String countryCode=val.get(3);
        String dateOfBirth=val.get(5);
        String createPassword=getRegistrationPage().getRandomValidPassword(val.get(6));
        String skipOtp=val.get(7);
        String phoneNumber= getRegistrationPage().getPhoneNumber(val.get(4));

        responseinfo=getRegistrationPage().mobileVerifyApi("Action Step",phoneNumber, countryCode, skipOtp);
        response=responseinfo.get(0);
        String secret=responseinfo.get(1);

        getRegistrationPage().verifyMobileApi("Verify Step", response);
        response=getRegistrationPage().mobileConfirmApi("Action Step",phoneNumber, secret,skipOtp , countryCode );
        getRegistrationPage().verifyMobileConfirmApi("Verify Step", response);
        response= getRegistrationPage().registerApi("Action & verify", fullName,userName,phoneNumber,countryCode,secret, emailAddress,dateOfBirth,createPassword,skipOtp);
        getRegistrationPage().verifyRegisterApi("Verify Step", response);


        //Update In Registration Page
        List<List<Object>> values1 = Arrays.asList(Arrays.asList(fullName,userName,emailAddress, countryCode,phoneNumber,dateOfBirth,createPassword,skipOtp));
        sheetAPI().appendRowData(TEST_DATA_GOOGLESHEET, CONSTANT_ROW, "USER_ENTERED", values1);

        //Update In LogIn Page
        List<List<Object>> values = Arrays.asList(Arrays.asList(userName, createPassword , fullName));
        sheetAPI().updateMultipleCellValues(TEST_DATA_GOOGLESHEET, LogInRange, "USER_ENTERED", values);

        Map<String, String> value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        username = value.get("UserName / Email / PhoneNumber");
        password = value.get("Password");
        fullname = value.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "valid username, password", username, password);
        getLoginPage().clickOnAddSkillsPopupCloseButton("Action Step");

        value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, BlueStoreRange);
        String title = value.get("Title");
        String description = value.get("Description_Values");
        String currency = value.get("Currency");
        String units = value.get("No. Units");
        String regularprice = value.get("Regular_Price");
        String salePrice = value.get("Sale_Price");
        String location = value.get("Location");
        String Category = value.get("Category");
        String page = value.get("Landing Page");

        value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, featureLinkRange);
        action = value.get("Action");

        getBlueStorePageActions().clickOnFeatureLinksOnNewsfeed("Action Step",action);
        getBlueStorePageActions().verifyCreateAnItemForSaleOrRentButtonOnBlueStoreLandingPage("Verify Step",page.split(",")[0]);
        getBlueStorePageActions().clickOnCreateAnItemForSaleOrRentButtonOnBlueStoreLandingPage("Action Step");
        getBlueStorePageActions().verifyDisplayOfBluStorePopUp("Action Step");
        getHomePageActions().attachFile("Action Step",imageFile);
        getPostWithHashTagActions().verifyDisplayOfUploadedBlueStoreThumbnail("Verify Step");
        getPostWithHashTagActions().enterBlueStoreTitle("Action Step",title);
        getPostWithHashTagActions().enterBlueStoreDescription("Action Step",description);
        getPostWithHashTagActions().enterBlueStoreLocation("Action Step",location);
        getPostWithHashTagActions().selectCurrencyType("Action Step", currency);
        getPostWithHashTagActions().selectBlueStoreCategory("Action Step",Category);
        getPostWithHashTagActions().enterBlueStoreUnits("Action Step",units);
        getPostWithHashTagActions().enterBlueStoreRegularPrice("Action Step",regularprice);
        getPostWithHashTagActions().enterBlueStoreSalePrice("Action Step",salePrice);
        getPostWithHashTagActions().clickOnBluestoreSubmitButton("Action Step");
        getBlueStorePageActions().clickOnMySalesBoardButton("Action Step");
        getBlueStorePageActions().verifyCreatedBlueStorePost("Verify Step",page.split(",")[1],title);
    }

    /**
     * Test_Caeses_For_BlueStore_Categories
     * Author:- Gandahrva
     * Date :- 17-03-2021
     */
    @Test(priority = 163, enabled = true, alwaysRun = true, description = "BlueStore Categories")
    public void TC_BS_02_P1_VerifyBlueStoreCategoriesTest() throws Exception
    {
        String LogInRange = "Login!A21:C21";
        String featureLinkRange = "Generic!A4:B4";
        String BlueStoreRange = "BlueStore!A9:K9";

        Map<String, String> value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, LogInRange);
        String username = value.get("UserName / Email / PhoneNumber");
        String password = value.get("Password");
        String fullname = value.get("FullName");

        getLoginPage().logIn("Action Step", fullname, "valid username, password", username, password);

        value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, featureLinkRange);
        String action = value.get("Action");

        getBlueStorePageActions().clickOnFeatureLinksOnNewsfeed("Action Step",action);

        value = sheetAPI().getSpreadSheetRowValueByColumnName(TEST_DATA_GOOGLESHEET, BlueStoreRange);
        String page = value.get("Landing Page");
        String categories = value.get("Categories");

        getBlueStorePageActions().verifyCreateAnItemForSaleOrRentButtonOnBlueStoreLandingPage("Verify Step",page.split(",")[0]);
        getBlueStorePageActions().verifyCategoriesOnBluStoreLandingPage("Verify Step",categories);
    }

    @AfterMethod(dependsOnMethods = {"com.aeione.ops.generic.TestSetUp.afterMethod"})
    public void after() throws IOException
    {
        getHomePageActions().navigateHomePage();
        getHomePageActions().clickOnTopBarDropdown("Action Step");
        getHomePageActions().clickOnSignOut("Action Step");
    }

}
