package Test.Assignment.Test;


import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.Test;

import Test.Assignment.Common.Actions.ActionsSteps;
import Test.Assignment.Common.CommonAction.CommonAction;
import Test.Assignment.Common.CommonAction.ConfigurationManager;
import Test.Assignment.Utilities.SuiteBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class AssignmentTest extends SuiteBase {

	CommonAction ca = new CommonAction();
	public static ConfigurationManager rd = new ConfigurationManager();
	String ActualCityTemp = null;
	String apiTempValue = null;
	
	
	@Test(priority = 1)
	public void verifyCityFromList() throws Exception {
		
		System.out.println("--------------------------Testcase to verify city from list ---------------------------");
		WebDriverWait wait = new WebDriverWait(driver, 15);
		ActionsSteps esAction = new ActionsSteps(driver, wait);

		String ActualCity = esAction.TC_verifyCityFromList();
		String ExpectedCity = rd.read_Configfile("city");
		System.out.println("Expected city :" + ExpectedCity);
		ca.verifyAssertEqual(ActualCity, ExpectedCity, "Test case for searcing city is Failed", "Test case for searcing city is Passed");
	}	
	
	
	@Test(priority = 2)
	public void verifyCityAvailibility() throws Exception {
		
		System.out.println("--------------------------Testcase to verify city availability in map ------------------");
		WebDriverWait wait = new WebDriverWait(driver, 15);
		ActionsSteps esAction = new ActionsSteps(driver, wait);

		String ActualCity = esAction.TC_verifySelectedCityAvailability();
		String ExpectedCity = rd.read_Configfile("cityName");
		System.out.println("Expected city Name :" + ExpectedCity);
		ca.verifyAssertEqual(ActualCity, ExpectedCity, "Test case to check availibility of city in map is Failed", "Test case to check availibility of city in map is Passed");
	}	
	
	@Test(priority = 3)
	public void verifyCityTemp() throws Exception {
		
		System.out.println("--------------------------Testcase to verify city temparature from UI ------------------");
		WebDriverWait wait = new WebDriverWait(driver, 15);
		ActionsSteps esAction = new ActionsSteps(driver, wait);

		ActualCityTemp = esAction.TC_verifyTempAvailibity();
		ca.verifyAsserTrue((ActualCityTemp != null && !ActualCityTemp.isEmpty()) , 
		"Test case to check availibility of city temperature is Passed", "Test case to check availibility of city temperature is Failed");
			
	}	

	@Test(priority = 4)
	public void verifyAPIReturnTemp() {

		System.out.println("--------------------------Testcase to verify API Response and Temperature ----------------");
			
		RestAssured.baseURI ="https://api.openweathermap.org/data/2.5/";
		
		RequestSpecification request = RestAssured.given();
		Response response = request.queryParam("q", "Mumbai").queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea").get("/weather");
		
		apiTempValue =  CommonAction.responseKeyValidationFromJsonObject(response, "main", "temp");
		
		System.out.println("Temperature from API is :" +apiTempValue);
		
		ca.responseCodeValiddation(response, 200);
		ca.verifyAsserTrue((apiTempValue != null && !ActualCityTemp.isEmpty()) , 
				"Test case to check availibility of city temperature is Passed", "Test case to check availibility of city temperature is Failed");
		
				
	}
	
	
	@Test(priority = 5)
	public void verifyDifference() {

		System.out.println("--------------------------Testcase to verify Difference of temperature and pass --------------");
		System.out.println("Temperature from UI is :" +ActualCityTemp);
			
		System.out.println("Temperature from API is :" +apiTempValue);
		
		double difference =  ca.ConvertandDifference(apiTempValue, ActualCityTemp);
		
		ca.verifyAsserTrue((difference <= 2) , 
				"Test case to check difference between temperature is Passed", "Test case to check difference between temperature is Failed");
			
	}
	
	
}
