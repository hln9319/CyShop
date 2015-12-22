package edu.iastate.cs309.dao;

import java.util.List;
import java.util.Map;

import edu.iastate.cs309.domain.Feedback;

/**
 * The Data Access Object interface for feedback. Defines the methods that will be used.
 */
public interface FeedbackDao {
	public List<Feedback> findAllFeedback();
	public List<Feedback> findAllSellerFeedback(Integer sellerId);
	public List<Feedback> findAllShopperFeedback(Integer shopperId);
	public void newFeedback(Feedback feedback) throws Exception;
	public List<Feedback> findAllFeedbackForItem(Integer itemId);
	public Map<String, Integer> getItemAverageRating(Integer itemId);
	public Map<String, Integer> getSellerAverageRating(Integer sellerId);
	public Map<String, Integer> getShopperAverageRating(Integer shopperId);
}
