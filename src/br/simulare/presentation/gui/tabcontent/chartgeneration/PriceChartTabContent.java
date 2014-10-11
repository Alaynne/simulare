package br.simulare.presentation.gui.tabcontent.chartgeneration;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.simulare.control.appcontroller.AppController;
import br.simulare.presentation.gui.MainFrame;
import br.simulare.presentation.gui.action.chartgeneration.PriceChartGenerationAction;
import br.simulare.presentation.gui.tabcontent.TabContent;
import br.simulare.util.ConfigurationException;
import br.simulare.util.ConfigurationManager;
import br.framesim.util.DBException;
import br.simulare.util.Util;
import br.simulare.util.MessageManager;

import com.toedter.calendar.JDateChooser;

/**
 * Graphic content of the price chart tab.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */
public class PriceChartTabContent extends TabContent {
	
	private MainFrame mainFrame;
	
	private JComboBox stockCombo;
	private JDateChooser startingDateChooser;
	private JDateChooser endingDateChooser;
	private JComboBox periodicityCombo;
	private JPanel pricePanel;
	
	private static final Dimension PRICE_PANEL_SIZE = new Dimension(640, 480);

	public PriceChartTabContent(MainFrame mainFrame) {
		
		component = createTabContentComponent();
		this.mainFrame = mainFrame;
	
	}
	
	protected Component createTabContentComponent() {
	
		MessageManager msgManager = MessageManager.getInstance();		
		JPanel externalPanel = new JPanel(new BorderLayout());
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		stockCombo = createStockCombo();
		
		try {
			ConfigurationManager configManager = ConfigurationManager.
					getInstance();
			startingDateChooser = new JDateChooser("dd/MM/yy", "##/##/##", '_');
			startingDateChooser.setDate(Util.parseDate(configManager.
					getValue("priceChartGeneration.startingDateBounds")));
			endingDateChooser = new JDateChooser("dd/MM/yy", "##/##/##", '_');
			endingDateChooser.setDate(Util.parseDate(configManager.
					getValue("priceChartGeneration.endingDateBounds")));
		} catch (Exception e) {
			// It uses the current date.
			startingDateChooser.setDate(new GregorianCalendar().getTime());
			endingDateChooser.setDate(new GregorianCalendar().getTime());
		}

		periodicityCombo = createPeriodicityCombo();
		
		// Event
		JButton generatePriceChart = 
			new JButton(new PriceChartGenerationAction(this));
		
		northPanel.add(new JLabel(msgManager.getMessage("stock")));
		northPanel.add(stockCombo);
		northPanel.add(new JLabel(msgManager.getMessage("startingDate")));
		northPanel.add(startingDateChooser);
		northPanel.add(new JLabel(msgManager.getMessage("endingDate")));
		northPanel.add(endingDateChooser);
		northPanel.add(new JLabel(msgManager.getMessage("periodicity")));
		northPanel.add(periodicityCombo);
		northPanel.add(generatePriceChart);

		pricePanel = new JPanel();
		pricePanel.setPreferredSize(PRICE_PANEL_SIZE);

		externalPanel.add(northPanel, BorderLayout.NORTH);
		externalPanel.add(pricePanel, BorderLayout.CENTER);

		return externalPanel;

	}
	
	private JComboBox createStockCombo() {
		
		try {
			String[] stockCodes = AppController.getInstance().getAllStockCodes();
			Arrays.sort(stockCodes);
			JComboBox stockCombo = new JComboBox(stockCodes);
			try {
				stockCombo.setSelectedItem(ConfigurationManager.getInstance().
					getValue("priceChartGeneration.defaultSelectedStock"));
			} catch (ConfigurationException e) {
				stockCombo.setSelectedIndex(0);
			}
			return stockCombo;
		} catch (DBException e) {
			// It creates an empty combo.
			return new JComboBox();
		}
		
	}
	
	private JComboBox createPeriodicityCombo() {
		
		return new JComboBox(AppController.getInstance().getAllPricePeriodicities());
	
	}
	
	// It returns the starting date selected for generating the price chart.
	public Date getStartingDate() {
		return startingDateChooser.getDate();
	}

	// It returns the ending date selected for generating the price chart.
	public Date getEndingDate() {
		return endingDateChooser.getDate();
	}

	// It returns the stock code selected for generating the price chart.
	public String getStockCode() {
		return stockCombo.getSelectedItem().toString();
	}

	// It returns the price periodicity selected for generating the price chart.
	public String getPricePeriodicity() {
		return periodicityCombo.getSelectedItem().toString();
	}
	
	public void setPricePanel(JPanel newPricePanel) {
		
		Container container = pricePanel.getParent();
		
		container.remove(pricePanel);
		container.add(newPricePanel);
		pricePanel = newPricePanel;
		container.invalidate();
		container.validate();
	
	}

	public void showMessage(String msg, int msgType) {
		
		mainFrame.showMessageDialog(MessageManager.getInstance().
				getMessage("priceChart"), msg, msgType);
	
	}

}
