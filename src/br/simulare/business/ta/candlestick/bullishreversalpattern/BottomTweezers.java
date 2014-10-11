package br.simulare.business.ta.candlestick.bullishreversalpattern;

import java.util.List;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Bottom Tweezers.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class BottomTweezers extends BullishReversalCandlePattern {

	public BottomTweezers() {
		super(2);
	}
	
	protected boolean matched(List<Price> candles) {
		
		Price candle1 = candles.get(0);
		Price candle2 = candles.get(1);
		
		if (candle1.isBlackCandlestick() && 
				(candle2.getLowPrice() == candle1.getLowPrice())) {
			return true;
		}
		
		return false;
		
	}
	
	protected MarketSignal buildReversalSignal(List<Price> patternCandles,
			List<Price> predecessorCandles) {
		
		Price patternCandle2 = patternCandles.get(1);
		String justification = getName() + " identified: ";
		MarketSignal reversalSignal;
		
		reversalSignal = MarketSignal.buildUptrendSignal(MarketSignal.STRONG, "");
	
		if (patternCandle2.isWhiteCandlestick()) {
			reversalSignal.increaseStrength();
			justification += "second candle is white.";
		} else {
			justification += "second candle is black.";
		}
		
		reversalSignal.setJustification(justification);
		return reversalSignal;
		
	}
	
	public String getShortName() {		
		return "Bottom_Tweezers";
	}
	
	public static String getName() {
		return "Bottom Tweezers";
	}
	
}
