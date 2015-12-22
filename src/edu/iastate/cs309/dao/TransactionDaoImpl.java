package edu.iastate.cs309.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.iastate.cs309.domain.Transaction;

/**
 * The Data Access Object implementation for transactions. Talks to the database.
 */
public class TransactionDaoImpl implements TransactionDao {

	@Autowired JdbcTemplate jdbcTemplate;

	/**
	 * Returns a list of all transactions.
	 */
	@Override
	public List<Transaction> findAllTransactions() {
		return jdbcTemplate.query("SELECT * from db309R14.transaction", new TransactionRowMapper());
	}
	
	/**
	 * Returns a list of the specified seller's transactions.
	 */
	@Override
	public List<Transaction> findAllSellerTransactions(Integer sellerId) {
		return jdbcTemplate.query("SELECT * from db309R14.transaction where seller_id=?", new Object[] {sellerId} ,new TransactionRowMapper());
	}
	
	@Override
	public void addNewTransaction(Transaction transaction) {
		String sql = "insert into db309R14.transaction (transaction_id, seller_id, seller_username, shopper_id, shopper_username, item_id, item_name, item_description, total_cost, transaction_date, quantity, shopperRated, sellerRated, order_id, shipping_number, email) values (?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?);";
		Object[] params = new Object[]{createNewTransactionId(), transaction.getSellerId(), transaction.getSellerUsername(), transaction.getShopperId(), transaction.getShopperUsername(), transaction.getItemId(), transaction.getItemName(), transaction.getItemDescription(), transaction.getCost(), transaction.getQuantity(), transaction.isShopperRated(), transaction.isSellerRated(), transaction.getOrderId(), transaction.getShippingNumber(), transaction.getEmail()};
		jdbcTemplate.update(sql, params);
	}
	
	@Override
	public Integer createNewTransactionId() {
		return jdbcTemplate.queryForObject("select max(transaction_id) + 1 from db309R14.transaction", Integer.class);
	}
	
	@Override
	public Integer createNewOrderId() {
		return jdbcTemplate.queryForObject("select max(order_id) + 1 from db309R14.transaction", Integer.class);
	}

	/**
	 * Returns all of the shopper's transactions.
	 */
	@Override
	public List<Transaction> findAllShopperTransactions(Integer shopperId) {
		return jdbcTemplate.query("SELECT * from db309R14.transaction where shopper_id=?", new Object[] {shopperId}, new TransactionRowMapper());
	}
	
	/**
	 * Returns transaction given transaction ID
	 * */
	@Override
	public Transaction findtransactionById(Integer transactionId){
		return jdbcTemplate.queryForObject("select * from db309R14.transaction where transaction_id=?", new Object[] { transactionId }, new TransactionRowMapper());
	}
	
	/**
	 * Update transaction given transaction
	 * */
	@Override
	public void updateTransaction(Transaction transaction) {
		jdbcTemplate.update("update db309R14.transaction set shopperRated = ?, sellerRated = ? where transaction_id=?", transaction.isShopperRated(), transaction.isSellerRated(), transaction.getTransactionId());
	}
	
	/**
	 * Gets a new shipping number.
	 */
	@Override
	public String getNewShippingNumber() {
		List<Transaction> allTransactions = jdbcTemplate.query("select * from db309R14.transaction order by shipping_number", new TransactionRowMapper());
		List<String> shippingNumbers = new ArrayList<String>();

		for (Transaction trx : allTransactions)
			shippingNumbers.add(trx.getShippingNumber());
		
		String shippingNumber = generateShippingNumber();
		if (!shippingNumbers.contains(shippingNumber))
			return shippingNumber;
		else
			return generateShippingNumber();
	}

	/**
	 * Returns a random shipping number.
	 */
	public String generateShippingNumber() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		for (int i = 0; i < 15; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * Maps a result set for a Transaction.
	 * @author SaldinBajric
	 *
	 */
	private class TransactionRowMapper implements RowMapper<Transaction> {
		@Override
        public Transaction mapRow(ResultSet rs, int rowNumber) throws SQLException {
			Transaction transaction = new Transaction();
			transaction.setTransactionId(rs.getInt("transaction_id"));
			transaction.setOrderId(rs.getInt("order_id"));
			transaction.setSellerId(rs.getInt("seller_id"));
			transaction.setSellerUsername(rs.getString("seller_username"));
			transaction.setShopperId(rs.getInt("shopper_id"));
			transaction.setShopperUsername(rs.getString("shopper_username"));
			transaction.setItemId(rs.getInt("item_id"));
			transaction.setItemName(rs.getString("item_name"));
			transaction.setItemDescription(rs.getString("item_description"));
			transaction.setCost(rs.getDouble("total_cost"));
			transaction.setTranscationDate(rs.getDate("transaction_date"));
			transaction.setQuantity(rs.getInt("quantity"));
			transaction.setShopperRated(rs.getBoolean("shopperRated"));
			transaction.setSellerRated(rs.getBoolean("sellerRated"));
			transaction.setShippingNumber(rs.getString("shipping_number"));
			transaction.setEmail(rs.getString("email"));
            return transaction;
        }
	}
}
