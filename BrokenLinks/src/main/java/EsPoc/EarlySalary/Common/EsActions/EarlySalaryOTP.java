package EsPoc.EarlySalary.Common.EsActions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import EsPoc.EarlySalary.UI_Map.WebUI.EarlySalaryLocators;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class EarlySalaryOTP {

	AndroidDriver<MobileElement> driver;
	EarlySalaryLocators esLoc = new EarlySalaryLocators();
	
	public String GetOTPValue() throws MalformedURLException, InterruptedException
	{
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9");
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, "Android"); //PNXID18121000238
		dc.setCapability("appPackage", "com.google.android.apps.messaging");
		dc.setCapability("appActivity", "com.google.android.apps.messaging.ui.conversationlist.ConversationListActivity");
	

		URL url = new URL("http://localhost:4723/wd/hub");
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(url, dc);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElementById("com.google.android.apps.messaging:id/conversation_list_spam_popup_positive_button").click();
		
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView[1]").click();
		String GetOtpText = driver.findElementById("com.google.android.apps.messaging:id/message_text").getText().split("is")[1].trim();
		//String GetOtpText = driver.findElementById("com.google.android.apps.messaging:id/message_text").getText().split("is: ")[1].trim();
				
		String OtpValue	= GetOtpText.substring(0, 6);	
		
	    System.out.println("OTP value is :"+OtpValue);
	    driver.quit();
	    return OtpValue;

						
//com.google.android.apps.messaging:id/conversation_name
	    
	}
	
	public static void main(String[] argc) throws Exception
	{
		EarlySalaryOTP obj = new EarlySalaryOTP();
		obj.GetOTPValue();
	
	}
}
