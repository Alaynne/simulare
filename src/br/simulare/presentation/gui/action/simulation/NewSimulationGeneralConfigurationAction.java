package br.simulare.presentation.gui.action.simulation;

import java.awt.event.ActionEvent;

import br.simulare.presentation.gui.action.Action;
import br.simulare.presentation.gui.tabcontent.simulation.SimulationGeneralConfigurationTabContent;
import br.simulare.util.MessageManager;

/**
 * Action for reseting the general configurations of the simulation.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public class NewSimulationGeneralConfigurationAction extends Action {

	public NewSimulationGeneralConfigurationAction(SimulationGeneralConfigurationTabContent
			simulationGeneralConfigurationTabContent) {
		
		super(MessageManager.getInstance().getMessage("new"), 
				simulationGeneralConfigurationTabContent);
	
	}

	public void actionPerformed(ActionEvent event) {
		((SimulationGeneralConfigurationTabContent)agent).reset();
	}
	
}
