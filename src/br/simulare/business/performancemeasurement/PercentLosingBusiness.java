package br.simulare.business.performancemeasurement;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Percentage of losing business.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentLosingBusiness implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		int business = element.getStockPortfolio().getBusinessHistory().size();
		int losingBusiness;
		NumLosingBusiness numLosingBusiness = new NumLosingBusiness();
		
		numLosingBusiness.calculateResult(element);
		if (numLosingBusiness.hasResult() == true) {
			losingBusiness = (Integer) numLosingBusiness.getResult();
			if (business != 0) {
				result = (double) losingBusiness / (double) business;
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
		
		return "% of Losing Business................................."
				+ "..................: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
