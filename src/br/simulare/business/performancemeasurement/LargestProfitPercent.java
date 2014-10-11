package br.simulare.business.performancemeasurement;

import java.util.List;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Largest Profit in Percent.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class LargestProfitPercent implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		NumWinBusiness numWinBusiness = new NumWinBusiness();
		List<Double> profitPercent;
		
		numWinBusiness.calculateResult(element);
		if (numWinBusiness.hasResult() == true) {
			if (((Integer)numWinBusiness.getResult()) != 0) {
				profitPercent = numWinBusiness.getProfitPercent();
				result = profitPercent.get(0);
				for (Double percent: profitPercent) {
					if (percent > result) {
						result = percent;
					}
				}
				hasResult = true;
			} else {
				hasResult = false;;
			}
		} else {
			hasResult = false;
		}
	
	}
	
	public boolean hasResult() {
		return hasResult;
	}
	
	public String getName() {
		
		return "Largest Profit in Percent..........................."
				+ ".....................: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
