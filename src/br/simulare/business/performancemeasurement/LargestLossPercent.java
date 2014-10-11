package br.simulare.business.performancemeasurement;

import java.util.List;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Largest loss in percent.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class LargestLossPercent implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		NumLosingBusiness numLosingBusiness = new NumLosingBusiness();
		List<Double> lossPercent;
		
		numLosingBusiness.calculateResult(element);
		if (numLosingBusiness.hasResult() == true) {
			if (((Integer)numLosingBusiness.getResult()) != 0) {
				lossPercent = numLosingBusiness.getLossPercent();
				result = lossPercent.get(0);
				for (Double percent: lossPercent) {
					if (percent < result) {
						result = percent;
					}
				}
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
		
		return "Largest Loss in Percent............................"
				+ "....................: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
