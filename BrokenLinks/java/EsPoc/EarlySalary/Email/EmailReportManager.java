package EsPoc.EarlySalary.Email;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.testng.*;
import org.testng.xml.XmlSuite;
import EsPoc.EarlySalary.Common.CommonAction.ConfigurationManager;
import EsPoc.EarlySalary.Utilities.SuiteBase;

public class EmailReportManager extends SuiteBase implements IReporter {
	// package buisness.managers;

	int failedtc = 0;
	int passedtc = 0;
	int totalexecuted = 0;
	static int serial = 0;

	public void generateReport(List<XmlSuite> arg0, List<ISuite> suites, String arg2) {

		EmailLogic em = new EmailLogic();
		ConfigurationManager rd = new ConfigurationManager();
		EmailReportManager erm = new EmailReportManager();

		em.sendMail(getReport(suites));

		// Setup.log.info("Email Done");

		try {
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp t = new java.sql.Timestamp(date.getTime());
			String dateNow = t.toString().replace(":", "_");
			String OldReportPath = rd.read_Configfile("OldReportPath");
			String CurrentReportPath = rd.read_Configfile("CurrentReportPath");
			File SrcFile = new File(System.getProperty("user.dir") + CurrentReportPath);
			Thread.sleep(2000);
			String username = System.getProperty("user.name");
			org.apache.commons.io.FileUtils.copyDirectory(SrcFile,
					new File(OldReportPath + "\\" + username + "\\" + "_" + dateNow + "Report"));
			// Setup.log.info("Report copied to path" + SrcFile);
		} catch (Exception e) {
			// Setup.log.fatal("Error in File moving"+e);
		}

	}

	private String startHtmlTable() {
		String msg;
		msg = "<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\" >";
		msg = msg + "<tr bgcolor=\"#0066FF\">";
		msg = msg + "<th> Sr. No.</th>";
		msg = msg + "<th>Test Cases</th>";
		// msg = msg + "<th>Parameters</th>";
		// msg = msg + "<th>Expected Result</th>";
		msg = msg + "<th>Status</th>";
		msg = msg + "<th>Execution Start Time<br/></th>";
		// msg=msg + "<th> Execution End Time<br/></th>";
//			msg = msg + "<th>Screen Shot Folder Path<br/></th>";
		msg = msg + "</tr>";

		return msg;

	}

	private String getQuickSummaryExecution() {
		String msg;

		msg = "<table border=\"3\">";
		msg = msg + "<th><strong>Quick Summary Of Executed Automation Script</strong></th>";
		msg = msg + "<th><strong>Count</strong></th>";
		msg = msg + "<tr>";
		msg = msg + "<td><strong>Total Testcase Executed</strong></td>";
		msg = msg + "<td>" + totalexecuted + "</td>";
		msg = msg + "</tr>";
		msg = msg + "<tr>";
		msg = msg + "<td><strong>Passed</strong></td>";
		msg = msg + "<td><font color=\"green\">" + passedtc + "</td>";
		msg = msg + "</tr>";
		msg = msg + "<tr>";
		msg = msg + "<td><strong>Failed</strong></td>";
		msg = msg + "<td><font color=\"red\">" + failedtc + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;

	}

	public String getReport(List<ISuite> suites) {

		String msg;
		String suitname = null;
		// msg = startHtmlTable();
		msg = writeHeader();
		msg = msg + writeMetaData();
		msg = msg + startHtmlTable();
		// buisness.managers.ScreenshotManager sm = new ScreenshotManager();
		/*
		 * String Fixp="LoginGroup"; int z=1;
		 */
		for (ISuite s : suites) {
			totalexecuted = s.getResults().size();
			System.out.println(s.getResults().toString());

			for (ISuiteResult r : s.getResults().values()) {

				ITestContext i = r.getTestContext();

				totalexecuted = i.getPassedTests().size() + i.getFailedTests().size() + i.getSkippedTests().size();
				for (ITestResult res : i.getPassedTests().getAllResults()) {
					passedtc = passedtc + 1;
					serial++;

					msg = msg + "<tr>";

					String testName = res.getName();
					List<String> elephantList3 = OtherClass(testName);
					String name = elephantList3.get(0);
					String result = elephantList3.get(1);
					msg = msg + "<td>" + serial + "</td>";
					msg = msg + "<td>" + name + "</td>";
					// msg = msg +
					// "<td>"+Utils.escapeHtml(Utils.toString(res.getParameters()))+"</td>";
					// msg = msg + "<td>"+result+"</td>";
					msg = msg + "<td><center><font color=\"green\">" + (res.getStatus() == 1 ? "Pass" : "pass")
							+ "</font></center></td>";

					msg = msg + "<td>" + i.getStartDate().toLocaleString() + "</td>";
					// msg=msg + "<td>"+i.getEndDate().toLocaleString()+"</td>";
					// msg = msg + "<td>"+Screenpath+"\\"+name+"</td>";
					msg = msg + "</tr>";

					/* j++; */
				}
				for (ITestResult res : i.getFailedTests().getAllResults()) {
					failedtc = failedtc + 1;
					serial++;
					msg = msg + "<tr>";
					String testName = res.getName();
					List<String> elephantList3 = OtherClass(testName);
					String name = elephantList3.get(0);
					String result = elephantList3.get(1);
					msg = msg + "<td>" + serial + "</td>";
					msg = msg + "<td>" + name + "</td>";
					// msg = msg +
					// "<td>"+Utils.escapeHtml(Utils.toString(res.getParameters()))+"</td>";
					// msg = msg + "<td>"+result+"</td>";
					msg = msg + "<td><center><font color=\"red\">" + (res.getStatus() == 1 ? "Fail" : "Fail")
							+ "</font></center></td>";
					msg = msg + "<td>" + i.getStartDate().toLocaleString() + "</td>";
					// msg=msg + "<td>"+i.getEndDate().toLocaleString()+"</td>";

					// msg = msg + "<td>"+Screenpath+"\\"+name+"</td>";
					msg = msg + "</tr>";

				}
			}
		}
		// msg="Please download Attached html file for observing report
		// "+msg+"\n"+getQuickSummaryExecution();

		msg = "Please download Attached log file to check each URL status.." + msg + "</table>" + "\n"
				+ getQuickSummaryExecution();

		// just added
		// msg = msg + "</table>";

		return msg;
	}

