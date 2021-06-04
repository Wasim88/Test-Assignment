package Test.Assignment.UI_Map;

import org.openqa.selenium.By;

import Test.Assignment.Common.CommonAction.ConfigurationManager;

public class Locators {

	public static ConfigurationManager rd = new ConfigurationManager();

	
	public By nDTVHome = By.xpath("//div[@class='ndtvlogo']");
	public By permissionPopUp = By.linkText("No Thanks");
	
	public By expandMenu = By.xpath("//a[@id='h_sub_menu']");
	public By whether = By.linkText("WEATHER");
	
	public By searchCity = By.xpath("//input[@id='searchBox']");
	public By listOfCity = By.xpath("//div[@id='messages']/div/label");
	
	public By citySelect = By.xpath("//div[contains(text(),'Mumbai')]");
	
	//public By citySelect = By.xpath("//div[contains(text(),  )]");
	public By citySelectDynamic = By.xpath("//div[contains(text(), '" + rd.read_Configfile("city")+"')]" );
	
	public By cityName = By.xpath("//span[contains(text(), '" + rd.read_Configfile("cityName")+"')]" );
	public By tempInDegree = By.xpath("//span[@class='heading'][4]");
}

