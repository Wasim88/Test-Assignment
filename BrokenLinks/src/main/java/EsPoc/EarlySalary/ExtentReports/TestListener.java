
package EsPoc.EarlySalary.ExtentReports;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import java.io.IOException;
import java.lang.reflect.Method;
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

	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Extent Reports Test Suite started!");
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println(("Extent Reports Test Suite is ending!"));
		extent.flush();
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		test.set(extentTest);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().log(Status.PASS,
				MarkupHelper.createLabel(result.getName() + " Test case Passed", ExtentColor.GREEN));
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));

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
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.get().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test case SKIPPED due to below issues:", ExtentColor.GREY));
		test.get().skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}
}