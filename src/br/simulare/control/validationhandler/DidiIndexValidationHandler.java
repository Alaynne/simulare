package br.simulare.control.validationhandler;

import java.util.Hashtable;

import br.simulare.business.ta.strategy.TAStrategyImplementation;
import br.simulare.business.ta.strategy.didiindex.DidiIndexFactory;
import br.simulare.control.appcontroller.AppController;
import br.framesim.simulation.ta.strategy.TAStrategy;
import br.simulare.util.InvalidDataException;

/**
 * It handles the validation of Didi Index indicator. It implements Chain of 
 * Responsibility design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class DidiIndexValidationHandler extends TAMethodValidationHandler{

	/**
	 * Its successor in the chain of responsibility is an instance of 
	 * MovingAvgValidationHandler class.  
	 */
	public DidiIndexValidationHandler() {
		super(new MovingAvgValidationHandler());
	}

	/**
	 * It validates the specified parameters, if the technical analysis method in
	 * question is Didi Index indicator.
	 * 
	 * @return a strategy based on Didi Index indicator.
	 * 
	 * @throws InvalidDataException - if the specified method is invalid or if the value 
	 * of the specified parameters are invalid for Didi Index indicator.
	 */
	public TAStrategy validateTAMethodParameters(int taMethod,
			Hashtable<Integer, String[]> parameters) throws InvalidDataException {
		
		if (taMethod == AppController.TAMETHOD_DIDIINDEX) {
			return new TAStrategyImplementation(new DidiIndexFactory());
		} else {
			return successor.validateTAMethodParameters(taMethod, parameters);
		}

	}

}
