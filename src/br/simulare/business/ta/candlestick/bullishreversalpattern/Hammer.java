package br.simulare.business.ta.candlestick.bullishreversalpattern;

import java.util.List;

import br.simulare.business.ta.fundaments.Volume;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Hammer.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class Hammer extends BullishReversalCandlePattern {
	
	private double littleShadowParameter;
	private double bigShadowParameter;
	private double volumeIncreaseParameter;
	
	public Hammer(double littleShadowParameter, 
			double bigShadowParameter, double volumeIncreaseParameter) {
		
		super(1);
		this.littleShadowParameter = littleShadowParameter;
		this.bigShadowParameter = bigShadowParameter;
		this.volumeIncreaseParameter = volumeIncreaseParameter;
	
	}
	
	public double getLittleShadowParameter() {
		return littleShadowParameter;
	}
	
	public double getBigShadowParameter() {
		return bigShadowParameter;
	}
	
	public double getVolumeIncreaseParameter() {
		return volumeIncreaseParameter;
	}
	
	protected boolean matched(List<Price> candles) {
		
		Price candle = candles.get(0);
		
		if (!candle.isDOJICandlestick()) {
			if ((candle.getLowerShadow() / candle.getRealBody()) >= 
					bigShadowParameter) {
				if (candle.getUpperShadow() <= (candle.getRealBody() +
						candle.getLowerShadow()) * littleShadowParameter){
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
			
		reversalSignal = MarketSignal.buildUptrendSignal(MarketSignal.MEDIUM,
				"");
	
		if (patternCandle.isWhiteCandlestick()) {
			reversalSignal.increaseStrength();
			justification += "white hammer ";
		} else {
			justification += "black hammer ";
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
		return "Hammer";		
	}
	
	public static String getName() {
		return "Hammer";
	}
	
}
