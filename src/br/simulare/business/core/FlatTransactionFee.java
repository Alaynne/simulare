package br.simulare.business.core;

import br.framesim.simulation.core.TradingFee;

/**
 * Flat Transaction Fee.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class FlatTransactionFee implements TradingFee {

	private double flatRate;

	public FlatTransactionFee() {
		;
	}
	
	public FlatTransactionFee(double flatRate) {
		this.flatRate = flatRate;
	}
	
	public double calculateFee(double transactionValue) {
		return flatRate;
	}
	
	public double getFlatRate() {
		return flatRate;
	}
	
	public void setFlatRate(double flatRate) {
		this.flatRate = flatRate;
	}
	
	public String getType() {
		return "Flat Transaction Fee (R$)";
	}
	
}
