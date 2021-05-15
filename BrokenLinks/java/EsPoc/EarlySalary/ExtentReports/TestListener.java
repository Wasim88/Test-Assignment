
package EsPoc.EarlySalary.ExtentReports;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import EsPoc.EarlySalary.Utilities.SuiteBase;
import EsPoc.EarlySalary.ExtentReports.ExtentManager;

public class TestListener extends SuiteBase implements ITestListener {

	// Extent Report Declarations
	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	public SuiteBase obj = new SuiteBase();
	public Method method;
	public String assertionName = "AssertionName";
	public String desc = "Description";
	public String screenShotPath = null;
	
	public ExtentTest logger;
	Logger log = Logger.getLogger("Test_Status");
	

	@Override
	public synchronized void onStart(ITestContext context) {
		log.info("Extent Reports Test Suite started!");
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		log.info(("Extent Reports Test Suite is ending!"));
		extent.flush();
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		log.info((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		test.set(extentTest);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		log.info((result.getMethod().getMethodName() + " passed!"));
		test.get().log(Status.PASS,
				MarkupHelper.createLabel(result.getName() + " Test case Passed", ExtentColor.GREEN));
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		log.info((result.getMethod().getMethodName() + " failed!"));

		// Added by Wasim on 11-02-2020
		
		
		test.get().log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
		test.get().fail(result.getThrowable());

		try {
			test.get().fail("Snapshot below: " + test.get().addScreenCaptureFromPath(screenShotPath));
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}

	}

	public synchronized ExtentTest createNode(String assertionName) {

		return test.get().createNode(assertionName);

	}

	public synchronized void onTestSkipped(ITestResult result) {
		log.info((result.getMethod().getMethodName() + " skipped!"));
		test.get().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test case SKIPPED due to below issues:", ExtentColor.GREY));
		test.get().skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.info(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}
}