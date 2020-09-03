/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Ajith Manjunath

* Date: 05/02/2018

* Purpose: Contains all the methods which will help reporting. 
*/

package com.aeione.ops.generic;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.aeione.ops.generic.FileHandling;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static com.aeione.ops.generic.FileHandling.createDirectoryByFolderName;


public class TestListener extends TestSetUp implements ITestListener
{
	static Logger log = Logger.getLogger(GenericFunctions.class.getName());
	String isTestPassed="";
    String overViewSheet=null;
    ArrayList<String> failedModules=null;
    ArrayList<String> totalSheets= null;
	 
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
    
    private static String getTestClassName(ITestResult iTestResult)
    {

    	return iTestResult.getMethod().getRealClass().getSimpleName();
    	
    }
    public GoogleSheetAPI sheetAPI() throws IOException
    {
        return new GoogleSheetAPI();
    }

    public GoogleDriveAPI driveAPI() throws IOException
    {
        return new GoogleDriveAPI();
    }

    //Before starting all tests, below method runs.
    
	@Override
    public void onStart(ITestContext iTestContext)
	{
	    try{
        createDirectoryByFolderName("reports");
        createDirectoryByFolderName("json-formatted-file");
        createDirectoryByFolderName("json-file");

        GoogleSheetAPI.getSheetsService();
        GoogleDriveAPI.getDriveService();

        failedModules =new ArrayList<String>();
        failedModules.clear();

    	System.out.println("I am in onStart method " + iTestContext.getName());
    	log.info("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", driver );


        totalSheets = sheetAPI().getSheetsName(TEST_EXECUTION_SHEET);


        overViewSheet=totalSheets.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
 
    //After ending all tests, below method runs.
    @Override
    public void onFinish(ITestContext iTestContext)
    {
        System.out.println("I am in onFinish method " + iTestContext.getName());
    	log.info("I am in onFinish method " + iTestContext.getName());

    }
 
    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("I am in onTestStart method " +  getTestMethodName(iTestResult) + " start , I am in onTestStart class " +  getTestClassName(iTestResult) + " start");
        log.info("I am in onTestStart method " +  getTestMethodName(iTestResult) + " start , I am in onTestStart class " +  getTestClassName(iTestResult) + " start");
        
        //Start operation for extentreports.
        ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(),iTestResult.getMethod().getDescription());
        //Get the class Name of the Test method

        System.out.println("Print on Test Start"+iTestResult.getTestClass().toString());

    }
 
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("I am in onTestSuccess method " +  getTestMethodName(iTestResult) + " succeed , I am in onTestSuccess class " +  getTestClassName(iTestResult) + " succeed");
       log.info("I am in onTestSuccess method " +  getTestMethodName(iTestResult) + " succeed , I am in onTestSuccess class " +  getTestClassName(iTestResult) + " succeed");
        //ExtentReports log operation for passed tests.
        
        ExtentTestManager.getTest().log(LogStatus.PASS, getTestClassName(iTestResult) +" Test passed");

        System.out.println(getTestClassName(iTestResult));

