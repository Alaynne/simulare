package br.simulare.business.ta.candlestick.bullishreversalpattern;

import java.util.List;

import org.apache.log4j.Logger;

import br.simulare.business.ta.fundaments.Gap;
import br.simulare.util.ConfigurationManager;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Bullish Abandoned Baby.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class BullishAbandonedBaby extends BullishReversalCandlePattern {

	// Logger for this class
	private static final Logger logger = Logger.
			getLogger(BullishAbandonedBaby.class);
	
	private double secondBodyRelativeToOtherBodiesParameter;
	private final double DEFAULT_SECOND_BODY_RELATIVE_TO_OTHER_BODIES_PARAMETER = 0.3;
	
	public BullishAbandonedBaby() {
		
		super(3);	
		try {
			secondBodyRelativeToOtherBodiesParameter = Double.
					parseDouble(ConfigurationManager.getInstance().
					getValue("abandonedBaby.secondBodyRelativeToOtherBodiesParameter"));
		} catch (Exception e) {
			secondBodyRelativeToOtherBodiesParameter = 
					DEFAULT_SECOND_BODY_RELATIVE_TO_OTHER_BODIES_PARAMETER;
			if (logger.isInfoEnabled()) {
				logger.info("BullishAbandonedBaby() - Using "
						+ "default configuration.");
			}
		}
		
	}
			
	protected boolean matched(List<Price> candles) {

		Price candle1 = candles.get(0);
		Price candle2 = candles.get(1);
		Price candle3 = candles.get(2);
	
		if (candle1.isBlackCandlestick() && candle3.isWhiteCandlestick()) {
				if ((((candle2.getRealBody() / candle1.getRealBody()) < 
					secondBodyRelativeToOtherBodiesParameter) || ((candle2.getRealBody() / 
					candle3.getRealBody()) < secondBodyRelativeToOtherBodiesParameter)) && 
					(Gap.hasDownCompleteGap(candle1, candle2)) && 
					(Gap.hasUpCompleteGap(candle2, candle3))) {
				return true;
			}
		}	
		
		return false;
		
	}
	
	protected MarketSignal buildReversalSignal(List<Price> patternCandles,
			List<Price> predecessorCandles) {
			
		return MarketSignal.buildUptrendSignal(MarketSignal.VERY_STRONG, 
				getName() + " identified.");

	}
	
	public String getShortName() {
		return "Bullish_Baby";
	}
	
	public static String getName() {
		return "Bullish Abandoned Baby";
	}
	
}
