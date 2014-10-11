package br.simulare.business.table;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.framesim.simulation.core.Business;
import br.framesim.simulation.table.Table;

/**
 * Table for describing the buying and selling transactions.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class BuyingSellingTable extends Table {

	private String[][] data;
	private String[] columnNames = {"STOCK", "TRANSACTION", "DATE", "PRICE",
		"SHARES", "TRADING FEES", "TOTAL"};

	private final int NUM_OF_COLUMNS = 7;

	public BuyingSellingTable(List<Business> business) {
		
		generateData(business);
		buildPane();
		
	}
	
	private void generateData(List<Business> business) {
		
		String[][] data = new String[business.size() * 2][NUM_OF_COLUMNS];
		int i = 0;
		
		for(Business bsn : business) {
			data[i][0] = bsn.getStock().getCode();
			data[i][1] = bsn.getBuyingTransaction().getTypeToString();
			data[i][2] = br.framesim.util.Util.formatDate(bsn.getBuyingTransaction().getDate());
			data[i][3] = br.simulare.util.Util.formatRealValue(bsn.getBuyingTransaction().
					getPrice());
			data[i][4] = Integer.toString(bsn.getBuyingTransaction().
					getQuantityOfShares());
			data[i][5] = br.simulare.util.Util.formatRealValue(bsn.getBuyingTransaction().
					getTotalTradingFee());
			data[i][6] = br.simulare.util.Util.formatRealValue(bsn.getBuyingTransaction().
					getTotalValue());
			
			data[i + 1][0] = bsn.getStock().getCode();
			data[i + 1][1] = bsn.getSellingTransaction().getTypeToString();
			data[i + 1][2] = br.framesim.util.Util.formatDate(bsn.getSellingTransaction().
					getDate());
			data[i + 1][3] = br.simulare.util.Util.formatRealValue(bsn.getSellingTransaction().
					getPrice());
			data[i + 1][4] = Integer.toString(bsn.getSellingTransaction().
					getQuantityOfShares());
			data[i + 1][5] = br.simulare.util.Util.formatRealValue(bsn.getSellingTransaction().
					getTotalTradingFee());
			data[i + 1][6] = br.simulare.util.Util.formatRealValue(bsn.getSellingTransaction().
					getTotalValue());
			
			i += 2;
		}
		
		this.data = data;
		
	}
	
	protected void buildPane() {
		
		TableModel tableModel = new TableModel();
		tableModel.setColumnNames(columnNames);
		tableModel.setData(data);
		table = new JTable();
		table.setModel(tableModel);
				
		pane = new JScrollPane(table);
		
	}
			
}
