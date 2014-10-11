package br.simulare.business.ta.fundaments;

import java.text.ParseException;

import org.apache.log4j.Logger;

import br.simulare.util.ConfigurationManager;
import br.framesim.util.Util;

/**
 * It represents the main movement traced by a set of values.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class Movement {
	
	// Logger for this class
	private static final Logger logger = Logger.getLogger(Movement.class);
	
	private static final double DEFAULT_MIN_UP_COEFFICIENT = 0.2;
	private static final double DEFAULT_MAX_DOWN_COEFFICIENT = -0.2;
		
	private static double[] linearLeastSquareFit(double[] values) {
		
		int n = values.length;
		double sumX = 0, sumY = 0, sumXY = 0, sumSquareX = 0;
		double a, b;
		double[] linearFitCoefficients = new double[2];
				
		for (int i = 1; i <= n; i++) {
			sumX += i;
		}
		for (int i = 1; i <= n; i++) {
			sumY += values[i - 1];
		}
		for (int i = 1; i <= n; i++) {
			sumXY += (i * values[i - 1]);
		}
		for (int i = 1; i <= n; i++) {
			sumSquareX += (i * i);
		}
		
		b = ((n * sumXY) - (sumX * sumY)) / ((n * sumSquareX) - (sumX * sumX));
		a = (sumY - (b * sumX)) / n;
		
		linearFitCoefficients[0] = a;
		linearFitCoefficients[1] = b;
	
		return linearFitCoefficients;
		
	}
	
	private static double calculateMovementCoefficient(double[] values, 
			double[] linearFitCoefficients) {
		
		double maxValue = values[0], minValue = values[0];
		double maxY, minY;
		double result = 0;
		
		for (int i = 1; i < values.length; i++) {
			if (values[i] > maxValue) {
				maxValue = values[i];
			}
		}
		for (int i = 1; i < values.length; i++) {
			if (values[i] < minValue) {
				minValue = values[i];
			}
		}
		
		minY = linearFitCoefficients[0] + linearFitCoefficients[1];
		maxY = linearFitCoefficients[0] + (linearFitCoefficients[1] * values.length);
		
		if ((maxValue - minValue) != 0) {
			try {
				result = Util.
					roundOffValue((maxY - minY) / (maxValue - minValue));
			} catch (ParseException e) {}
		}
		
		return result;
		
	}
	
	// It verifies if the specified values trace a down movement.
	public static boolean isDown(double[] values) {
		
		double[] linearFitCoefficients = new double[2];
		double movementCoefficient, maxDownMovementCoefficient;
		
		if (values.length >= 2) {
			linearFitCoefficients = Movement.linearLeastSquareFit(values);
			movementCoefficient = Movement.calculateMovementCoefficient(values, linearFitCoefficients);
		
			try {
				maxDownMovementCoefficient = Double.parseDouble(ConfigurationManager.
					getInstance().getValue("movement.maxDownCoefficient"));
			} catch (Exception e) {
				maxDownMovementCoefficient = DEFAULT_MAX_DOWN_COEFFICIENT;
				if (logger.isInfoEnabled()) {
					logger.info("isDown() - Using default configuration.");
				}
			}		
			if (movementCoefficient <= maxDownMovementCoefficient) {
				return true;
			}
		}
		
		return false;
		
	}
	
	// It verifies if the specified values trace an up movement.
	public static boolean isUp(double[] values) {
		
		double[] linearFitCoefficients = new double[2];
		double movementCoefficient, minUpMovementCoefficient;
		
		if (values.length >= 2) {
			linearFitCoefficients = Movement.linearLeastSquareFit(values);
			movementCoefficient = Movement.calculateMovementCoefficient(values, linearFitCoefficients);
			
			try {
				minUpMovementCoefficient = Double.parseDouble(ConfigurationManager.
					getInstance().getValue("movement.minUpCoefficient"));
			} catch (Exception e) {
				minUpMovementCoefficient = DEFAULT_MIN_UP_COEFFICIENT;
				if (logger.isInfoEnabled()) {
					logger.info("isUp() - Using default configuration.");
				}
			}
			
			if (movementCoefficient >= minUpMovementCoefficient) {
				return true;
			}
		}
		
		return false;
		
	}
	
	// It verifies if the specified values trace a lateral movement.
	public static boolean isLateral(double[] values) {
		
		double[] linearFitCoefficients = new double[2];
		double movementCoefficient, maxDownMovementCoefficient, minUpMovementCoefficient;
		
		if (values.length >= 2) {
			linearFitCoefficients = Movement.linearLeastSquareFit(values);
			movementCoefficient = Movement.calculateMovementCoefficient(values, linearFitCoefficients);
		
			try {
				maxDownMovementCoefficient = Double.parseDouble(ConfigurationManager.
					getInstance().getValue("movement.maxDownCoefficient"));
				minUpMovementCoefficient = Double.parseDouble(ConfigurationManager.
					getInstance().getValue("movement.minUpCoefficient"));
			} catch (Exception e) {
				maxDownMovementCoefficient = DEFAULT_MAX_DOWN_COEFFICIENT;
				minUpMovementCoefficient = DEFAULT_MIN_UP_COEFFICIENT;
				if (logger.isInfoEnabled()) {
					logger.info("isLateral() - Using default configurations.");
				}
			}
			
			if ((movementCoefficient > maxDownMovementCoefficient) && (movementCoefficient < minUpMovementCoefficient)){
				return true;
			}
		}
		
		return false;
		
	}
	
}
