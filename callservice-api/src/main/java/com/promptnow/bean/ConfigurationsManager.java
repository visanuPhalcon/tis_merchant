package com.promptnow.bean;

import java.util.Properties;

import com.promptnow.utility.UtilAsset;

public class ConfigurationsManager {

	private static ConfigurationsManager manager = null;
	
	private Properties properties = null;
	
	public static ConfigurationsManager getInstance()
	{
		if(manager == null)
		{
			manager = new ConfigurationsManager();
		}
		
		return manager;
	}
	
	public static void clearInstance()
	{
		manager = null;
	}
	
	public static boolean isAllowSessionChange()
	{
		boolean result = false;
		
		result = getInstance().getConfiguration("network.allowsessionchange").equals("true");
		
		return result;
	}
	
	public static boolean isAllowRootedDevice()
	{
		boolean result = false;
		
		result = getInstance().getConfiguration("network.allowrooteddevice").equals("true");
		
		return result;
	}

	public static boolean isDHECKeyExchange(){
		boolean result = false;
		result = getInstance().getConfiguration("keyexchange.DHEC").equals("true");
		return result;
	}

	public static boolean isTrustAllCertificates(){
		boolean result = false;
		result = getInstance().getConfiguration("certificates.trustAll").equals("true");
		return result;
	}
	
	public ConfigurationsManager()
	{
		reloadAllConfigurations();
	}
	
	public void reloadAllConfigurations()
	{
		properties = UtilAsset.loadPropertiesFile("promptnow.properties");
	}
	
	public String getConfiguration(String configKey)
	{
		String result = "";
		
		if(properties.containsKey(configKey))
		{
			result = properties.getProperty(configKey);
		}
		
		return result;
	}
}
