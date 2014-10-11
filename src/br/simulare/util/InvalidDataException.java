package br.simulare.util;

/**
 * For invalid data.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public class InvalidDataException extends Exception {

	private String msg;
	
	public InvalidDataException(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return msg;
	}
	
	public void setMessage(String msg) {
		this.msg = msg;
	}
	
}
