package EsPoc.EarlySalary.Utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
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
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import EsPoc.EarlySalary.Common.CommonAction.CommonAction;
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
	protected static CommonAction ca = new CommonAction();
	public static Logger log = Logger.getLogger("Test_Suit  ");


	@BeforeSuite(alwaysRun = true) // Changed from @BeforeTest
	public void setupTest() throws IOException {

		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		String browser = rd.read_Configfile("browser");
		DOMConfigurator.configure("log4j.xml"); // added by swapnil 13-11-19
		
		 
		

	}

	/* @AfterSuite(alwaysRun = true) // changed from @AfterTest
	public void tearownTest() {
		log.info("-------End of Test-------");
		extent.flush();
	//	driver.quit();
		} 
	*/
	
	@AfterSuite(alwaysRun = true) // changed from @AfterTest
	public void tearownTest() {
		log.info("-------End of Test-------");
	}
		
	public AndroidDriver<MobileElement> getDriver() {
		return appDriver;
	}

	public String captureFailureScreen(String methodName) {
		File scrFile = ((TakesScreenshot) appDriver).getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());
		String srcfile = "./ScreenShot/" + methodName + "_" + timeStamp + ".jpg";
		System.out.println("srcfile :" + srcfile);
		try {
			FileUtils.copyFile(scrFile, new File(srcfile));
			// logger.log(LogStatus.FAIL, logger.addScreenCapture(srcfile));
		} catch (IOException e) {
			System.out.println("Screen not captured..");
			log.info("Screen not captured..");
		}
		return srcfile;
	}

	public static String capture(WebDriver driver, String screenShotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir") + "/ScreenShot/" + screenShotName + ".jpg";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);

		return dest;
	}

	/*
	@AfterTest
	public void endReport() {
	extent.flush();
		driver.quit();
	}
*/
}
