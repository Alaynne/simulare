package br.simulare.presentation.gui.tabcontent.simulation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.simulare.control.appcontroller.AppController;
import br.simulare.presentation.gui.action.simulation.MovingAvgConfigurationAction;
import br.simulare.presentation.gui.tabcontent.TabContent;
import br.simulare.util.MessageManager;

/**
 * Graphic content of the tab for trend following indicators, related to the simulation.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class TrendFollowingIndicatorTabContent extends TabContent {
	
	private JCheckBox didiIndexCheckBox;
	private JCheckBox movingAvgCheckBox;
	private JComboBox movingAvgTypeCombo;
	private JTextField movingAvgTimeSpanField;
	
	private final Dimension EXTERNAL_PANEL_SIZE = new Dimension(970, 400);
	private final Dimension WEST_PANEL_SIZE = new Dimension(515, 300);

	TrendFollowingIndicatorTabContent() {
		component = createTabContentComponent();
	}

	protected Component createTabContentComponent() {
		
		JPanel externalPanel = new JPanel(new BorderLayout());
		externalPanel.setPreferredSize(EXTERNAL_PANEL_SIZE);
		
		JPanel westPanel = new JPanel(new GridLayout(5, 1));
		westPanel.setPreferredSize(WEST_PANEL_SIZE);
		
		westPanel.add(this.createDidiIndexPanel());
		westPanel.add(this.createMovingAvgPanel());
		
		externalPanel.add(westPanel, BorderLayout.WEST);
		
		return externalPanel;
		
	}
	
	private JPanel createDidiIndexPanel() {
		
		JPanel didiIndexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		didiIndexCheckBox = new JCheckBox(MessageManager.getInstance().
				getMessage("didiIndex"));
		
		didiIndexPanel.add(didiIndexCheckBox);
		
		return didiIndexPanel;
		
	}
	
	private JPanel createMovingAvgPanel() {
		
		MessageManager msgManager = MessageManager.getInstance();
		JPanel movingAvgPanel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel(new FlowLayout());
		
		movingAvgCheckBox = 
				new JCheckBox(new MovingAvgConfigurationAction(this));
		
		JLabel movingAvgType = new JLabel(msgManager.
				getMessage("movingAvgType"));
		movingAvgTypeCombo = createMovingAvgTypeCombo();
		movingAvgTypeCombo.setEnabled(false);
		
		JLabel movingAvgTimeSpan = new JLabel(msgManager.
				getMessage("timeSpan"));
		movingAvgTimeSpanField = new JTextField(10);
		movingAvgTimeSpanField.setEnabled(false);

		centerPanel.add(movingAvgType);
		centerPanel.add(movingAvgTypeCombo);
		centerPanel.add(movingAvgTimeSpan);
		centerPanel.add(movingAvgTimeSpanField);
		
		movingAvgPanel.add(movingAvgCheckBox, BorderLayout.NORTH);
		movingAvgPanel.add(centerPanel, BorderLayout.CENTER);
		
		return movingAvgPanel;
		
	}
	
	private JComboBox createMovingAvgTypeCombo() {
		
		return new JComboBox(AppController.getInstance()
				.getAllMovingAvgTypes());
	
	}

	public void enableMovingAvgConfiguration() {
		
		movingAvgTypeCombo.setEnabled(true);
		movingAvgTimeSpanField.setEnabled(true);
			
	}

	public void disableMovingAvgConfiguration() {
		
		movingAvgTypeCombo.setEnabled(false);
		movingAvgTimeSpanField.setEnabled(false);
			
	}

	public boolean isDidiIndexSelected() {
		return didiIndexCheckBox.isSelected();
	}
	
	public boolean isMovingAvgSelected () {
		return movingAvgCheckBox.isSelected();
	}
	
	// It returns the moving average type selected for the moving average indicator.
	public String getMovingAvgType() {
		return movingAvgTypeCombo.getSelectedItem().toString();
	}
	
	// It returns the moving average time span selected for the moving average indicator.
	public String getMovingAvgTimeSpan() {
		return movingAvgTimeSpanField.getText();
	}

	// It returns the trend following indicators selected for the simulation.
	public Hashtable<Integer, Hashtable<Integer, String[]>> 
			getTrendFollowingIndicators() {
		
		Hashtable<Integer, Hashtable<Integer, String[]>> trendFollowingIndicators = 
				new Hashtable<Integer, Hashtable<Integer, String[]>>(); 
		
		if (isDidiIndexSelected()) {
			trendFollowingIndicators.put(AppController.TAMETHOD_DIDIINDEX, 
					new Hashtable<Integer, String[]>());
		}
		
		if (isMovingAvgSelected()) {
			trendFollowingIndicators.
					put(AppController.TAMETHOD_MOVINGAVERAGE,
							getMovingAvgConfiguration());
		}

		return trendFollowingIndicators;
		
	}
	
	// It returns the configuration selected for the moving average indicator.
	public Hashtable<Integer, String[]> getMovingAvgConfiguration() {
		
		Hashtable<Integer, String[]> movingAvgConfiguration = 
				new Hashtable<Integer, String[]>();
				
		String[] movingAvgType = new String[1];
		movingAvgType[0] = getMovingAvgType();
		movingAvgConfiguration.put(AppController.TAPARAMETER_MOVINGAVERAGETYPE,
				movingAvgType);
		
		String[] movingAvgTimeSpan = new String[1];
		movingAvgTimeSpan[0] = getMovingAvgTimeSpan();
		movingAvgConfiguration.put(AppController.TAPARAMETER_MOVINGAVERAGETIMESPAN, movingAvgTimeSpan);
		
		return movingAvgConfiguration;
		
	}
	
}
