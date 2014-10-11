package br.simulare.control.appcontroller;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import br.framesim.dataaccess.DBManager;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.Periodicity;
import br.framesim.util.DBException;
import br.simulare.util.InvalidDataException;
import br.simulare.business.chart.CandleChart;
import br.simulare.util.NullParameter;

/**
 * Command for generating the price chart. It implements Command design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PriceChartGenerationCommand implements Command {

	public static final int STOCK_CODE = 0;
	public static final int STARTING_DATE = 1;
	public static final int ENDING_DATE = 2;
	public static final int PRICE_PERIODICITY = 3;
	
	/**
	 * It generates a price chart using the specified parameters.
	 * 
	 * @return the price chart generated.
	 * 
	 * @throws InvalidDataException - if a parameter has an invalid value.
	 * @throws DBException - if a database error occurs. 
	 */
	public Object execute(Hashtable<Integer, Object> parameters)
			throws InvalidDataException, DBException {
		
		String stockCode;
		Date startingDate;
		Date endingDate;
		String pricePeriodicityStr;
		Periodicity pricePeriodicity;
		List<Price> prices;
		
		if (parameters.get(STOCK_CODE) instanceof NullParameter) {
			stockCode = (String) NullParameter.getValue();
		} else {
			stockCode = (String)parameters.get(STOCK_CODE);
		}
		if (parameters.get(STARTING_DATE) instanceof NullParameter) {
			startingDate = (Date) NullParameter.getValue();
		} else {
			startingDate = (Date)parameters.get(STARTING_DATE);
		}
		if (parameters.get(ENDING_DATE) instanceof NullParameter) {
			endingDate = (Date) NullParameter.getValue();
		} else {
			endingDate = (Date)parameters.get(ENDING_DATE);
		}
		if (parameters.get(PRICE_PERIODICITY) instanceof NullParameter) {
			pricePeriodicityStr = (String) NullParameter.getValue();
		} else {
			pricePeriodicityStr = (String)parameters.get(PRICE_PERIODICITY);
		}
		
		validateStockCode(stockCode);
		validateStartingDate(startingDate);
		validateEndingDate(endingDate, startingDate);
		pricePeriodicity = validatePricePeriodicity(pricePeriodicityStr);
		
		prices = DBManager.getInstance().getAllPrices(stockCode, startingDate, 
				endingDate, pricePeriodicity);
		return new CandleChart(stockCode, prices);

	}

	private void validateStockCode(String stockCode) throws InvalidDataException {
		
		if (stockCode == null) {
			throw new InvalidDataException("Stock Code not informed.");
		}
	
	}
	
	private void validateStartingDate(Date startingDate) throws InvalidDataException {
		
		if (startingDate == null) {
			throw new InvalidDataException("Starting Date not informed.");
		}

	}
	
	private void validateEndingDate(Date endingDate, Date startingDate) 
			throws InvalidDataException {
		
		if (endingDate == null) {
			throw new InvalidDataException("Ending Date not informed.");
		}
		
		if (endingDate.before(startingDate)) {
			throw new InvalidDataException("Ending Date expected: date equal or" +
					" later to the Starting Date.");
		}

	}

	private Periodicity validatePricePeriodicity(String pricePeriodicity)
			throws InvalidDataException {
		
		return AppController.getInstance().validatePeriodicity(pricePeriodicity);
		
	}

}
