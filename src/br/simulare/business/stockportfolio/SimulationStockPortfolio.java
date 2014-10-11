package br.simulare.business.stockportfolio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import br.framesim.simulation.core.Stock;
import br.framesim.simulation.core.Business;
import br.framesim.simulation.core.Transaction;
import br.framesim.simulation.stockportfolio.StockPortfolio;
import br.framesim.simulation.table.Table;
import br.framesim.util.DBException;
import br.simulare.util.InvalidDataException;
import br.simulare.business.table.BuyingSellingTable;

/**
 * Stock portfolio for the strategy simulator.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public class SimulationStockPortfolio extends StockPortfolio {
	
	private double longPositionSizePercentage;

	public SimulationStockPortfolio(Date openingDate, List<Stock> tradedStocks,
			double initialInvestment, double longPositionSizePercentage, 
			String transactionPriceType) {
		
		super(openingDate);
		this.tradedStocks = tradedStocks;
		stockPatrimony = new Hashtable<Stock, List<Transaction>>();
		moneyPatrimony = initialInvestment;
		initialValue = initialInvestment;
		businessHistory = new ArrayList<Business>();
		this.longPositionSizePercentage = longPositionSizePercentage;
		this.transactionPriceType = transactionPriceType;
		
	}
	
	public SimulationStockPortfolio(Date openingDate) {
		super(openingDate);
	}
	
	private void addStockPatrimony(Stock stock, 
			int numOfSharesBought, Date buyingDate, double buyingPrice, 
					double TotalTradingFee) {
		
		Transaction buyingTransaction = Transaction.buildBuyingTransaction(numOfSharesBought, 
				buyingDate, buyingPrice, TotalTradingFee);
		List<Transaction> buyingTransactionList = new ArrayList<Transaction>();
		
		buyingTransactionList.add(buyingTransaction);
		stockPatrimony.put(stock, buyingTransactionList);
			
	}
	
	private void removeStockPatrimony(Stock stock, Transaction buyingTransaction) {
		stockPatrimony.remove(stock);
	}

	private double calculateAvailableValueForBuying() {
		
		if (longPositionSizePercentage == 0) {
			// It divides equally the money among the stocks.
			return moneyPatrimony/tradedStocks.size();
		}
		
		return moneyPatrimony * longPositionSizePercentage;
		
	}
		
	private void addBusinessToHistory(Stock stock, Transaction buyingTransaction, 
			Transaction sellingTransaction) {
		businessHistory.add(new Business(stock, buyingTransaction, sellingTransaction));
	}

	public boolean buy(Stock stock, Date tradingSessionDate) throws DBException {
		
		double availableValue, buyingPrice, totalBuyingValue, totalTradingFee;
		int numOfSharesBought;
		
		if (isTraded(stock) && !hasShares(stock)) {
			if ((availableValue = calculateAvailableValueForBuying()) > 0) {
				if ((buyingPrice = getTransactionPrice(stock, tradingSessionDate)) != 0){
					numOfSharesBought = stock.getStockMarket().
						calculateQuantityOfSharesForBuying(stock, buyingPrice,
							availableValue);
					if (numOfSharesBought > 0) {	
						totalBuyingValue = (numOfSharesBought / 
								stock.getQuotingFactor()) * buyingPrice;
						totalTradingFee = stock.getStockMarket().
							calculateTotalTradingFee(totalBuyingValue);
						if ((moneyPatrimony - totalBuyingValue - 
								totalTradingFee) >= 0) {
							moneyPatrimony -= totalBuyingValue;
							moneyPatrimony -= totalTradingFee;
							addStockPatrimony(stock, 
								numOfSharesBought, tradingSessionDate, buyingPrice, 
								totalTradingFee);
							return true;
						}
					}
				}
			}
		}
		
		return false;
		
	}

	public boolean sell(Stock stock, Date tradingSessionDate) throws DBException {
		
		double sellingPrice, totalSellingValue, totalTradingFee;
		int numOfSharesSold;
		Transaction sellingTransaction;
		Transaction buyingTransaction;
			
		if (isTraded(stock)) {
			if (hasShares(stock)) {
				if ((sellingPrice = getTransactionPrice(stock, tradingSessionDate))	!= 0) {
					buyingTransaction = stockPatrimony.get(stock).get(0);
					numOfSharesSold = buyingTransaction.getQuantityOfShares();
					totalSellingValue = (numOfSharesSold / 
							stock.getQuotingFactor()) * sellingPrice;
					totalTradingFee = stock.getStockMarket().
						calculateTotalTradingFee(totalSellingValue);
					if (((moneyPatrimony + totalSellingValue) - 
							totalTradingFee) >= 0) {
						moneyPatrimony += totalSellingValue;
						moneyPatrimony -= totalTradingFee;
						sellingTransaction = Transaction.buildSellingTransaction(numOfSharesSold, 
								tradingSessionDate, sellingPrice, totalTradingFee);
						addBusinessToHistory(stock, buyingTransaction, sellingTransaction);
						removeStockPatrimony(stock, buyingTransaction);
						return true;
					}
				}
			}
		}

		return false;

	}

	public void close(Date closingDate) throws DBException {
		
		Set<Stock> stocks = new HashSet<Stock>();
		stocks.addAll(stockPatrimony.keySet());
		
		for (Stock stock : stocks) {
			sell(stock, closingDate);
		}
	
		this.closingDate = closingDate;
		finalValue = moneyPatrimony;
		
	}
	
	public SimulationStockPortfolio clone() {
		
		if (tradedStocks != null) {
			return new SimulationStockPortfolio(openingDate, tradedStocks, 
					initialValue, longPositionSizePercentage, transactionPriceType);
		}
		
		return new SimulationStockPortfolio(openingDate);

	}
		
	public static double validateInitialInvestment(String initialInvestmentStr)
			throws InvalidDataException {

		double initialInvestment;

		if (initialInvestmentStr == null) {
			throw new InvalidDataException("Initial Investment not " +
					"informed.");
		}
		
		if ("".equals(initialInvestmentStr)) {
			throw new InvalidDataException("Initial Investment not " +
					"informed.");
		}
		
		try {
			initialInvestment = Double.parseDouble(initialInvestmentStr);
		} catch (NumberFormatException e) {
			throw new InvalidDataException("Invalid Initial Investment.");
		}
		
		if (initialInvestment <= 0) {
			throw new InvalidDataException("Initial Investment "
					+ "expected: Value > 0.");
		}

		return initialInvestment;
		
	}
	
	public static double validateLongPositionSizePercentage(
			String longPositionSizePercentageStr) throws InvalidDataException {

		double longPositionSizePercentage = 0;

		if (longPositionSizePercentageStr != null) {
			if (!"".equals(longPositionSizePercentageStr)) {
				try {
					longPositionSizePercentage = Double.
							parseDouble(longPositionSizePercentageStr);
				} catch (NumberFormatException e) {
					throw new InvalidDataException("Invalid Long Position Size " +
							"Percentage.");
				}
				
				if ((longPositionSizePercentage < 0) || 
						(longPositionSizePercentage > 1)) {
					throw new InvalidDataException("Long Position Size Percentage "
							+ "expected: Value between 0 and 1.");
				}
			}
		}
		
		return longPositionSizePercentage;

	}
	
	public static void validateTradingPriceType(String tradingPriceType) 
			throws InvalidDataException {
		
		if (tradingPriceType == null) {
			throw new InvalidDataException("Trading Price Type not " +
				"informed.");
		}
		
		if ("".equals(tradingPriceType)) {
			throw new InvalidDataException("Trading Price Type not " +
				"informed.");
		}
		
		if ((!TRANSACTION_OPEN_PRICE.equals(tradingPriceType)) &&
			(!TRANSACTION_CLOSE_PRICE.equals(tradingPriceType)) &&
				(!TRANSACTION_MIDDLE_PRICE.equals(tradingPriceType))) {
			throw new InvalidDataException("Invalid Trading Price Type.");
		}
		
	}
	
	public Table historyToTable(Stock stock) {
		return new BuyingSellingTable(getBusinessHistory(stock));
	}

	public String toString() {
		
		String result = "";
		Transaction buyingTransaction;
		
		for (Stock stock : stockPatrimony.keySet()) {
			buyingTransaction = stockPatrimony.get(stock).get(0);
			result += "[" + stock.getCode() + ", " + 
					buyingTransaction.getQuantityOfShares() + "]\n";
		}
		
		return result;

	}
	
}
