package Test.Assignment.Common.CommonAction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class ConfigurationManager {
	
	   public String read_Configfile(String scriptname)
	   {
		   String property=null;
		   try
		   {
			   Properties or_prop=new Properties();
			   FileInputStream or_file=new FileInputStream(System.getProperty("user.dir")+"\\Configurations\\ApplicationConfig.properties");
			   or_prop.load(or_file);
			   property=or_prop.getProperty(scriptname);
		   } catch (FileNotFoundException e) 
		   {
			   System.out.println("Unable to find the file for properties");
		   } catch (IOException e) 
		   {
			   e.printStackTrace();
		   }
			return property;
	   }
}
