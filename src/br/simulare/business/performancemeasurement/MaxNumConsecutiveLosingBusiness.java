package br.simulare.business.performancemeasurement;

import java.util.List;

import br.framesim.simulation.core.Business;
import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;

/**
 * Maximum number of consecutive losing business.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class MaxNumConsecutiveLosingBusiness implements PerformanceMeasurement {

	private int result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		List<Business> businessHistory = element.getStockPortfolio().
				getBusinessHistory();
		double[] resultPercent = new double[businessHistory.size()];
		double totalBuyingValue, totalSellingValue, percent;
		int index = 0;
		int currentSequence, maxSequence = 0;
		
		if (businessHistory.size() != 0) {
			for (Business business : businessHistory) {
				totalBuyingValue = business.getBuyingTransaction().getTotalValue();
				totalSellingValue = business.getSellingTransaction().getTotalValue();
				percent = (totalSellingValue / totalBuyingValue) - 1;
				resultPercent[index] = percent;
				index++;
			}
		
			for (int i = 0, j = 0; i < resultPercent.length;) {
				if (resultPercent[i] < 0) {
					currentSequence = 1;
					j = i + 1;
					while ((j < resultPercent.length) && 
							(resultPercent[j] < 0)) {
						currentSequence++;
						j++;
					}
					if (currentSequence > maxSequence) {
						maxSequence = currentSequence;
					}
					i = j;
				} else {
					i++;
				}
			}
		
			result = maxSequence;
			hasResult = true;
		} else {
			hasResult = false;
		}
		
	}
	
	public boolean hasResult() {
		return hasResult;
	}
	
	public String getName() {
		
		return "Max. Number of Consecutive Losing Business...." +
				"....: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Integer.toString(result);
	}
	
}
