package br.simulare.business.ta.candlestick.bullishreversalpattern;

import java.util.List;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Bullish Harami.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class BullishHarami extends BullishReversalCandlePattern {

	public BullishHarami() {
		super(2);
	}
	
	protected boolean matched(List<Price> candles) {
		
		Price candle1 = candles.get(0);	
		Price candle2 = candles.get(1);
		
		if (candle1.isBlackCandlestick() && candle2.isWhiteCandlestick()) {
			if ((candle2.getOpenPrice() > candle1.getClosePrice()) &&
					(candle2.getClosePrice() <	
							candle1.getOpenPrice())) {
				return true;
			}
		}
		
		return false;
		
	}
	
	protected MarketSignal buildReversalSignal(List<Price> patternCandles,
			List<Price> predecessorCandles) {
		
		Price patternCandle1 = patternCandles.get(0);
		Price patternCandle2 = patternCandles.get(1);
		String justification = getName() + " identified: ";
		MarketSignal reversalSignal;
		
		reversalSignal = MarketSignal.buildUptrendSignal(MarketSignal.STRONG, "");
	
		if ((patternCandle2.getLowPrice() > 
				patternCandle1.getClosePrice())&&
						(patternCandle2.getHighPrice() < 
								patternCandle1.getOpenPrice())) {
			reversalSignal.increaseStrength();
			justification += "shadows for the second candle engulfed.";
		} else {
			justification += "shadows for the second candle not engulfed.";
		}
		
		reversalSignal.setJustification(justification);
		return reversalSignal;
		
	}
	
	public String getShortName() {		
		return "Bullish_Harami";		
	}
	
	public static String getName() {
		return "Bullish Harami";
	}
	
}
