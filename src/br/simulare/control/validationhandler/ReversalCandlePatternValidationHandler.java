package br.simulare.control.validationhandler;

import java.util.Hashtable;

import br.simulare.business.ta.candlestick.reversalpattern.ReversalCandlePatternTypeFactory;
import br.simulare.business.ta.strategy.TAStrategyImplementation;
import br.simulare.business.ta.strategy.reversalcandlepatterns.ReversalCandlePatternFactory;
import br.simulare.control.appcontroller.AppController;
import br.framesim.simulation.ta.strategy.TAStrategy;
import br.simulare.util.InvalidDataException;

/**
 * It handles the validation of the Reversal Candlestick Chart Patterns. It implements 
 * Chain of Responsibility design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ReversalCandlePatternValidationHandler extends 
		TAMethodValidationHandler {
	
	private final String PARAMETERS_NOT_INF_MSG = "'Reversal Candlestick Patterns' " +
			"TA Method: Parameters not informed.";
	private final String BULLISH_PATTERNS_PARAMETER_NOT_INF_MSG = 
			"'Reversal Candlestick Patterns' TA Method: " +
					"'Bullish Patterns' parameter not informed.";
	private final String BEARISH_PATTERNS_PARAMETER_NOT_INF_MSG = 
			"'Reversal Candlestick Patterns' TA Method: " +
					"'Bearish Patterns' parameter not informed.";
	private final String INV_BULLISH_PATTERNS_PARAMETER_MSG = 
			"'Reversal Candlestick Patterns' TA Method - " +
					"'Bullish Patterns' parameter: ";
	private final String INV_BEARISH_PATTERNS_PARAMETER_MSG = 
			"'Reversal Candlestick Patterns' TA Method - " +
					"'Bearish Patterns' parameter: ";
	private final String INV_TA_METHOD_MSG = 
			"Technical Analysis Method is invalid: ";
	
	// It does not have a successor in the chain of responsibility (successor == null).  
	public ReversalCandlePatternValidationHandler() {
		super(null);
	}

	/**
	 * It validates the specified parameters, if the technical analysis method in
	 * question is Reversal Candlestick Patterns.
	 * 
	 * @return a strategy based on Reversal Candlestick Patterns.
	 * 
	 * @throws InvalidDataException - if the specified method is invalid or if the value 
	 * of the specified parameters are invalid for Reversal Candlestick Patterns.
	 */
	public TAStrategy validateTAMethodParameters(int taMethod,
			Hashtable<Integer, String[]> parameters) throws InvalidDataException {
		
		String[] bullishPatternsStr, bearishPatternsStr;
		
		if (taMethod == 
				AppController.TAMETHOD_REVERSALCANDLEPATTERNS) {
			if (parameters == null) {
				throw new InvalidDataException(PARAMETERS_NOT_INF_MSG);
			}
				
			if ((bullishPatternsStr = parameters.
					get(AppController.TAPARAMETER_BULLISHPATTERNS)) == null) {
				throw new InvalidDataException(BULLISH_PATTERNS_PARAMETER_NOT_INF_MSG);
			} else if (bullishPatternsStr.length == 0) {
				throw new InvalidDataException(BULLISH_PATTERNS_PARAMETER_NOT_INF_MSG);
			}
					
			for (int i = 0; i < bullishPatternsStr.length; i++) {
				if (!ReversalCandlePatternTypeFactory.
						isBullishPatternTypeValid(bullishPatternsStr[i])) {
					throw new InvalidDataException(INV_BULLISH_PATTERNS_PARAMETER_MSG 
							+ bullishPatternsStr[i] + " is an invalid pattern.");
				}
			}
					
			if ((bearishPatternsStr = parameters.
					get(AppController.TAPARAMETER_BEARISHPATTERNS)) == null) {
			   throw new InvalidDataException(BEARISH_PATTERNS_PARAMETER_NOT_INF_MSG);
			} else if (bearishPatternsStr.length == 0) {
			   throw new InvalidDataException(BEARISH_PATTERNS_PARAMETER_NOT_INF_MSG);
			}
					
			for (int i = 0; i < bearishPatternsStr.length; i++) {
				if (!ReversalCandlePatternTypeFactory.
						isBearishPatternTypeValid(bearishPatternsStr[i])) {
					throw new InvalidDataException(INV_BEARISH_PATTERNS_PARAMETER_MSG
							+ bearishPatternsStr[i] + " is an invalid pattern.");
				}
			}	
			
			return new TAStrategyImplementation(new ReversalCandlePatternFactory(bullishPatternsStr,
					bearishPatternsStr));
		} else {
			throw new InvalidDataException(INV_TA_METHOD_MSG + 
					taMethod + ".");
		}
		
	}

}
