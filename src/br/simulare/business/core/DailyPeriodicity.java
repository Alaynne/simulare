package br.simulare.business.core;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.framesim.simulation.core.Periodicity;

/**
 * Daily Price Periodicity.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class DailyPeriodicity implements Periodicity {
	
	public Date getPreviousDate(Date date) {
		
		GregorianCalendar previousDate = new GregorianCalendar();
		
		previousDate.setTime(date);
		previousDate.add(Calendar.DAY_OF_YEAR, -1);
		
		return previousDate.getTime();
		
	}

	public Date getLaterDate(Date date) {
		
		GregorianCalendar laterDate = new GregorianCalendar();
		
		laterDate.setTime(date);
		laterDate.add(Calendar.DAY_OF_YEAR, 1);
		
		return laterDate.getTime();
		
	}
	
	public String getType() {
		return "DAILY PERIODICITY";
	}
	
	public String toString() {
		return "Daily Periodicity";
	}
	
}
