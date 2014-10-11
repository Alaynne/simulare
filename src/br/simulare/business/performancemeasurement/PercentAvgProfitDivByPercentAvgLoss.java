package br.simulare.business.performancemeasurement;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Percentage of average profit divided by percentage of average loss.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentAvgProfitDivByPercentAvgLoss implements 
		PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		PercentAvgProfit percentAvgProfit = new PercentAvgProfit();
		PercentAvgLoss percentAvgLoss = new PercentAvgLoss();
		
		percentAvgProfit.calculateResult(element);
		percentAvgLoss.calculateResult(element);
		if ((percentAvgProfit.hasResult() == true) && 
				(percentAvgLoss.hasResult() == true)) {
			result = Math.abs(((Double)percentAvgProfit.getResult())
					/ ((Double)percentAvgLoss.getResult()));
			hasResult = true;
		} else {
			hasResult = false;
		}
		
	}
	
	public boolean hasResult() {
		return hasResult;
	}
	
	public String getName() {
		
		return "% of Average Profit / % of Average Loss......"
				+ "...............: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatValue(result);
	}
	
}
