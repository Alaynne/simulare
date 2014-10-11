package br.simulare.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * It manages the application messages, which are saved in the file "Messages.properties".
 * It implements Singleton design pattern.
 * 
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public class MessageManager {
	
	// Logger for this class
	private static final Logger logger = Logger.getLogger(MessageManager.class);
	
	private ResourceBundle messages;

	private static MessageManager singletonInstance = null;

	private MessageManager() {		
		this.messages = ResourceBundle.getBundle("Messages");
	}

	// It implements Singleton design pattern.
	public static MessageManager getInstance() {
		
		if (singletonInstance == null) {
			singletonInstance = new MessageManager();
		}
		return singletonInstance;
	
	}

	// It changes the messages to English.
	public void toEnglish() {
		
		Locale locale = new Locale("en", "US");
		
		this.messages = ResourceBundle.getBundle("Messages", locale);
	
	}

	// It changes the messages to Portuguese.
	public void toPortuguese() {
		
		Locale locale = new Locale("pt", "BR");
		
		this.messages = ResourceBundle.getBundle("Messages", locale);
	
	}

	// It returns the message for the specified key.
	public String getMessage(String key) {
				
		try {
			return this.messages.getString(key);
		} catch (NullPointerException e) {
			if (logger.isInfoEnabled()) {
				logger.info("getMessage(String) - Key not informed.");
			}
			return "";
		} catch (MissingResourceException e) {
			if (logger.isInfoEnabled()) {
				logger.info("getMessage(String) - " + key + 
						": Non-existent key.");
			}
			return key;
		} 
				
	}

}
