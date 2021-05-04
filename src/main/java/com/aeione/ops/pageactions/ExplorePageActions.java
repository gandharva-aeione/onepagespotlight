package com.aeione.ops.pageactions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aeione.ops.generic.DriverManager;
import com.aeione.ops.generic.ExtentTestManager;
import com.aeione.ops.generic.GenericFunctions;
import com.aeione.ops.pageobjects.ExplorePageObjects;
import com.aeione.ops.pageobjects.HomePageObjects;
import com.aeione.ops.pageobjects.ProfilePageObjects;
import com.relevantcodes.extentreports.LogStatus;

/*
 * @author: Aishwarya
 * Description: Explore Page Actions
 * Date of Creation: 21-05-2020
 */

public class ExplorePageActions {

	GenericFunctions genericfunctions;
	ExplorePageObjects explorePageObjects = new ExplorePageObjects();
	HomePageObjects homePageObjects = new HomePageObjects();
	Actions action;

	public ExplorePageActions() throws IOException
	{

		genericfunctions = new GenericFunctions(DriverManager.getDriver());
		PageFactory.initElements(DriverManager.getDriver(), this);
		PageFactory.initElements(DriverManager.getDriver(), explorePageObjects);
		PageFactory.initElements(DriverManager.getDriver(), homePageObjects);
		action=new Actions(DriverManager.getDriver());
	}

	///////////////////////////////////////////// Page actions //////////////////////////////////////////////////////

	/*
	 * @author: Aishwarya
	 * Description: Click on Explore link from the Home page header
	 * Created on : 21-05-2020
	 */

	public void clickOnExploreLink(String... strings) {
		try 
		{
			genericfunctions.waitTillTheElementIsVisible(homePageObjects.topbar_explore);
			homePageObjects.topbar_explore.click();
		}
		catch(Exception e)
		{
			Assert.fail("Could not perform action on \"Explore \"link" +"&"+ e.getMessage());
		}
	}

	


	/////////////////////////////////////////// Page Verification ///////////////////////////////////////////////////////////	

	/*
	 * @author: Aishwarya
	 * Description: Verify navigation to the Explore page
	 * Created on : 22-05-2020
	 */
	public void verifyNavigationToExplorePage(String ...strings)
	{
		ExtentTestManager.getTest().log(LogStatus.INFO, " " + strings[0] + " ::  Navigation to the Explore page on clicking"
				+ " Explore link from Home page header with following assertions :");

		try {
			genericfunctions.waitTillTheElementIsVisible(explorePageObjects.search_TextField);
			Assert.assertTrue(explorePageObjects.search_TextField.isDisplayed());

			ExtentTestManager.getTest().log(LogStatus.PASS,  "User successfully navigated to the \" Explore page\" ");
		} catch (Exception e)
		{
			Assert.fail("Expected :: Successfully navigated to \"Explore page\"  ; Actual :: Failed to navigate to the \"Explore page\" ");
		}
	}

	/*
	 * @author: Gandharva
	 * Description: Verify navigation to the Explore page
	 * Created on : 27-04-2021
	 */
	public void verifySuggestedHashtagsInSubHeaderOfExplorePage(String ... strings)
	{
		List<WebElement> tabs=null;
		String[] expectedTabs=strings[1].split("\\,");
		String currentExpectedTabs= null;
		String currentActualTabs = null;

		tabs = explorePageObjects.suggested_HashtagsList;
		System.out.println("tabs Size:-"+tabs.size());

		ExtentTestManager.getTest().log(LogStatus.INFO, " " +strings[0] + " ::  Suggested Hashtags in sub-header of Explore page"
				+ " with following assertions :");

		try
		{
			for (int i = 0; i < tabs.size(); i++)
			{
				currentExpectedTabs = expectedTabs[i].trim();
				currentActualTabs = tabs.get(i).getText().trim();
				System.out.println("Tabs :-"+currentActualTabs);
				System.out.println("Excepted Tabs :-"+currentExpectedTabs);

				genericfunctions.waitTillTheElementIsVisible(explorePageObjects.suggested_HashtagsList.get(i));
				genericfunctions.waitTillTheElementIsVisibleAndClickable(explorePageObjects.suggested_HashtagsList.get(i));
				Assert.assertTrue(currentActualTabs.equals(currentExpectedTabs));
				ExtentTestManager.getTest().log(LogStatus.PASS, " " + "<b>" + explorePageObjects.suggested_HashtagsList.get(i).getText() + " is Clickable" + "</b>");
			}
		}
		catch (Throwable e)
		{
			Assert.fail("&"+"Expected :: " + currentExpectedTabs + " Category should be displayed  ; Actual :: "
					+ currentActualTabs + " Category is not displayed"+"&"+e.getMessage()+"");
		}

	}

}
