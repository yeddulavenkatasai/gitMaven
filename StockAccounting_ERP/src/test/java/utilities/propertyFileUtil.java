package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class propertyFileUtil 
{
public static String getValueforkey(String key)throws Throwable
{
	Properties config = new Properties();
	config.load(new FileInputStream("./propertyFiles/Environment.properties"));
	return config.getProperty(key);
	
}
	
	
}
