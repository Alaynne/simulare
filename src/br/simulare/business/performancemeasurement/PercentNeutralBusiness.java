package br.simulare.business.performancemeasurement;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Percentage of neutral business.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentNeutralBusiness implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		int business = element.getStockPortfolio().getBusinessHistory().size();
		int neutralBusiness;
		NumNeutralBusiness numNeutralBusiness = new NumNeutralBusiness();
		
		numNeutralBusiness.calculateResult(element);
		if (numNeutralBusiness.hasResult() == true) {
			neutralBusiness = (Integer) numNeutralBusiness.getResult();
			if (business != 0) {
				result = (double) neutralBusiness / (double) business;
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
		
		return "% of Neutral Business............................."
				+ "......................: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
