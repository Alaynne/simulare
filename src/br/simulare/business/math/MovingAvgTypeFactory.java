package br.simulare.business.math;

import java.util.ArrayList;
import java.util.List;

/**
 * It factories types of moving average. It implements Factory design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class MovingAvgTypeFactory {
	
	public static final String ARITHMETIC_MOVING_AVG = "Arithmetic Moving Average";
	public static final String EXPONENTIAL_MOVING_AVG = "Exponential Moving Average";
	
	public static List<String> getAllMovingAvgTypes() {
		
		List<String> allMovingAvgTypes = new ArrayList<String>();
		
		allMovingAvgTypes.add(ARITHMETIC_MOVING_AVG);
		allMovingAvgTypes.add(EXPONENTIAL_MOVING_AVG);

		return allMovingAvgTypes;

	}

	// It is a factory method.
	public static MovingAvgType buildMovingAvg(String movingAvgType, int movingAvgTimeSpan) {
		
		MovingAvgType movingAvg = null;
		
		if (ARITHMETIC_MOVING_AVG.equals(movingAvgType)) {
			movingAvg = new ArithmeticMovingAvg(movingAvgTimeSpan); 
		} else if (EXPONENTIAL_MOVING_AVG.equals(movingAvgType)) {
			movingAvg = new ExponentialMovingAvg(movingAvgTimeSpan); 
		}
		
		return movingAvg;
		
	}
	
	public static boolean isMovingAvgTypeValid(String type) {
		return (getAllMovingAvgTypes().contains(type));
	}
	
	public static String getMovingAvgName(String movingAvgType,int movingAvgTimeSpan) {
		
		MovingAvgType movingAvg = buildMovingAvg(movingAvgType, movingAvgTimeSpan);
		
		if (movingAvg != null) {
			return movingAvg.toString();
		}
		
		return "";
		
	}
	
}
