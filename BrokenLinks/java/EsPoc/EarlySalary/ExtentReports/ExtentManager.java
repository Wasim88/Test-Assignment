package EsPoc.EarlySalary.ExtentReports;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.openqa.selenium.Platform;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.configuration.Theme;
import EsPoc.EarlySalary.Common.CommonAction.ConfigurationManager;

public class ExtentManager {
	private static ExtentReports extent;
	private static Platform platform;
	private static String reportFileName = "AutomationTestReport.html";
	private static String macPath = System.getProperty("user.dir") + "/Report";
	private static String windowsPath = System.getProperty("user.dir") + "\\Report";
	private static String macReportFileLoc = macPath + "/" + reportFileName;
	private static String winReportFileLoc = windowsPath + "\\" + reportFileName;
	private static String reportName = "TEST AUTOMATION REPORT";
	

	public ExtentHtmlReporter htmlReporter;
	public ExtentTest logger;
	public static ConfigurationManager rd = new ConfigurationManager();

	static String workingDir = System.getProperty("user.dir");
	static String userName = System.getProperty("user.name");
	static String OSName = System.getProperty("os.name");

	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance();
		return extent;
	}

	// Create an extent report instance
	public static ExtentReports createInstance() {
		platform = getCurrentPlatform();
		String fileName = getReportFileLocation(platform);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		// htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		// htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		// htmlReporter.loadXMLConfig("extent-config.xml");
		htmlReporter.config().setReportName(fileName);
		htmlReporter.config().setCSS(".r-img { width: 10%; }"); // screenshot size change
		htmlReporter.config().setReportName(reportName);
	

		// Get Machine/Host Name
		String machineName = null;
		try {
			machineName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("User Name", userName);
		extent.setSystemInfo("Environment", rd.read_Configfile("Environment"));
		extent.setSystemInfo("OS Name", OSName);
		extent.setSystemInfo("Machine Name", machineName);
		

		return extent;
	}

	// Select the extent report file location based on platform
	private static String getReportFileLocation(Platform platform) {
		String reportFileLocation = null;
		switch (platform) {
		case MAC:
			reportFileLocation = macReportFileLoc;
			createReportPath(macPath);
			System.out.println("ExtentReport Path for MAC: " + macPath + "\n");
			break;
		case WINDOWS:
			reportFileLocation = winReportFileLoc;
			createReportPath(windowsPath);
			System.out.println("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
			break;
		default:
			System.out.println("ExtentReport path has not been set! There is a problem!\n");
			break;
		}
		return reportFileLocation;
	}

	// Create the report path if it does not exist
	private static void createReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory: " + path + " is created!");
			} else {
				System.out.println("Failed to create directory: " + path);
			}
		} else {
			System.out.println("Directory already exists: " + path);
		}
	}

	// Get current platform
	private static Platform getCurrentPlatform() {
		if (platform == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				platform = Platform.WINDOWS;
			} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
				platform = Platform.LINUX;
			} else if (operSys.contains("mac")) {
				platform = Platform.MAC;
			}
		}
		return platform;
	}

}
