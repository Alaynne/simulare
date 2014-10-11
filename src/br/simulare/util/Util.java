package br.simulare.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.framesim.simulation.core.Price;

/**
 * Utilities.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public class Util {

	// The specified String is in the format dd/MM/yyyy.
	public static Date parseDate(String dateStr) throws ParseException {
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.parse(dateStr);	
	
	}
	
	public static List<Double> buildClosePriceList(List<Price> prices){
		
		List<Double> priceList = new ArrayList<Double>();
				
		for (Price price: prices) {
			priceList.add(new Double(price.getClosePrice()));
		} 
		return priceList;
		
	}
	
	public static double[] buildClosePriceArray(List<Price> prices) {
		
		List<Double> priceList = Util.buildClosePriceList(prices);
		
		return Util.toArray(priceList);
		
	}

	public static double[] toArray(List<Double> list) {
		
		double[] array;
				
		array = new double[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	
	}
	
	public static String[] toArray(Collection<String> collection) {
		
		String[] array;
		int i = 0;
				
		array = new String[collection.size()];
		for (String str : collection) {
			array[i] = str;
			i++;
		}
		return array;
	
	}
	
	/**
	 * It formats the specified value in Reais. 
	 * Example: value in Reais = 4.5
	 *          formated value in Reais = R$ 4,50
	 */
	public static String formatRealValue(double valueInReais){
		
		return NumberFormat.getCurrencyInstance().format(valueInReais);
	
	}
	
	/**
	 * It formats the specified percentage value.
	 * Example: percentage value = 0.4
	 *          formated percentage value = 40,00%
	 */
	public static String formatPercentage(double percentageValue) {
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		
		return nf.format(percentageValue);
	
	}
	
	/**
	 * It formats the specified value. 
	 * Example: value = 4.5
	 *          formated value = 4,50
	 */
	public static String formatValue(double value) {
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		
		return nf.format(value);
	
	}
	
}
