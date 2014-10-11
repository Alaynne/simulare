package br.simulare.business.core;

import java.util.HashSet;
import java.util.Set;

import br.framesim.simulation.core.StockMarket;
import br.framesim.simulation.core.Stock;
import br.framesim.simulation.core.TradingFee;

/**
 * Cash Market Round Lot.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class CashMarketRoundLot extends StockMarket {
	
	public CashMarketRoundLot() {
		super(new HashSet<TradingFee>());
	}

	public CashMarketRoundLot(Set<TradingFee> tradingFees) {
		super(tradingFees);
	}

	public int calculateQuantityOfSharesForBuying(Stock stock, double buyingPrice,
			double availableValue) {
		
		int quantityOfLotsForBuying;
		
		quantityOfLotsForBuying = (int) (availableValue /
				(buyingPrice * (stock.getRoundLot() / stock.getQuotingFactor())));

		return stock.getRoundLot() * quantityOfLotsForBuying;
		
	}
	
	public String getType() {
		return "CASH MARKET ROUND LOT";
	}
	
	public String toString() {
		return "Cash Market Round Lot";
	}
	
	private static boolean isValidDigit(char c) {
		
		if (Character.isDigit(c) && c != '0') {
			return true;
		}
		
		return false;
		
	}
	
	private static boolean isValidSecondDigit(char c) {
		
		if (c == '0' || c == '1' || c == '2' || c == '3') {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean isValidCode(String code) {
		
		code = code.replaceAll("\\s", "");
		if (code.length() == 5) {
			if ((Character.isLetter(code.charAt(0))) && 
					(Character.isLetter(code.charAt(1))) && 
					(Character.isLetter(code.charAt(2))) && 
					(Character.isLetter(code.charAt(3))) && 
					(isValidDigit(code.charAt(4)))) {
				return true;
			}
		} else if (code.length() == 6) {
			if ((Character.isLetter(code.charAt(0))) && 
					(Character.isLetter(code.charAt(1))) && 
					(Character.isLetter(code.charAt(2))) && 
					(Character.isLetter(code.charAt(3))) && 
					(code.charAt(4) == '1') &&
					(isValidSecondDigit(code.charAt(5)))) {
				return true;
			}
		}
		return false;
		
	}

}
