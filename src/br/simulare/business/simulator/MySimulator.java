package br.simulare.business.simulator;

import java.util.Date;
import java.util.List;

import br.framesim.simulation.core.Periodicity;
import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.Simulator;
import br.framesim.simulation.stockportfolio.StockPortfolio;
import br.framesim.simulation.ta.strategy.TAStrategy;
import br.framesim.util.DBException;
import br.simulare.util.InvalidDataException;

/**
 * It contains the simulator of FrameSim.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class MySimulator {
	
	private Simulator simulator;

	public MySimulator(Date startingDate, Date endingDate,
			List<TAStrategy> taStrategies, StockPortfolio stockPortfolio,
			List<PerformanceMeasurement> performanceMeasurements,
			Periodicity periodicity) {
		
		simulator = new Simulator(startingDate, endingDate, taStrategies, stockPortfolio,
				performanceMeasurements, periodicity);
	
	}

	public Simulator getSimulator() {
		
		return simulator;
		
	}
	
	public MySimulationResult simulate() throws DBException {
	
		MySimulationResult mySimulationResult;
		
		mySimulationResult = new MySimulationResult(simulator.simulate());
		
		return mySimulationResult;
		
	}
	
	public static Date validateSimulationStartingDate(Date startingDate) 
			throws InvalidDataException {
		
		if (startingDate == null) {
			throw new InvalidDataException("Starting Date not informed.");
		}
		
		return startingDate;
		
	}

	public static Date validateSimulationEndingDate(Date endingDate, Date startingDate) 
			throws InvalidDataException {
		
		if (endingDate == null) {
			throw new InvalidDataException("Ending Date not informed.");
		}
		
		if (!endingDate.after(startingDate)) {
			throw new InvalidDataException("Ending Date expected: Date " +
					"later to the Starting Date.");
		}
			
		return endingDate;
		
	}

}
