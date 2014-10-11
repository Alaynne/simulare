package br.simulare.business.ta.strategy.reversalcandlepatterns;

import org.apache.log4j.Logger;

import br.framesim.simulation.chart.Chart;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.Recommendation;
import br.framesim.simulation.core.MarketSignal;
import br.simulare.business.chart.CandleChart;
import br.simulare.business.ta.method.reversalchartpattern.ReversalCandlePatterns;
import br.simulare.business.ta.strategy.BasicAlgorithm;
import br.simulare.util.ConfigurationManager;

/**
 * Reversal candlestick pattern algorithm.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ReversalCandlePatternAlgorithm extends BasicAlgorithm {
	
	// Logger for this class
	private static final Logger logger = Logger.
			getLogger(ReversalCandlePatternAlgorithm.class);
	
	private int minimalMarketSignalStrength;
	
	private final int DEFAULT_MINIMAL_MARKET_SIGNAL_STRENGTH = MarketSignal.STRONG;
	
	public ReversalCandlePatternAlgorithm(ReversalCandlePatterns 
			reversalCandlePatterns) {
		
		super(reversalCandlePatterns);
		minimalMarketSignalStrength = getMinimalMarketSignalStrength();	
		
	}

	private int getMinimalMarketSignalStrength() {
		
		int minimalMarketSignalStrength;
		
		try {
			minimalMarketSignalStrength = Integer.parseInt(ConfigurationManager.
				getInstance().
				getValue("strategy.reversalCandlePatternAlgorithm.minimalMarketSignalStrength"));
		} catch (Exception e) {
			minimalMarketSignalStrength = DEFAULT_MINIMAL_MARKET_SIGNAL_STRENGTH;
			if (logger.isInfoEnabled()) {
				logger.info("getMinimalMarketSignalStrength() - Using " +
						"default configuration.");
			}
		}
		
		return minimalMarketSignalStrength;
		
	}

	protected void finishStartup() {
		
		((ReversalCandlePatterns)taMethods.get(0)).
				setCandlesForAnalysis(priceHistory);
		
	}

	protected void update(Price newPrice) {
		
		priceHistory.add(newPrice);

		((ReversalCandlePatterns)taMethods.get(0)).
				setCandlesForAnalysis(priceHistory);

		lastTradingSessionDate = newPrice.getTradingSessionDate();
		
	}

	public Recommendation getRecommendation(Price newPrice, boolean isInLongPosition) {
		
		MarketSignal marketSignal;
		String justification;
		Recommendation recommendation;
		
		update(newPrice);
			
		marketSignal = taMethods.get(0).getMarketSignal();
		
		if (isInLongPosition) {
			if ((marketSignal != null) && (marketSignal.isDowntrendSignal())) {
			   if(!marketSignal.hasStrengthLowerThan(minimalMarketSignalStrength)){
					justification = marketSignal.toString();
					recommendation = Recommendation.buildSellRecommendation(justification);
			   } else {
					justification = "Downtrend signal identified, " +
							"but with insignificant strength.";
					recommendation = Recommendation.buildHoldRecommendation(justification);
			   }
			} else {
				justification = "No downtrend signal identified.";
				recommendation = Recommendation.buildHoldRecommendation(justification);
			}
		} else {
			if ((marketSignal != null) && (marketSignal.isUptrendSignal())) {
			   if(!marketSignal.hasStrengthLowerThan(minimalMarketSignalStrength)){
				    justification = marketSignal.toString();
				    recommendation =Recommendation.buildBuyRecommendation(justification);
			   } else {
				    justification = "Uptrend signal identified, " +
							"but with insignificant strength.";
				    recommendation = Recommendation.buildHoldRecommendation(justification);
			   }
			} else {
				justification = "No uptrend signal identified.";
				recommendation = Recommendation.buildHoldRecommendation(justification);
			}
		}
				
		return recommendation;

	}

	public Chart historyToChart() {
		
		if (isInitialized()) { 
			return new CandleChart(priceHistory);
		}
		
		return null;
		
	}
	
}
