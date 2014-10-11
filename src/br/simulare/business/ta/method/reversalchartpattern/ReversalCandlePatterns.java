package br.simulare.business.ta.method.reversalchartpattern;

import java.util.List;

import br.simulare.business.ta.candlestick.bearishreversalpattern.BearishReversalCandlePattern;
import br.simulare.business.ta.candlestick.bullishreversalpattern.BullishReversalCandlePattern;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;

/**
 * Reversal Candlestick Patterns. 
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ReversalCandlePatterns implements ReversalChartPattern {

	private List<BullishReversalCandlePattern> bullishPatterns;
	private List<BearishReversalCandlePattern> bearishPatterns;
	private List<Price> candlesForAnalysis;
	
	public ReversalCandlePatterns(List<BullishReversalCandlePattern> 
			bullishPatterns, List<BearishReversalCandlePattern> bearishPatterns) {
		
		this.bullishPatterns = bullishPatterns;
		this.bearishPatterns = bearishPatterns;
		
	}
	
	public List<BullishReversalCandlePattern> getBullishPatterns() {
		return bullishPatterns;
	}
	
	public List<BearishReversalCandlePattern> getBearishPatterns() {
		return bearishPatterns;
	}

	public void setCandlesForAnalysis(List<Price> priceHistory) {
		
		candlesForAnalysis = priceHistory.
				subList(priceHistory.size() - getNecessaryNumOfPrices(),
						priceHistory.size());
		
	}
	
	public int getNecessaryNumOfPrices() {
		
		int necessaryNumOfCandles, necessaryNumOfPrices = 0;
		
		for (BullishReversalCandlePattern bullishPattern : bullishPatterns) {
			if ((necessaryNumOfCandles = bullishPattern.
					getNumRequiredCandles()) > necessaryNumOfPrices) {
				necessaryNumOfPrices = necessaryNumOfCandles;
			}
		}
		for (BearishReversalCandlePattern bearishPattern : bearishPatterns) {
			if ((necessaryNumOfCandles = bearishPattern.
					getNumRequiredCandles()) > necessaryNumOfPrices) {
				necessaryNumOfPrices = necessaryNumOfCandles;
			}
		}
		
		return necessaryNumOfPrices;
		
	}
	
	public MarketSignal getMarketSignal() {
		
		MarketSignal marketSignal;
		
		for (BearishReversalCandlePattern bearishPattern : bearishPatterns) {
			marketSignal = bearishPattern.getDowntrendSignal(candlesForAnalysis);
			if (marketSignal != null) {
				return marketSignal;
			}
		}
		
		for (BullishReversalCandlePattern bullishPattern : bullishPatterns) {
			marketSignal = bullishPattern.getUptrendSignal(candlesForAnalysis);
			if (marketSignal != null) {
				return marketSignal;
			}
		}
		
		return null;
		
	}
	
}
