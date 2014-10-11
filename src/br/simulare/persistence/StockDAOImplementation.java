package br.simulare.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.framesim.dataaccess.DBManager;
import br.framesim.dataaccess.StockDAO;
import br.framesim.simulation.core.StockMarket;
import br.framesim.simulation.core.Stock;
import br.framesim.util.DBException;
import br.simulare.control.appcontroller.AppController;

/**
 * It implements DAO (Data Access Object) design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class StockDAOImplementation implements StockDAO {

	// Logger for this class.
	private static final Logger logger = Logger.getLogger(StockDAOImplementation.class);
	
	private List<Stock> fillStocks(ResultSet result) throws SQLException {
	
		List<Stock> stocks = new ArrayList<Stock>();
	
		while (result.next()) {
			Stock stock = new Stock();
			stock.setId(result.getLong("id"));
			stock.setCode(result.getString("code"));
			stock.setShortName(result.getString("short_name"));
			stock.setStockMarket(AppController.getInstance().
					parseStockMarket(result.getString("stock_market")));
			stock.setRoundLot(result.getInt("round_lot"));
			stock.setQuotingFactor(result.getInt("quoting_factor"));
		
			stocks.add(stock);
		}
	
		return stocks;

	}
		
	public void createStock(Stock stock) throws DBException {
		
		try {
			PreparedStatement sql = DBManager.getInstance().
				getConnection().prepareStatement("INSERT INTO STOCK (code, " +
					"short_name,	stock_market, round_lot, quoting_factor)" +
					" VALUES (?,?,?,?,?)");
		
			sql.setString(1, stock.getCode());
			sql.setString(2, stock.getShortName());
			sql.setString(3, stock.getStockMarket().getType());
			sql.setInt(4, stock.getRoundLot());
			sql.setInt(5, stock.getQuotingFactor());
			sql.execute();
			sql.close();
		} catch (SQLException e) {
			logger.error("createStock(Stock)", e);
			throw new DBException(e.getMessage());
		}

	}

	public long findStockId(String code) throws DBException {
		
		long id = -1;
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
					prepareStatement("SELECT ID FROM STOCK WHERE CODE=?");
			sql.setString(1, code);
			ResultSet result = sql.executeQuery();
			if (!result.next()) {
				if (logger.isDebugEnabled()) {
					logger.debug("findStockId(String) - The stock " + code
							+ " could not be found in the database.");
				}
			} else {
				id = result.getLong("ID");
			}
			result.close();
			sql.close();
		} catch (SQLException e) {
			logger.error("findStockId(String)", e);
			throw new DBException(e.getMessage());
		}
		
		return id;
		
	}

	public Stock getStock(String stockCode) throws DBException {
		
		Stock stock = null;
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
					prepareStatement("SELECT * FROM stock WHERE code=?");
			sql.setString(1, stockCode);
			ResultSet result = sql.executeQuery();
			List<Stock> stocks = fillStocks(result);
			if (stocks.size() > 0) {
				stock = stocks.get(0);
			}
			result.close();
			sql.close();
		} catch (SQLException e) {
			logger.error("getStock(String)", e);
			throw new DBException(e.getMessage());
		}
		
		return stock;
		
	}

	public List<String> getAllStockCodes() throws DBException {
		
		List<String> codes = new ArrayList<String>();
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
					prepareStatement("SELECT code FROM stock");
			ResultSet result = sql.executeQuery();
			while (result.next()) {
				codes.add(result.getString(1));
			}
			result.close();
			sql.close();
		
			return codes;
		} catch (SQLException e) {
			logger.error("getAllStockCodes()", e);
			throw new DBException(e.getMessage());
		}
		
	}

	public List<String> getAllStockCodes(StockMarket stockMarket)
			throws DBException {
		
		List<String> codes = new ArrayList<String>();
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
				prepareStatement("SELECT code FROM stock WHERE stock_market=?");
			sql.setString(1, stockMarket.getType());
			ResultSet result = sql.executeQuery();
			while (result.next()) {
				codes.add(result.getString(1));
			}
			result.close();
			sql.close();
		} catch (SQLException e) {
			logger.error("getAllStockCodes(StockMarket)", e);
			throw new DBException(e.getMessage());
		}
		
		return codes;
		
	}

	public void updateStock(Stock stock) throws DBException {
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
				prepareStatement("UPDATE stock SET round_lot=?, " +
						"quoting_factor=? WHERE id=?");
			sql.setInt(1, stock.getRoundLot());
			sql.setInt(2, stock.getQuotingFactor());
			sql.setLong(3, stock.getId());
			sql.execute();
			sql.close();
		} catch (SQLException e) {
			logger.error("updateStock(Stock)", e);
			throw new DBException(e.getMessage());
		}
		
	}

	public void deleteAllStocks() throws DBException {
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
				prepareStatement("DELETE FROM STOCK");
			sql.execute();
			sql.close();
		} catch (SQLException e) {
			logger.error("deleteAllStocks()", e);
			throw new DBException(e.getMessage());
		}
		
	}

}
