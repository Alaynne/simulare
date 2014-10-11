package br.simulare.presentation.gui.action.simulation;

import java.awt.event.ActionEvent;

import br.simulare.presentation.gui.MainFrame;
import br.simulare.presentation.gui.action.Action;
import br.simulare.util.MessageManager;

/**
 * Action for showing the dialog related to a new simulation.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public class NewSimulationFlowAction extends Action {
	
	public NewSimulationFlowAction(MainFrame mainFrame) {
		
		super(MessageManager.getInstance().
				getMessage("mainFrame.simulationMenu.new"), mainFrame);
	
	}
	
	public void actionPerformed(ActionEvent event) {
		((MainFrame)agent).showSimulationDialog();
	}

}
