package br.simulare.presentation.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Date;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import br.simulare.business.simulator.MySimulationResult;
import br.simulare.presentation.gui.action.simulation.SimulationAction;
import br.simulare.presentation.gui.dialog.DialogWithProgressPanel;
import br.simulare.presentation.gui.tabcontent.simulation.BuyingSellingTabContent;
import br.simulare.presentation.gui.tabcontent.simulation.PerformanceSummaryTabContent;
import br.simulare.presentation.gui.tabcontent.simulation.SimulationTabContent;
import br.simulare.util.MessageManager;

/**
 * Dialog (secondary window) for the control of a simulation.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class SimulationDialog extends DialogWithProgressPanel {
		
	private MessageManager msgManager;
	private SimulationTabContent simulationTabContent;
	private PerformanceSummaryTabContent performanceSummaryTabContent;
	private BuyingSellingTabContent buyingSellingTabContent;
	
	private JTabbedPane tabbedPane;
		
	public SimulationDialog(JFrame parentFrame) {
		
		super(MessageManager.getInstance().
				getMessage("dialog.simulation.title"), parentFrame);
		msgManager = MessageManager.getInstance();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab(msgManager.
				getMessage("dialog.simulation.simulationTab.title"),
				createSimulationTab());

		dialogComponent.setContentPane(tabbedPane);
		dialogComponent.pack();
		
	}

	private Component createSimulationTab() {
		
		JPanel externalPanel = new JPanel(new BorderLayout());
		JPanel southPanel = createProgressPanel();
				
		simulationTabContent = new SimulationTabContent();
		
		externalPanel.add(simulationTabContent.getTabContentComponent(), 
				BorderLayout.CENTER);
		externalPanel.add(southPanel, BorderLayout.SOUTH);
		
		// Events
		SimulationAction simulationAction = new SimulationAction(this);
		okButton.setAction(simulationAction);
		cancelButton.addActionListener(simulationAction);
		
		return externalPanel;
		
	}
		
	// It returns the starting date selected for the simulation.
	public Date getStartingDate() {
		
		return simulationTabContent.getSimulationGeneralConfigurationTabContent().
				getStartingDate();
	
	}

	// It returns the ending date selected for the simulation.
	public Date getEndingDate() {
		
		return simulationTabContent.getSimulationGeneralConfigurationTabContent().
				getEndingDate();
	
	}

	// It returns the stock codes selected for the simulation.
	public String[] getStockCodes() {
		
		return simulationTabContent.getSimulationGeneralConfigurationTabContent().
				getStockCodes();
	
	}

	// It returns the initial investment selected for the simulation.
	public String getInitialInvestment() {
		
		return simulationTabContent.getSimulationGeneralConfigurationTabContent().
				getInitialInvestment();

	}
	
	/**
	 * It returns the percentage of the long position size selected for the  
	 * simulation.
	 */
	public String getLongPositionSizePercentage() {
		
		return simulationTabContent.getSimulationGeneralConfigurationTabContent().
				getLongPositionSizePercentage();
	
	}

	// It returns the price periodicity selected for the simulation.
	public String getPeriodicity() {
		
		return simulationTabContent.getSimulationGeneralConfigurationTabContent().
				getPeriodicity();
	
	}
	
	// It returns the trading price type selected for the simulation.
	public String getTradingPriceType() {
		
		return simulationTabContent.getSimulationGeneralConfigurationTabContent().
				getTradingPriceType();
	
	}
	
	// It returns the trading fee type selected for the simulation.
	public String getTradingFeeType() {
		
		return simulationTabContent.getSimulationGeneralConfigurationTabContent().
				getTradingFeeType();
	
	}
	
	// It returns the trading fee selected for the simulation.
	public String getTradingFee() {
		
		return simulationTabContent.getSimulationGeneralConfigurationTabContent().
				getTradingFee();

	}
	
	// It returns the technical analysis methods selected for the simulation.
	public Hashtable<Integer, Hashtable<Integer, String[]>> getTAMethods() {
		
		Hashtable<Integer, Hashtable<Integer, String[]>> taMethods = 
			simulationTabContent.
				getTrendFollowingIndicatorTabContent().getTrendFollowingIndicators();
		
		taMethods.putAll(simulationTabContent.
				getChartPatternTabContent().getChartPatterns());
	
		return taMethods;
		
	}
	
	// It shows the simulation result.
	public void showSimulationResult(MySimulationResult mySimulationResult){
		
		int tabCount = tabbedPane.getTabCount();

		for (int i = tabCount-1; i > 0 ; i--) {
			tabbedPane.remove(i);
		}
		
		performanceSummaryTabContent = 
				new PerformanceSummaryTabContent(mySimulationResult);
		tabbedPane.addTab(msgManager.
				getMessage("dialog.simulation.performanceSummaryTab.title"),
				performanceSummaryTabContent.getTabContentComponent());
		
		buyingSellingTabContent = 
				new BuyingSellingTabContent(mySimulationResult);
		tabbedPane.addTab(msgManager.
				getMessage("dialog.simulation.buyingSellingTab.title"),
				buyingSellingTabContent.getTabContentComponent());
		
	}

}
