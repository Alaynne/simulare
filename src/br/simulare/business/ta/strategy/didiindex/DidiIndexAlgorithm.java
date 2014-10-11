package br.simulare.business.ta.strategy.didiindex;

import br.framesim.simulation.chart.Chart;
import br.framesim.simulation.core.Price;
import br.simulare.business.chart.CandleChart;
import br.simulare.business.ta.method.trendfollowingindicator.DidiIndex;
import br.simulare.business.ta.strategy.BasicAlgorithm;

/**
 * Didi index algorithm.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class DidiIndexAlgorithm extends BasicAlgorithm {

	public DidiIndexAlgorithm(DidiIndex didiIndex) {
		super(didiIndex);
	}

	protected void finishStartup() {
		
		((DidiIndex)taMethods.get(0)).
				setShortTermMovingAvgValues(priceHistory);
		((DidiIndex)taMethods.get(0)).
				setMediumTermAvgValues(priceHistory);
		((DidiIndex)taMethods.get(0)).
				setLongTermAvgValues(priceHistory);
		((DidiIndex)taMethods.get(0)).
				setPriceForAnalysis(priceHistory);
		
	}

	protected void update(Price newPrice) {
		
		priceHistory.add(newPrice);
		
		((DidiIndex)taMethods.get(0)).addValueToShortTermMovingAvg(newPrice);
		((DidiIndex)taMethods.get(0)).addValueToMediumTermMovingAvg(newPrice);
		((DidiIndex)taMethods.get(0)).addValueToLongTermMovingAvg(newPrice);
		((DidiIndex)taMethods.get(0)).setPriceForAnalysis(priceHistory);
		
		lastTradingSessionDate = newPrice.getTradingSessionDate();
		
	}

	public Chart historyToChart() {
		
		if (isInitialized()) { 
			return new CandleChart(priceHistory);
		}
		
		return null;
		
	}
	
}
