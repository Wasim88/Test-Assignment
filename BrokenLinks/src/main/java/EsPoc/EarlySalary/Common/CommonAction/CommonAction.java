package EsPoc.EarlySalary.Common.CommonAction;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

// import com.relevantcodes.extentreports.ExtentTest;
// import com.relevantcodes.extentreports.LogStatus;
import com.aventstack.extentreports.ExtentTest;

import EsPoc.EarlySalary.Utilities.SuiteBase;

public class CommonAction extends SuiteBase
{
	
	public boolean isElementPresent(By loc, WebDriver driver)
	{
		try
		{
			driver.findElement(loc);
			return true;
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			System.out.println("Locator not found - "+loc);
			return false;
		}
	}
	
	public static void assertFailWithOutException(String failMsg)
	{
		Assert.fail(failMsg);
	}
	
	public void verifyAsserTrue(boolean condition, String passMsg , String failMsg, String methodName)
	{
		try
		{
			Assert.assertTrue(condition, failMsg);
			System.out.println(passMsg);
		}
		catch(AssertionError e)
		{
			System.out.println(failMsg+methodName);
		}
	}
	
	public void verifyAssertEqualsString(String actual, String expected,String failMsg,String passMsg ,String methodName,ExtentTest logger)
	{
		try
		{
			Assert.assertEquals(actual, expected,failMsg);
			System.out.println(passMsg);
			//logger.log(LogStatus.PASS, passMsg);
		}
		catch(AssertionError ex)
		{
			 System.out.println(ex.getMessage());
			 //logger.log(LogStatus.FAIL , "Unable to verify url."+ex.getMessage());
		}
	}
	
	
	
	public void verifyAssertEquals(String actual, String expected,String failMsg,String passMsg ,
			String methodName,ExtentTest logger,String TestCaseId)
	{
		try
		{
			Assert.assertEquals(actual, expected,failMsg);
			System.out.println(TestCaseId+" Pass :"+passMsg);
			//logger.log(LogStatus.PASS, TestCaseId+" Pass:"+passMsg);
		}
		catch(AssertionError ex)
		{
			System.out.println("FAIL :"+TestCaseId+" "+ex.getMessage());
			//logger.log(LogStatus.FAIL ,"Fail :"+TestCaseId+" Unable to verify "+ex.getMessage());
			/*String screenshotPath = CommonAction.getScreenhot(driver, TestCaseId);
			 //To add it in the extent report  
			//logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));*/
			
		}
	}
	
	
	
	public void verifyAssertEquals(String actual, String expected,String failMsg,String passMsg ,String methodName,ExtentTest logger,String TestCaseId,WebDriver driver ) throws Exception
	{
		try
		{
			Assert.assertEquals(actual, expected,failMsg);
			System.out.println(TestCaseId+" Pass : "+passMsg);
			//logger.log(LogStatus.PASS, TestCaseId+" Pass:"+passMsg);
		}
		catch(AssertionError ex)
		{
			System.out.println("FAIL :"+TestCaseId+" "+ex.getMessage());
			//logger.log(LogStatus.FAIL ,"Fail :"+TestCaseId+" Unable to verify "+ex.getMessage());
			//String screenshotPath = CommonAction.getScreenhot(driver, TestCaseId);
			 //To add it in the extent report  
			//logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
			//CommonAction.assertFailWithOutException(failMsg);
			Assert.fail(); // Added line which will know TestNG that test-case is failed and counted in failed TC --Akshay P
		}
	}
	
	
	
    //This method is to capture the screenshot and return the path of the screenshot.
	 
	public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
               //after execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+"_"+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	
	public void verifyAssertEqual(String actual, String expected,String failMsg,String passMsg ,String methodName,ExtentTest logger)
	{
		try
		{
			Assert.assertEquals(actual, expected,failMsg);
			System.out.println(passMsg);
			//logger.log(LogStatus.PASS, passMsg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			captureFailureScreen(methodName);
			//logger.log(LogStatus.FAIL, failMsg);
			assertFailWithOutException(failMsg);
			
		}
	}
	

