package edu.iastate.cs309.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.iastate.cs309.domain.Feedback;

/**
 * The Data Access Object implementation for feedback. Talks to the database.
 */
public class FeedbackDaoImpl implements FeedbackDao {
	@Autowired JdbcTemplate jdbcTemplate;
	
	/**
	 * Returns a list of all feedback in the database.
	 */
	@Override
    public List<Feedback> findAllFeedback() {
        return jdbcTemplate.query("SELECT * from db309R14.feedback", new FeedbackRowMapper());
    }
	
	/**
	 * Returns a list of the requested seller's feedback.
	 */
	@Override
    public List<Feedback> findAllSellerFeedback(Integer sellerId) {
        return jdbcTemplate.query("SELECT * from db309R14.feedback where seller_id=? And shopperToSeller=?", new Object[] {sellerId, true},new FeedbackRowMapper());
    }
	
	/**
	 * Returns a list of the requested shopper's feedback.
	 */
	@Override
    public List<Feedback> findAllShopperFeedback(Integer shopperId) {
        return jdbcTemplate.query("SELECT * from db309R14.feedback where shopper_id=? And shopperToSeller=?", new Object[] {shopperId,false},new FeedbackRowMapper());
    }
	
	@Override
    public List<Feedback> findAllFeedbackForItem(Integer itemId) {
        return jdbcTemplate.query("SELECT * from db309R14.feedback where item_id=? And shopperToSeller=?", new Object[] {itemId,true},new FeedbackRowMapper());
    }
	
	@Override
	public void newFeedback(Feedback feedback) throws Exception{
		try {
			jdbcTemplate.update(
					"insert into db309R14.feedback (rating, comment, item_id, item_name, seller_id, seller_username, shopper_id, shopper_username, transaction_id, shopperToSeller) values (?,?,?,?,?,?,?,?,?,?);",
					new Object[] { feedback.getRating(), feedback.getComment(), feedback.getItemId(), feedback.getItemName(), feedback.getSellerId(), feedback.getSellerUsername(), feedback.getShopperId(), feedback.getShopperUsername(), feedback.getTransactionId(), feedback.isShopperToSeller() });
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * Returns the average rating and number of reviews for the item.
	 */
    @Override
    public Map<String, Integer> getItemAverageRating(Integer itemId) {
		List<Feedback> allItemFeedback = findAllFeedbackForItem(itemId);
		return getAverageRating(allItemFeedback);
    }
    
    /**
	 * Returns the average rating and number of reviews for the seller.
	 */
    @Override
    public Map<String, Integer> getSellerAverageRating(Integer sellerId) {
		List<Feedback> allItemFeedback = findAllSellerFeedback(sellerId);
		return getAverageRating(allItemFeedback);
    }
    
    /**
	 * Returns the average rating and number of reviews for the shopper.
	 */
    @Override
    public Map<String, Integer> getShopperAverageRating(Integer shopperId) {
		List<Feedback> allItemFeedback = findAllShopperFeedback(shopperId);
		return getAverageRating(allItemFeedback);
    }
    
    /**
     * Returns the average rating and number of reviews for the item, seller, or shopper.
     * @param allFeedback
     * @return
     */
    public Map<String, Integer> getAverageRating(List<Feedback> allFeedback) {
    	Map<String, Integer> rating = new HashMap<String, Integer>();
    	Integer averageRating = new Integer(0);
		if (allFeedback.size() > 0) {
			for (Feedback fb : allFeedback) {
				averageRating += fb.getRating();
			}
			averageRating /= allFeedback.size();
		}
		rating.put("averageRating", averageRating);
		rating.put("reviews", allFeedback.size());
		return rating;
    }
	
	/**
	 * Maps a result set of Feedback objects.
	 * @author SaldinBajric
	 *
	 */
	private class FeedbackRowMapper implements RowMapper<Feedback> {
		@Override
        public Feedback mapRow(ResultSet rs, int rowNumber) throws SQLException {
			Feedback feedback = new Feedback();
            feedback.setRating(rs.getInt("rating"));
            feedback.setComment(rs.getString("comment"));
            feedback.setItemId(rs.getInt("item_id"));
            feedback.setItemName(rs.getString("item_name"));
            feedback.setSellerId(rs.getInt("seller_id"));
            feedback.setSellerUsername(rs.getString("seller_username"));
            feedback.setShopperId(rs.getInt("shopper_id"));
            feedback.setShopperUsername(rs.getString("shopper_username"));
            feedback.setTransactionId(rs.getInt("transaction_id"));
            feedback.setShopperToSeller(rs.getBoolean("shopperToSeller"));
            return feedback;
        }
	}
}
