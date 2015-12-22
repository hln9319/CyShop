package edu.iastate.cs309.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.iastate.cs309.dao.FactDaoImpl;
import edu.iastate.cs309.dao.FeedbackDaoImpl;
import edu.iastate.cs309.dao.ItemDaoImpl;
import edu.iastate.cs309.dao.TransactionDaoImpl;
import edu.iastate.cs309.dao.UserDaoImpl;
import edu.iastate.cs309.domain.EmailHandler;
import edu.iastate.cs309.domain.Item;

/**
 * The controller for seller actions.
 */
@Controller
@RequestMapping(value="/seller")
public class SellerController {
	
	@Autowired UserDaoImpl userDao;
	@Autowired ItemDaoImpl itemDao;
	@Autowired TransactionDaoImpl transactionDao;
	@Autowired FeedbackDaoImpl feedbackDao;
	@Autowired FactDaoImpl factDao;
	@Autowired EmailHandler mailer;
	
	/**
	 * Returns the seller's home page.
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(HttpServletRequest req, HttpServletResponse resp, Model model) {
		model.addAttribute("sellerItems", itemDao.findSellerItems((Integer) req.getSession().getAttribute("userId")));
		model.addAttribute("sellerFeedback", feedbackDao.findAllSellerFeedback((Integer) req.getSession().getAttribute("userId")));
		model.addAttribute("sellerTransactions", transactionDao.findAllSellerTransactions((Integer) req.getSession().getAttribute("userId")));
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Seller Home");
		return "sellerHome";
	}
	
	/**
	 * Allows the seller to add a new item/post.
	 */
	@RequestMapping(value = "/addNewItem", method = RequestMethod.POST)
	public String saveCreateAccount(
			@ModelAttribute("item") final Item item,
			HttpServletRequest req, 
			HttpServletResponse resp, 
			Model model) {
		itemDao.addNewItem(item);
		model.addAttribute("fact", factDao.generateRandomFact());
		return "redirect:/controller/seller/view";
	}

	/**
	 * Allows the seller to delete an item/post.
	 */
	@RequestMapping(value = "/deleteItem/{itemId}", method = RequestMethod.POST)
	public ModelAndView deleteUser(@PathVariable("itemId") Integer itemId) {
		Item item = new Item();
		item.setItemId(itemId);
		itemDao.deleteItem(item);
		return new ModelAndView("sellerHome");
	}
}
