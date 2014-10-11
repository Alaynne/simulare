package br.simulare.business.ta.fundaments;

import java.text.ParseException;

import org.apache.log4j.Logger;

import br.framesim.simulation.core.Price;
import br.framesim.util.Util;
import br.simulare.util.ConfigurationManager;

/**
 * It is a space of prices in which there are not trades.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class Gap {

	// Logger for this class
	private static final Logger logger = Logger.getLogger(Gap.class);

	private static final double DEFAULT_MINIMAL_SPACE = 0.01;
		
	/**
	 * It verifies if there is a gap between the high price of the specified price and 
	 * the low price of the specified next price.
	 */ 
	public static boolean hasUpCompleteGap(Price price, Price nextPrice) {
		
		double minimalSpace;
		
		try {
			minimalSpace = Double.parseDouble(ConfigurationManager.getInstance().
					getValue("gap.minimalSpace"));
		} catch (Exception e) {
			minimalSpace = DEFAULT_MINIMAL_SPACE;
			if (logger.isInfoEnabled()) {
				logger.info("hasUpCompleteGap() - Using " +
						"default configuration.");
			}
		}
		
		try {
			if (Util.roundOffValue(price.getHighPrice() + minimalSpace)
					< nextPrice.getLowPrice()) {
				return true;
			}
		} catch (ParseException e) {}
		
		return false;
		
	}
	
	/**
	 * It verifies if there is a gap between the low price of the specified price and 
	 * the high price of the specified next price.
	 */ 
	public static boolean hasDownCompleteGap(Price price, Price nextPrice) {
		
		double minimalSpace;
		
		try {
			minimalSpace = Double.parseDouble(ConfigurationManager.getInstance().
					getValue("gap.minimalSpace"));
		} catch (Exception e) {
			minimalSpace = DEFAULT_MINIMAL_SPACE;
			if (logger.isInfoEnabled()) {
				logger.info("hasDownCompleteGap() - Using "
						+ "default configuration.");
			}
		}
		
		try {
			if (Util.roundOffValue(nextPrice.getHighPrice() +
					minimalSpace) < price.getLowPrice()) {
				return true;
			}
		} catch (ParseException e) {}
		
		return false;
			
	}
	
	/**
	 * It verifies if there is a gap between the bodies of the specified price and next 
	 * price, but with intersection between the upper shadow of the price and the lower
	 * shadow of the next price.
	 */
	public static boolean hasUpBodyGap(Price price, Price nextPrice) {
		
		double minimalSpace;
		
		try {
			minimalSpace = Double.parseDouble(ConfigurationManager.getInstance().
					getValue("gap.minimalSpace"));
		} catch (Exception e) {
			minimalSpace = DEFAULT_MINIMAL_SPACE;
			if (logger.isInfoEnabled()) {
				logger.info("hasUpBodyGap() - Using " +
						"default configuration.");
			}
		}
		
		try {
			if (nextPrice.isWhiteCandlestick()) {
				if (price.isWhiteCandlestick()) {
					if ((Util.roundOffValue(price.getClosePrice() 
						+ minimalSpace) < nextPrice.getOpenPrice()) && 
						(!Gap.hasUpCompleteGap(price, nextPrice))) {
						return true;
					}
				} else {
					if ((Util.roundOffValue(price.getOpenPrice() 
						+ minimalSpace) < nextPrice.getOpenPrice()) && 
						(!Gap.hasUpCompleteGap(price, nextPrice))) {
						return true;
					}
				}
			} else {
				if (price.isWhiteCandlestick()) {
					if ((Util.roundOffValue(price.getClosePrice()
						+ minimalSpace) < nextPrice.getClosePrice()) 
						&&(!Gap.hasUpCompleteGap(price,nextPrice))) {
						return true;
					}
				} else {
					if ((Util.roundOffValue(price.getOpenPrice() +
						minimalSpace) < nextPrice.getClosePrice()) && 
						(!Gap.hasUpCompleteGap(price, nextPrice))) {
						return true;
					}
				}
			}
		} catch (ParseException e) {}
		
		return false;
			
	}
	
	/**
	 * It verifies if there is a gap between the bodies of the specified price and next 
	 * price, but with intersection between the lower shadow of the price and the upper
	 * shadow of the next price.
	 */
	public static boolean hasDownBodyGap(Price price, Price nextPrice) {
		
		double minimalSpace;
		
		try {
			minimalSpace = Double.parseDouble(ConfigurationManager.getInstance().
					getValue("gap.minimalSpace"));
		} catch (Exception e) {
			minimalSpace = DEFAULT_MINIMAL_SPACE;
			if (logger.isInfoEnabled()) {
				logger.info("hasDownBodyGap() - Using " +
						"default configuration.");
			}
		}
		
		try {
			if (price.isWhiteCandlestick()) {
				if (nextPrice.isWhiteCandlestick()) {
					if ((Util.roundOffValue(nextPrice.
						getClosePrice() + minimalSpace) < 
						price.getOpenPrice()) && 
						(!Gap.hasDownCompleteGap(price, nextPrice))) {
						return true;
					}
				} else {
					if ((Util.roundOffValue(nextPrice.
						getOpenPrice() + minimalSpace) < 
						price.getOpenPrice()) && 
						(!Gap.hasDownCompleteGap(price, nextPrice))) {
						return true;
					}
				}
			} else {
				if (nextPrice.isWhiteCandlestick()) {
					if ((Util.roundOffValue(nextPrice.
						getClosePrice() + minimalSpace) < 
						price.getClosePrice()) && 
						(!Gap.hasDownCompleteGap(price, nextPrice))) {
						return true;
					}
				} else {
					if ((Util.roundOffValue(nextPrice.
						getOpenPrice() + minimalSpace) < 
						price.getClosePrice()) && 
						(!Gap.hasDownCompleteGap(price, nextPrice))) {
						return true;
					}
				}
			}
		} catch(ParseException e) {}
			
		return false;
				
	}
	
}
