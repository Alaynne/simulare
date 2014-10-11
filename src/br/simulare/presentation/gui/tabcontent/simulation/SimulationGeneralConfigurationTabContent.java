package br.simulare.presentation.gui.tabcontent.simulation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import br.simulare.control.appcontroller.AppController;
import br.simulare.presentation.gui.action.simulation.NewSimulationGeneralConfigurationAction;
import br.simulare.presentation.gui.tabcontent.TabContent;
import br.simulare.util.ConfigurationException;
import br.simulare.util.ConfigurationManager;
import br.simulare.util.Util;
import br.framesim.util.DBException;
import br.simulare.util.MessageManager;

/**
 * Graphic content of the general configuration tab, related to the simulation.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class SimulationGeneralConfigurationTabContent extends TabContent {
	
	private JDateChooser startingDateChooser;
	private JDateChooser endingDateChooser;
	private List stockList;
	private JTextField initialInvestmentField;
	private JTextField longPositionSizePercentageField;
	private JComboBox periodicityCombo;
	private JComboBox tradingPriceTypeCombo;
	private JComboBox tradingFeeTypeCombo;
	private JTextField tradingFeeField;
	
	private int defaultSelectedStockIndex;
	
	private final Dimension EXTERNAL_PANEL_SIZE = new Dimension(970, 400);
	
	public SimulationGeneralConfigurationTabContent() {
		component = createTabContentComponent();
	}
	
	protected Component createTabContentComponent() {
		
		JPanel externalPanel = new JPanel(new BorderLayout());
		externalPanel.setPreferredSize(EXTERNAL_PANEL_SIZE);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100,
				170));
		
		centerPanel.add(createTimeframePanel(), BorderLayout.NORTH);
		centerPanel.add(createOperationalPanel(), BorderLayout.CENTER);
		
		externalPanel.add(centerPanel, BorderLayout.CENTER);
		
		// Event
		JButton newSimulationGeneralConfiguration = 
			new JButton(new NewSimulationGeneralConfigurationAction(this));
		
		eastPanel.add(newSimulationGeneralConfiguration);
		
		externalPanel.add(eastPanel, BorderLayout.EAST);

		return externalPanel;
		
	}
	
	private JPanel createTimeframePanel() {
		
		MessageManager msgManager = MessageManager.getInstance();
		JPanel timeframePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 
				20, 5));
		
		timeframePanel.setBorder(BorderFactory.createTitledBorder(msgManager.
					getMessage("dialog.simulation.timeframePanel.title")));
		
		JLabel startingDate = new JLabel(msgManager.getMessage("startingDate"));
		JLabel endingDate = new JLabel(msgManager.getMessage("endingDate"));
		startingDateChooser = new JDateChooser("dd/MM/yy", "##/##/##", '_');
		endingDateChooser = new JDateChooser("dd/MM/yy", "##/##/##", '_');
		try {
			ConfigurationManager configManager = ConfigurationManager.
					getInstance();
			startingDateChooser.setDate(Util.parseDate(configManager.
					getValue("simulation.startingDateBounds")));
			endingDateChooser.setDate(Util.parseDate(configManager.
					getValue("simulation.endingDateBounds")));
		} catch (Exception e) {
			// It uses the current date.
			startingDateChooser.setDate(new GregorianCalendar().getTime());
			endingDateChooser.setDate(new GregorianCalendar().getTime());
		}
		
		timeframePanel.add(startingDate);
		timeframePanel.add(startingDateChooser);
		timeframePanel.add(endingDate);
		timeframePanel.add(endingDateChooser);

		return timeframePanel;
		
	}

	private JPanel createOperationalPanel() {
		
		MessageManager msgManager = MessageManager.getInstance();
		JPanel operationalPanel = new JPanel(new BorderLayout());
		
		operationalPanel.setBorder(BorderFactory.
				createTitledBorder(msgManager.
				getMessage("dialog.simulation.operationalPanel.title")));

		JLabel stocks = new JLabel(msgManager.getMessage("stocks"));
		stockList = createStockList();
		JScrollPane scrollPane = new JScrollPane(stockList);
		
		JLabel initialInvestment = new JLabel(msgManager.
				getMessage("initialInvestment"));
		initialInvestmentField = new JTextField(10);
				
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,0));
		northPanel.add(stocks);
		northPanel.add(scrollPane);
		northPanel.add(initialInvestment);
		northPanel.add(initialInvestmentField);
		
		JLabel longPositionSizePercentage = new JLabel(msgManager.
				getMessage("longPositionSizePercentage"));
		longPositionSizePercentageField = new JTextField(10);
		
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 30));
		panel1.add(longPositionSizePercentage);
		panel1.add(longPositionSizePercentageField);
		
		JLabel periodicity = new JLabel(msgManager.
				getMessage("periodicity"));
		periodicityCombo = createPeriodicityCombo();
		JLabel tradingPriceType = new JLabel(msgManager.
				getMessage("tradingPriceType"));
		tradingPriceTypeCombo = createTradingPriceTypeCombo();
		
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 30));
		panel2.add(periodicity);
		panel2.add(periodicityCombo);
		panel2.add(tradingPriceType);
		panel2.add(tradingPriceTypeCombo);
		
		JLabel tradingFeeTypes = new JLabel(msgManager.
				getMessage("tradingFeeTypes"));
		tradingFeeTypeCombo = createTradingFeeTypeCombo();
		
		JLabel tradingFee = new JLabel(msgManager.
				getMessage("tradingFee"));
		tradingFeeField = new JTextField(10);
		
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 30));
		panel3.add(tradingFeeTypes);
		panel3.add(tradingFeeTypeCombo);
		panel3.add(tradingFee);
		panel3.add(tradingFeeField);
	
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(panel1, BorderLayout.NORTH);
		southPanel.add(panel2, BorderLayout.CENTER);
		southPanel.add(panel3, BorderLayout.SOUTH);
		
		operationalPanel.add(northPanel, BorderLayout.NORTH);
		operationalPanel.add(southPanel, BorderLayout.SOUTH);
		
		return operationalPanel;
		
	}
	
	private List createStockList() {
		
		List stockList = new List(5, true);
		String defaultSelectedStock;
		
		try {
			String[] stockCodes = AppController.getInstance().
					getAllStockCodes();
			Arrays.sort(stockCodes);
			try {
				defaultSelectedStock = ConfigurationManager.getInstance().
					getValue("simulation.defaultSelectedStock");
			} catch (ConfigurationException e) {
				defaultSelectedStock = "";
			}

			for (int i = 0; i < stockCodes.length; i++) {
				stockList.add(stockCodes[i]);
				if (stockCodes[i].equals(defaultSelectedStock)) {
					defaultSelectedStockIndex = i;
				}
			}
			
			stockList.select(defaultSelectedStockIndex);
			
		} catch (DBException e) {
			// It creates an empty list.
		}	
		
		return stockList;
		
	}

	private JComboBox createPeriodicityCombo() {
		return new JComboBox(AppController.getInstance().
				getAllPricePeriodicities());
	}
	
	private JComboBox createTradingPriceTypeCombo() {
		return new JComboBox(AppController.getInstance().
				getAllTradingPriceTypes());
	}
	
	private JComboBox createTradingFeeTypeCombo() {
		return new JComboBox(AppController.getInstance().
				getAllTradingFeeTypes());
	}
	
	// It returns the starting date selected for the simulation.
	public Date getStartingDate() {
		return startingDateChooser.getDate();
	}

	// It returns the ending date selected for the simulation.
	public Date getEndingDate() {
		return endingDateChooser.getDate();
	}
	
	// It returns the stock codes selected for the simulation.
	public String[] getStockCodes() {
		return stockList.getSelectedItems();
	}

	// It returns the initial investment selected for the simulation.
	public String getInitialInvestment() {
		return initialInvestmentField.getText();
	}

	// It returns the percentage of long position size selected for the simulation.
	public String getLongPositionSizePercentage() {
		return longPositionSizePercentageField.getText();
	}

	// It returns the price periodicity selected for the simulation.
	public String getPeriodicity() {
		return periodicityCombo.getSelectedItem().toString();
	}
	
	// It returns the trading price type selected for the simulation.
	public String getTradingPriceType() {
		return tradingPriceTypeCombo.getSelectedItem().toString();
	}
	
	// It returns the trading fee type selected for the simulation.
	public String getTradingFeeType() {
		return tradingFeeTypeCombo.getSelectedItem().toString();
	}
	
	// It returns the trading fee selected for the simulation.
	public String getTradingFee() {
		return tradingFeeField.getText();
	}

	public void reset() {
		
		try {
			ConfigurationManager configManager = ConfigurationManager.
					getInstance();
			startingDateChooser.setDate(Util.parseDate(configManager.
					getValue("simulation.startingDateBounds")));
			endingDateChooser.setDate(Util.parseDate(configManager.
					getValue("simulation.endingDateBounds")));
		} catch (Exception e) {
			// It uses the current date.
			startingDateChooser.setDate(new GregorianCalendar().getTime());
			endingDateChooser.setDate(new GregorianCalendar().getTime());
		}
	
		int[] selectedStockIndexes = stockList.getSelectedIndexes();
		for(int i = 0; i < selectedStockIndexes.length; i++) {
			stockList.deselect(selectedStockIndexes[i]);
		}
		stockList.select(defaultSelectedStockIndex);
		initialInvestmentField.setText("");
		longPositionSizePercentageField.setText("");
		periodicityCombo.setSelectedIndex(0);
		tradingPriceTypeCombo.setSelectedIndex(0);
		tradingFeeTypeCombo.setSelectedIndex(0);
		tradingFeeField.setText("");
		
	}
	
}
