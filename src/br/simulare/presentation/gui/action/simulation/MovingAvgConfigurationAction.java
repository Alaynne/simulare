package br.simulare.presentation.gui.action.simulation;

import java.awt.event.ActionEvent;

import br.simulare.presentation.gui.action.Action;
import br.simulare.presentation.gui.tabcontent.simulation.TrendFollowingIndicatorTabContent;
import br.simulare.util.MessageManager;

/**
 * Action for enabling (or disabling) the configuration of the moving average 
 * indicator for the simulation. 
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public class MovingAvgConfigurationAction extends Action {
	
	public MovingAvgConfigurationAction(TrendFollowingIndicatorTabContent 
			trendFollowingIndicatorTabContent) {
		
		super(MessageManager.getInstance().getMessage("movingAvg"),
				trendFollowingIndicatorTabContent);

	}

	public void actionPerformed(ActionEvent event) {
				 
		if (((TrendFollowingIndicatorTabContent)agent).
				isMovingAvgSelected()) {
			((TrendFollowingIndicatorTabContent)agent).
					enableMovingAvgConfiguration();
		} else {
			((TrendFollowingIndicatorTabContent)agent).
					disableMovingAvgConfiguration();
		}	
	
	}
	
}
