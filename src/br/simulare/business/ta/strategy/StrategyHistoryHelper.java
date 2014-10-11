package br.simulare.business.ta.strategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.framesim.dataaccess.DBManager;
import br.framesim.simulation.core.Stock;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.Periodicity;
import br.framesim.simulation.ta.strategy.TAAlgorithm;
import br.framesim.util.DBException;
import br.framesim.util.InsufficientDataException;

/**
 * It helps the strategy to build its price history.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public class StrategyHistoryHelper {
	
	public static void buildPriceHistory(Stock stock, 
			TAAlgorithm algorithm, Date tradingDate, 
					Periodicity periodicity) throws DBException,
							InsufficientDataException {
		
		Date lastDate = algorithm.getLastTradingSessionDate();
		int count = 0;
		List<Price> priceHistory = new ArrayList<Price>();
		GregorianCalendar calendar = new GregorianCalendar();
		
		calendar.setTime(tradingDate);
		if (lastDate == null) {
			// First time ...
			while ((priceHistory.size() < algorithm.getMinimalHistorySize())
					&& (count < 2 * algorithm.getMinimalHistorySize())) {
				StrategyHistoryHelper.
						addPriceToHistory(stock, calendar.getTime(),
								priceHistory, periodicity);
				count++;
				calendar.setTime(periodicity.
						getPreviousDate(calendar.getTime()));
			}
			algorithm.startup(priceHistory);
		} else {
			while (calendar.getTime().after(lastDate)) {
				StrategyHistoryHelper.
						addPriceToHistory(stock, calendar.getTime(),
								priceHistory, periodicity);
				calendar.setTime(periodicity.
						getPreviousDate(calendar.getTime()));
			}
			
			for (Price price : priceHistory) {
				algorithm.getRecommendation(price, false);
			}
		}

	}

	private static void addPriceToHistory(Stock stock, Date date,
			List<Price> priceHistory, Periodicity periodicity) 
					throws DBException {
		
		Price price = DBManager.getInstance().
				getPrice(stock.getCode(), date, periodicity);
	
		if (price != null) {
			priceHistory.add(0, price);
		}
		
	}
	
}
