package EsPoc.EarlySalary.Common.EsActions;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

// import com.relevantcodes.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import EsPoc.EarlySalary.Common.CommonAction.CommonAction;
import EsPoc.EarlySalary.UI_Map.WebUI.EarlySalaryLocators;

public class earlySalaryActions {

	WebDriver driver;
	WebDriverWait wait;
	
	String callerClassName = Thread.currentThread().getStackTrace()[2].getMethodName();
	
	EarlySalaryLocators esLoc = new EarlySalaryLocators();
	CommonAction ca = new CommonAction();
	EarlySalaryOTP otp = new EarlySalaryOTP();


	
	public earlySalaryActions(WebDriver driver, WebDriverWait wait)
	{
		this.driver = driver;
		this.wait = wait;
	}
	
	public String TC_EarlySalaryLogin(String methodName, Hashtable<String, String> sheetData, ExtentTest logger) throws InterruptedException, MalformedURLException, Exception
	{
		driver.navigate().refresh();
		ca.ElementClear(esLoc.enterEmailId, driver, wait);
		ca.ElementToSendDataLogger(esLoc.enterEmailId, sheetData.get("EmailID"), driver, wait, logger, "Steps 1");
		Thread.sleep(4000);
		ca.ElementToClickWithLogger(esLoc.nextButton , driver, wait, logger, "Steps 2");
		Thread.sleep(4000);
		ca.ElementToSendDataLogger(esLoc.enterPassword , sheetData.get("Password"), driver, wait, logger, "Steps 3");
		Thread.sleep(4000);
		ca.ElementToClickWithLogger(esLoc.nextButton , driver, wait, logger, "Steps 4");
		//Thread.sleep(2000);
		//to run through appium to get otp from mobile message
		//ca.ElementToSendDataLogger(esLoc.enterOTP, otp.GetOTPValue(), driver, wait, logger, "Steps 5");
		String getLogginText = "Pass";
		
		return getLogginText;
	}
	
	
}






