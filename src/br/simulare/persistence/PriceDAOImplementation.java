package br.simulare.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.framesim.dataaccess.DBManager;
import br.framesim.dataaccess.PriceDAO;
import br.framesim.simulation.core.Price;
import br.framesim.simulation.core.Periodicity;
import br.framesim.util.DBException;
import br.framesim.util.Util;

/**
 * It implements DAO (Data Access Object) design pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class PriceDAOImplementation implements PriceDAO {

	// Logger for this class.
	private static final Logger logger = Logger.getLogger(PriceDAOImplementation.class);
	
	private List<Price> fillPrices(ResultSet result) throws SQLException {

		List<Price> prices = new ArrayList<Price>();

		while (result.next()) {
			Price price = new Price();
			price.setId(result.getLong("id"));
			price.setTradingSessionDate(result.getDate("trading_session_date"));
			price.setOpenPrice(result.getDouble("open_price"));
			price.setHighPrice(result.getDouble("high_price"));
			price.setLowPrice(result.getDouble("low_price"));
			price.setMiddlePrice(result.getDouble("middle_price"));
			price.setClosePrice(result.getDouble("close_price"));
			price.setPriceOfBestBuyingTransaction(result.
					getDouble("best_buying_price"));
			price.setPriceOfBestSellingTransaction(result.
					getDouble("best_selling_price"));
			price.setQuantityOfShares(result.
					getLong("quantity_shares"));
			price.setTotalVolume(result.getLong("total_volume"));
	
			prices.add(price);
		}

		return prices;

	}
		
	public boolean createPrice(Price price) throws DBException {

		try {
			PreparedStatement sql = DBManager.getInstance().getConnection(). 
				prepareStatement("INSERT INTO PRICE" +
				" ("
				+ "stock_id,"
				+ "trading_session_date,"
				+ "open_price,"
				+ "high_price,"
				+ "low_price,"
				+ "middle_price,"
				+ "close_price,"
				+ "best_buying_price,"
				+ "best_selling_price,"
				+ "quantity_shares, total_volume, periodicity"
				+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
	
			long stockId = DBManager.getInstance().
					findStockId(price.getStock().getCode());
			if (stockId == -1) {
				return false;
			}
	
			sql.setLong(1, stockId);
			sql.setDate(2, new Date(price.getTradingSessionDate().getTime()));
			sql.setDouble(3, price.getOpenPrice());
			sql.setDouble(4, price.getHighPrice());
			sql.setDouble(5, price.getLowPrice());
			sql.setDouble(6, price.getMiddlePrice());
			sql.setDouble(7, price.getClosePrice());
			sql.setDouble(8, price.getPriceOfBestBuyingTransaction());
			sql.setDouble(9, price.getPriceOfBestSellingTransaction());
			sql.setLong(10, price.getQuantityOfShares());
			sql.setLong(11, price.getTotalVolume());
			sql.setString(12, price.getPeriodicity().getType());
	
			sql.execute();
			sql.close();
			return true;
		} catch (SQLException e) {
			logger.error("createPrice(Price)", e);
			throw new DBException(e.getMessage());
		}
		
	}
	
	public List<Price> getAllPrices(String stockCode, 
					Periodicity periodicity) throws DBException {
		
		List<Price> prices = new ArrayList<Price>();
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
					prepareStatement("SELECT price.* FROM price, stock " +
					"WHERE stock.id=price.stock_id AND periodicity=? " +
					"AND stock.code=? " +
					"ORDER BY trading_session_date ASC");
			sql.setString(1, periodicity.getType());
			sql.setString(2, stockCode);
			ResultSet result = sql.executeQuery();

			prices = fillPrices(result);
			result.close();
			sql.close();
		} catch (SQLException e) {
			logger.error("getAllPrices(String, Periodicity)", e);
		}
		return prices;
	
	}

	public List<Price> getAllPrices(String stockCode,
			java.util.Date startingDate, java.util.Date endingDate, 
					Periodicity periodicity) throws DBException {
		
		List<Price> prices = new ArrayList<Price>();
		Price lastPrice;
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
					prepareStatement("SELECT PRICE.* FROM PRICE" + 
					", stock WHERE stock.id=" + 
					"PRICE.stock_id AND stock.code=? " +
					"AND trading_session_date>=? AND trading_session_date<? AND periodicity=?"
					+ "ORDER BY trading_session_date ASC");
			
			sql.setString(1, stockCode);
			sql.setDate(2, new Date(startingDate.getTime()));
			sql.setDate(3, new Date(endingDate.getTime()));
			sql.setString(4, periodicity.getType());
			ResultSet result = sql.executeQuery();

			prices = fillPrices(result);
			result.close();
			sql.close();
			
			if ((lastPrice = DBManager.getInstance().
					getPrice(stockCode, endingDate, periodicity)) != null) {
				prices.add(lastPrice);
			}
			
			return prices;
		} catch (SQLException e) {
			logger.error("getAllPrices(String, Date, Date, " +
					"Periodicity)", e);
			throw new DBException(e.getMessage());
		}
	
	}

	public Price getPrice(String stockCode, java.util.Date date,
			Periodicity periodicity) throws DBException {
		
		Price price = null;
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
					prepareStatement("SELECT PRICE" + 
					".* FROM PRICE" + 
					", stock WHERE stock.id=" + 
					"PRICE.stock_id AND stock.code=? AND trading_session_date=? " +
					"AND periodicity=?");
			
			sql.setString(1, stockCode);
			sql.setDate(2, new Date(date.getTime()));
			sql.setString(3, periodicity.getType());
			ResultSet result = sql.executeQuery();
			List<Price> prices = fillPrices(result);
			if (prices.size() > 0) {
				price = prices.get(0);
			}
			result.close();
			sql.close();
		} catch (SQLException e) {
			logger.error("getPrice(String, Date, Periodicity)", e);
			throw new DBException(e.getMessage());
		}
		
		return price;
	
	}

	public void updatePrice(Price price) throws DBException {
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
					prepareStatement("UPDATE PRICE" 
							+ " SET open_price=?, "
							+ "high_price=?, low_price=?, middle_price=?,"
							+ " close_price=?, best_buying_price=?, " 
							+ "best_selling_price=?, quantity_shares=?, "
							+ "total_volume=?  WHERE id=?");
			sql.setDouble(1, price.getOpenPrice());
			sql.setDouble(2, price.getHighPrice());
			sql.setDouble(3, price.getLowPrice());
			sql.setDouble(4, price.getMiddlePrice());
			sql.setDouble(5, price.getClosePrice());
			sql.setDouble(6, price.getPriceOfBestBuyingTransaction());
			sql.setDouble(7, price.getPriceOfBestSellingTransaction());
			sql.setLong(8, price.getQuantityOfShares());
			sql.setLong(9, price.getTotalVolume());
			sql.setLong(10, price.getId());
			sql.execute();
			sql.close();
		} catch (SQLException e) {
			logger.error("updatePrice(Price)", e);
			throw new DBException(e.getMessage());
		}
		
	}

	public void deleteAllPrices() throws DBException {
		
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
					prepareStatement("DELETE FROM PRICE");
			sql.execute();
			sql.close();
		} catch (SQLException e) {
			logger.error("deleteAllPrices()", e);
			throw new DBException(e.getMessage());
		}
		
	}
	
	public boolean hasTradingSession(java.util.Date date, 
			Periodicity periodicity) throws DBException {
		
		boolean hasPrice = false;
				
		try {
			PreparedStatement sql = DBManager.getInstance().getConnection().
					prepareStatement("SELECT stock_id FROM PRICE" + 
						" WHERE trading_session_date=? AND periodicity=? LIMIT 1");
			sql.setDate(1, new Date(date.getTime()));
			sql.setString(2, periodicity.getType());
			ResultSet result = sql.executeQuery();
			hasPrice = result.next();
			
			result.close();
			sql.close();
			
			return hasPrice;
		} catch (SQLException e) {
			logger.error("hasTradingSession(" + Util.formatDate(date) 
					+ ", " + periodicity.getType() + ")", e);
			throw new DBException(e.getMessage());
		}
	
	}
	
}