	public void assertfailwithoutExcetion(String actual, String expected,String failMsg,String passMsg ,String methodName,ExtentTest logger){
		try {
			Assert.assertEquals(actual,expected);
			//logger.log(LogStatus.PASS , "\" "+expected+"\" " +passMsg);
			System.out.println("\"PASS "+expected+"\" "+passMsg);
			} catch (AssertionError ex) {
				System.out.println(failMsg+ex.getMessage());
				//logger.log(LogStatus.FAIL , failMsg+ex.getMessage());
			}
	}
	
		 
	public boolean verifyAssertfail(String actual, String expected,String failMsg,String passMsg ,String methodName,ExtentTest logger)
	{
		try
		{
			Assert.assertEquals(actual, expected);
			System.out.println(passMsg);
			//logger.log(LogStatus.PASS, passMsg);
			return true;
		}
		catch(AssertionError e)
		{
			System.out.println("Unable to verify "+e.getMessage());
			//logger.log(LogStatus.FAIL, failMsg);
			return false;
		}
	}
	
	public boolean verifyAssertWithoutFail(String actual, String expected,String failMsg,String passMsg ,String methodName,ExtentTest logger,String TestCaseId)
	{
		try
		{
			Assert.assertEquals(actual, expected);
			System.out.println(TestCaseId+" Pass:"+passMsg);
			//logger.log(LogStatus.PASS, TestCaseId+" Pass:"+passMsg);
			return true;
		}
		catch(AssertionError ex)
		{
			System.out.println("FAIL :"+TestCaseId+"Unable to verify "+ex.getMessage());
			//logger.log(LogStatus.FAIL ,"Fail :"+TestCaseId+failMsg);
			return false;
		}
	}
	
	
	public boolean isAllTrue(Iterable<?> list) {
		for (Object obj : list) {
		    if ((Boolean)obj != true)
		    return false;
		}
		return true;
	}
	
	
	public boolean isClickable(By Element,WebDriver driver)      
	{
	 boolean clickResult = true;
	try
	{
	   WebDriverWait wait = new WebDriverWait(driver, 5);
	   wait.until(ExpectedConditions.elementToBeClickable(Element));
	   ((WebElement) Element).click();
	   clickResult = false;
	   return clickResult;
	}
		catch (Exception e)
		{
			return clickResult;
		}
	}
	
	public static String isElemnetClickable(By Element,WebDriver driver)      
	{
		String clickResult = "true";
		try
		{
		   WebDriverWait wait = new WebDriverWait(driver, 5);
		   wait.until(ExpectedConditions.elementToBeClickable(Element));
		   ((WebElement) Element).click();
		   clickResult = "false";
		   return clickResult;
		}
		catch (Exception e)
		{
			return clickResult;
		}
	}
	
	 public String splitString(String label,String Expression)throws InterruptedException {
		 	String[] parts ;
			parts = label.split(Expression);
			return parts[1];
		}
	 
	 public String ElementGetAttribute(By Element,WebDriver driver,String value){
			String elementValue=driver.findElement(Element).getAttribute("value");	
			return elementValue;
		}
	 
	 public String ElementGetAttrributeTextValue(By Element,WebDriver driver,String attribute){
			String elementValue=driver.findElement(Element).getAttribute(attribute);	
			return elementValue;
		}
	 

