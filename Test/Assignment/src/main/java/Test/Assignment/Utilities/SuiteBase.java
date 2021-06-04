package Test.Assignment.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.*;

import Test.Assignment.Common.CommonAction.CommonAction;
import Test.Assignment.Common.CommonAction.ConfigurationManager;

public class SuiteBase {
	public static WebDriver driver;
	
	public static ConfigurationManager rd = new ConfigurationManager();
	protected static CommonAction ca = new CommonAction();


	@BeforeSuite(alwaysRun = true) 
	public void setupTest() throws IOException {

		String browser = rd.read_Configfile("browser");
		
		 
	try {

			if (browser.equalsIgnoreCase("firefox")) {
				String FireDriversPath = rd.read_Configfile("FireDriversPath");
				String FireDriversName = rd.read_Configfile("FireDriversName");
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + FireDriversPath + "\\" + FireDriversName);

				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("iexplorer")) {
				String IeDriverPath = rd.read_Configfile("IeDriversPath");
				String IeDriverName = rd.read_Configfile("IeDriversName");
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + IeDriverPath + "\\" + IeDriverName);
				driver = new InternetExplorerDriver();
				
			} 
			else if(browser.equalsIgnoreCase("chrome"))
	        {
	            String ChromeDriversPath=rd.read_Configfile("ChromeDriversPath");
	            String ChromeDriversName=rd.read_Configfile("ChromeDriversName");
	            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ChromeDriversPath+"\\"+ChromeDriversName);
	            
	    		driver = new ChromeDriver();
	            
	        }
			
			driver.manage().window().maximize();
			driver.get(rd.read_Configfile("appurl"));
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		} 
			catch (Exception e) {
			System.out.println(e);
		}

	}

	
	@AfterSuite(alwaysRun = true) 
	public void tearownTest() {
		driver.quit();
	}


}