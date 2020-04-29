package flowstep.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class ConfigReader {
	private static final String propertyJmeterTestExecutionFilePath= System.getProperty("user.dir")+ "/src/test/resources/configuration/JmeterTestExecution.properties";
	private static final String propertyGeneralFilePath= System.getProperty("user.dir")+ "/src/test/resources/configuration/General.properties";
	private static final String propertyJMXMappingFilePath= System.getProperty("user.dir")+ "/src/test/resources/configuration/JMX_Mapping.properties";
	private static HashMap<String, String> globalConfig = new HashMap<String, String>();

	/*public ConfigReader(String filename) {
		Properties globalProp = loadPropertyFile(filename);
		globalConfig = loadConfig(globalProp);
	}*/

	private ConfigReader(String filename) {
		Properties globalProp = loadPropertyFile(filename);
		globalConfig = loadConfig(globalProp);
	}

	public static ConfigReader generalProperties() {
		return new ConfigReader(propertyGeneralFilePath);
	}

	public static ConfigReader jmeterTestExecutionProperties() {
		return new ConfigReader(propertyJmeterTestExecutionFilePath);
	}

	public static ConfigReader jmxMappingProperties() {
		return new ConfigReader(propertyJMXMappingFilePath);
	}

	private static Properties loadPropertyFile(String pathToPropertyLocation) {
		Properties pageProp = new Properties();
		try {
			File f = new File(pathToPropertyLocation);
			FileInputStream fis = new FileInputStream(f);
			pageProp.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageProp;
	}

	private static HashMap<String, String> loadConfig(Properties prop) {
		HashMap<String, String> propMap = new HashMap<String, String>();
		try {
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String val = prop.getProperty(key);
				propMap.put(key, val);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propMap;
	}

	public HashMap<String, String> getConfig() {
		return globalConfig;
	}
}