	 	public void ElementToClick(By Element ,WebDriver driver ,WebDriverWait wait)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.elementToBeClickable(Element));
	    	driver.findElement(Element).click();
		}
		
		public void ElementToSendData(By Element, String data ,WebDriver driver ,WebDriverWait wait)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.elementToBeClickable(Element));
	    	driver.findElement(Element).clear();
	    	driver.findElement(Element).sendKeys(data);
		}
		
		
		public String ElementGetText(By Element,WebDriver driver ,WebDriverWait wait)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
	    	String elementText = driver.findElement(Element).getText();
	    	return elementText;
		}
		

		public List<WebElement> ListOfElement(By Element,WebDriver driver ,WebDriverWait wait)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
	    	List<WebElement> elementList = driver.findElements(Element);
	    	return elementList;
		}
		
		public void ElementClear(By Element,WebDriver driver ,WebDriverWait wait)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
	    	driver.findElement(Element).clear();
	     }

		public void focusToElement(By Element,WebDriver driver)
		{
			if(isElementPresent(Element, driver))
			{
				driver.findElement(Element).sendKeys("");
			}
			else{
				System.out.println("Loc not found -"+Element);
			}
		}
		
		public String ElementGetTextValue(By Element,WebDriver driver){
			String elementValue=driver.findElement(Element).getAttribute("value");	
			return elementValue;
		}
		
		public void EnterPress(By Element ,WebDriver driver ,WebDriverWait wait)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.elementToBeClickable(Element));
	    	driver.findElement(Element).sendKeys(Keys.ENTER);
		}
	 
		public int ListOfElementSize(By Element,WebDriver driver ,WebDriverWait wait)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
	    	List<WebElement> elementList = driver.findElements(Element);
	    	return elementList.size();
		}
		
		
		public void ElementToClickWithLogger(By Element ,WebDriver driver ,WebDriverWait wait, ExtentTest logger,String Steps)throws InterruptedException {    	
	    	wait.until(ExpectedConditions.elementToBeClickable(Element));
	    	driver.findElement(Element).click();
	    	//logger.log(LogStatus.INFO,Steps+": " +Element+" Element is clickable..");
			System.out.println(Steps+": " +Element+" Element is clickable..");
		}
		
		
		public void ElementToSendDataLogger(By Element, String data ,WebDriver driver ,WebDriverWait wait, ExtentTest logger,String Steps)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.elementToBeClickable(Element));
	    	driver.findElement(Element).clear();
	    	driver.findElement(Element).sendKeys(data);
	    	//logger.log(LogStatus.INFO,Steps+": " +Element+" Element is Send Data is :"+data);
	    	System.out.println(Steps+": " +Element+" Element is Send Data is :"+data);
	    	
		}
		
		public void SelectElementFromList(By Element0, By Element1, String data, WebDriver driver, WebDriverWait wait, ExtentTest logger, String Steps) throws InterruptedException
		{
			driver.findElement(Element0).click();
			wait.until(ExpectedConditions.elementToBeClickable(Element1));
			List<WebElement> options = driver.findElements(Element1);
			for (WebElement option : options)
			{
				if(option.getText().equals(data))
				{
					option.click();
					System.out.println(Steps+": " +Element1+" Element is selected from list dropdown :"+data);
			    	
					break;
				}
			}
					
		}

		public String ElementGetTextLogger(By Element,WebDriver driver ,WebDriverWait wait, ExtentTest logger,String Steps)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
	    	String elementText = driver.findElement(Element).getText();
	    	//logger.log(LogStatus.INFO,Steps+": " +Element+" Element is Getting Data is :"+elementText);
			System.out.println(Steps+": " +Element+" Element is Getting Data is :"+elementText);
	    	return elementText;
		}
		
		public List<WebElement> ListOfElementWithLogger(By Element,WebDriver driver ,WebDriverWait wait,ExtentTest logger,String Steps)throws InterruptedException {    	    	    
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
	    	List<WebElement> elementList = driver.findElements(Element);
	    	//logger.log(LogStatus.INFO,Steps+": " +Element+" Element list Getting Data is :"+elementList.size());
			System.out.println(Steps+": " +Element+" Element is Getting Data is :"+elementList.size());
			
	    	return elementList;
		}
		
		
			public  String getRandomString() {
	        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 10) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;

	    }
		
			public String  trimuicount(By Element,WebDriver driver, WebDriverWait wait,ExtentTest logger,String steps)
			{
				int index=0;
				char character = ' ';
				int i,j;
				String rawcount = driver.findElement(Element).getText();
				j=(rawcount.length()-1);
				
				for(i=j;rawcount.charAt(i)!=character;i--)
				{}
			
				index=i;
				return rawcount.substring(index+1,rawcount.length());
			
			}

				
		public String[] splitElementWithExpression(By Element,WebDriver driver ,WebDriverWait wait,ExtentTest logger,String Expression,String Steps)throws InterruptedException {
			String[] parts ;
			String testString = ElementGetText(Element, driver,wait);
			parts = testString.split(Expression);
			//logger.log(LogStatus.INFO,Steps+": " +Element+" Split Element String is :"+testString);
			System.out.println(Steps+": " +Element+" Split Element String is :"+testString);
			
		/*for(String name : parts){
				System.out.println(name);
				}
		*/
			return parts;
		}	

		
}
