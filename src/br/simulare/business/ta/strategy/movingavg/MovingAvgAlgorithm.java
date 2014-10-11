package br.simulare.business.ta.strategy.movingavg;

import br.framesim.simulation.chart.Chart;
import br.framesim.simulation.core.Price;
import br.simulare.business.chart.CandleChart;
import br.simulare.business.ta.method.trendfollowingindicator.MovingAvg;
import br.simulare.business.ta.strategy.BasicAlgorithm;

/**
 * Moving average algorithm.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class MovingAvgAlgorithm extends BasicAlgorithm {
	
	public MovingAvgAlgorithm(MovingAvg movingAvg) {
		super (movingAvg);
	}
		
	protected void finishStartup() {
		((MovingAvg)taMethods.get(0)).setPrices(priceHistory);		
	}
	
	protected void update(Price newPrice) {
		
		priceHistory.add(newPrice);
		((MovingAvg)taMethods.get(0)).addPrice(newPrice);
		lastTradingSessionDate = newPrice.getTradingSessionDate();
		
	}
		
	public Chart historyToChart() {
		
		if (isInitialized()) { 
			return new CandleChart(priceHistory);
		}
		
		return null;
		
	}
	
}
