package br.simulare.business.ta.candlestick.bearishreversalpattern;

import java.util.List;

import org.apache.log4j.Logger;

import br.simulare.business.ta.fundaments.Gap;
import br.simulare.util.ConfigurationManager;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Bearish Abandoned Baby.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class BearishAbandonedBaby extends BearishReversalCandlePattern {

	// Logger for this class
	private static final Logger logger = Logger.
			getLogger(BearishAbandonedBaby.class);
	
	private double secondBodyRelativeToOtherBodiesParameter;
	private final double DEFAULT_SECOND_BODY_RELATIVE_TO_OTHER_BODIES_PARAMETER = 0.3;
	
	public BearishAbandonedBaby() {
		
		super(3);		
		try {
			secondBodyRelativeToOtherBodiesParameter = Double.
					parseDouble(ConfigurationManager.getInstance().
					getValue("abandonedBaby.secondBodyRelativeToOtherBodiesParameter"));
		} catch (Exception e) {
			secondBodyRelativeToOtherBodiesParameter = 
					DEFAULT_SECOND_BODY_RELATIVE_TO_OTHER_BODIES_PARAMETER;
			if (logger.isInfoEnabled()) {
				logger.info("BearishAbandonedBaby() - Using " +
						"default configuration.");
			}
		}
		
	}
		
	protected boolean matched(List<Price> candles) {

		Price candle1 = candles.get(0);
		Price candle2 = candles.get(1);
		Price candle3 = candles.get(2);
	
		if (candle1.isWhiteCandlestick() && candle3.isBlackCandlestick()) {
			if ((((candle2.getRealBody() / candle1.getRealBody()) < 
					secondBodyRelativeToOtherBodiesParameter) || ((candle2.getRealBody() / 
					candle3.getRealBody()) < secondBodyRelativeToOtherBodiesParameter)) && 
					(Gap.hasUpCompleteGap(candle1, candle2)) && 
					(Gap.hasDownCompleteGap(candle2, candle3))) {
				return true;
			}
		}	
		
		return false;
		
	}
	
	protected MarketSignal buildReversalSignal(List<Price> patternCandles,
			List<Price> predecessorCandles) {
			
		return MarketSignal.buildDowntrendSignal(MarketSignal.VERY_STRONG, 
				getName() + " identified.");

	}
	
	public String getShortName() {
		return "Bearish_Baby";
	}
	
	public static String getName() {
		return "Bearish Abandoned Baby";
	}
		
}
