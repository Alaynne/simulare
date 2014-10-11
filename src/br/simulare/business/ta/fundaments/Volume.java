package br.simulare.business.ta.fundaments;

import java.text.ParseException;
import java.util.List;

import br.framesim.simulation.core.Price;
import br.framesim.util.Util;

/**
 * It is used for confirming trends. It can be represented by the number of shares traded.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class Volume {
	
	public static long numSharesTraded(Price price) {
		return price.getQuantityOfShares();
	}
	
	public static double avgNumSharesTraded(List<Price> prices) {
		
		long totalNumOfShares = 0;
		double averageNumOfShares = 0;
		
		if (prices.size() == 0) {
			return 0;
		}
		
		for (Price price : prices) {
			totalNumOfShares += price.getQuantityOfShares();
		}	
		
		try {
			averageNumOfShares = Util.roundOffValue(
					(double)totalNumOfShares/(double)prices.size());
		} catch (ParseException e) {}
		
		return averageNumOfShares;
		
	}

}
