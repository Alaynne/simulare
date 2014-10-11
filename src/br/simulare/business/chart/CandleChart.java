package br.simulare.business.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.ui.TextAnchor;
import demo.CircleDrawer;

import br.framesim.simulation.chart.Chart;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.Transaction;

/**
 * Candlestick chart.
 * 
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public class CandleChart extends Chart {

	private DefaultOHLCDataset dataset;

	public CandleChart(String chartTitle, List<Price> prices) {
		
		super(chartTitle);
		dataset = criateDataSet(prices);
		createPanel();
	
	}
	
	public CandleChart(List<Price> prices) {

		super("");
		dataset = criateDataSet(prices);
		createPanel();

	}
		
	private DefaultOHLCDataset criateDataSet(List<Price> prices) {
		
		OHLCDataItem item[] = new OHLCDataItem[prices.size()];
		
		for (int i = 0; i < item.length; i++) {
			Price price = prices.get(i);
			if (price.getOpenPrice() == 0 && price.getClosePrice()
					!=0) {
				price.setOpenPrice(price.getClosePrice());
				price.setHighPrice(price.getClosePrice());
				price.setMiddlePrice(price.getClosePrice());
				price.setLowPrice(price.getClosePrice());
			}
			item[i] = new OHLCDataItem(price.getTradingSessionDate(), price
					.getOpenPrice(), price.getHighPrice(), price
					.getLowPrice(), price.getClosePrice(), price
					.getTotalVolume());
		}
		
		return new DefaultOHLCDataset("", item);
	
	}

	protected void createPanel() {
		
		chart = ChartFactory.createCandlestickChart(title, "Date",
				"Price", dataset, false);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		CandlestickRenderer renderer = (CandlestickRenderer)plot.getRenderer();
        renderer.setBaseStroke(new BasicStroke(2.0f));
       
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
       
        NumberAxis yAxis1 = (NumberAxis) plot.getRangeAxis();
        yAxis1.setAutoRangeIncludesZero(false);  
		
		panel = new ChartPanel(chart);

	}

	public void addBuyingTransactions(Collection<Transaction> buyingTransactions) {
		
		for (Transaction transaction : buyingTransactions) {
			addBuyingPoint(transaction.getDate(), transaction.getPrice());
		}
	
	}
	
	public void addSellingTransactions(Collection<Transaction> sellingTransactions) {
		
		for (Transaction transaction : sellingTransactions) {
			addSellingPoint(transaction.getDate(), transaction.getPrice());
		}
	
	}

	private void addBuyingPoint(Date buyingDate, double buyingPrice) {
		addPoint(buyingDate,buyingPrice,Color.RED,Color.BLUE,"[B]",1);
	}

	private void addSellingPoint(Date sellingDate, double sellingPrice) {
		addPoint(sellingDate,sellingPrice,Color.RED,Color.MAGENTA,"[S]",-1);
	}

	private void addPoint(Date date, double value, Color circleColor,
			Color textColor, String label, int orientation) {
		
		long millis = date.getTime();
		final CircleDrawer cd = new CircleDrawer(circleColor, new BasicStroke(
				1.0f), null);
		final XYAnnotation bestBid = new XYDrawableAnnotation(millis, value,
				11, 11, cd);
		
		XYPlot plot = chart.getXYPlot();
		plot.addAnnotation(bestBid);
		XYPointerAnnotation annotation = new XYPointerAnnotation(label, millis,
				value, orientation * 3.0 * Math.PI / 4.0);
		annotation.setTipRadius(10.0);
		annotation.setBaseRadius(35.0);
		annotation.setFont(new Font("SansSerif", Font.PLAIN, 9));
		annotation.setPaint(textColor);
		annotation.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
		plot.addAnnotation(annotation);
		
	}
		
}
