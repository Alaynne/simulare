package br.simulare.presentation.gui.tabcontent.simulation;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.TextArea;

import javax.swing.JPanel;

import br.simulare.business.simulator.MySimulationResult;
import br.simulare.presentation.gui.tabcontent.TabContent;

/**
 * Graphic content of the performance summary tab, related to the simulation.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PerformanceSummaryTabContent extends TabContent {

	private MySimulationResult mySimulationResult;
	
	private final String LINE_END = System.getProperty("line.separator");
	
	public PerformanceSummaryTabContent(MySimulationResult mySimulationResult){
		
		this.mySimulationResult = mySimulationResult;
		component = createTabContentComponent();
		
	}

	protected Component createTabContentComponent() {
	
		JPanel externalPanel = new JPanel(new FlowLayout());
		StringBuffer buffer = new StringBuffer();
		String separator = "________________________________________________" +
				"___________________________________________________________" +
				"__________";
		
		// It adds the simulation description.
		buffer.append(mySimulationResult.getSimulationResult().getSimulationDescription());
		buffer.append(LINE_END + separator + LINE_END + separator + LINE_END 
				+ LINE_END);
		
		// It adds the measurement report of each strategy which was simulated.
		mySimulationResult.start();
		do {
			buffer.append(mySimulationResult.getMeasurementReport());
			buffer.append(LINE_END + separator	+ LINE_END + LINE_END);
			mySimulationResult.next();
		} while(mySimulationResult.hasNext());
		
		TextArea textArea = 
			new TextArea(buffer.toString(), 33, 120, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea.setEditable(false);
		
		externalPanel.add(textArea);

		return externalPanel;
		
	}

}
