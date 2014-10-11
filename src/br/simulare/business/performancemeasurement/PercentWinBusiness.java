package br.simulare.business.performancemeasurement;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Percentage of winning business.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentWinBusiness implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		int business = element.getStockPortfolio().getBusinessHistory().size();
		int winBusiness;
		NumWinBusiness numWinBusiness = new NumWinBusiness();
		
		numWinBusiness.calculateResult(element);
		if (numWinBusiness.hasResult() == true) {
			winBusiness = (Integer) numWinBusiness.getResult();
			if (business != 0) {
				result = (double) winBusiness / (double) business;
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
		
		return "% of Winning Business.............................."
				+ "...................: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
