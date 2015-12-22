package edu.iastate.cs309.domain;

/**
 * A domain object for the Feedback model with getter/setter methods for its attributes.
 * @author SaldinBajric
 *
 */
public class Feedback {
	private Integer rating;
	private String comment;
	private Integer itemId;
	private String itemName;
	private Integer sellerId;
	private String sellerUsername;
	private Integer shopperId;
	private String shopperUsername;
	private Integer transactionId;
	private boolean shopperToSeller;
	
	public Feedback() {}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	public Integer getShopperId() {
		return shopperId;
	}

	public void setShopperId(Integer shopperId) {
		this.shopperId = shopperId;
	}

	public String getShopperUsername() {
		return shopperUsername;
	}

	public void setShopperUsername(String shopperUsername) {
		this.shopperUsername = shopperUsername;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public boolean isShopperToSeller() {
		return shopperToSeller;
	}

	public void setShopperToSeller(boolean shopperToSeller) {
		this.shopperToSeller = shopperToSeller;
	}
}
