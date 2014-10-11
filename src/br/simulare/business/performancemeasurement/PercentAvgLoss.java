package br.simulare.business.performancemeasurement;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Percentage of average loss.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentAvgLoss implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		NumLosingBusiness numLosingBusiness = new NumLosingBusiness();
		double totalLossPercent = 0;
		int losingBusiness;
		
		numLosingBusiness.calculateResult(element);
		if (numLosingBusiness.hasResult() == true) {
			for (Double percent: 
					numLosingBusiness.getLossPercent()) {
				totalLossPercent += percent;
			}
			losingBusiness = (Integer) numLosingBusiness.getResult();
			if (losingBusiness != 0) {
				result = totalLossPercent / losingBusiness;
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
		
		return "% of Average Loss...................................................."
				+ "......: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
