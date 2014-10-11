package br.simulare.business.ta.candlestick.reversalpattern;

import java.util.List;

import org.apache.log4j.Logger;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;
import br.simulare.util.ConfigurationManager;

/**
 * It represents a type of reversal candlestick pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public abstract class ReversalCandlePatternType {
	
	// Logger for this class
	private static final Logger logger = Logger.
			getLogger(ReversalCandlePatternType.class);
	
	// Number of candles that compose the pattern.
	protected final int NUM_CANDLES;
	// Number of predecessor candles required for identifying the pattern.
	protected final int NUM_PREDECESSOR_CANDLES;
	// Default number of predecessor candles required for identifying the pattern.
	protected final int DEFAULT_NUM_PREDECESSOR_CANDLES = 3;
	
	public ReversalCandlePatternType(int numCandles) {
		
		int numPredecessorCandles;
		
		NUM_CANDLES = numCandles;	
		try {
			numPredecessorCandles = Integer.parseInt(ConfigurationManager.
				getInstance().
				getValue("reversalCandlePatterns.numPredecessorCandles"));
		} catch (Exception e) {
			numPredecessorCandles = DEFAULT_NUM_PREDECESSOR_CANDLES;
			if (logger.isInfoEnabled()) {
				logger.info("ReversalCandlePatternType() - Using " +
						"default configuration.");
			}
		}
		NUM_PREDECESSOR_CANDLES = numPredecessorCandles;
		
	}
	
	// It returns the number of candles required for identifying the pattern.
	public int getNumRequiredCandles() {
		return (NUM_PREDECESSOR_CANDLES + NUM_CANDLES);
	}
	
	// It verifies if the specified candles match with the pattern.
	protected abstract boolean matched(List<Price> candles);
	
	/**
	 * It builds a reversal market signal based on the specified pattern candles and 
	 * predecessor candles.
	 */
	protected abstract MarketSignal buildReversalSignal(List<Price> 
			patternCandles, List<Price> predecessorCandles);
	
	// It returns the short name of the pattern.
	public abstract String getShortName();
	
}
