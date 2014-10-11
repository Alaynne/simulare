package br.simulare.business.simulator;

import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.MeasurementReport;
import br.simulare.util.Util;

/**
 * It contains the measurement report of FrameSim.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class MyMeasurementReport {

	private MeasurementReport measurementReport;
	
	private final String LINE_END = System.getProperty("line.separator");
	
	public MyMeasurementReport(MeasurementReport measurementReport) {
		this.measurementReport = measurementReport;
	}
	
	public MeasurementReport getMeasurementReport() {
		
		return measurementReport;
		
	}
	
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Initial Value Invested..............................."
			+ "........................: "  
			+ Util.formatRealValue(measurementReport.getSimulationElement().
					getStockPortfolio().getInitialValue()) + LINE_END);
		buffer.append("Final Value........................................."
			+ "...............................: " 
			+ Util.formatRealValue(measurementReport.getSimulationElement().
					getStockPortfolio().getFinalValue()) + LINE_END);
		buffer.append("Investment Appreciation................................."
			+ "................: " 
			+ Util.formatPercentage(measurementReport.getSimulationElement().
					getStockPortfolio().getAppreciation()) + LINE_END + LINE_END);
		
		for (PerformanceMeasurement measurement: measurementReport.getPerformanceMeasurements()) {
			if (measurement.hasResult() == true) {
				buffer.append(measurement.getName() + 
						measurement.formatResult() + LINE_END);
			}
		}
			
		return buffer.toString();
	
	}
	
}
