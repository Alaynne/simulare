package br.simulare.business.ta.candlestick.bullishreversalpattern;

import java.util.ArrayList;
import java.util.List;

import br.simulare.business.ta.fundaments.Volume;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Bullish Engulfing.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class BullishEngulfing extends BullishReversalCandlePattern {
	
	private double volumeIncreaseParameter;
	
	public BullishEngulfing(double volumeIncreaseParameter) {
		
		super(2);		
		this.volumeIncreaseParameter = volumeIncreaseParameter;
	
	}
		
	protected boolean matched(List<Price> candles) {
		
		Price candle1 = candles.get(0);	
		Price candle2 = candles.get(1);
		
		if (candle1.isBlackCandlestick() && candle2.isWhiteCandlestick()) {
			if ((candle2.getOpenPrice() < candle1.getClosePrice()) && 
					(candle2.getClosePrice() > 
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
		List<Price> auxCandles = new ArrayList<Price>();
		
		reversalSignal = MarketSignal.buildUptrendSignal(MarketSignal.STRONG, "");
	
		auxCandles.addAll(predecessorCandles);
		auxCandles.add(patternCandle1);
		if (((double)Volume.numSharesTraded(patternCandle2) / 
				Volume.avgNumSharesTraded(auxCandles)) >= volumeIncreaseParameter) {
			reversalSignal.increaseStrength();
			justification += "with volume increase for the second day.";
		} else {
			justification += "without volume increase for the second day.";
		}
		
		reversalSignal.setJustification(justification);
		return reversalSignal;

	}
	
	public String getShortName() {		
		return "Bullish_Engulfing";
	}
	
	public static String getName() {
		return "Bullish Engulfing";
	}
	
}