       try{
        List<List<Object>> updateStatus = Arrays.asList(Arrays.asList("PASSED"));
        String a = "Sanity Suite!A1:ZZ";
        int rowNum = sheetAPI().getRowIndexByColumnValue(TEST_EXECUTION_SHEET, a, getTestMethodName(iTestResult));
        String testCaseLevelRange = "Sanity Suite!F:F"+rowNum+"";
        sheetAPI().updateRowDataByColumnName(TEST_EXECUTION_SHEET, testCaseLevelRange, "USER_ENTERED", updateStatus);

           String currentModuleRange = "" + totalSheets.get(1) + "!A1:ZZ";
           Map<String, String> values = sheetAPI().getSpreadSheetRowValueByColumnValue(TEST_EXECUTION_SHEET, currentModuleRange, getTestMethodName(iTestResult));
           String currentTestModule = values.get("Module").trim();

           if(!failedModules.contains(currentTestModule))
           {
               isTestPassed="";
           }

            if(isTestPassed==""&& !isTestPassed.equals("FAILED") || isTestPassed.equals("PASSED") )
            {
                isTestPassed="PASSED";
                String CurrentRange = "" + totalSheets.get(1) + "!A1:ZZ";
                Map<String, String> currentValues = sheetAPI().getSpreadSheetRowValueByColumnValue(TEST_EXECUTION_SHEET, CurrentRange, getTestMethodName(iTestResult));
                String testModule = currentValues.get("Module").trim();
                String testModuleLevelRange = "" + overViewSheet + "!A1:ZZ";
                rowNum = sheetAPI().getRowIndexByColumnValue(TEST_EXECUTION_SHEET, testModuleLevelRange, testModule);
                testModuleLevelRange = "" + overViewSheet + "!D:D" + rowNum + "";
                sheetAPI().updateRowDataByColumnName(TEST_EXECUTION_SHEET, testModuleLevelRange, "USER_ENTERED", updateStatus);
            }

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void onTestFailure(ITestResult iTestResult)
    {
        System.out.println("I am in onTestFailure method " +  getTestMethodName(iTestResult) + " failed, I am in onTestFailure class " +  getTestClassName(iTestResult) + " failed");
        log.info("I am in onTestFailure method " +  getTestMethodName(iTestResult) + " failed, I am in onTestFailure class " +  getTestClassName(iTestResult) + " failed");

        //Get driver from BaseTest and assign to local webdriver variable.
        Object testClass = iTestResult.getInstance();

        //Take base64Screenshot screenshot.
        String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);

        String throwableAssertionMessage=null;
        try {
            String assertionMessage = iTestResult.getThrowable().getMessage();
            String[] a = assertionMessage.split("&");

            throwableAssertionMessage = "" + a[0] + "<br><b><font color=red>" + a[1] + "</font></b>";

        }catch(Exception e)
        {
            throwableAssertionMessage=iTestResult.getThrowable().getMessage();
        }
        ExtentTestManager.getTest().log(LogStatus.FAIL, throwableAssertionMessage);


        ExtentTestManager.getTest().log(LogStatus.FAIL,getTestClassName(iTestResult)+" Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));

        String image = ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot);
        String screenPath = "<img height='42' width='42' src='" + ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot)+"'/></img>";
        Reporter.log(screenPath);


       //   capturing browser console logs if failed
//        LogEntries logEntries = DriverManager.getDriver().manage().logs().get(LogType.BROWSER);
//		for (LogEntry entry : logEntries)
//		{
//			log.error(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + "BROWSER LOG ::" + entry.getMessage());
//		}
        try{
        List<List<Object>> updateStatus = Arrays.asList(Arrays.asList("FAILED"));
        String a = "Sanity Suite!A1:ZZ";
        int rowNum = sheetAPI().getRowIndexByColumnValue(TEST_EXECUTION_SHEET, a, getTestMethodName(iTestResult));
        String testCaseLevelRange = "Sanity Suite!F:F"+rowNum+"";
        sheetAPI().updateRowDataByColumnName(TEST_EXECUTION_SHEET, testCaseLevelRange, "USER_ENTERED", updateStatus);

        isTestPassed="FAILED";
                String range = "" + totalSheets.get(1) + "!A1:ZZ";
                Map<String, String> values = sheetAPI().getSpreadSheetRowValueByColumnValue(TEST_EXECUTION_SHEET, range, getTestMethodName(iTestResult));
                String testModule = values.get("Module").trim();
                String testModuleLevelRange = "" + overViewSheet + "!A1:ZZ";
                 rowNum = sheetAPI().getRowIndexByColumnValue(TEST_EXECUTION_SHEET, testModuleLevelRange, testModule);
                testModuleLevelRange = "" + overViewSheet + "!D:D" + rowNum + "";
                sheetAPI().updateRowDataByColumnName(TEST_EXECUTION_SHEET, testModuleLevelRange, "USER_ENTERED", updateStatus);
            failedModules.add(testModule);
        }  catch (Exception e) {
            e.printStackTrace();
        }


    }
 
    @Override
    public void onTestSkipped(ITestResult iTestResult) 
    {
        System.out.println("I am in onTestSkipped method "+  getTestMethodName(iTestResult) + " skipped , I am in onTestSkipped class "+  getTestClassName(iTestResult) + " skipped");
        log.info("I am in onTestSkipped method "+  getTestMethodName(iTestResult) + " skipped , I am in onTestSkipped class "+  getTestClassName(iTestResult) + " skipped");
        
        //Extentreports log operation for skipped tests.
        ExtentTestManager.getTest().log(LogStatus.SKIP, getTestClassName(iTestResult)+" Test Skipped");

        try{
            List<List<Object>> updateStatus = Arrays.asList(Arrays.asList("SKIPPED"));
            String a = "Sanity Suite!A1:ZZ";
            int rowNum = sheetAPI().getRowIndexByColumnValue(TEST_EXECUTION_SHEET, a, getTestMethodName(iTestResult));
            String testCaseLevelRange = "Sanity Suite!F:F"+rowNum+"";
            sheetAPI().updateRowDataByColumnName(TEST_EXECUTION_SHEET, testCaseLevelRange, "USER_ENTERED", updateStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
        log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
    
    
    
}