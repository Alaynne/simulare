package br.simulare.control.validationhandler;

import java.util.Hashtable;

import br.simulare.business.math.MovingAvgType;
import br.simulare.business.math.MovingAvgTypeFactory;
import br.simulare.business.ta.strategy.TAStrategyImplementation;
import br.simulare.business.ta.strategy.movingavg.MovingAvgFactory;
import br.simulare.control.appcontroller.AppController;
import br.framesim.simulation.ta.strategy.TAStrategy;
import br.simulare.util.InvalidDataException;

/**
 * It handles the validation of Moving Average indicator. It implements Chain of 
 * Responsibility design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class MovingAvgValidationHandler extends 
		TAMethodValidationHandler {

	private final String PARAMETERS_NOT_INF_MSG = "'Moving Average indicator' TA Method: " +
			"Parameters not informed.";
	private final String INV_MOVING_AVG_TYPE_PARAM_MSG = 
			"'Moving Average indicator' TA Method: 'Moving Average Type' parameter" +
					" is invalid.";
	private final String MOVING_AVG_TYPE_PARAM_NOT_INF_MSG = 
			"'Moving Average indicator' TA Method: 'Moving Average Type' parameter" +
					" not informed.";
	private final String INV_MOVING_AVG_TIME_SPAN_PARAM_MSG = 
			"'Moving Average indicator' TA Method: 'Moving Average Time Span' parameter"
					+ " is invalid.";	
	private final String INV_MOVING_AVG_TIME_SPAN_PARAM_MSG_2 = 
			"'Moving Average indicator' TA Method - 'Moving Average Time Span' " +
			"parameter: ";
	private final String MOVING_AVG_TIME_SPAN_PARAM_NOT_INF_MSG =
			"'Moving Average indicator' TA Method: 'Moving Average Time Span' parameter"
					+ " not informed.";
	
	/**
	 * Its successor in the chain of responsibility is an instance of 
	 * ReversalCandlePatternValidationHandler class.  
	 */
	public MovingAvgValidationHandler() {
		super(new ReversalCandlePatternValidationHandler());
	}

	/**
	 * It validates the specified parameters, if the technical analysis method in
	 * question is Moving Average indicator.
	 * 
	 * @return a strategy based on Moving Average indicator.
	 * 
	 * @throws InvalidDataException - if the specified method is invalid or if the value 
	 * of the specified parameters are invalid for Moving Average indicator.
	 */
	public TAStrategy validateTAMethodParameters(int taMethod,
			Hashtable<Integer, String[]> parameters) throws InvalidDataException {
		
		String movingAvgType;
		int movingAvgTimeSpan;
		
		if (taMethod == AppController.TAMETHOD_MOVINGAVERAGE){
			if (parameters == null) {
				throw new InvalidDataException(PARAMETERS_NOT_INF_MSG);
			}
			
			try {
				movingAvgType = parameters.
						get(AppController.TAPARAMETER_MOVINGAVERAGETYPE)[0];
			} catch (Exception e) {
				throw new InvalidDataException(MOVING_AVG_TYPE_PARAM_NOT_INF_MSG);
			}
				
			if (!MovingAvgTypeFactory.isMovingAvgTypeValid(movingAvgType)) {
				throw new InvalidDataException(INV_MOVING_AVG_TYPE_PARAM_MSG);
			}
			
			try {
				movingAvgTimeSpan = Integer.parseInt(parameters.
					get(AppController.TAPARAMETER_MOVINGAVERAGETIMESPAN)[0]);
			} catch (NumberFormatException e) {
			  throw new InvalidDataException(INV_MOVING_AVG_TIME_SPAN_PARAM_MSG);
			} catch (Exception e) {
			  throw new InvalidDataException(MOVING_AVG_TIME_SPAN_PARAM_NOT_INF_MSG);
			} 	
			
			try {
				MovingAvgType.validateTimeSpan(movingAvgTimeSpan);
			} catch (InvalidDataException e) {
				e.setMessage(INV_MOVING_AVG_TIME_SPAN_PARAM_MSG_2 + e.getMessage());
				throw e;
			}
		
			return new TAStrategyImplementation(new MovingAvgFactory(movingAvgType,
					movingAvgTimeSpan));
		} else {
			return successor.validateTAMethodParameters(taMethod, parameters);
		}
		
	}
	
}
