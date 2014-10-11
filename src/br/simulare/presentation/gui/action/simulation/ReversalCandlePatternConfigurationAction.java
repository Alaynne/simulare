package br.simulare.presentation.gui.action.simulation;

import java.awt.event.ActionEvent;

import br.simulare.presentation.gui.action.Action;
import br.simulare.presentation.gui.tabcontent.simulation.ChartPatternTabContent;
import br.simulare.util.MessageManager;

/**
 * Action for enabling (or disabling) the configuration of the reversal candlestick chart
 * patterns for the simulation. 
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public class ReversalCandlePatternConfigurationAction extends Action {

	public ReversalCandlePatternConfigurationAction(ChartPatternTabContent 
			chartPatternTabContent) {
		
		super(MessageManager.getInstance().
				getMessage("reversalCandlePatterns"), 
					chartPatternTabContent);
	
	}

	public void actionPerformed(ActionEvent event) {
				
		if (((ChartPatternTabContent)agent).
				areReversalCandlePatternsSelected()) {
			((ChartPatternTabContent)agent).
					enableReversalCandlePatternConfiguration();
		} else {
			((ChartPatternTabContent)agent).
					disableReversalCandlePatternConfiguration();
		}
	
	}
	
}
