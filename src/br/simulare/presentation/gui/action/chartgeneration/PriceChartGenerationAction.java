package br.simulare.presentation.gui.action.chartgeneration;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JOptionPane;

import br.simulare.business.chart.CandleChart;
import br.simulare.control.appcontroller.AppController;
import br.simulare.presentation.gui.action.Action;
import br.simulare.presentation.gui.tabcontent.chartgeneration.PriceChartTabContent;

import br.framesim.util.DBException;
import br.simulare.util.InvalidDataException;
import br.simulare.util.MessageManager;

/**
 * Action for the generation of a new price chart.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public class PriceChartGenerationAction extends Action {

	public PriceChartGenerationAction(PriceChartTabContent 
			priceChartTabContent) {
		
		super(MessageManager.getInstance().getMessage("generatePriceChart"),
				priceChartTabContent);
		
	}

	public void actionPerformed(ActionEvent event) {
		
		MessageManager msgManager = MessageManager.getInstance();
		Date startingDate = ((PriceChartTabContent)agent).getStartingDate();
		Date endingDate = ((PriceChartTabContent)agent).getEndingDate();
		String stockCode = ((PriceChartTabContent)agent).getStockCode();
		String pricePeriodicity = ((PriceChartTabContent)agent).getPricePeriodicity();

		try {
			CandleChart priceChart = AppController.getInstance().
				generatePriceChart(stockCode, startingDate, endingDate, 
						pricePeriodicity);
			((PriceChartTabContent)agent).setPricePanel(priceChart.getPanel());
		} catch (InvalidDataException e) {
			((PriceChartTabContent)agent).showMessage(msgManager.
					getMessage("error.invalidData.priceChartGeneration") + '\n'
							+ e.getMessage(), JOptionPane.ERROR_MESSAGE);
		} catch (DBException e) {
			((PriceChartTabContent)agent).showMessage(MessageManager.
					getInstance().
					getMessage("error.unexpectedEvent.priceChartGeneration"),
					JOptionPane.ERROR_MESSAGE);
		}

	}
	
}
