package br.simulare.business.ta.candlestick.bearishreversalpattern;

import java.util.List;

import br.simulare.business.ta.fundaments.Volume;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Shooting Star.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ShootingStar extends BearishReversalCandlePattern {
	
	private double littleShadowParameter; 
	private double bigShadowParameter;
	private double volumeIncreaseParameter;
	
	public ShootingStar(double littleShadowParameter, 
			double bigShadowParameter, double volumeIncreaseParameter) {
		
		super(1);	
		this.littleShadowParameter = littleShadowParameter;
		this.bigShadowParameter = bigShadowParameter;
		this.volumeIncreaseParameter = volumeIncreaseParameter;
	
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
		
		reversalSignal = MarketSignal.
			buildDowntrendSignal(MarketSignal.MEDIUM, "");
	
		if (patternCandle.isBlackCandlestick()) {
			reversalSignal.increaseStrength();
			justification += "shooting star is black ";
		} else {
			justification += "shooting star is white ";
		}

		if (((double)Volume.numSharesTraded(patternCandle) / 
				Volume.avgNumSharesTraded(predecessorCandles)) >= volumeIncreaseParameter) {
			reversalSignal.increaseStrength();
			justification += "with volume increase.";
		} else {
			justification += "without volume increase.";
		}
		
		reversalSignal.setJustification(justification);
		return reversalSignal;
	
	}
	
	public String getShortName() {
		return "Shooting_Star";
	}
	
	public static String getName() {
		return "Shooting Star";
	}
	
}
