package br.simulare.business.performancemeasurement;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;

/**
 * Number of neutral business.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class NumNeutralBusiness implements PerformanceMeasurement {

	private int result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		NumWinBusiness numWinBusiness = new NumWinBusiness();
		NumLosingBusiness numLosingBusiness = new NumLosingBusiness();
		int business, winBusiness, losingBusiness;
		
		business = element.getStockPortfolio().getBusinessHistory().size();
		
		numWinBusiness.calculateResult(element);
		numLosingBusiness.calculateResult(element);
		if ((numWinBusiness.hasResult() == true) && 
				(numLosingBusiness.hasResult() == true)) {
			winBusiness = (Integer) numWinBusiness.getResult();
			losingBusiness = (Integer) numLosingBusiness.getResult();
			
			result = business - winBusiness - losingBusiness;		
			hasResult = true;
		} else {
			hasResult = false;
		}
		
	}
	
	public boolean hasResult() {
		return hasResult;
	}
	
	public String getName() {
		
		return "Number of Neutral Business......................."
				+ ".................: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Integer.toString(result);
	}
	
}
