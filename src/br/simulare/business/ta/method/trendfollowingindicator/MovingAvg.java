package br.simulare.business.ta.method.trendfollowingindicator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.MarketSignal;
import br.simulare.util.Util;
import br.simulare.business.math.MovingAvgType;
import br.simulare.util.ConfigurationManager;

/**
 * Moving Average.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class MovingAvg implements TrendFollowingIndicator {
	
	// Logger for this class
	private static final Logger logger = Logger.getLogger(MovingAvg.class);
	
	private List<Price> prices; 
	private MovingAvgType movingAvgType;
	private int numOfSafetyElements;
	private int maxNumOfIntersectionElements;
	
	private final int DEFAULT_NUM_OF_SAFETY_ELEMENTS = 2;
	private final int DEFAULT_MAX_NUM_OF_INTERSECTION_ELEMENTS = 3;
		
	public MovingAvg(MovingAvgType movingAvgType) {
		
		this.movingAvgType = movingAvgType;
		
		try {
			numOfSafetyElements = Integer.parseInt(ConfigurationManager.
					getInstance().getValue("movingAvg.numOfSafetyElements"));
			maxNumOfIntersectionElements = Integer.parseInt(ConfigurationManager.
					getInstance().getValue("movingAvg.maxNumOfIntersectionElements"));
		} catch (Exception e) {
			numOfSafetyElements = DEFAULT_NUM_OF_SAFETY_ELEMENTS;
			maxNumOfIntersectionElements = DEFAULT_MAX_NUM_OF_INTERSECTION_ELEMENTS;
			if (logger.isInfoEnabled()) {
				logger.info("MovingAvg(MovingAvgType) - " +
						"Using default configurations.");
			}
		}
	
	}
	
	public int getNecessaryNumOfPrices() {
		
		return movingAvgType.getNecessaryNumOfValues((2 * numOfSafetyElements) + 
				maxNumOfIntersectionElements);
	
	}
	
	public List<Price> getPrices() {
		return prices;
	}
	
	public MovingAvgType getMovingAvgType() {
		return movingAvgType;
	}
	
	public void setPrices(List<Price> priceHistory) {
	
		prices = new ArrayList<Price>();
		prices.addAll(priceHistory);
		movingAvgType.setValues(Util.buildClosePriceList(priceHistory));
		
	}
		
	public void addPrice(Price newPrice) {
		
		prices.add(newPrice);
		movingAvgType.addValue(newPrice.getClosePrice());
		
	}
		
	private List<Price> getPricesForAnalysis() {
		
		return prices.subList(prices.size() - ((2 * numOfSafetyElements) + 
				maxNumOfIntersectionElements), prices.size());
		
	}
	
	private double[] getAveragesForAnalysis() {

		List<Double> averages = movingAvgType.getAverages();
		
		return Util.toArray(averages.subList(averages.size() - 
				((2 * numOfSafetyElements) + maxNumOfIntersectionElements), averages.size()));
		
	}
		
	private boolean pricesCrossedAbove(List<Price> pricesForAnalysis, 
			double[] averagesForAnalysis){
		
		int index = pricesForAnalysis.size() - 1;
		boolean hasIntersection = true;
				
		// Last prices above the average ...
		for (int i = 0; i < numOfSafetyElements; i++) {
			if (pricesForAnalysis.get(index).getLowPrice() > 
					averagesForAnalysis[index]) {
				index--;
			} else {
				return false;
			}
		}
		
		// Intersection between the prices and the average ...
		while ((index >= numOfSafetyElements) && (hasIntersection)) {
			if((averagesForAnalysis[index] >= 
				pricesForAnalysis.get(index).getLowPrice()) &&
					(averagesForAnalysis[index] <= 
						pricesForAnalysis.get(index).getHighPrice())) {
				index--;
			} else {
				hasIntersection = false;
			}
		}
		
		// Predecessor prices under the average ...
		for (int i = 0; i < numOfSafetyElements; i++) {
			if (pricesForAnalysis.get(index).getHighPrice() < 
					averagesForAnalysis[index]) { 
				index--;
			} else {
				return false;
			}
		}
		
		return true;
		
	}
	
	private boolean pricesCrossedUnder(List<Price> pricesForAnalysis, 
			double[] averagesForAnalysis) {

		int index = pricesForAnalysis.size() - 1;
		boolean hasIntersection = true;
		
		// Last prices under the average ...
		for (int i = 0; i < numOfSafetyElements; i++) {
			if (pricesForAnalysis.get(index).getHighPrice() <
					averagesForAnalysis[index]) {
				index--;
			} else {
				return false;
			}
		}
		
		// Intersection between the prices and the average ...
		while ((index >= numOfSafetyElements) && (hasIntersection)) {
			if((averagesForAnalysis[index] >= 
				pricesForAnalysis.get(index).getLowPrice()) &&
					(averagesForAnalysis[index] <= 
						pricesForAnalysis.get(index).getHighPrice())) {
				index--;
			} else {
				hasIntersection = false;
			}
		}
		
		// Predecessor prices above the average ...
		for (int i = 0; i < numOfSafetyElements; i++) {
			if (pricesForAnalysis.get(index).getLowPrice() > 
					averagesForAnalysis[index]) { 
				index--;
			} else {
				return false;
			}
		}
		
		return true;
		
	}
	
	public MarketSignal getMarketSignal() {
		
		List<Price> pricesForAnalysis;
		double[] averagesForAnalysis;
		String justification;
		MarketSignal marketSignal = null;
		
		pricesForAnalysis = getPricesForAnalysis();
		averagesForAnalysis = getAveragesForAnalysis();
				
		if (pricesCrossedUnder(pricesForAnalysis, averagesForAnalysis)) {
			justification = "The prices crossed under the moving average.";
			marketSignal = MarketSignal.buildDowntrendSignal(justification);
		} else if (pricesCrossedAbove(pricesForAnalysis, averagesForAnalysis)) {
			justification = "The prices crossed above the moving average.";
			marketSignal = MarketSignal.buildUptrendSignal(justification);
		}
		
		return marketSignal;
		
	}	

}
