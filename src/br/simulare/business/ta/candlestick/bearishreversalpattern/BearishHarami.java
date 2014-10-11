package br.simulare.business.ta.candlestick.bearishreversalpattern;

import java.util.List;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Bearish Harami.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class BearishHarami extends BearishReversalCandlePattern {

	public BearishHarami() {
		super(2);
	}
	
	protected boolean matched(List<Price> candles) {
		
		Price candle1 = candles.get(0);	
		Price candle2 = candles.get(1);	
	
		if (candle1.isWhiteCandlestick() && candle2.isBlackCandlestick()) {
			if ((candle2.getClosePrice() > candle1.getOpenPrice()) &&
					(candle2.getOpenPrice() < 
							candle1.getClosePrice())) {
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
		
		reversalSignal = MarketSignal.buildDowntrendSignal(MarketSignal.STRONG, "");
	
		if ((patternCandle2.getLowPrice() > patternCandle1.getOpenPrice())
				&& (patternCandle2.getHighPrice() < 
					patternCandle1.getClosePrice())) {
			reversalSignal.increaseStrength();
			justification += "shadows for the second candle engulfed.";
		} else {
			justification += "shadows for the second candle not engulfed.";
		}
		
		reversalSignal.setJustification(justification);
		return reversalSignal;
			
	}
	
	public String getShortName() {
		return "Bearish_Harami";
	}
	
	public static String getName() {
		return "Bearish Harami";
	}
	
}
