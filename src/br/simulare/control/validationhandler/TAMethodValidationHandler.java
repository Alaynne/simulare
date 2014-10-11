package br.simulare.control.validationhandler;

import java.util.Hashtable;

import br.framesim.simulation.ta.strategy.TAStrategy;
import br.simulare.util.InvalidDataException;

/**
 * It handles the validation of a technical analysis method. It implements Chain of 
 * Responsibility design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public abstract class TAMethodValidationHandler {

	// Successor in the chain of responsibility.
	protected TAMethodValidationHandler successor;
	
	public TAMethodValidationHandler(TAMethodValidationHandler successor) {
		this.successor = successor;
	}
	
	/**
	 * It validates the specified parameters for the specified technical analysis method.
	 * 
	 * @return a strategy based on the specified technical analysis method.
	 * 
	 * @throws InvalidDataException - if the specified method is invalid or if the value 
	 * of the specified parameters are invalid for the technical analysis method in 
	 * question.
	 */
	public abstract TAStrategy validateTAMethodParameters(int taMethod, 
			Hashtable<Integer, String[]> parameters) throws InvalidDataException;
	
}
