package Test.Assignment.Common.apitest;

import org.testng.annotations.Test;

import Test.Assignment.Common.CommonAction.CommonAction;
import Test.Assignment.Common.CommonAction.ConfigurationManager;
import Test.Assignment.Utilities.SuiteBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class ApiTest extends SuiteBase {

	String key = "main" ;
	
	ConfigurationManager rd = new ConfigurationManager();
	CommonAction ca = new CommonAction();

	@Test
	public void CiRReportPostAPI() {

			
		RestAssured.baseURI ="https://api.openweathermap.org/data/2.5/";
		
		
		RequestSpecification request = RestAssured.given();
		Response response = request.queryParam("q", "Mumbai").queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea").get("/weather");
		
		String tempValue =  CommonAction.responseKeyValidationFromJsonObject(response, "main", "temp");
		
		System.out.println(tempValue);
		
		String tempvalueUI = "33.0";
		
		ca.ConvertandDifference(tempValue, tempvalueUI);
		
				
	}

public static void main (String []argc)
{
ApiTest obj  = new ApiTest();

obj.CiRReportPostAPI();
}

}
