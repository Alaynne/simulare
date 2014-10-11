package br.simulare.util;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * It manages the application configurations, which are saved in the file 
 * "Configuration.properties". It implements Singleton design pattern.
 * 
 * @author Rodrigo Paes
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ConfigurationManager {
	
	// Logger for this class
	private static final Logger logger = Logger
			.getLogger(ConfigurationManager.class);

	private static ConfigurationManager singletonInstance = null;

	private Properties properties;

	private ConfigurationManager() throws IOException {
		
		properties = new Properties();
		properties.load(ClassLoader.
				getSystemResourceAsStream("Configuration.properties"));
	
	}

	// It implements Singleton design pattern.
	public static ConfigurationManager getInstance() 
			throws ConfigurationException {
		
		if (singletonInstance == null) {
			try {
				singletonInstance = new ConfigurationManager();
			} catch (IOException e) {
				logger.error("ConfigurationManager()", e);
				throw new ConfigurationException(e.getMessage());
			}
		}
		return singletonInstance;
		
	}
	
	// It returns the configuration value for the specified key.
	public String getValue(String key) {
		
		try {
			return properties.get(key).toString();
		} catch (NullPointerException e) {
			if (logger.isInfoEnabled()) {
				logger.info("getValue(String) - Key not informed.");
			}
			return "";
		} catch (MissingResourceException e) {
			if (logger.isInfoEnabled()) {
				logger.info("getValue(String) - " + key + 
						": Non-existent key.");
			}
			return key;
		} 	
		
	}
	
	/**
	 * It sets, in cache, the configuration value for the specified key with the new 
	 * specified value.
	 */
	public void setValueInCache(String key, String value) {
		
		properties.setProperty(key, value);
		
	}

}
