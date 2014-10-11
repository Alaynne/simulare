package br.simulare.business.performancemeasurement;

import java.util.ArrayList;
import java.util.List;

import br.framesim.simulation.core.Business;
import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;

/**
 * Number of winning business.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class NumWinBusiness implements PerformanceMeasurement {

	private List<Double> profitPercent;
	private int result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		List<Business> businessHistory = element.getStockPortfolio().
				getBusinessHistory();
		double totalBuyingValue, totalSellingValue;
		
		profitPercent = new ArrayList<Double>();
		result = 0;
		
		for (Business business : businessHistory) {
			totalBuyingValue = business.getBuyingTransaction().getTotalValue();
			totalSellingValue = business.getSellingTransaction().getTotalValue();
			if (totalSellingValue > totalBuyingValue) { 
				// There was profit.
				profitPercent.add((totalSellingValue / totalBuyingValue) - 1);
				result++;
			}
		}
		
		hasResult = true;
		
	}
	
	public boolean hasResult() {
		return hasResult;
	}
	
	public String getName() {
		
		return "Number of Winning Business........................"
				+ "..............: ";
	
	}
	
	public Object getResult() {
		return result;
	}
	
	public List<Double> getProfitPercent() {
		return profitPercent;
	}

	public String formatResult() {
		return Integer.toString(result);
	}
	
}
