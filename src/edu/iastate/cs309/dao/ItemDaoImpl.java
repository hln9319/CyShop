package edu.iastate.cs309.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import edu.iastate.cs309.domain.Item;

/**
 * The Data Access Object implementation for items/posts. Talks to the database.
 */
public class ItemDaoImpl implements ItemDao {
	@Autowired JdbcTemplate jdbcTemplate;
	@Autowired private NamedParameterJdbcTemplate namedParameterTemplate;
	
	/**
	 * Finds the biggest item id in the database and adds 1 to it to create a new unique item id.
	 */
	@Override
	public Integer createNewItemId() {
		return jdbcTemplate.queryForObject("select max(item_id) + 1 from db309R14.item", Integer.class);
	}
	
	/**
	 * Returns a list of all items/posts in the database.
	 */
	@Override
	public List<Item> findAllItems() {
		return jdbcTemplate.query("SELECT * from db309R14.item", new ItemRowMapper());
	}
	
	/**
	 * Returns a list of all of the specified seller's items/posts.
	 */
	@Override
	public List<Item> findSellerItems(Integer sellerId) {
		return jdbcTemplate.query("SELECT * from db309R14.item where seller_id=?", new Object[] {sellerId}, new ItemRowMapper());
	}
	
	/**
	 * Creates a new item/post record into the database.
	 */
	@Override
	public void addNewItem(Item item) {
		String sql = "insert into db309R14.item (item_id, item_name, description, item_cost, item_type, quantity, image_source, created, seller_id, seller_username, amount_sold) values (?,?,?,?,?,?,?, now(),?,?,?);";
		item.setItemId(createNewItemId());
		Object[] params = new Object[]{item.getItemId(), item.getItemName(), item.getDescription(), item.getItemCost(), item.getItemType(), item.getQuantity(), item.getImageSource(), item.getSellerId(), item.getSellerUsername(), item.getAmountSold()};
		jdbcTemplate.update(sql, params);
	}
	
	@Override
	public void updateItem(Item item) {
		jdbcTemplate.update("update db309R14.item set item_name = ?, description = ?, item_type = ?, item_cost = ?, quantity = ?, amount_sold = ?, image_source=? where item_id = ?", item.getItemName(), item.getDescription(), item.getItemType(), item.getItemCost(), item.getQuantity(), item.getAmountSold(), item.getImageSource(),item.getItemId());
	}
	
	/**
	 * Removes an item/post record from the database.
	 */
	@Override
	public void deleteItem(Item item) {
		jdbcTemplate.update("delete from db309R14.item where item_id=?", item.getItemId());
	}
	
	/**
	 * Returns a list of items that are in the specified category.
	 */
	@Override
	public List<Item> findItemsByCategory(String category) {
		return jdbcTemplate.query("SELECT * from db309R14.item where item_type = ?", new Object[] {category}, new ItemRowMapper());
	}
	
	/**
	 * Generates a list of hot items for the home page.
	 */
	@Override
	public List<Item> getHotItems() {
		return jdbcTemplate.query("select * from db309R14.item order by amount_sold desc limit 12", new ItemRowMapper());
	}
	
	/**
	 * Generates a list of suggested items for the specified user.
	 */
	@Override
	public List<Item> generateSuggestedItems(Integer userId) {
		List<Item> itemsPurchased = findShopperPurchases(userId);

		Random randomGenerator = new Random();
		List<String> suggestedCategories = new ArrayList<String>();
		List<String> suggestedSellers = new ArrayList<String>();
		List<Item> suggestedItems = new ArrayList<Item>();
		
		for(Item item : itemsPurchased) {
			if(!suggestedCategories.contains(item.getItemType()))
				suggestedCategories.add(item.getItemType());
			if(!suggestedSellers.contains(item.getSellerUsername()))
				suggestedSellers.add(item.getSellerUsername());
		}
		
		int i = 0;
		while (i < 12) {
			if (suggestedItems.size() == 12) {
				break;
			} else {
				int sellersIndex = randomGenerator.nextInt(suggestedSellers.size());
				int categoriesIndex = randomGenerator.nextInt(suggestedCategories.size());
				String suggestedSeller = suggestedSellers.get(sellersIndex);
				String suggestedCategory = suggestedCategories.get(categoriesIndex);
				suggestedItems.add(jdbcTemplate.queryForObject("select * from db309R14.item where seller_username=? limit 1", new Object[]{suggestedSeller}, new ItemRowMapper()));
				suggestedItems.add(jdbcTemplate.queryForObject("select * from db309R14.item where item_type=? limit 1", new Object[]{suggestedCategory}, new ItemRowMapper()));
			}
			i++;
		}
		return suggestedItems;
	}
	
	/**
	 * Returns the list of items that the user has bought.
	 */
	@Override
	public List<Item> findShopperPurchases(Integer userId) {
		return jdbcTemplate
		.query("select distinct i.* from db309R14.item i join db309R14.transaction t on i.item_id=t.item_id join db309R14.user u on u.user_id=t.shopper_id where u.user_id=? group by i.item_id",
				new Object[] { userId }, new ItemRowMapper());
	}
	
	/**
	 * Returns a list of items who's item name, description, or seller matches the input.
	 */
	@Override
	public List<Item> search(String keyword) {
		return jdbcTemplate.query("select * from db309R14.item where ((item_name like '%" + keyword + "%') OR (description like '%" + keyword + "%') OR (seller_username like '%" + keyword + "%'))", new ItemRowMapper());
	}

	/**
	 * Returns the item with the given item id.
	 */
	@Override
	public Item findItemById(Integer id) {
		return jdbcTemplate.queryForObject("select * from db309R14.item where item_id = ?", new Object[] {id}, new ItemRowMapper());
	}
	
	/**
	 * Returns all items given the list of id's.
	 */
	@Override
	public List<Item> findItemByIds(List<String> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		return this.namedParameterTemplate.query("select * from db309R14.item where item_id in (:ids)", parameters, new ItemRowMapper());
	}
	
	/**
	 * Maps a result set for the Item object.
	 * @author SaldinBajric
	 *
	 */
	private class ItemRowMapper implements RowMapper<Item> {
		@Override
        public Item mapRow(ResultSet rs, int rowNumber) throws SQLException {
			Item item = new Item();
			item.setSellerUsername(rs.getString("seller_username"));
			item.setSellerId(rs.getInt("seller_id"));
            item.setItemId(rs.getInt("item_id"));
            item.setItemName(rs.getString("item_name"));
            item.setItemCost(rs.getDouble("item_cost"));
            item.setItemType(rs.getString("item_type"));
            item.setDescription(rs.getString("description"));
            item.setQuantity(rs.getInt("quantity"));
            item.setImageSource(rs.getString("image_source"));
            item.setCreated(rs.getDate("created"));
            item.setDeleted(rs.getDate("deleted"));
            item.setAmountSold(rs.getInt("amount_sold"));
            return item;
        }
	}
}
