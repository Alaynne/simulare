package br.simulare.business.ta.candlestick.bearishreversalpattern;

import java.util.List;

import br.simulare.business.ta.fundaments.Volume;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Hanging Man.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class HangingMan extends BearishReversalCandlePattern {

	private double littleShadowParameter; 
	private double bigShadowParameter;
	private double volumeIncreaseParameter;
	
	public HangingMan(double littleShadowParameter, 
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
		
		reversalSignal = MarketSignal.
			buildDowntrendSignal(MarketSignal.MEDIUM, "");
	
		if (patternCandle.isBlackCandlestick()) {
			reversalSignal.increaseStrength();
			justification += "hanging man is black ";
		} else {
			justification += "hanging man is white ";
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
		return "Hanging_Man";
	}
	
	public static String getName() {
		return "Hanging Man";
	}
	
}
