package EsPoc.EarlySalary.BrokenLink;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.model.Log;

import EsPoc.EarlySalary.Common.CommonAction.ConfigurationManager;
import EsPoc.EarlySalary.Email.*;
import EsPoc.EarlySalary.Utilities.SuiteBase;


@Listeners(value = EmailReportManager.class)
public class BrokenLinks extends SuiteBase{

	public static WebDriver driver;
	public ConfigurationManager rd = new ConfigurationManager();

	@Test
	public void findBrokenLinks() throws InterruptedException {

		String ChromeDriversPath=rd.read_Configfile("ChromeDriversPath");
	    String ChromeDriversName=rd.read_Configfile("ChromeDriversName");
	    
	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ChromeDriversPath+"\\"+ChromeDriversName);
    //Code added for download file
    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	chromePrefs.put("profile.default_content_settings.popups", 0);
	ChromeOptions options = new ChromeOptions();
	options.setExperimentalOption("prefs", chromePrefs);
	
	
	options.setAcceptInsecureCerts(true);
	options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
	
	
	driver = new ChromeDriver(options);
 
	driver.get("https://www.earlysalary.com/");
			Thread.sleep(5000);
			List<WebElement> links = driver.findElements(By.tagName("a"));	
			// System.out.println("Total links are "+links.size());	
			log.info("Total links are "+links.size());
			
			for(int i=0; i<links.size(); i++) {
				WebElement element = links.get(i);
				String url=element.getAttribute("href");
			
				verifyLink(url);			
			}	
			
			driver.quit();
		}
		
		public static void verifyLink(String urlLink) {
	        try {
				URL link = new URL(urlLink);
				
				
				URL url2 = new URL(link.getProtocol(), link.getHost(), link.getPort(), link.getPath() + "/");
				
				//System.out.println("New URL - "+url2);
				
				HttpURLConnection httpConn =(HttpURLConnection)url2.openConnection();
				 httpConn.setConnectTimeout(100000);
				httpConn.connect();

				// System.out.println(urlLink+" - "+httpConn.getResponseMessage());
				
				log.info(urlLink+" - "+httpConn.getResponseMessage());
//				if(httpConn.getResponseCode()== 200) {	
//				System.out.println(urlLink+" - "+httpConn.getResponseMessage());
//			}
//					if(httpConn.getResponseCode()== 404) {
//						System.out.println(urlLink+" - "+httpConn.getResponseMessage());
//			}	
//						if(httpConn.getResponseCode()>= 404) {
//							
//						System.out.println(urlLink+" - "+httpConn.getResponseCode());
//					}
				}
				
			catch (Exception e) {
			}
	        
	        
	    } 
		
		public static void main(String[] args) throws InterruptedException {

			BrokenLinks obj = new BrokenLinks();
			obj.findBrokenLinks();
			driver.close();
			
	}
}		