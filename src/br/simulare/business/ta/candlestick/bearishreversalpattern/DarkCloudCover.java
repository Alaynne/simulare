package br.simulare.business.ta.candlestick.bearishreversalpattern;

import java.util.List;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Dark Cloud Cover.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class DarkCloudCover extends BearishReversalCandlePattern {

	public DarkCloudCover() {
		super(2);
	}

	protected boolean matched(List<Price> candles) {
		
		Price candle1 = candles.get(0);		
		Price candle2 = candles.get(1);		
	
		if (candle1.isWhiteCandlestick() && candle2.isBlackCandlestick()) {
			if (candle2.getOpenPrice() > candle1.getClosePrice()) {
				if ((candle2.getClosePrice() <= 
					(candle1.getOpenPrice() + 
						(candle1.getRealBody() / (double)2))) &&
							(candle2.getClosePrice() >	
								candle1.getOpenPrice())) {
					return true;
				}
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
		return "Dark_Cloud";
	}
	
	public static String getName() {
		return "Dark Cloud";
	}
	
}
