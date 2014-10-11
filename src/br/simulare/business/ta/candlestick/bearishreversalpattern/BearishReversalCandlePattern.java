package br.simulare.business.ta.candlestick.bearishreversalpattern;

import java.util.ArrayList;
import java.util.List;

import br.simulare.business.ta.candlestick.reversalpattern.ReversalCandlePatternType;
import br.simulare.business.ta.fundaments.Movement;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;
import br.simulare.util.Util;

/**
 * Bearish Reversal Candlestick Pattern. It implements Template Method design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public abstract class BearishReversalCandlePattern extends 
		ReversalCandlePatternType {
	
	public BearishReversalCandlePattern(int numCandles) {
		super(numCandles);
	}
	
	protected boolean isUpMoviment(List<Price> referenceCandles,
			List<Price> predecessorCandles) {
		
		List<Price> auxCandles = new ArrayList<Price>();
	
		auxCandles.addAll(predecessorCandles);
		auxCandles.add(referenceCandles.get(0));
		
		return (Movement.
				isUp(Util.buildClosePriceArray(auxCandles)));
		
	}
	
	// It is a template method.
	public final MarketSignal getDowntrendSignal(List<Price> candles){

		List<Price> referenceCandles, predecessorCandles;

		referenceCandles = candles.subList(candles.size() - 
				NUM_CANDLES, candles.size());
		predecessorCandles = candles.
		subList(candles.size() - NUM_CANDLES - 
				NUM_PREDECESSOR_CANDLES, 
						candles.size() - NUM_CANDLES);

		if (isUpMoviment(referenceCandles, predecessorCandles)) {
			if (matched(referenceCandles)) {
				return buildReversalSignal(referenceCandles, predecessorCandles);
			}
		}
		
		return null;

	}
	
}
