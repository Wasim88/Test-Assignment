package Test.Assignment.Common.CommonAction;

import java.util.List;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.restassured.response.Response;

import org.testng.Assert;

import Test.Assignment.Utilities.SuiteBase;

public class CommonAction extends SuiteBase
{
	
	public void verifyAsserTrue(boolean condition, String passMsg , String failMsg)
	{
		try
		{
			Assert.assertTrue(condition, failMsg);
			System.out.println(passMsg);
		}
		catch(AssertionError e)
		{
			System.out.println(failMsg);
		}
	}
	
	
	public void verifyAssertEqual(String actual, String expected,String failMsg,String passMsg)
	{
		try
		{
			Assert.assertEquals(actual, expected,failMsg);
			System.out.println(passMsg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
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
	
	public String SelectElementFromList(By Element1, String data, WebDriver driver, WebDriverWait wait) throws InterruptedException
		{

			String getValuefromlist = null;
			List<WebElement> options = driver.findElements(Element1);
			for (WebElement option : options)
			{
				if(option.getAttribute("for").equals(data))
				{	
					getValuefromlist = option.getAttribute("for");
					
					break;
				}
			}
			return getValuefromlist;
			
					
		}

	public double convertKelvinToCelsius(double kelvin) {

			  return (double) (kelvin - 273.15);
		}
		
	
	public static String responseKeyValidationFromJsonObject(Response response, String key1, String key2) {
				
			String actualValue = null;

				JSONObject json1 = new JSONObject(response.getBody().asString());
				if(json1.has(key1) && json1.get(key1)!= null) {
					
				JSONObject json2 = json1.getJSONObject(key1);
			
				 actualValue = json2.get(key2).toString();
			
				}
				return actualValue;
		
		}
		
		
	public static void responseCodeValiddation(Response response, int statusCode) {

			try {
				
				ca.verifyAsserTrue(response.getStatusCode()==statusCode , 
						"Sucessfully validated value of response code and it is :: " +response.getStatusCode(), 
						"Validation failed :: " +response.getStatusCode()+" and :: " +statusCode  + " did not matched"); 
				
			} catch (AssertionError e) {
				
				System.out.println("Expected status code is :: " + statusCode + " , insted of getting :: " + response.getStatusCode());
				
			} catch (Exception e) {
			
				System.out.println(e.fillInStackTrace());
				
			}
		}
		
	
	public static double ConvertandDifference(String str1, String str2)
		{
			double x=Double.parseDouble(str1);
			double z = ca.convertKelvinToCelsius(x);
			
			System.out.println("Temperature from API after conversion is : "+z);
			
			double y=Double.parseDouble(str2);
			
			double diff = Math.abs(z - y);

			System.out.println("Differce between two temperature is :  "+diff);
	
			return diff;

		}
		
		
}
