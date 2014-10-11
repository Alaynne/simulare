package br.simulare.presentation.gui.tabcontent.simulation;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import br.simulare.presentation.gui.tabcontent.TabContent;
import br.simulare.util.MessageManager;

/**
 * Graphic content of the simulation tab.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class SimulationTabContent extends TabContent {

	private SimulationGeneralConfigurationTabContent simulationGeneralConfigurationTabContent;
	private TrendFollowingIndicatorTabContent trendFollowingIndicatorTabContent;
	private ChartPatternTabContent chartPatternTabContent;
	
	private JTabbedPane tabs;
	
	public SimulationTabContent() {
		component = createTabContentComponent();
	}
	
	protected Component createTabContentComponent() {
		
		MessageManager msgManager = MessageManager.getInstance();
		JPanel externalPanel = new JPanel(new FlowLayout());
		
		simulationGeneralConfigurationTabContent = new SimulationGeneralConfigurationTabContent();
		trendFollowingIndicatorTabContent = new TrendFollowingIndicatorTabContent();
		chartPatternTabContent = new ChartPatternTabContent();
		
		tabs = new JTabbedPane();
		tabs.addTab(msgManager.
				getMessage("dialog.simulation.generalConfigurationTab.title"), 
					simulationGeneralConfigurationTabContent.getTabContentComponent());
		tabs.addTab(msgManager.
				getMessage("dialog.simulation.trendFollowingIndicatorTab.title"),
					trendFollowingIndicatorTabContent.getTabContentComponent());
		tabs.addTab(msgManager.
				getMessage("dialog.simulation.chartPatternTab.title"), 
					chartPatternTabContent.getTabContentComponent());
		
		externalPanel.add(tabs);
		
		return externalPanel;

	}
	
	public SimulationGeneralConfigurationTabContent 
			getSimulationGeneralConfigurationTabContent() {
		return simulationGeneralConfigurationTabContent;
	}
	
	public TrendFollowingIndicatorTabContent getTrendFollowingIndicatorTabContent() {
		return trendFollowingIndicatorTabContent;
	}
	
	public ChartPatternTabContent getChartPatternTabContent() {
		return chartPatternTabContent;
	}
	
}
