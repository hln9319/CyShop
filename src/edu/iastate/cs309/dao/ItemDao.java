package edu.iastate.cs309.dao;

import java.util.List;

import edu.iastate.cs309.domain.Item;

/**
 * The Data Access Object interface for items/posts. Defines the methods that will be used.
 */
public interface ItemDao {
	public List<Item> findAllItems();
	public List<Item> findSellerItems(Integer sellerId);
	public void deleteItem(Item item);
	public void addNewItem(Item item);
	public void updateItem(Item item);
	public Integer createNewItemId();
	public List<Item> findItemsByCategory(String category);
	public Item findItemById(Integer id);
	public List<Item> search(String input);
	public List<Item> findItemByIds(List<String> ids);
	public List<Item> generateSuggestedItems(Integer userId);
	public List<Item> getHotItems();
	public List<Item> findShopperPurchases(Integer userId);
}
