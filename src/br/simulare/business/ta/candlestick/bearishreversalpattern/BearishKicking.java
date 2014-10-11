package br.simulare.business.ta.candlestick.bearishreversalpattern;

import java.util.ArrayList;
import java.util.List;

import br.simulare.business.ta.fundaments.Gap;
import br.simulare.business.ta.fundaments.Volume;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Bearish Kicking.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class BearishKicking extends BearishReversalCandlePattern {
	
	private double littleShadowParameter;
	private double volumeIncreaseParameter;
	
	public BearishKicking(double littleShadowParameter,double volumeIncreaseParameter){
		
		super(2);		
		this.littleShadowParameter = littleShadowParameter;
		this.volumeIncreaseParameter = volumeIncreaseParameter;
	
	}
			
	protected boolean matched(List<Price> candles) {

		Price candle1 = candles.get(0);
		Price candle2 = candles.get(1);
	
		if (candle1.isWhiteCandlestick() && (candle1.getUpperShadow() <= 
				(candle1.getRealBody() * littleShadowParameter)) && 
				(candle1.getLowerShadow() <= (candle1.getRealBody() * 
						littleShadowParameter))) {
			if (candle2.isBlackCandlestick() && (candle2.getUpperShadow() <= 
					(candle2.getRealBody() * littleShadowParameter)) && 
					(candle2.getLowerShadow() <= (candle2.getRealBody() * 
							littleShadowParameter))) {
				if (Gap.hasDownCompleteGap(candle1, candle2)) {
					return true;
				}
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
				
		reversalSignal = MarketSignal.buildDowntrendSignal(MarketSignal.STRONG, "");
	
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
		return "Bearish_Kicking";
	}
	
	public static String getName() {	
		return "Bearish Kicking";
	}
	
}
