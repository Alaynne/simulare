package br.simulare.business.math;

import java.text.ParseException;

import br.framesim.util.Util;

/**
 * Arithmetic moving average (or simple moving average).
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ArithmeticMovingAvg extends MovingAvgType {

	public ArithmeticMovingAvg(int timeSpan) {
		super(timeSpan);
	}
	
	protected void addNewAverage() {
		
		double sum = 0, result;
		int valueIndex;
		
		if (values.size() >= timeSpan) {
			valueIndex = (values.size() - 1);
		
			for (int j = 0; j < timeSpan; j++) {
				sum += values.get(valueIndex - j);
			}
		
			result = sum / (double)timeSpan;
		
			try {
				averages.add(Util.roundOffValue(result));
			} catch (ParseException e) {}
		}
		
	}
	
	public int getNecessaryNumOfValues(int numOfAverages) {
		
		if (numOfAverages == 0) {
			return 0;
		}
		
		return ((timeSpan + numOfAverages) - 1);
	
	}
		
	public String toString() {
		return "SMA-" + timeSpan;
	}
	
}
