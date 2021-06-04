package Test.Assignment.Common.Actions;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import Test.Assignment.Common.CommonAction.CommonAction;
import Test.Assignment.Common.CommonAction.ConfigurationManager;
import Test.Assignment.UI_Map.Locators;

public class ActionsSteps {

	WebDriver driver;
	WebDriverWait wait;
	
	String callerClassName = Thread.currentThread().getStackTrace()[2].getMethodName();
	
	Locators esLoc = new Locators();
	CommonAction ca = new CommonAction();
	public static ConfigurationManager rd = new ConfigurationManager();

	
	public ActionsSteps(WebDriver driver, WebDriverWait wait)
	{
		this.driver = driver;
		this.wait = wait;
	}
	
	public String TC_verifyCityFromList() throws Exception
	{
		
		Thread.sleep(4000);
		ca.ElementToClick(esLoc.permissionPopUp, driver, wait);
		ca.ElementToClick(esLoc.expandMenu, driver, wait);
		ca.ElementToClick(esLoc.whether, driver, wait);
			
		Thread.sleep(15000); 
		ca.ElementToSendData(esLoc.searchCity, rd.read_Configfile("city") , driver, wait);
		
		
		String	getCityFromList = ca.SelectElementFromList(esLoc.listOfCity, rd.read_Configfile("city"), driver, wait);
				
		Thread.sleep(4000);
		
		System.out.println("Actual City :" +getCityFromList);
		return getCityFromList;
	}
	
	
	public String TC_verifySelectedCityAvailability() throws Exception
	{
		
		Thread.sleep(2000);
		ca.ElementToClick(esLoc.citySelectDynamic, driver, wait);
		Thread.sleep(2000);
		String cityName = ca.ElementGetText(esLoc.cityName, driver, wait);
		
		System.out.println("Actual City Name :" +cityName);
		return cityName;
	}
	
	
	public String TC_verifyTempAvailibity() throws Exception
	{
		
		Thread.sleep(2000);
		String temp = ca.ElementGetText(esLoc.tempInDegree, driver, wait);
		
		System.out.println("Actual City temp :" +temp);
		
		String cityTemp = temp.split(":")[1].trim();

		System.out.println("Temp of selected city :" +cityTemp);
		return cityTemp;
	}
	
	
}






