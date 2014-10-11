package br.simulare.business.performancemeasurement;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.simulare.util.Util;

/**
 * Percentage of trading sessions in long position.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentTradingSessionsLongPosition implements PerformanceMeasurement {

	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		int seriesSize, longPosition;
		
		seriesSize = element.getSeriesSize();
		longPosition = element.getLongPosition();
		System.out.println(element.getLongPosition());
		if (seriesSize != 0) {
			result = (double) longPosition / (double) seriesSize;
			hasResult = true;
		} else {
			hasResult = false;
		}
		
	}
	
	public boolean hasResult() {
		return hasResult;
	}
	
	public String getName() {
		
		return "% of Trading Sessions in Long Position.............." +
				"......: ";
	
	}
	
	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return Util.formatPercentage(result);
	}
	
}
