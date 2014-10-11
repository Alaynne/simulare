package br.simulare.business.ta.strategy;

import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import br.framesim.dataaccess.DBManager;
import br.framesim.simulation.core.Stock;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.Periodicity;
import br.framesim.simulation.core.Recommendation;
import br.framesim.simulation.stockportfolio.StockPortfolio;
import br.framesim.simulation.stockportfolio.RecommendationSet;
import br.framesim.simulation.ta.strategy.TAAlgorithm;
import br.framesim.simulation.ta.strategy.TAStrategy;
import br.framesim.simulation.ta.strategy.AlgorithmFactory;
import br.framesim.util.DBException;
import br.framesim.util.InsufficientDataException;
import br.framesim.util.Util;

/**
 * Strategy that executes an independent examination of each stock in a portfolio. It 
 * means that the strategy gives an independent investment recommendation for each of the
 * stocks.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public class TAStrategyImplementation extends TAStrategy {
	
	// Logger for this class
	private static final Logger logger = Logger.getLogger(TAStrategyImplementation.class);
	
	public TAStrategyImplementation (AlgorithmFactory algorithmFactory) {
		
		this.algorithmFactory = algorithmFactory;
		taAlgorithms = new Hashtable<Stock, TAAlgorithm>();
		
	}
	
	public RecommendationSet 
			getRecommendation(StockPortfolio stockPortfolio, Date tradingDate, 
					Periodicity periodicity) throws DBException {
		
		Date previousDate;
		Price price;
		DBManager db = DBManager.getInstance();
		TAAlgorithm algorithm;
		Recommendation recommendation;
		boolean isInLongPosition;
		RecommendationSet recommendationSet = new RecommendationSet();
		
		for (Stock stock : stockPortfolio.getTradedStocks()) {
			price = db.getPrice(stock.getCode(), tradingDate, periodicity);
			
			if (price != null) {
				// There is an algorithm for each stock.
				algorithm = taAlgorithms.get(stock);
				if (algorithm == null) {
					algorithm = algorithmFactory.buildAlgorithm();
					taAlgorithms.put(stock, algorithm);
				}
				
				try {
					// It builds the price history.
					previousDate = periodicity.getPreviousDate(tradingDate);
					StrategyHistoryHelper.buildPriceHistory(stock, 
							algorithm, previousDate, periodicity);
					isInLongPosition = stockPortfolio.hasShares(stock);
					recommendation = algorithm.getRecommendation(price, isInLongPosition);
				} catch (InsufficientDataException e) {					
					recommendation = Recommendation.buildHoldRecommendation("There are not sufficient "
						+ "prices for building the history "
							+ "of the stock " + stock.getCode() + " on "
								+ Util.formatDate(tradingDate) + ".");
					if (logger.isInfoEnabled()) {
						logger.info("getRecommendation(StockPortfolio, Date, Periodicity) - " +
							"Stock " + stock.getCode() + ", on " +
								Util.formatDate(tradingDate) + ": " +
									e.getMessage());
					}
				}
			} else {
				recommendation = Recommendation.buildHoldRecommendation("There are not prices " +
						"for the stock " + stock.getCode() + " on "
								+ Util.formatDate(tradingDate) + ".");
				if (logger.isInfoEnabled()) {
					logger.info("getRecommendation(StockPortfolio, Date, Periodicity) - " +
						"Stock " + stock.getCode() + ", on " +
							Util.formatDate(tradingDate) + 
								": There are not prices.");
				}
			}
				
			recommendationSet.addRecommendation(stock, recommendation);
		}

		return recommendationSet;
		
	}
	
	public String toString() {
		return this.algorithmFactory.toString();
	}
		
}
