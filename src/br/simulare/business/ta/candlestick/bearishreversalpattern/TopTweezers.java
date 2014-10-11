package br.simulare.business.ta.candlestick.bearishreversalpattern;

import java.util.List;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Top Tweezers.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class TopTweezers extends BearishReversalCandlePattern {

	public TopTweezers() {
		super(2);
	}
	
	protected boolean matched(List<Price> candles) {
		
		Price candle1 = candles.get(0);
		Price candle2 = candles.get(1);
	
		if (candle1.isWhiteCandlestick() && 
				(candle2.getHighPrice() == candle1.getHighPrice())) {
			return true;
		}
		
		return false;
		
	}
	
	protected MarketSignal buildReversalSignal(List<Price> patternCandles,
			List<Price> predecessorCandles) {
		
		Price patternCandle2 = patternCandles.get(1);
		String justification = getName() + " identified: ";
		MarketSignal reversalSignal;
		
		reversalSignal = MarketSignal.buildDowntrendSignal(MarketSignal.STRONG, "");
	
		if (patternCandle2.isBlackCandlestick()) {
			reversalSignal.increaseStrength();
			justification += "second candle is black.";
		} else {
			justification += "second candle is white.";
		}
		
		reversalSignal.setJustification(justification);
		return reversalSignal;
		
	}
	
	public String getShortName() {
		return "Top_Tweezers";
	}
	
	public static String getName() {
		return "Top Tweezers";
	}
	
}
