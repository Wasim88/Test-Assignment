package EsPoc.EarlySalary.Utilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.tools.ant.taskdefs.Recorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.protobuf.Method;

import EsPoc.EarlySalary.Common.CommonAction.ConfigurationManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class SuiteBase {
	public static WebDriver driver;
	public static AndroidDriver<MobileElement> appDriver;
	// public static AppiumDriver<MobileElement> appDriver;
	public ExtentReports report;
	public ExtentTest logger;
	public AppiumDriverLocalService appiumService;

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;

	public static ConfigurationManager rd = new ConfigurationManager();
	//protected static CommonActionApp ca = new CommonActionApp();
	public static Logger log = Logger.getLogger("Test_Suit  ");

	

	@BeforeSuite(alwaysRun = true) // Changed from @BeforeTest
	public void setupTest() throws IOException {

		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		String browser = rd.read_Configfile("browser");
		DOMConfigurator.configure("log4j.xml"); // added by swapnil 13-11-19
		
		Runtime rt = Runtime.getRuntime();
	//	Process pr = rt.exec("taskkill /F /IM node.exe");
		log.info("-----Killed all running NODE.js*** processes----");
		
		 
		try {

			if (browser.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("iexplorer")) {
				// Update the driver path with your location
				String IeDriverPath = rd.read_Configfile("IeDriversPath");
				String IeDriverName = rd.read_Configfile("IeDriversName");
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + IeDriverPath + "/" + IeDriverName);
				driver = new InternetExplorerDriver();

			} else if (browser.equalsIgnoreCase("chrome")) {
				// Update the driver path with your location
				String ChromeDriversPath = rd.read_Configfile("ChromeDriversPath");

				System.out.println(ChromeDriversPath);
				String ChromeDriversName = rd.read_Configfile("ChromeDriversName");

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + ChromeDriversPath + "/" + ChromeDriversName);
				// Code added for download file
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", chromePrefs);

				// DesiredCapabilities cap = DesiredCapabilities.chrome();
				// cap.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
				// true);
				// cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				// cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
				// UnexpectedAlertBehaviour.IGNORE);
				// cap.setCapability(ChromeOptions.CAPABILITY, options);

				options.setAcceptInsecureCerts(true);
				options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);

				driver = new ChromeDriver(options);
				// driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("app")) {
				// PLS UPDATE BELOW BOOLEANS AS NEEDED

				/*
				 * .apk path is at /Executable/Android directory as defined in
				 * ApplicationConfig.properties Below boolean reinstallApp -- provides an option
				 * to re-install the apk By default, this should not reinstall - so set FALSE
				 */
				boolean reinstallApp = true;
				/*
				 * APP is having issues when changing default activity back to APP in automation
				 * logic for file permission at Bank Statement Thus, unable to access on-screen
				 * elements Hence, using the auto-grant method for now as default
				 */
				boolean permissionsGranted = true;
				/*
				 * IF true, Appium server will run from Java Code Else, IF false, Appium server
				 * requires a desktop utility or server on Terminal/CLI to execute the program
				 */
				boolean appiumViaCode = true;

				String androidVersion = rd.read_Configfile("AndroidVersion");
				String androidDeviceName = rd.read_Configfile("AndroidDeviceName");
				boolean deviceIdAvailable = true;
				String androidDeviceID = "";
				try {
					androidDeviceID = rd.read_Configfile("AndroidDeviceID");
				} catch (Exception e) {
					// Do Nothing if element not found
					deviceIdAvailable = false;
				}

				String androidApkPath = rd.read_Configfile("AndroidApkPath");
				String androidApkName = rd.read_Configfile("AndroidApkName");
				String desktopOS = System.getProperty("os.name").toUpperCase();

				DesiredCapabilities dc = new DesiredCapabilities();

				dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				// dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, androidVersion);

				// J9AAGF051118DAF // Android // Android 6 Test Device: 0123456789ABCDEF
				dc.setCapability(MobileCapabilityType.DEVICE_NAME, androidDeviceName);
				if (deviceIdAvailable)
					dc.setCapability(MobileCapabilityType.UDID, androidDeviceID);
				/*
				 * adb devices adb tcpip 5555 adb connect 192.168.0.186:5555
				 */

				if (reinstallApp) {
					String apkPath = ("" + System.getProperty("user.dir") + androidApkPath + androidApkName);

					dc.setCapability("app", apkPath);
				}

				dc.setCapability("autoGrantPermissions", permissionsGranted);

				dc.setCapability("appPackage", rd.read_Configfile("AndroidAppPackage"));
				dc.setCapability("appActivity", rd.read_Configfile("AndroidAppActivity"));

				log.info("- - - - - - - - Desired Capabilities Defined for Android Driver - - - - - - - - ");
				if (appiumViaCode) {

					log.info("- - - - - - - - Initializing Appium Server - - - - - - - - ");

					if (desktopOS.contains("MAC")) {
						//appiumService = AppiumDriverLocalService.buildDefaultService();

						appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
								.usingDriverExecutable(new File(rd.read_Configfile("NodeExecutable_MAC")))
								.withAppiumJS(new File(rd.read_Configfile("NPMAppiumExecutabe_MAC")))
								.withIPAddress("0.0.0.0")
								// .usingPort(4723)
								.usingAnyFreePort().withCapabilities(dc)
								.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
								.withArgument(GeneralServerFlag.LOG_LEVEL, "error")
								.withLogFile(new File("target/" + rd.read_Configfile("AndroidDeviceName") + ".log")));
					} else if (desktopOS.contains("WINDOWS")) {
						/* Windows Code is not tested and might need some changes */
						appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
								.usingDriverExecutable(new File(rd.read_Configfile("NodeExecutable_WINDOWS")))
								.withAppiumJS(new File(rd.read_Configfile("NPMAppiumExecutabe_WINDOWS")))
								.withIPAddress("127.0.0.1")
								// .usingPort(4723)
								.usingAnyFreePort().withCapabilities(dc)
								.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
								.withArgument(GeneralServerFlag.LOG_LEVEL, "error")
								.withLogFile(new File("target/" + rd.read_Configfile("AndroidDeviceName") + ".log")));
					} else {
						Assert.fail("Unspecified OS found, Appium can't run");
					}

					log.info("- - - - - - - - Starting Appium Server - - - - - - - - ");
					appiumService.start();
					URL appiumServiceUrl = appiumService.getUrl();
					log.info("Appium Service Address : - " + appiumServiceUrl.toString());
					log.info("Appium Server Started....");

					log.info("- - - - - - - - Initializing Android Driver - - - - - - - - ");
					appDriver = new AndroidDriver<MobileElement>(appiumServiceUrl, dc);
					// appDriver = new AndroidDriver<MobileElement>(dc);
					// appiumDriver = new AppiumDriver<MobileElement>(appiumServiceUrl, dc);

				} else { // If appiumViaCode == false --> Run appium via desktop utility
					/* PREVIUOS METHOD OF CONNECTING -- VIA APPIUM DESKTOP SERVER APPLICATION */
					URL url = null;

					if (desktopOS.contains("WINDOWS")) {
						url = new URL("http://localhost:4723/wd/hub"); // For Windows OS
					} else if (desktopOS.contains("MAC")) {
						url = new URL("http://0.0.0.0:4723/wd/hub"); // For Mac OS
					} else {
						System.out.println("OS not identified. Hence, URL is empty.");
					}

					log.info("- - - - - - - - Initializing Android Driver - - - - - - - - ");

					appDriver = new AndroidDriver<MobileElement>(url, dc);
					// appiumDriver = new AppiumDriver<MobileElement>(url, dc);

				}
			}

			if (!browser.equalsIgnoreCase("app")) {
				log.info("Browser Started");
				// screen.stop();
				driver.manage().window().maximize();
				driver.get(rd.read_Configfile("url"));
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@AfterSuite(alwaysRun = true) // changed from @AfterTest
	public void tearownTest() {
		String browser = rd.read_Configfile("browser");
		if (browser.equalsIgnoreCase("app")) {
			appDriver.quit();
			appiumService.stop();
		} else {
			driver.quit();
		}
	}

	public AndroidDriver<MobileElement> getDriver() {
		return appDriver;
	}

	
	@AfterTest
	public void endReport() {
		log.info("-------End of Test-------");
		extent.flush();
	}
	
/*	
	//added for video recording 29-07-200
	
	@BeforeMethod
	public synchronized void beforeMethod(Method methode) throws MalformedURLException {
	
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		Date date = new Date();
		try {
		
			ATUTestRecorder recorder = new ATUTestRecorder(System.getProperty("user.dir")+"\\ScriptVideos", methode.getName()+"_"+ dateFormat.format(date), false);
			
			recorder.start();
			
		} catch (Exception e) {
			
		}
		
	}
	
	@AfterMethod
	public void afterMethod (ITestResult result) {
		try {
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//end
	
*/
}
