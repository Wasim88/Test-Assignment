package EsPoc.EarlySalary.earlySalary;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
//import jdk.internal.org.objectweb.asm.commons.Method;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

// import EsPoc.EarlySalary.ExtentReports.ExtentTestManager;
import EsPoc.EarlySalary.Common.CommonAction.CommonAction;
import EsPoc.EarlySalary.Common.CommonAction.ExcelReader;
import EsPoc.EarlySalary.Common.EsActions.earlySalaryActions;
import EsPoc.EarlySalary.Utilities.SuiteBase;
import java.lang.reflect.Method;


public class EarlySalaryTest extends SuiteBase {

	CommonAction ca = new CommonAction();
	String methodName;
	public ExtentTest logger;
	public ExtentReports extent;

	private ExcelReader ex = new ExcelReader();
	private String getDataFromDataProviderSheet = System.getProperty("user.dir") + rd.read_Configfile("earlySalary");

	@DataProvider(name = "TC_Login")
	public Object[][] getLoginData() throws InvalidFormatException, IOException {
		log.info("Data Provider Start ");
		Object[][] LoginData = ex.getDataingrid(getDataFromDataProviderSheet, "TC_AutoLogin_Data");
		log.info("Data Provider Ends ");
		return LoginData;
	}

	@Test(dataProvider = "TC_Login", priority = 1)
	public void VerifyValidLogin(Hashtable<String, String> ExcelSheetData, Method method) throws Exception {
		System.out.println("\n" + "--------------------------------------TC_LoginSuccessfull");
		// ExtentTestManager.startTest(method.getName(), "Test case to verify login by entring correct OTP");
		
		// logger = extent.createTest("To verify Google Title");
		
		WebDriverWait wait = new WebDriverWait(driver, 15);
		earlySalaryActions esAction = new earlySalaryActions(driver, wait);

		String ActualLoginText = esAction.TC_EarlySalaryLogin(methodName, ExcelSheetData, logger);
		String ExpectedLogintext = ExcelSheetData.get("Expected_Text");
		System.out.println("Login text from sheet :" + ExpectedLogintext);
		ca.verifyAssertEqual(ActualLoginText, ExpectedLogintext,
				"Unable to verify \" " + ExcelSheetData.get("Mobile_Number") + "\" message for login number.",
				"\" " + ExcelSheetData.get("Mobile_Number") + "\" message for username verified successfully..",
				ExpectedLogintext, logger);
	}

	
}
