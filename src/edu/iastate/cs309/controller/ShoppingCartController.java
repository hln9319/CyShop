package edu.iastate.cs309.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.iastate.cs309.dao.FactDaoImpl;
import edu.iastate.cs309.dao.FeedbackDaoImpl;
import edu.iastate.cs309.dao.ItemDaoImpl;
import edu.iastate.cs309.dao.TransactionDaoImpl;
import edu.iastate.cs309.dao.UserDaoImpl;
import edu.iastate.cs309.domain.EmailHandler;
import edu.iastate.cs309.domain.Item;
import edu.iastate.cs309.domain.Transaction;
import edu.iastate.cs309.domain.User;

@Controller
@RequestMapping(value="/cart")
public class ShoppingCartController {

	@Autowired UserDaoImpl userDao;
	@Autowired ItemDaoImpl itemDao;
	@Autowired TransactionDaoImpl transactionDao;
	@Autowired FeedbackDaoImpl feedbackDao;
	@Autowired FactDaoImpl factDao;
	@Autowired EmailHandler mailer;
	
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String shoppingCart(Model model) {
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Shopping Cart");
		return "shoppingCart";
	}
	@RequestMapping(value = "orderConfirmation", method = RequestMethod.GET)
	public String orderConfirmation(Model model) {
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Order Confirmation");
		return "orderConfirmation";
	}
	
	@RequestMapping(value = "items", method = RequestMethod.POST)
	public @ResponseBody List<Item> get(final String[] itemList) {
		List<String> idList = new ArrayList<String>();
		for(String id: itemList) {
			idList.add(id);
		}
		return this.itemDao.findItemByIds(idList);
	}
	
	@RequestMapping(value = "checkout", method = RequestMethod.GET)
	public String checkout(HttpServletRequest request, Model model) {
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		if(userId == 0) {
			model.addAttribute("user", new User());
		} else {
			model.addAttribute("user", this.userDao.findUserById(userId));
		}
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Checkout");
		return "checkout";
	}
	@RequestMapping(value = "confirmation", method = RequestMethod.POST)
	public int confirmPurchase(final String[] itemList, Model model, HttpServletRequest request, HttpServletResponse response) {
		String username = (String) request.getSession().getAttribute("username");
		User shopper = userDao.findUserByUsername(username);
		Integer orderId = transactionDao.createNewOrderId();
		String shippingNumber = transactionDao.getNewShippingNumber();
		List<Item> items = this.get(itemList);
		List<Transaction> transactions = new ArrayList<Transaction>();
		for(Item item: items) {
			Integer quantity = 1; //TODO: NEED TO GET THIS WORKING
			updateItem(item, quantity);
			Transaction transaction = createTransaction(orderId, item, shopper, quantity, shippingNumber);
			transactions.add(transaction);
			this.transactionDao.addNewTransaction(transaction);
		}
		mailer.sendOrderConfirmationEmail(shopper, transactions);
		return HttpServletResponse.SC_OK;
	}
	private Transaction createTransaction(Integer orderId, Item item, User shopper, Integer quantityBought, String shippingNumber) {
		Transaction transaction = new Transaction();
		transaction.setOrderId(orderId);
		transaction.setItemDescription(item.getDescription());
		transaction.setItemId(item.getItemId());
		transaction.setItemName(item.getItemName());
		transaction.setQuantity(quantityBought);
		transaction.setSellerId(item.getSellerId());
		transaction.setSellerUsername(item.getSellerUsername());
		transaction.setShopperId(shopper.getUserId());
		transaction.setShopperUsername(shopper.getUsername());
		transaction.setCost(item.getItemCost());
		transaction.setShippingNumber(shippingNumber);
		transaction.setShopperRated(false);
		transaction.setSellerRated(false);
		return transaction;
	}
	private void updateItem(Item item, Integer quantityBought) {
		Integer itemQuanity = item.getQuantity();
		Integer itemSold = item.getAmountSold();
		item.setQuantity(itemQuanity - quantityBought);
		item.setAmountSold(itemSold + quantityBought);
		itemDao.updateItem(item);
	}
	
}
