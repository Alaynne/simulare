package br.simulare.business.performancemeasurement;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Percentage of standard deviation to losing business.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentStandardDeviationLosingBusiness implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		NumLosingBusiness numLosingBusiness = new NumLosingBusiness();
		int losingBusiness;
		SummaryStatistics lossPercent = new SummaryStatistics();
		
		numLosingBusiness.calculateResult(element);
		if (numLosingBusiness.hasResult() == true) {
			losingBusiness = (Integer) numLosingBusiness.getResult();
			
			for (Double percent: 
					numLosingBusiness.getLossPercent()) {
				lossPercent.addValue(percent);
			}
			
			if (losingBusiness != 0) {
				result = lossPercent.getStandardDeviation();
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
		
		return "% of Standard Deviation to Losing Business........." +
				"..: ";
		
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
