package edu.iastate.cs309.domain;

import java.util.Date;

/**
 * A domain object for the Item model with getter/setter methods for its attributes.
 * @author SaldinBajric
 *
 */
public class Item {
	private String sellerUsername;
	private Integer sellerId;
	private Integer itemId;
	private String itemName;
	private Double itemCost;
	private String itemType;
	private String description;
	private Integer quantity;
	private String imageSource;
	private Date created;
	private Date deleted;
	private Integer amountSold;
	
	public Item() {}
	
	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
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
	public Double getItemCost() {
		return itemCost;
	}
	public void setItemCost(Double itemCost) {
		this.itemCost = itemCost;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getImageSource() {
		return imageSource;
	}
	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getDeleted() {
		return deleted;
	}
	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}
	public Integer getAmountSold() {
		return amountSold;
	}
	public void setAmountSold(Integer amountSold) {
		this.amountSold = amountSold;
	}
}
