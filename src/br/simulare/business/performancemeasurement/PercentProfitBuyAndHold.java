package br.simulare.business.performancemeasurement;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import br.framesim.dataaccess.DBManager;
import br.framesim.simulation.core.Stock;
import br.framesim.simulation.performancemeasurement.PerformanceMeasurement;
import br.framesim.simulation.simulator.SimulationElement;
import br.framesim.simulation.stockportfolio.StockPortfolio;
import br.framesim.util.DBException;

/**
 * Percentage of profit with Buy and Hold strategy.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PercentProfitBuyAndHold implements PerformanceMeasurement {

	// Logger for this class
	private static final Logger logger = Logger.
			getLogger(PercentProfitBuyAndHold.class);
	
	private double result;
	private boolean hasResult;
	
	public void calculateResult(SimulationElement element) {
		
		Date startingDate, endingDate;
		GregorianCalendar firstTradingSessionDate = new GregorianCalendar();
		GregorianCalendar lastTradingSessionDate = new GregorianCalendar();
		StockPortfolio stockPortfolio;
		
		startingDate = element.getStockPortfolio().getOpeningDate();
		endingDate = element.getStockPortfolio().getClosingDate();
		stockPortfolio = element.getStockPortfolio().clone();
		stockPortfolio.setPeriodicity(element.getStockPortfolio().
				getPeriodicity());
		
		try {
			lastTradingSessionDate.setTime(endingDate);
			while ((!DBManager.getInstance().
					hasTradingSession(lastTradingSessionDate.getTime(), 
							stockPortfolio.getPeriodicity())) && 
							(lastTradingSessionDate.getTime().after(startingDate))) {
				lastTradingSessionDate.setTime(stockPortfolio.getPeriodicity().
						getPreviousDate(lastTradingSessionDate.getTime()));
			}
			
			firstTradingSessionDate.setTime(startingDate);
			while ((!DBManager.getInstance().
					hasTradingSession(firstTradingSessionDate.getTime(), 
					stockPortfolio.getPeriodicity())) && (firstTradingSessionDate.
					getTime().before(lastTradingSessionDate.getTime()))) {
				firstTradingSessionDate.setTime(stockPortfolio.getPeriodicity().
						getLaterDate(firstTradingSessionDate.getTime()));
			}
			
			if(firstTradingSessionDate.getTime().before(lastTradingSessionDate.getTime())){
				for (Stock stock : stockPortfolio.getTradedStocks()) {
					stockPortfolio.buy(stock, firstTradingSessionDate.getTime());
				}		
				
				stockPortfolio.close(lastTradingSessionDate.getTime());				
				result = stockPortfolio.getAppreciation();
				hasResult = true;
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("calculateResult(SimulationElement) - It was not "
							+ "possible to execute Buy and Hold strategy "
								+ "for the timeframe from " 
									+ br.framesim.util.Util.formatDate(startingDate) + " until " + 
										br.framesim.util.Util.formatDate(endingDate) + ".");
				}
				hasResult = false;
			}
		} catch (DBException e) {
			if (logger.isInfoEnabled()) {
				logger.info("calculateResult(SimulationElement) - It was not "
						+ "possible to execute Buy and Hold strategy "
							+ "for the timeframe from " 
								+ br.framesim.util.Util.formatDate(startingDate) + " until " + 
									br.framesim.util.Util.formatDate(endingDate) + ".");
			}
			hasResult = false;
		}		
		
	}

	public boolean hasResult() {
		return hasResult;
	}
	
	public String getName() {
		
		return " % of Profit with Buy and Hold strategy........................: ";
	
	}

	public Object getResult() {
		return result;
	}

	public String formatResult() {
		return br.simulare.util.Util.formatPercentage(result);
	}
	
}
