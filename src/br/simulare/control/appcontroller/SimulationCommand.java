package br.simulare.control.appcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import br.framesim.simulation.core.Stock;
import br.framesim.simulation.core.TradingFee;
import br.framesim.simulation.core.Periodicity;
import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.ta.strategy.TAStrategy;
import br.framesim.util.DBException;
import br.simulare.util.InvalidDataException;
import br.simulare.business.performancemeasurement.*;
import br.simulare.business.simulator.MySimulator;
import br.simulare.business.stockportfolio.SimulationStockPortfolio;
import br.simulare.control.validationhandler.DidiIndexValidationHandler;
import br.simulare.util.ConfigurationManager;
import br.simulare.util.NullParameter;

/**
 * Command for simulating strategies. It implements Command design pattern.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public class SimulationCommand implements Command {
	
	// Logger for this class
	private static final Logger logger = Logger.getLogger(SimulationCommand.class);

	private List<PerformanceMeasurement> performanceMeasurements;
	private final String MSG_TA_METHODS_NOT_INFORMED = 
		"Technical Analysis Methods not informed.";
	
	public static final int STARTING_DATE = 0;
	public static final int ENDING_DATE = 1;
	public static final int STOCK_CODES = 2;
	public static final int INITIAL_INVESTMENT = 3;
	public static final int LONG_POSITION_SIZE_PERCENTAGE = 4;
	public static final int TRADING_FEES = 5;
	public static final int TA_METHODS = 6;
	public static final int PERIODICITY = 7;
	public static final int TRADING_PRICE_TYPE = 8;
	
	public SimulationCommand() {
		
		performanceMeasurements = new ArrayList<PerformanceMeasurement>();

		performanceMeasurements.add(new PercentTradingSessionsLongPosition());
		performanceMeasurements.add(new NumWinBusiness());
		performanceMeasurements.add(new PercentWinBusiness());
		performanceMeasurements.add(new NumNeutralBusiness());
		performanceMeasurements.add(new PercentNeutralBusiness());
		performanceMeasurements.add(new NumLosingBusiness());
		performanceMeasurements.add(new PercentLosingBusiness());
		performanceMeasurements.add(new PercentAvgProfit());
		performanceMeasurements.add(new LargestProfitPercent());
		performanceMeasurements.add(new PercentStandardDeviationWinBusiness());
		performanceMeasurements.add(new PercentAvgLoss());
		performanceMeasurements.add(new LargestLossPercent());
		performanceMeasurements.add(new PercentStandardDeviationLosingBusiness());
		performanceMeasurements.add(new PercentAvgProfitDivByPercentAvgLoss());
		performanceMeasurements.add(new MaxNumConsecutiveWinBusiness());
		performanceMeasurements.add(new MaxNumConsecutiveLosingBusiness());
		performanceMeasurements.add(new PercentProfitBuyAndHold());
		
	}
	
	/**
	 * It simulates strategies using the specified parameters.
	 * 
	 * @return the result of the simulation executed.
	 * 
	 * @throws InvalidDataException - if a parameter has an invalid value.
	 * @throws DBException - if a database error occurs. 
	 */
	@SuppressWarnings("unchecked")
	public Object execute(Hashtable<Integer, Object> parameters) 
			throws InvalidDataException, DBException {
		
		Date startingDate;
		Date endingDate;
		List<Stock> stocks;
		double initialInvestment;
		double longPositionSizePercentage;
		Set<TradingFee> tradingFees;
		List<TAStrategy> strategies;
		Periodicity periodicity;
		String tradingPriceType;
		
		if (parameters.get(STARTING_DATE) instanceof NullParameter) {
			startingDate = validateStartingDate((Date)NullParameter.getValue());
		} else {	
			startingDate = validateStartingDate((Date)parameters.get(STARTING_DATE));
		}
		
		if (parameters.get(ENDING_DATE) instanceof NullParameter) {
			endingDate = validateEndingDate((Date)NullParameter.getValue(), startingDate);
		} else {
			endingDate = validateEndingDate((Date)parameters.get(ENDING_DATE), startingDate);
		}
		
		if (parameters.get(TRADING_FEES) instanceof NullParameter) {
			tradingFees = validateTradingFees((Hashtable<String, String>)NullParameter.
						getValue());
		} else {
			tradingFees = validateTradingFees((Hashtable<String, String>)parameters.
					get(TRADING_FEES));
		}
		
		if (parameters.get(STOCK_CODES) instanceof NullParameter) {
			stocks = validateStockCodes((String[])NullParameter.getValue());
		} else {
			stocks = validateStockCodes((String[])parameters.
						get(STOCK_CODES));
		}
		
		for(Stock stock: stocks) {
			stock.getStockMarket().setTradingFees(tradingFees);
		}
		
		if (parameters.get(INITIAL_INVESTMENT) instanceof NullParameter) {
			initialInvestment = validateInitialInvestment((String)NullParameter.getValue());
		} else {
			initialInvestment = validateInitialInvestment((String)parameters.get(INITIAL_INVESTMENT));
		}
		
		if (parameters.get(LONG_POSITION_SIZE_PERCENTAGE) instanceof NullParameter) {
			longPositionSizePercentage = validateLongPositionSizePercentage((String)NullParameter.
					getValue());
		} else {
			longPositionSizePercentage = validateLongPositionSizePercentage((String)parameters.
					get(LONG_POSITION_SIZE_PERCENTAGE));
		}
				
		if (parameters.get(TA_METHODS) instanceof NullParameter) {
			strategies = validateTAMethods(
				(Hashtable<Integer, Hashtable<Integer, String[]>>)
					NullParameter.getValue());
		} else {
			strategies = validateTAMethods(
				(Hashtable<Integer, Hashtable<Integer, String[]>>)parameters.
					get(TA_METHODS));
		}

		if (parameters.get(PERIODICITY) instanceof NullParameter) {
			periodicity = validatePeriodicity((String) NullParameter.getValue());
		} else {
			periodicity = validatePeriodicity((String) parameters.get(PERIODICITY));
		}

		if (parameters.get(TRADING_PRICE_TYPE) instanceof NullParameter) {
			tradingPriceType = validateTradingPriceType((String) NullParameter.getValue());
		} else {
			tradingPriceType = validateTradingPriceType((String) parameters.get(TRADING_PRICE_TYPE));
		}
		
		SimulationStockPortfolio simulationStockPortfolio = new SimulationStockPortfolio(startingDate, 
				stocks, initialInvestment, longPositionSizePercentage, tradingPriceType);
			
		MySimulator mySimulator = new MySimulator(startingDate, endingDate, strategies, 
				simulationStockPortfolio, buildPerformanceMeasurements(), periodicity);
		
		return mySimulator.simulate();
		
	}
	
	private Date validateStartingDate(Date startingDate) throws InvalidDataException {
			
		return MySimulator.validateSimulationStartingDate(startingDate);
		
	}

	private Date validateEndingDate(Date endingDate, Date startingDate) throws InvalidDataException {
		
		return MySimulator.validateSimulationEndingDate(endingDate, startingDate);
		
	}
	
	private Set<TradingFee> validateTradingFees(Hashtable<String, 
			String> tradingFees) throws InvalidDataException {
		
		return AppController.getInstance().validateTradingFees(tradingFees);
	
	}
	
	private List<Stock> validateStockCodes(String[] stockCodes) 
			throws InvalidDataException, DBException {
		
		return AppController.getInstance().
				validateSimulationStockCodes(stockCodes);
		
	}
	
	private double validateInitialInvestment(String initialInvestmentStr) 
			throws InvalidDataException {
		
		return SimulationStockPortfolio.validateInitialInvestment(initialInvestmentStr);
		
	}
	
	private double validateLongPositionSizePercentage(String longPositionSizePercentageStr) 
			throws InvalidDataException {
		
		return SimulationStockPortfolio.
				validateLongPositionSizePercentage(longPositionSizePercentageStr);
		
	}
	
	private List<TAStrategy> validateTAMethods(Hashtable<Integer, 
			Hashtable<Integer, String[]>> TAMethods) 
				throws InvalidDataException {
		
		List<TAStrategy> strategies = new ArrayList<TAStrategy>();
		DidiIndexValidationHandler taMethodValidationHandler = 
			new DidiIndexValidationHandler();
		Hashtable<Integer, String[]> taMethodParameters;
		
		if (TAMethods == null) {
			throw new InvalidDataException(MSG_TA_METHODS_NOT_INFORMED);
		} else if (TAMethods.isEmpty()) {
			throw new InvalidDataException(MSG_TA_METHODS_NOT_INFORMED);
		}
		
		for(Integer taMethod : TAMethods.keySet()) {
			taMethodParameters = TAMethods.get(taMethod);
			strategies.add(taMethodValidationHandler.
					validateTAMethodParameters(taMethod, taMethodParameters));
		}

		return strategies;
		
	}
	
	private Periodicity validatePeriodicity(String periodicityStr) 
			throws InvalidDataException {
		
		return AppController.getInstance().validatePeriodicity(periodicityStr);
		
	}
	
	private String validateTradingPriceType(String tradingPriceType) 
			throws InvalidDataException {
		
		SimulationStockPortfolio.validateTradingPriceType(tradingPriceType);
		return tradingPriceType;
		
	}
	
	private List<PerformanceMeasurement> buildPerformanceMeasurements() {
		
		List<PerformanceMeasurement> measurements = 
				new ArrayList<PerformanceMeasurement>();
		boolean isTrue;
	
		for(PerformanceMeasurement measurement: performanceMeasurements) {
			try {
				isTrue = Boolean.parseBoolean(ConfigurationManager.
						getInstance().getValue("performanceMeasurement." + 
							measurement.getClass().getSimpleName()));
				if (isTrue) {
					measurements.add(measurement.getClass().newInstance());
				}
			} catch (Exception e) {
				if (logger.isInfoEnabled()) {
					logger.info("buildPerformanceMeasurements() - " + 
							measurement.getClass().getSimpleName() +
								": Using default configuration.");
				}
			}
		}
		
		return measurements;
		
	}
	
}
