package br.simulare.control.appcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import br.framesim.dataaccess.DBManager;
import br.framesim.simulation.core.StockMarket;
import br.framesim.simulation.core.Stock;
import br.framesim.simulation.core.TradingFee;
import br.framesim.simulation.core.Periodicity;
import br.framesim.simulation.stockportfolio.StockPortfolio;
import br.framesim.util.DBException;
import br.simulare.util.InvalidDataException;
import br.simulare.util.Util;
import br.simulare.util.ConfigurationManager;
import br.simulare.util.NullParameter;
import br.simulare.business.chart.CandleChart;
import br.simulare.business.core.CashMarketRoundLot;
import br.simulare.business.core.DailyPeriodicity;
import br.simulare.business.core.FlatTransactionFee;
import br.simulare.business.math.MovingAvgTypeFactory;
import br.simulare.business.simulator.MySimulationResult;
import br.simulare.business.ta.candlestick.reversalpattern.ReversalCandlePatternTypeFactory;
import br.simulare.persistence.StockDAOImplementation;
import br.simulare.persistence.PriceDAOImplementation;

/** 
 * Application controller - It exposes the core functionalities of the application as
 * services that the user interface can consume. It implements Singleton and Facade 
 * design pattern.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public class AppController {
	
	// Logger for this class
	private static final Logger logger = Logger.getLogger(AppController.class);
	
	private static AppController singletonInstance = null;
	
	private String[] stockCache;
	private long stockCacheTime;
	private Set<StockMarket> stockMarketTypes;
	private Set<Periodicity> periodicityTypes;
	private Set<TradingFee> tradingFeeTypes;
	
	private final long DEFAULT_CACHE_EXPIRY_TIME = 60 * 1000;
	
	public static final int TAMETHOD_DIDIINDEX = 1;
	public static final int TAMETHOD_MOVINGAVERAGE = 2;
	public static final int TAMETHOD_REVERSALCANDLEPATTERNS = 3;
	public static final int TAPARAMETER_MOVINGAVERAGETYPE = 4;
	public static final int TAPARAMETER_MOVINGAVERAGETIMESPAN = 5;
	public static final int TAPARAMETER_BULLISHPATTERNS = 6;
	public static final int TAPARAMETER_BEARISHPATTERNS = 7;
	
	private AppController() {
		
		stockMarketTypes = new HashSet<StockMarket>();
		stockMarketTypes.add(new CashMarketRoundLot());
		
		periodicityTypes = new HashSet<Periodicity>();
		periodicityTypes.add(new DailyPeriodicity());
		
		tradingFeeTypes = new HashSet<TradingFee>();
		tradingFeeTypes.add(new FlatTransactionFee());

	}
	
	// It returns the time for expiring the information in cache.
	private long getCacheExpiryTime() {
		
		try {
			return Long.parseLong(ConfigurationManager.getInstance().
					getValue("appController.cacheExpiryTime"));
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("getCacheExpiryTime() - Using " +
						"default configuration.");
			}
			return DEFAULT_CACHE_EXPIRY_TIME;
		}
		
	}
	
	// It implements Singleton design pattern.
	public static AppController getInstance() {
		
		if (singletonInstance == null) {
			singletonInstance = new AppController();
		}
		return singletonInstance;
	
	}
	
	/**
	 * It connects the database of the application.
	 * 
	 * @throws DBException - if a database error occurs.
	 */
	public void connectDataBase() throws DBException {
		
		DBManager dbManager = DBManager.getInstance();

		dbManager.setStockDAO(new StockDAOImplementation());
		dbManager.setPriceDAO(new PriceDAOImplementation());
		dbManager.connect("com.mysql.jdbc.Driver", "url", "username", "password");
	}
	
	/**
	 * It returns all stock codes of the application.
	 * 
	 * @throws DBException - if a database error occurs.
	 */
	public String[] getAllStockCodes() throws DBException {
		
		long currentTime = System.currentTimeMillis();
		List<String> allStockCodes = new ArrayList<String>();

		if (stockCacheTime == 0) { // First time			
			stockCacheTime = currentTime;
			for (StockMarket stockMarketType : stockMarketTypes) {
				allStockCodes.addAll(DBManager.getInstance().
						getAllStockCodes(stockMarketType));
			}
			stockCache = Util.toArray(allStockCodes);
		} else if((currentTime - stockCacheTime) > getCacheExpiryTime()) {
			// The time for the stocks in cache expired.
			stockCacheTime = currentTime;
			for (StockMarket stockMarketType : stockMarketTypes) {
				allStockCodes.addAll(DBManager.getInstance().
						getAllStockCodes(stockMarketType));
			}
			stockCache = Util.toArray(allStockCodes);
		}
	
		return stockCache;
	
	}
	
	public StockMarket parseStockMarket(String stockMarketTypeStr) {
		
		for (StockMarket stockMarketType : stockMarketTypes) {
			if (stockMarketType.getType().equals(stockMarketTypeStr)) {
				try {
					return stockMarketType.getClass().newInstance();
				} catch (Exception e) {
					return null;
				}
			}
		}
	
		return null;
	
	}
		
	// It returns all price periodicities of the application.
	public String[] getAllPricePeriodicities() {
		
		List<String> allPricePeriodicities = new ArrayList<String>();
		
		for (Periodicity periodicityType : periodicityTypes) {
			allPricePeriodicities.add(periodicityType.toString());
		}
		
		return Util.toArray(allPricePeriodicities);
		
	}

	// It returns all trading fee types of the application.
	public String[] getAllTradingFeeTypes() {
		
		List<String> allTradingFeeTypes = new ArrayList<String>();

		allTradingFeeTypes.add("No Trading Fees");
		
		for (TradingFee tradingFeeType : tradingFeeTypes) {
			allTradingFeeTypes.add(tradingFeeType.getType());
		}
		
		return Util.toArray(allTradingFeeTypes);

	}

	// It returns all trading price types of the application.
	public String[] getAllTradingPriceTypes() {
		
		String[] allTradingPriceTypes = new String[1];

		allTradingPriceTypes[0] = StockPortfolio.TRANSACTION_CLOSE_PRICE;
		
		return allTradingPriceTypes;

	}
	
	// It returns all moving average types of the application.
	public String[] getAllMovingAvgTypes() {
		return Util.toArray(MovingAvgTypeFactory.getAllMovingAvgTypes());
	}

	// It returns all bullish candlestick pattern types of the application.
	public String[] getAllBullishCandlePatternTypes() {
		
		return Util.toArray(ReversalCandlePatternTypeFactory.
				getAllBullishCandlePatternTypes());
	
	}

	// It returns all bearish candlestick pattern types of the application.
	public String[] getAllBearishCandlePatternTypes() {
		
		return Util.toArray(ReversalCandlePatternTypeFactory.
				getAllBearishCandlePatternTypes());
	
	}

	/**
	 * It generates the price chart of the specified stock, considering the specified
	 * parameters.
	 * 
	 * @return the price chart generated.
	 * 
	 * @throws InvalidDataException - if a parameter has an invalid value.
	 * @throws DBException - if a database error occurs.
	 */
	public CandleChart generatePriceChart(String stockCode, 
			Date startingDate, Date endingDate, String pricePeriodicity) 
			throws InvalidDataException, DBException {
		
		PriceChartGenerationCommand command =
				new PriceChartGenerationCommand();
		Hashtable<Integer, Object> parameters = new Hashtable<Integer, Object>();
		
		if (stockCode != null) {
			parameters.put(PriceChartGenerationCommand.STOCK_CODE, stockCode);
		} else {
			parameters.put(PriceChartGenerationCommand.STOCK_CODE, 
					new NullParameter());
		}
		if (startingDate != null) {
			parameters.put(PriceChartGenerationCommand.STARTING_DATE, startingDate);
		} else {
			parameters.put(PriceChartGenerationCommand.STARTING_DATE, 
					new NullParameter());
		}
		if (endingDate != null) {
			parameters.put(PriceChartGenerationCommand.ENDING_DATE, endingDate);
		} else {
			parameters.put(PriceChartGenerationCommand.ENDING_DATE, new NullParameter());
		}
		if (pricePeriodicity != null) {
			parameters.put(PriceChartGenerationCommand.PRICE_PERIODICITY, 
				pricePeriodicity);
		} else {
			parameters.put(PriceChartGenerationCommand.PRICE_PERIODICITY, 
					new NullParameter());
		}
		
		return (CandleChart) command.execute(parameters);
	
	}

	/**
	 * It simulates the strategies based on the specified technical analysis methods,
	 * considering the specified parameters.
	 * 
	 * @return the simulation result.
	 * 
	 * @throws InvalidDataException - if a parameter has an invalid value.
	 * @throws DBException - if a database error occurs.	 */
	public MySimulationResult simulate(Date startingDate, Date endingDate, 
			String[] stockCodes, String initialInvestment, 
			String longPositionSizePercentage, 
			Hashtable<String, String> tradingFees, Hashtable<Integer, 
			Hashtable<Integer, String[]>> taMethods, 
			String periodicity, String tradingPriceType)
			throws InvalidDataException, DBException {
		
		SimulationCommand command = new SimulationCommand();
		Hashtable<Integer, Object> parameters = new Hashtable<Integer, Object>();
		
		if (startingDate != null) {
			parameters.put(SimulationCommand.STARTING_DATE, startingDate);
		} else {
			parameters.put(SimulationCommand.STARTING_DATE, new NullParameter());
		}
		if (endingDate != null) {
			parameters.put(SimulationCommand.ENDING_DATE, endingDate);
		} else {
			parameters.put(SimulationCommand.ENDING_DATE, new NullParameter());
		}
		if (stockCodes != null) {
			parameters.put(SimulationCommand.STOCK_CODES, stockCodes);
		} else {
			parameters.put(SimulationCommand.STOCK_CODES, new NullParameter());
		}
		if (initialInvestment != null) {
			parameters.put(SimulationCommand.INITIAL_INVESTMENT, initialInvestment);
		} else {
			parameters.put(SimulationCommand.INITIAL_INVESTMENT, new NullParameter());
		}
		if (longPositionSizePercentage != null) {
			parameters.put(SimulationCommand.LONG_POSITION_SIZE_PERCENTAGE, 
					longPositionSizePercentage);
		} else {
			parameters.put(SimulationCommand.LONG_POSITION_SIZE_PERCENTAGE, 
					new NullParameter());
		}
		if (tradingFees != null) {
			parameters.put(SimulationCommand.TRADING_FEES, tradingFees);
		} else {
			parameters.put(SimulationCommand.TRADING_FEES,new NullParameter());
		}
		if (taMethods != null) {
			parameters.put(SimulationCommand.TA_METHODS, taMethods);
		} else {
			parameters.put(SimulationCommand.TA_METHODS, new NullParameter());
		}
		if (periodicity != null) {
			parameters.put(SimulationCommand.PERIODICITY, periodicity);
		} else {
			parameters.put(SimulationCommand.PERIODICITY, new NullParameter());
		}
		if (tradingPriceType != null) {
			parameters.put(SimulationCommand.TRADING_PRICE_TYPE, tradingPriceType);
		} else {
			parameters.put(SimulationCommand.TRADING_PRICE_TYPE, new NullParameter());
		}

		return (MySimulationResult) command.execute(parameters);

	}

	/**
	 * It validates the stock codes specified for the simulation.
	 * 
	 * @return the stocks, if the codes are valid.
	 * 
	 * @throws InvalidDataException - if the stock codes are invalid.
	 * @throws DBException - if a database error occurs.
	 */
	public List<Stock> validateSimulationStockCodes(String[] stockCodes) 
			throws InvalidDataException, DBException {
		
		List<Stock> stocks = new ArrayList<Stock>();
		Stock stock;
		
		if (stockCodes == null) {
			throw new InvalidDataException("Stocks not informed.");
		}
		
		if (stockCodes.length == 0) {
			throw new InvalidDataException("Stocks not informed.");
		}
		
		for (int i = 0; i < stockCodes.length; i++) {
			if ((stock = DBManager.getInstance().
					getStock(stockCodes[i])) == null) {
				throw new InvalidDataException(stockCodes[i] + 
						": Invalid stock.");
			}
			
			if (!new CashMarketRoundLot().getType().equals(stock.getStockMarket().
					getType())) {
				throw new InvalidDataException(stockCodes[i] + 
						": Invalid stock: It is not traded in Cash Market with Round " +
								"Lot.");
			}
			
			stocks.add(stock);
		}
		
		return stocks;
		
	}
	
	/**
	 * It validates the specified trading fees.
	 * 
 	 * @return the trading fees, if they are valid.
 	 * 
	 * @throws InvalidDataException - if the trading fees are invalid.
	 */
	public Set<TradingFee> validateTradingFees(Hashtable<String, 
			String> tradingFeesStr) throws InvalidDataException {
		
		Set<TradingFee> tradingFees = new HashSet<TradingFee>();
		
		if (tradingFeesStr == null) {
			throw new InvalidDataException("Trading Fees not " +
					"informed.");
		}
		
		for (String tradingFeeStr: tradingFeesStr.keySet()) {
			if ("No Trading Fees".equals(tradingFeeStr)) {
				return new HashSet<TradingFee>();
			} else if (new FlatTransactionFee().getType().equals(tradingFeeStr)) {
				FlatTransactionFee flatTransactionFee = new FlatTransactionFee();
				double flatRate;
				try {
					flatRate = Double.
						parseDouble(tradingFeesStr.get(tradingFeeStr));
					if (flatRate == 0) {
						throw new InvalidDataException(tradingFeeStr + 
							": It is expected a value > 0.");
					}
				} catch (NumberFormatException e) {
					throw new InvalidDataException(tradingFeeStr + 
						": Invalid value.");
				}
				flatTransactionFee.setFlatRate(flatRate);
				tradingFees.add(flatTransactionFee);
			} else {
				throw new InvalidDataException(tradingFeeStr + 
						": Invalid Trading Fee type.");
			}
		}
	
		return tradingFees;
		
	}
	
	/**
	 * It validates the specified price periodicity.
	 * 
 	 * @return the price periodicity, if it is valid.
 	 * 
	 * @throws InvalidDataException - if the price periodicity is invalid.
	 */
	public Periodicity validatePeriodicity(String periodicityStr) 
			throws InvalidDataException {
		
		if (periodicityStr != null) {
			for (Periodicity periodicityType : periodicityTypes) {
				if (periodicityType.toString().equals(periodicityStr)) {
					try {
						return periodicityType.getClass().newInstance();
					} catch (Exception e) {}
				}
			}
			throw new InvalidDataException("Invalid Price Periodicity.");
		} else {
			throw new InvalidDataException("Price Periodicity not " +
					"informed.");
		}
				
	}
	
}
