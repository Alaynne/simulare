package br.simulare.business.performancemeasurement;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Percentage of standard deviation to winning business.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentStandardDeviationWinBusiness implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		NumWinBusiness numWinBusiness = new NumWinBusiness();
		int winBusiness;
		SummaryStatistics profitPercent = new SummaryStatistics();
		
		numWinBusiness.calculateResult(element);
		if (numWinBusiness.hasResult() == true) {
			winBusiness = (Integer) numWinBusiness.getResult();
			
			for (Double percent: 
					numWinBusiness.getProfitPercent()) {
				profitPercent.addValue(percent);
			}
			
			if (winBusiness != 0) {
				result = profitPercent.getStandardDeviation();
				hasResult = true;
			} else {
				hasResult = false;
			}
		} else {
			hasResult = false;
		}
		
	}
	
	public boolean hasResult() {
		return hasResult;
	}
	
	public String getName() {
		
		return "% of Standard Deviation to Winning Business......"
				+ "...: ";
		
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