	protected PrintWriter createWriter(String outdir) throws IOException {
		// SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-DD HH-MM a");
		// java.util.Date now = new Date(0);
		new File(outdir).mkdirs();
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "Custom Report"// +sd.format(now)
				+ ".html"))));
	}

	private String writeHeader() {
		String msg = "";
		msg = msg
				+ ("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		msg = msg + ("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		msg = msg + ("<head>");
		msg = msg + ("<title>DashBoard Report</title>");
		msg = msg + ("</head>");
		msg = msg + ("<body>");
		return msg;
	}

	/**
	 * writes additional details like Executor name
	 * 
	 * @throws IOException
	 */
	private String writeMetaData() {
		String msg = "";
	//	String Module = rd.read_Configfile("Module");
	//  String Build = rd.read_Configfile("AndroidApkName");
	//	String Environment = rd.read_Configfile("Environment");
	//	String Version = rd.read_Configfile("Version");

		msg = msg + ("<h5>Test Executor: " + System.getProperty("user.name") + "</h3>");
		msg = msg + ("<h5>Date and Time: " + new Date() + "</h3>");
		//msg = msg + ("<h5>Module: " + Module + "</h3>");
		//msg = msg + ("<h5>Build: " + Build + "</h3>");
		//msg = msg + ("<h5>Version: " + Version + "</h3>");
		//msg = msg + ("<h5>Environment: " + Environment + "</h3>");
		if (new ConfigurationManager().read_Configfile("url").contains("https://www.earlysalary.com/")) {
			// msg = msg + ("<h5>Environment: " +"Live"+ "</h3>");

		} else if (new ConfigurationManager().read_Configfile("url").contains("https://www.earlysalary.com/")) {
			msg = msg + ("<h5>Environment:" + "Production" + "</h3>");
		} else if (new ConfigurationManager().read_Configfile("url").contains("https://www.earlysalary.com/")) {
			msg = msg + ("<h5>Environment:" + "Production" + "</h3>");
		}
		try {
			msg = msg + ("<h5><a href=\"" + new ConfigurationManager().read_Configfile("url")
					+ "\">Go to application</a></h5>");
		} catch (Exception e) {
			msg = msg + ("Unable to retrive URL link");
		}

		return msg;
	}

	public static int[] splitToComponentTimes(int biggy) {
		long longVal = biggy;// .longValue();
		int hours = (int) longVal / 3600;
		int remainder = (int) longVal - hours * 3600;
		int mins = remainder / 60;
		remainder = remainder - mins * 60;
		int secs = remainder;

		int[] ints = { hours, mins, secs };
		return ints;
	}

	private String getDurationString(int seconds) {

		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = seconds % 60;
		String h = twoDigitString(hours);
		String m = twoDigitString(minutes);
		String s = twoDigitString(seconds);
		return h + " : " + m + " : " + s;
	}

	private String twoDigitString(int number) {

		if (number == 0) {
			return "00";
		}

		if (number / 10 == 0) {
			return "0" + number;
		}

		return String.valueOf(number);
	}

	public List<String> OtherClass(String input) {

		List<String> elephantList = Arrays.asList(input.split(":"));
		try {
			String name = elephantList.get(0);
			String result = elephantList.get(1);
		} catch (Exception e) {
			String result = "Result Not Defined";
			List<String> elephantList1 = new ArrayList<String>(1);
			elephantList1.add(0, input);
			elephantList1.add(1, result);
			return elephantList1;
		}
		return elephantList;
	}

}
