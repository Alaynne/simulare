package br.simulare.business.ta.method.trendfollowingindicator;

import java.util.List;

import org.apache.log4j.Logger;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;
import br.simulare.util.Util;
import br.simulare.business.math.ArithmeticMovingAvg;
import br.simulare.util.ConfigurationManager;

/**
 * Didi Index.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class DidiIndex implements TrendFollowingIndicator {
	
	// Logger for this class
	private static final Logger logger = Logger.getLogger(DidiIndex.class);
	
	private ArithmeticMovingAvg shortTermMovingAvg;
	private ArithmeticMovingAvg mediumTermMovingAvg;
	private ArithmeticMovingAvg longTermMovingAvg;
	private Price priceForAnalysis;
	
	private static final int SHORT_TERM_TIME_SPAN = 3;
	private static final int MEDIUM_TERM_TIME_SPAN = 8;
	private static final int LONG_TERM_TIME_SPAN = 20;
	private final int NUM_OF_NECESSARY_ELEMENTS;
	private static final int DEFAULT_NUM_OF_SEPARATION_ELEMENTS = 2;
	
	public DidiIndex() {
		
		int numOfSeparationElements;
		
		shortTermMovingAvg = new ArithmeticMovingAvg(SHORT_TERM_TIME_SPAN);
		mediumTermMovingAvg = new ArithmeticMovingAvg(MEDIUM_TERM_TIME_SPAN);
		longTermMovingAvg = new ArithmeticMovingAvg(LONG_TERM_TIME_SPAN);
		
		try {
			numOfSeparationElements = Integer.parseInt(ConfigurationManager.
					getInstance().getValue("didiIndex.numOfSeparationElements"));
		} catch (Exception e) {
			numOfSeparationElements = DEFAULT_NUM_OF_SEPARATION_ELEMENTS;
			if (logger.isInfoEnabled()) {
				logger.info("DidiIndex() - Using " +
						"default configuration.");
			}
		}
		NUM_OF_NECESSARY_ELEMENTS = 1 + numOfSeparationElements; 
		
	}
	
	public ArithmeticMovingAvg getShortTermMovingAvg() {
		return shortTermMovingAvg;
	}
	
	public ArithmeticMovingAvg getMediumTermMovingAvg() {
		return mediumTermMovingAvg;
	}

	public ArithmeticMovingAvg getLongTermMovingAvg() {
		return longTermMovingAvg;
	}
	
	public Price getPriceForAnalysis() {
		return priceForAnalysis;
	}
	
	public int getNecessaryNumOfPrices() {
		return longTermMovingAvg.getNecessaryNumOfValues(NUM_OF_NECESSARY_ELEMENTS);
	}
	
	public void setShortTermMovingAvgValues(List<Price> priceHistory) {
		
		List<Double> closePrices = Util.buildClosePriceList(priceHistory);
		
		shortTermMovingAvg.setValues(closePrices);
	
	}
	
	public void setMediumTermAvgValues(List<Price> priceHistory) {
		
		List<Double> closePrices = Util.buildClosePriceList(priceHistory);
		
		mediumTermMovingAvg.setValues(closePrices);
	
	}
	
	public void setLongTermAvgValues(List<Price> priceHistory) {
		
		List<Double> closePrices = Util.buildClosePriceList(priceHistory);
		
		longTermMovingAvg.setValues(closePrices);
	
	}
	
	public void setPriceForAnalysis(List<Price> priceHistory) {
		
		priceForAnalysis = priceHistory.
				get(priceHistory.size() - NUM_OF_NECESSARY_ELEMENTS);
		
	}
	
	public void addValueToShortTermMovingAvg(Price newPrice) {
		shortTermMovingAvg.addValue(newPrice.getClosePrice());
	}
	
	public void addValueToMediumTermMovingAvg(Price newPrice) {
		mediumTermMovingAvg.addValue(newPrice.getClosePrice());
	}

	public void addValueToLongTermMovingAvg(Price newPrice) {
		longTermMovingAvg.addValue(newPrice.getClosePrice());
	}

	private double[] getNecessaryAverages(ArithmeticMovingAvg movingAvg) {

		List<Double> averages = movingAvg.getAverages();
			
		return Util.toArray(averages.subList(averages.size()-NUM_OF_NECESSARY_ELEMENTS, 
				averages.size()));

	}		
		
	private boolean hasUpNeedled(double[] necessaryShortTermAverages, 
			double[] necessaryMediumTermAverages, double[] necessaryLongTermAverages) {
		
		if (priceForAnalysis.isWhiteCandlestick()) { 
			if ((necessaryShortTermAverages[0] < 
				priceForAnalysis.getClosePrice()) && 
					(necessaryShortTermAverages[0] > priceForAnalysis.getOpenPrice())) {
				if ((necessaryMediumTermAverages[0] < 
					priceForAnalysis.getClosePrice()) && 
						(necessaryMediumTermAverages[0] > priceForAnalysis.getOpenPrice())) {
					if ((necessaryLongTermAverages[0] < 
						priceForAnalysis.getClosePrice()) && 
							(necessaryLongTermAverages[0] > priceForAnalysis.getOpenPrice())) {
						for (int i = 1; i < NUM_OF_NECESSARY_ELEMENTS; i++) {
							if ((necessaryShortTermAverages[i] > 
								necessaryMediumTermAverages[i]) && 
									(necessaryMediumTermAverages[i] > 
										necessaryLongTermAverages[i])) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
		
	}

	private boolean hasDownNeedled(double[] necessaryShortTermAverages, 
			double[] necessaryMediumTermAverages, double[] necessaryLongTermAverages) {
		
		if (priceForAnalysis.isBlackCandlestick()) { 
			if ((necessaryShortTermAverages[0] > 
				priceForAnalysis.getClosePrice()) && 
					(necessaryShortTermAverages[0] < priceForAnalysis.
							getOpenPrice())) {
				if ((necessaryMediumTermAverages[0] > 
					priceForAnalysis.getClosePrice()) && 
						(necessaryMediumTermAverages[0] < priceForAnalysis.
								getOpenPrice())) { 
					if ((necessaryLongTermAverages[0] > 
						priceForAnalysis.getClosePrice()) && 
							(necessaryLongTermAverages[0] < priceForAnalysis.
									getOpenPrice())) { 
						for (int i = 1; i < NUM_OF_NECESSARY_ELEMENTS; i++) {
							if ((necessaryShortTermAverages[i] < 
								necessaryMediumTermAverages[i]) && 
									(necessaryMediumTermAverages[i] < 
										necessaryLongTermAverages[i])) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
		
	}
	
	public MarketSignal getMarketSignal() {
		
		double[] necessaryShortTermAverages, necessaryMediumTermAverages, 
				necessaryLongTermAverages;
		
		MarketSignal marketSignal = null;
		String justification;
		
		necessaryShortTermAverages = getNecessaryAverages(shortTermMovingAvg);
		necessaryMediumTermAverages = getNecessaryAverages(mediumTermMovingAvg);
		necessaryLongTermAverages = getNecessaryAverages(longTermMovingAvg);
		
		// If it has down needled ...
		if (hasDownNeedled(necessaryShortTermAverages, necessaryMediumTermAverages, 
				necessaryLongTermAverages)) {
			justification = "A down needled was identified.";
			marketSignal = MarketSignal.buildDowntrendSignal(justification);
		} else {
			// If it has up needled ...
			if (hasUpNeedled(necessaryShortTermAverages, necessaryMediumTermAverages, 
					necessaryLongTermAverages)) {
				justification = "An up needled was identified.";
				marketSignal = MarketSignal.buildUptrendSignal(justification);
			} 
		}
		
		return marketSignal;
		
	}

}
