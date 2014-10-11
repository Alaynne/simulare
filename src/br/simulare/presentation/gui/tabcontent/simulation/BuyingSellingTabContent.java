package br.simulare.presentation.gui.tabcontent.simulation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import br.framesim.simulation.chart.Chart;
import br.framesim.simulation.ta.strategy.TAStrategy;
import br.framesim.simulation.table.Table;
import br.simulare.business.simulator.MySimulationResult;
import br.simulare.presentation.gui.tabcontent.TabContent;

/**
 * Graphic content of the buying and selling tab, related to the simulation.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class BuyingSellingTabContent extends TabContent {

	private MySimulationResult mySimulationResult;
	
	private final Dimension CHART_INTERNALFRAME_SIZE = new Dimension(420, 225);
	private final Dimension TABLE_PANE_SIZE = new Dimension(510, 225);
	private final Dimension STRATEGY_SCROLLPANE_SIZE = new Dimension(970, 450);
	
	public BuyingSellingTabContent(MySimulationResult mySimulationResult){
		
		this.mySimulationResult = mySimulationResult;
		component = createTabContentComponent();
		
	}

	protected Component createTabContentComponent() {
		
		JPanel externalPanel = new JPanel(new FlowLayout());
		JTabbedPane internalTabs = new JTabbedPane();
		
		mySimulationResult.start();
		while (mySimulationResult.hasNext()) {
			TAStrategy strategy = mySimulationResult.getCurrentSimulationElement().
					getTAStrategy();
			List<Table> tables = mySimulationResult.getCurrentTables();
			List<Chart> charts = mySimulationResult.getCurrentCharts();

			JPanel strategyPanel = new JPanel(new GridLayout(charts.size(),	1));
		
			// It adds the charts and tables related to the strategy.
			for (int i = 0; i < charts.size(); i++) {
			
				JInternalFrame internalFrame = new JInternalFrame(charts.
						get(i).getTitle(), true, true, true, true);
				internalFrame.setPreferredSize(CHART_INTERNALFRAME_SIZE);
				internalFrame.setContentPane(charts.get(i).getPanel());
				internalFrame.setVisible(true);
				tables.get(i).getPane().setPreferredSize(TABLE_PANE_SIZE);
								
				JPanel activePanel = new JPanel(new FlowLayout());
				activePanel.add(internalFrame);
				activePanel.add(tables.get(i).getPane());
				strategyPanel.add(activePanel);
				
			}
			
			JScrollPane strategyScrollPane = new JScrollPane(strategyPanel);
			strategyScrollPane.setPreferredSize(STRATEGY_SCROLLPANE_SIZE);
			
			// It adds the tab related to the strategy.
			internalTabs.addTab(strategy.toString(), strategyScrollPane);

			mySimulationResult.next();
		}
		
		externalPanel.add(internalTabs);
		
		return externalPanel;
		
	}
	
}
