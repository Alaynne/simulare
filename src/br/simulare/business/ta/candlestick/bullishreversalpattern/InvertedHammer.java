package br.simulare.business.ta.candlestick.bullishreversalpattern;

import java.util.List;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Inverted Hammer.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class InvertedHammer extends BullishReversalCandlePattern {
		
	private double littleShadowParameter;
	private double bigShadowParameter;
	
	public InvertedHammer(double littleShadowParameter, 
			double bigShadowParameter) {
		
		super(1);
		this.littleShadowParameter = littleShadowParameter;
		this.bigShadowParameter = bigShadowParameter;
	
	}
		
	protected boolean matched(List<Price> candles) {
		
		Price candle = candles.get(0);
				
		if (!candle.isDOJICandlestick()) {
			if((candle.getUpperShadow() / candle.getRealBody()) >= 
					bigShadowParameter) {
				if (candle.getLowerShadow() <= (candle.getRealBody() +
						candle.getUpperShadow()) * littleShadowParameter){
					return true;
				}
			}
		}	
		
		return false;
		
	}
	
	protected MarketSignal buildReversalSignal(List<Price> patternCandles,
			List<Price> predecessorCandles) {
		
		Price patternCandle = patternCandles.get(0);
		String justification = getName() + " identified: ";
		MarketSignal reversalSignal;
		
		reversalSignal = MarketSignal.buildUptrendSignal(MarketSignal.STRONG, "");
	
		if (patternCandle.isWhiteCandlestick()) {
			reversalSignal.increaseStrength();
			justification += "white inverted hammer.";
		} else {
			justification += "black inverted hammer.";
		}
		
		reversalSignal.setJustification(justification);
		return reversalSignal;
	
	}
	
	public String getShortName() {
		return "Inv_Hammer";		
	}
	
	public static String getName() {
		return "Inverted Hammer";
	}
	
}
