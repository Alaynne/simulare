package br.simulare.business.ta.strategy;

import java.util.ArrayList;

import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.Recommendation;
import br.framesim.simulation.core.MarketSignal;
import br.framesim.simulation.ta.method.TAMethod;
import br.framesim.simulation.ta.strategy.TAAlgorithm;

/**
 * To extract an investment recommendation, a basic algorithm simply combines the market
 * signal captured by its analysis method with the basic investment rule -- buying low, 
 * selling high.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public abstract class BasicAlgorithm extends TAAlgorithm {

	public BasicAlgorithm(TAMethod taMethod) {
		
		super();
		taMethods = new ArrayList<TAMethod>();
		taMethods.add(taMethod);
		
	}
	
	public TAMethod getTAMethod() {
		return taMethods.get(0);
	}
	
	public int getMinimalHistorySize() {
		return taMethods.get(0).getNecessaryNumOfPrices();
	}
	
	public Recommendation getRecommendation(Price newPrice, 
			boolean isInLongPosition) {
		
		MarketSignal marketSignal;
		String justification;
		Recommendation recommendation;
		
		update(newPrice);
			
		marketSignal = taMethods.get(0).getMarketSignal();
			
		if (isInLongPosition) {
			if ((marketSignal != null) && (marketSignal.isDowntrendSignal())) {
				justification = marketSignal.toString();
				recommendation = Recommendation.buildSellRecommendation(justification);
			} else {
				justification = "No downtrend signal identified.";
				recommendation = Recommendation.buildHoldRecommendation(justification);
			}
		} else {
			if ((marketSignal != null) && (marketSignal.isUptrendSignal())) {
				justification = marketSignal.toString();
				recommendation =Recommendation.buildBuyRecommendation(justification);
			} else {
				justification = "No uptrend signal identified.";
				recommendation = Recommendation.buildHoldRecommendation(justification);
			}
		}
		
		return recommendation;

	}
			
}
