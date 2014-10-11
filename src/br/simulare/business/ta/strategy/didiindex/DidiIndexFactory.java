package br.simulare.business.ta.strategy.didiindex;

import br.framesim.simulation.ta.strategy.TAAlgorithm;
import br.framesim.simulation.ta.strategy.AlgorithmFactory;
import br.simulare.business.ta.method.trendfollowingindicator.DidiIndex;

/**
 * It factories the Didi index algorithm. It implements Factory design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class DidiIndexFactory implements AlgorithmFactory {
	
	// It is a factory method.
	public TAAlgorithm buildAlgorithm() {
		return new DidiIndexAlgorithm(new DidiIndex());
	}
	
	public String toString() {
		return "Didi Index";
	}

}
