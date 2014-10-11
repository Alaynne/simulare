package br.simulare.business.table;

import javax.swing.table.AbstractTableModel;

/**
 * It manages a table.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel {
		
		private String[] columnNames;
	    private String[][] data;

	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return data.length;
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }
	    
	    public void setColumnNames(String[] columnNames) {
	    	this.columnNames = columnNames;
	    }
	    
	    public void setData(String[][] data) {
	    	this.data = data;
	    }
	 
	}

