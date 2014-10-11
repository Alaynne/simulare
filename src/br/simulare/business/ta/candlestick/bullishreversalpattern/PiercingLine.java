package br.simulare.business.ta.candlestick.bullishreversalpattern;

import java.util.List;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Piercing Line.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PiercingLine extends BullishReversalCandlePattern {
	
	public PiercingLine() {
		super(2);
	}

	protected boolean matched(List<Price> candles) {
		
		Price candle1 = candles.get(0);		
		Price candle2 = candles.get(1);
		
		if (candle1.isBlackCandlestick() && candle2.isWhiteCandlestick()) {
			if (candle2.getOpenPrice() < candle1.getClosePrice()) {
				if ((candle2.getClosePrice() >= 
						(candle1.getClosePrice() + 
							(candle1.getRealBody() / (double)2))) &&
								(candle2.getClosePrice() <	
									candle1.getOpenPrice())) {
					return true;
				}
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
		return "Piercing";
	}
	
	public static String getName() {
		return "Piercing Line";
	}
	
}
