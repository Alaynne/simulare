package br.simulare.presentation.gui.tabcontent.simulation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.simulare.control.appcontroller.AppController;
import br.simulare.presentation.gui.action.simulation.ReversalCandlePatternConfigurationAction;
import br.simulare.presentation.gui.tabcontent.TabContent;
import br.simulare.util.MessageManager;

/**
 * Graphic content of the technical analysis chart pattern tab, related to the simulation.
 *  
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ChartPatternTabContent extends TabContent {
	
	private JCheckBox reversalCandlePatternCheckBox;
	private List bullishCandlePatternList;
	private List bearishCandlePatternList;
	
	private final Dimension EXTERNAL_PANEL_SIZE = new Dimension(970, 400);
	private final Dimension CANDLESTICK_PANEL_SIZE = new Dimension(480, 380);
	
	public ChartPatternTabContent() {
		component = createTabContentComponent();
	}

	protected Component createTabContentComponent() {
		
		JPanel externalPanel = new JPanel(new BorderLayout());
		externalPanel.setPreferredSize(EXTERNAL_PANEL_SIZE);
		
		JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		westPanel.add(createCandlestickPanel());
		
		externalPanel.add(westPanel, BorderLayout.WEST);
		
		return externalPanel;
		
	}
	
	private JPanel createCandlestickPanel() {
		
		JPanel candlestickPanel = new JPanel(new GridLayout(2, 1));
		
		candlestickPanel.setPreferredSize(CANDLESTICK_PANEL_SIZE);
		candlestickPanel.setBorder(BorderFactory.
				createTitledBorder(MessageManager.getInstance().
				getMessage("dialog.simulation.candlestickPanel.title")));
		candlestickPanel.add(createReversalCandlePatternPanel());
		
		return candlestickPanel;
		
	}

	private JPanel createReversalCandlePatternPanel() {
		
		MessageManager msgManager = MessageManager.getInstance();
		
		JPanel reversalCandlePatternPanel = new JPanel(new BorderLayout()); 
		JPanel centerPanel = new JPanel(new FlowLayout());
		
		reversalCandlePatternCheckBox = 
			new JCheckBox(new ReversalCandlePatternConfigurationAction(this));
				
		JLabel bullishPatterns = new JLabel(msgManager
				.getMessage("bullishCandlePatterns"));
		bullishCandlePatternList = createBullishCandlePatternList();
		bullishCandlePatternList.setEnabled(false);
		JLabel bearishPatterns = new JLabel(msgManager.
				getMessage("bearishCandlePatterns"));
		bearishCandlePatternList = createBearishCandlePatternList();
		bearishCandlePatternList.setEnabled(false);
		
		centerPanel.add(bullishPatterns);
		centerPanel.add(bullishCandlePatternList);
		centerPanel.add(bearishPatterns);
		centerPanel.add(bearishCandlePatternList);

		reversalCandlePatternPanel.add(reversalCandlePatternCheckBox, 
				BorderLayout.NORTH);
		reversalCandlePatternPanel.add(centerPanel, BorderLayout.CENTER);
		
		return reversalCandlePatternPanel;
		
	}
	
	private List createBullishCandlePatternList() {
		
		List bullishCandlePatternList = new List(5, true);
		String[] bullishCandlePatternTypes = AppController.getInstance().
				getAllBullishCandlePatternTypes();
		
		for(String patternType : bullishCandlePatternTypes) {
			bullishCandlePatternList.add(patternType);
		}

		return bullishCandlePatternList;
		
	}
	
	private List createBearishCandlePatternList() {
		
		List bearishCandlePatternList = new List(5, true);
		String[] bearishCandlePatternTypes = AppController.getInstance().
				getAllBearishCandlePatternTypes();
		
		for(String patternType : bearishCandlePatternTypes) {
			bearishCandlePatternList.add(patternType);
		}
		
		return bearishCandlePatternList;
		
	}

	public void enableReversalCandlePatternConfiguration() {
		
		bullishCandlePatternList.setEnabled(true);
		bearishCandlePatternList.setEnabled(true);
				
	}
	
	public void disableReversalCandlePatternConfiguration() {
		
		bullishCandlePatternList.setEnabled(false);
		bearishCandlePatternList.setEnabled(false);
				
	}

	public boolean areReversalCandlePatternsSelected() {
		return reversalCandlePatternCheckBox.isSelected();
	}	
	
	// It returns the bullish candle patterns selected for the simulation.
	public String[] getBullishCandlePatterns() {
		return bullishCandlePatternList.getSelectedItems();	
	}
	
	// It returns the bearish candle patterns selected for the simulation.
	public String[] getBearishCandlePatterns() {
		return bearishCandlePatternList.getSelectedItems();	
	}
	
	// It returns the technical analysis chart patterns selected for the simulation.
	public Hashtable<Integer,Hashtable<Integer,String[]>> getChartPatterns(){
		
		Hashtable<Integer, Hashtable<Integer, String[]>> chartPatterns =
				new Hashtable<Integer, Hashtable<Integer, String[]>>();
				
		if (areReversalCandlePatternsSelected()) {	
			chartPatterns.
					put(AppController.TAMETHOD_REVERSALCANDLEPATTERNS,
							getReversalCandlePatternConfiguration());	
		}
		
		return chartPatterns;
		
	}
	
	// It returns the configuration selected for the reversal candlestick chart patterns.
	public Hashtable<Integer, String[]> getReversalCandlePatternConfiguration() {
		
		Hashtable<Integer, String[]> reversalCandlePatternConfiguration = 
				new Hashtable<Integer, String[]>();
		
		reversalCandlePatternConfiguration. put(AppController.TAPARAMETER_BULLISHPATTERNS, 
				getBullishCandlePatterns());
		reversalCandlePatternConfiguration.put(AppController.TAPARAMETER_BEARISHPATTERNS, 
				getBearishCandlePatterns());
		
		return reversalCandlePatternConfiguration;
		
	}
	
}
