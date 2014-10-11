package br.simulare.business.performancemeasurement;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Percentage of average profit.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentAvgProfit implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		NumWinBusiness numWinBusiness = new NumWinBusiness();
		double totalProfitPercent = 0;
		int winBusiness;
		
		numWinBusiness.calculateResult(element);
		if (numWinBusiness.hasResult() == true) {
			for (Double percent: 
					numWinBusiness.getProfitPercent()) {
				totalProfitPercent += percent;
			}
			winBusiness = (Integer) numWinBusiness.getResult();
			if (winBusiness != 0) {
				result = totalProfitPercent / winBusiness;
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
		
		return "% of Average Profit................................................"
				+ "..........: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
