package br.simulare.business.simulator;

import java.util.List;

import br.framesim.simulation.chart.Chart;
import br.framesim.simulation.core.Stock;
import br.framesim.simulation.simulator.SimulationElement;
import br.framesim.simulation.simulator.SimulationResult;
import br.framesim.simulation.table.Table;

/**
 * It contains the simulation result of FrameSim.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class MySimulationResult {
	
	private SimulationResult simulationResult;
	
	// Cursor for accessing the parts of the simulation result.
	private int cursor;

	private final String LINE_END = System.getProperty("line.separator");
	
	public MySimulationResult (SimulationResult simulationResult) {
		 
		this.simulationResult = simulationResult;
		cursor = 0;
		
	}
		
	public SimulationResult getSimulationResult() {
		
		return simulationResult;
		
	}
	
	// It verifies if there is a next position for the cursor.
	public boolean hasNext() {
		return (cursor < simulationResult.getSize());
	}
	
	// It moves the cursor for the next position.
	public void next() {
		cursor++;
	}
	
	// It moves the cursor for the first position.
	public void start() {
		cursor = 0;
	}

	// It returns the tables related to the simulation element pointed out by the cursor.
	public List<Table> getCurrentTables() {
		return simulationResult.getTables().get(simulationResult.getSimulationElements().get(cursor));
	}
	
	// It returns the charts related to the simulation element pointed out by the cursor.
	public List<Chart> getCurrentCharts() {
		return simulationResult.getCharts().get(simulationResult.getSimulationElements().get(cursor));
	}
	
	// It returns the simulation element pointed out by the cursor.
	public SimulationElement getCurrentSimulationElement() {
		return simulationResult.getSimulationElements().get(cursor);
	}
	
	/** 
	 * It returns the measurement report related to the simulation element pointed out by
	 * the cursor.
	 */
	public String getMeasurementReport() {
		
		StringBuffer buffer = new StringBuffer();
		SimulationElement element = getCurrentSimulationElement();
		MyMeasurementReport myMeasurementReport;
		
		buffer.append("Strategy: " + element.getTAStrategy().toString()
			+ LINE_END);
		buffer.append("Stock(s): ");
		for (Stock stock : element.getStockPortfolio().getTradedStocks()) {
			buffer.append("[" + stock.getCode() + "]");
		}
		buffer.append(LINE_END + LINE_END + 
			"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - " +
			"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - " +
			"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - " +
			"- - - - - - -" + LINE_END + LINE_END);
		myMeasurementReport = new MyMeasurementReport(simulationResult.getMeasurementReports().get(cursor));
		buffer.append(myMeasurementReport.toString());
		
		return buffer.toString();
	
	}

}
