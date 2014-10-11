package br.simulare.business.math;

import java.text.ParseException;

import br.framesim.util.Util;

/**
 * Exponential moving average.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ExponentialMovingAvg extends MovingAvgType {

	public ExponentialMovingAvg(int timeSpan) {
		super(timeSpan);
	}
	
	protected void addNewAverage() {
		
		int valueIndex;
		double coefficient, previousEMA, result;
		ArithmeticMovingAvg arithmeticMovingAvg;
		
		if (values.size() >= (timeSpan + 1)) {
			coefficient = (double)2 / (double)(timeSpan + 1);
			
			if (averages.size() == 0) {
				arithmeticMovingAvg = new ArithmeticMovingAvg(timeSpan);
				arithmeticMovingAvg.setValues(values.subList(0, timeSpan));
				previousEMA = arithmeticMovingAvg.getAverages().get(0);
			} else {
				previousEMA = averages.get(averages.size() - 1);
			}
			
			valueIndex = (values.size() - 1);
			
			result = (values.get(valueIndex) * coefficient) + 
					(previousEMA * (1 - coefficient));
			
			try {
				averages.add(Util.roundOffValue(result));
			} catch (ParseException e) {}
		}
		
	}
	
	public int getNecessaryNumOfValues(int numOfAverages) {
		
		if (numOfAverages == 0) {
			return 0;
		}
		
		return (timeSpan + numOfAverages);
	
	}
	
	public String toString() {
		return "EMA-" + timeSpan;
	}
	
}
