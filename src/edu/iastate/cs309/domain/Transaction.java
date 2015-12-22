package edu.iastate.cs309.domain;

import java.util.Date;

/**
 * A domain object for the Transaction model with getter/setter methods for its attributes.
 * @author SaldinBajric
 *
 */
public class Transaction {
	private Integer orderId;
	private Integer transactionId;
	private Integer sellerId;
	private String sellerUsername;
	private Integer shopperId;
	private String shopperUsername;
	private Integer itemId;
	private String itemName;
	private String itemDescription;
	private Double cost;
	private Date transcationDate;
	private Integer quantity;
	private boolean shopperRated;
	private boolean sellerRated;
	private String shippingNumber;
	private String email;

	public Transaction() {}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Date getTranscationDate() {
		return transcationDate;
	}

	public void setTranscationDate(Date transcationDate) {
		this.transcationDate = transcationDate;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean isShopperRated() {
		return shopperRated;
	}

	public void setShopperRated(boolean shopperRated) {
		this.shopperRated = shopperRated;
	}

	public boolean isSellerRated() {
		return sellerRated;
	}

	public void setSellerRated(boolean sellerRated) {
		this.sellerRated = sellerRated;
	}

	public String getShippingNumber() {
		return shippingNumber;
	}

	public void setShippingNumber(String shippingNumber) {
		this.shippingNumber = shippingNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
