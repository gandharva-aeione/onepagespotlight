package com.aeione.ops.generic;


import com.relevantcodes.extentreports.ExtentReports;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static com.aeione.ops.generic.HandleCommandsBatchFile.runBatchFile;


public abstract class TestSetUp implements IAutoConst {

    public static WebDriver driver;
    static Logger log = Logger.getLogger(GenericFunctions.class.getName());
    public BrowserMobProxy proxy;
    protected Har har = new Har();
    ReadJSONExample readJson = new ReadJSONExample();
    public static String userDirPath = null;
    public static ConfigurationManager prop;
    public static String OSName = null;
    public static String broswerversion;
    public static String broswerName;
    Capabilities browserCap = null;
    static GoogleDriveAPI dsriveapi = null;
    static String reportPath;
    static LocalDate localDate = null;


    static {

        userDirPath = System.getProperty("user.dir");
        OSName = System.getProperty("os.name").trim();
        dsriveapi = new GoogleDriveAPI();
        localDate = LocalDate.now();
        try {
            runBatchFile(DRIVER_UPDATION_BATCHFILE, OSName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Parameters({"reportName", "replaceExistingReport", })
    @BeforeTest
    public static void beforeTest(String reportName, boolean replaceExistingReport) {

        DriverManager.setReportName(reportName);
        DriverManager.setReplaceExistingReport(replaceExistingReport);

        ExtentReports extent = ExtentManager.getReporter();
        DriverManager.setExtentReport(extent);
    }


    @Parameters({"testBrowserName", "RunHeadless"})
    @BeforeClass(alwaysRun = true)
    public void launchApplication(String testBrowserName, String RunHeadless) throws Exception {
        // start the proxy
        proxy = new BrowserMobProxyServer();
        proxy.start(0);

        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        switch (testBrowserName) {

            case FIREFOX:

                WebDriverManager.firefoxdriver().setup();
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("browser.private.browsing.autostart",true);
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--no-sandbox");
                if(Boolean.valueOf(RunHeadless)){
                options.addArguments("--headless");}
                options.addArguments("-private");
                options.addArguments("--window-size=1980,1080");
                options.setProfile(firefoxProfile);
                driver = new FirefoxDriver(options);
                DriverManager.setBrowserName(FIREFOX);
                break;

            case CHROME:

                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                if(Boolean.valueOf(RunHeadless)){
                chromeOptions.addArguments("--headless");}
                chromeOptions.addArguments("disable-gpu");
                chromeOptions.addArguments("–disable-dev-shm-usage");
                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("--window-size=1980,1080");
                driver = new ChromeDriver(chromeOptions);
                DriverManager.setBrowserName(CHROME);
                break;

            case SAFARI:

                driver = new SafariDriver();
                DriverManager.setBrowserName(SAFARI);
                break;

            case INTERNET_EXPLORER:

                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                DriverManager.setBrowserName(INTERNET_EXPLORER);
                break;

            case EDGE:

                WebDriverManager.edgedriver().forceDownload().setup();
                driver = new EdgeDriver();
                DriverManager.setBrowserName(EDGE);
                break;

            default:
                System.out.println("Browser is not specified ");
        }

        DriverManager.setDriver(driver);

        //Set browser version
        browserCap = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();
        broswerversion = browserCap.getVersion();
        broswerName = DriverManager.getBrowserName();
        DriverManager.setBrowserVersion(broswerversion.trim());


        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, java.util.logging.Level.ALL);
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().deleteAllCookies();


        //Navigate to test url
        DriverManager.getDriver().get(TEST_APP_URL);

        //Set timeouts
        DriverManager.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        DriverManager.getDriver().manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

//      enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        // create a new HAR with the label "yahoo.com"
        proxy.newHar("ops");
        // get the HAR data
        har = proxy.getHar();

        // DOMConfigurator.configure(userDirPath+"/src/main/resources/log4j.xml");

    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult iTestResult) throws Exception {
        FileOutputStream fos;

        String jsonLogFileName = "log" + "-" + DriverManager.getBrowserName().toLowerCase() + "-" + iTestResult.getTestClass().getName() + "-" + iTestResult.getMethod().getMethodName() + ".json";

        String file = userDirPath + "/json-file/" + jsonLogFileName + "";
        String newFile = userDirPath + "/json-formatted-file/" + jsonLogFileName + "";

        if (!iTestResult.isSuccess()) {
            try {

                String sFileName = userDirPath + "/HAR-Files/HAR-Information" + iTestResult.getTestClass().toString() + ".har";
                fos = new FileOutputStream(sFileName);

                har.writeTo(fos);

                File harFile = new File(sFileName);

                OutputStream out = new FileOutputStream(file);
                Writer w = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                har.writeTo(harFile);
                har.writeTo(w);

                System.out.println("created json file");

                if (harFile.delete()) {
                    System.out.println("HAR File deleted from Project root directory");
                } else
                    System.out.println("HAR File  doesn't exist in the project root directory");

                ReadJSONExample.readHAR(file, newFile);

            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("Could not find file ");
                e.printStackTrace();
            }
        } else if (iTestResult.isSuccess()) {
            try {
                proxy.stop();

            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("Couldn't stop the proxy");
                e.printStackTrace();
            }

        }

        try {
            System.out.println("Test Execution Completed : Closing the browser");
            ReadJSONExample.readHAR(file, newFile);
            proxy.stop();

        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Couldn't close the browser");
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void closeApplication() {
        DriverManager.getDriver().manage().deleteAllCookies();
        DriverManager.getDriver().quit();
    }


    @Parameters({"shareReportToGoogleDrive", "isParallel"})
    @AfterTest
    public static void afterTest(boolean shareReportToGoogleDrive, boolean isParallel) {

        ExtentTestManager.endTest();
        DriverManager.getExtentReport().flush();

        if (!isParallel == true) {
            String renamedReport = renameReport();

            if (shareReportToGoogleDrive == true) {


                GoogleDriveAPI.getUploadFileIntoGoogleDrive(renamedReport, AUTOMATION_REPORTS);
            }
        }

    }


    @Parameters({"shareReportToGoogleDrive", "isParallel"})
    @AfterSuite
    public static void afterSuite(boolean shareReportToGoogleDrive, boolean isParallel) {

        if (isParallel == true) {
            String renamedReport = renameReport();
            if (shareReportToGoogleDrive == true) {
                GoogleDriveAPI.getUploadFileIntoGoogleDrive(renamedReport, AUTOMATION_REPORTS);
            }
        }
        removeFilesByExtention(".jpg");
        removeFilesByExtention(".mp3");
        removeFilesByExtention(".mp4");
        removeFilesByExtention(".png");
    }


    public static String renameReport() {
        File newFile = null;
        try {
            LocalTime localTime = LocalTime.now();
            File directoryPath = new File(ExtentManager.localDirectoryPath);

            String renameFileDirPath = ExtentManager.localDirectoryPath + "/";
            File[] listOfFiles = directoryPath.listFiles();

            for (int i = 0; i <= listOfFiles.length - 1; i++) {
                String currentFileName = renameFileDirPath + listOfFiles[i].getName().trim();
                String expectedReportName = renameFileDirPath + ExtentManager.reportName + ".html".trim();
                System.out.println(currentFileName);
                System.out.println(expectedReportName);

                if (currentFileName.equals(expectedReportName))
                {
                    File currentFile = listOfFiles[i];

                    if(currentFile.exists()) {
                        String a = OSName.toLowerCase();
                        String b = broswerName.toLowerCase();
                        String c = broswerversion;
                        String d = String.valueOf(localDate);
                        String e = String.valueOf(localTime);


                        String newFileName = renameFileDirPath + "extentReport-" + a + "-" + b + "[" + c + "]-" + d + "-" + e + ".html";

                        newFile = new File(newFileName);
                        if(!newFile.exists()) {
                            System.out.println(currentFile.getAbsolutePath());
                            System.out.println(newFile.getAbsolutePath());

                            if (currentFile.renameTo(newFile)) {
                                System.out.println("File renamed");
                            } else {
                                System.out.println("File not renamed");
                            }

                        }

                        break;
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return newFile.getAbsolutePath();
    }






    /**
     * @author Kirthana SS
     * @param fileExtention
     *
     * Remove files by file extention
     */
    public static void removeFilesByExtention(String fileExtention)
    {
        try {
            FileFilter fileFilter = new FileFilter(fileExtention);
            File parentDir = new File(userDirPath);

            // Put the names of all files ending with .txt in a String array
            String[] listOfTextFiles = parentDir.list(fileFilter);

            if (listOfTextFiles.length == 0) {
                System.out.println("There are no text files in this directory!");
                return;
            }

            for (String file : listOfTextFiles) {

                //construct the absolute file paths...
                String absoluteFilePath = new StringBuffer(userDirPath).append(File.separator).append(file).toString();

                System.out.println(absoluteFilePath);

                File fileNamePath = new File(absoluteFilePath);

                    if (fileNamePath.exists()) {
                        fileNamePath.delete();

                        System.out.println("Removed test files ");
                    } else {
                        System.out.println("Could not remove test files");
                    }


                }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
