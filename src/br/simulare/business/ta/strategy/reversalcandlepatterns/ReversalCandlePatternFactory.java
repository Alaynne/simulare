package br.simulare.business.ta.strategy.reversalcandlepatterns;

import java.util.ArrayList;
import java.util.List;

import br.framesim.simulation.ta.strategy.TAAlgorithm;
import br.framesim.simulation.ta.strategy.AlgorithmFactory;
import br.simulare.business.ta.candlestick.bearishreversalpattern.BearishReversalCandlePattern;
import br.simulare.business.ta.candlestick.bullishreversalpattern.BullishReversalCandlePattern;
import br.simulare.business.ta.candlestick.reversalpattern.ReversalCandlePatternTypeFactory;
import br.simulare.business.ta.method.reversalchartpattern.ReversalCandlePatterns;

/**
 * It factories the reversal candlestick pattern algorithm. It implements Factory
 * design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ReversalCandlePatternFactory implements AlgorithmFactory {
	
	private String[] bullishPatternNames;
	private String[] bearishPatternNames;
	private ReversalCandlePatternTypeFactory reversalCandlePatternTypeFactory;
	
	public ReversalCandlePatternFactory(String[] bullishPatternNames,
			String[] bearishPatternNames) {
		
		this.bullishPatternNames = bullishPatternNames;
		this.bearishPatternNames = bearishPatternNames;
		reversalCandlePatternTypeFactory = new ReversalCandlePatternTypeFactory();
		
	}

	// It is a factory method.
	public TAAlgorithm buildAlgorithm() {
		
		List<BullishReversalCandlePattern> bullishPatterns = 
				new ArrayList<BullishReversalCandlePattern>();
		List<BearishReversalCandlePattern> bearishPatterns = 
				new ArrayList<BearishReversalCandlePattern>();
		
		for (int i = 0; i < bullishPatternNames.length; i++) {
			bullishPatterns.add(reversalCandlePatternTypeFactory.
					buildBullishPattern(bullishPatternNames[i]));
		}
		
		for (int i = 0; i < bearishPatternNames.length; i++) {
			bearishPatterns.add(reversalCandlePatternTypeFactory.
					buildBearishPattern(bearishPatternNames[i]));
		}

		return new ReversalCandlePatternAlgorithm(
				new ReversalCandlePatterns(bullishPatterns, bearishPatterns));

	}
	
	public String toString() {			
		
		String result = "Reversal Candlestick Patterns ";
		
		for(String name: bullishPatternNames) {
			result += "[" + name + "]";
		}
		
		for(String name: bearishPatternNames) {
			result += "[" + name + "]";
		}
		
		return result;
	}

}
