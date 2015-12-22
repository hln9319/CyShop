package edu.iastate.cs309.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.iastate.cs309.dao.FactDaoImpl;
import edu.iastate.cs309.dao.FeedbackDaoImpl;
import edu.iastate.cs309.dao.ItemDaoImpl;
import edu.iastate.cs309.dao.TransactionDaoImpl;
import edu.iastate.cs309.dao.UserDaoImpl;
import edu.iastate.cs309.domain.EmailHandler;
import edu.iastate.cs309.domain.Feedback;

/**
 * The controller for shopper actions.
 */
@Controller
@RequestMapping(value="/shopper")
public class ShopperController {
	
	@Autowired UserDaoImpl userDao;
	@Autowired ItemDaoImpl itemDao;
	@Autowired TransactionDaoImpl transactionDao;
	@Autowired FeedbackDaoImpl feedbackDao;
	@Autowired FactDaoImpl factDao;
	@Autowired EmailHandler mailer;
	
	/**
	 * Returns the shopper's home page.
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(HttpServletRequest req, HttpServletResponse resp, Model model) {
		model.addAttribute("shopperTransactions", transactionDao.findAllShopperTransactions((Integer) req.getSession().getAttribute("userId")));
		List<Feedback> list =  feedbackDao.findAllShopperFeedback((Integer) req.getSession().getAttribute("userId"));
		model.addAttribute("shopperFeedback",list);
		model.addAttribute("purchases", itemDao.findShopperPurchases((Integer) req.getSession().getAttribute("userId")));
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Shopper Home");
		return "shopperHome";
	}
	
	/**
	 * Sends the shopper to the checkout page.
	 */
	@RequestMapping(value = "/checkout/{userId}", method = RequestMethod.GET)
	public String checkout(@PathVariable("userId") Integer userId, Model model) {
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Checkout");
		return "checkout";
	}
	
	/**
	 * Sends the shopper to the order confirmation page after checking out.
	 */
	@RequestMapping(value = "/orderConfirmation/{shoppingCartId}", method = RequestMethod.GET)
	public String confirmOrder(@PathVariable("shoppingCartId") Integer shoppingCartId, Model model) {
		//Item item = new Item();
		
		//Transaction trx = new Transaction();
		//set transaction stuff here
		
		//create the transaction in the database
		//this will return once the transaction is completed
		
		//need something to set item to "shipping" or whatever is necessary
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Order Confirmation");
		return "orderConfirmation";
	}
}
