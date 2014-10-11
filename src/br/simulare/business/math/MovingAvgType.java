package br.simulare.business.math;

import java.util.ArrayList;
import java.util.List;

import br.simulare.util.InvalidDataException;

/**
 * It represents a set of averages.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public abstract class MovingAvgType {

	protected List<Double> values; // The averages are calculated on those values.
	protected int timeSpan;
	protected List<Double> averages;

	public MovingAvgType(int timeSpan) {
		
		values = new ArrayList<Double>();
		this.timeSpan = timeSpan;
		averages = new ArrayList<Double>();
		
	}
	
	public List<Double> getValues() {
		return values;
	}
	
	public int getTimeSpan() {
		return timeSpan;
	}
	
	public List<Double> getAverages() {
		return averages;
	}

	public void setValues(List<Double> values) {
		
		this.values = new ArrayList<Double>();
		averages = new ArrayList<Double>();
		
		if (values != null) {
			for (Double value : values) {
				addValue(value);
			} 
		}
		
	}
	
	public void addValue(double newValue) {
		
		values.add(newValue);
		
		addNewAverage();
		
	}

	public static void validateTimeSpan(int timeSpan) 
			throws InvalidDataException {
		
		if (timeSpan < 1) { 
			throw new InvalidDataException("It is expected an integer value >= 1.");
		}
	
	}
	
	protected abstract void addNewAverage();
	
	/**
	 * It returns the necessary number of values for obtaining the specified number of  
	 * averages.
	 */
	public abstract int getNecessaryNumOfValues(int numOfAverages);
	
}
