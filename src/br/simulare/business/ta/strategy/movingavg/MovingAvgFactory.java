package br.simulare.business.ta.strategy.movingavg;

import br.framesim.simulation.ta.strategy.TAAlgorithm;
import br.framesim.simulation.ta.strategy.AlgorithmFactory;
import br.simulare.business.math.MovingAvgType;
import br.simulare.business.math.MovingAvgTypeFactory;
import br.simulare.business.ta.method.trendfollowingindicator.MovingAvg;

/**
 * It factories the moving average algorithm. It implements Factory design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class MovingAvgFactory implements AlgorithmFactory {

	private String movingAvgTypeStr;
	private int movingAvgTimeSpan;
	
	public MovingAvgFactory(String movingAvgTypeStr, int movingAvgTimeSpan) {
		
		this.movingAvgTypeStr = movingAvgTypeStr;
		this.movingAvgTimeSpan = movingAvgTimeSpan;
			
	}

	// It is a factory method.
	public TAAlgorithm buildAlgorithm() {
		
		MovingAvgType movingAvgType = MovingAvgTypeFactory.
				buildMovingAvg(movingAvgTypeStr, movingAvgTimeSpan); 
		return new MovingAvgAlgorithm(new MovingAvg(movingAvgType));

	}
		
	public String toString() {
		
		return MovingAvgTypeFactory.
				getMovingAvgName(movingAvgTypeStr, movingAvgTimeSpan);
		
	}

}
