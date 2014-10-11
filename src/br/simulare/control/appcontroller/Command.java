package br.simulare.control.appcontroller;

import java.util.Hashtable;

import br.framesim.util.DBException;
import br.simulare.util.InvalidDataException;

/**
 * It defines an interface for executing a feature of the application. It implements
 * Command design pattern.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public interface Command {
	
	public Object execute(Hashtable<Integer, Object> parameters) 
			throws InvalidDataException, DBException;

}
