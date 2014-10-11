package br.simulare.business.ta.method.trendfollowingindicator;

import br.framesim.simulation.core.MarketSignal;
import br.framesim.simulation.ta.method.TAMethod;

/**
 * It defines an interface for a trend following indicator.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public interface TrendFollowingIndicator extends TAMethod {

	// It returns the market signal captured by the method.
	public MarketSignal getMarketSignal();
	
	// It returns the necessary number of prices for capturing the market signal.
	public int getNecessaryNumOfPrices();
	
}
