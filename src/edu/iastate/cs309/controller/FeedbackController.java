package edu.iastate.cs309.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import edu.iastate.cs309.domain.Transaction;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {

	@Autowired UserDaoImpl userDao;
	@Autowired ItemDaoImpl itemDao;
	@Autowired TransactionDaoImpl transactionDao;
	@Autowired FeedbackDaoImpl feedbackDao;
	@Autowired FactDaoImpl factDao;
	@Autowired EmailHandler mailer;

	@RequestMapping(value = "/newFeedback/{transactionId}", method = RequestMethod.GET)
	public String newFeedback(Model model, @PathVariable("transactionId") Integer transactionId) {
		Transaction transaction = transactionDao.findtransactionById(transactionId);
		model.addAttribute("transaction", transaction);
		model.addAttribute("feedback", new Feedback());
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("shopperToSeller", true);	//this feedback is given to seller from shopper.
		model.addAttribute("pageTitle","Create Feedback");
		return "feedback";
	}
	
	@RequestMapping(value = "/newSellerFeedback/{transactionId}", method = RequestMethod.GET)
	public String newSellerFeedback(Model model, @PathVariable("transactionId") Integer transactionId) {
		Transaction transaction = transactionDao.findtransactionById(transactionId);
		model.addAttribute("transaction", transaction);
		model.addAttribute("feedback", new Feedback());
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("shopperToSeller", false);	//this feedback is given to shopper from seller.
		model.addAttribute("pageTitle","Create Feedback");
		return "feedback";
	}

	@RequestMapping(value = "/submitFeedback/{transactionId}", method = RequestMethod.POST)
	public String saveFeedback(Model model, @ModelAttribute("feedback") final Feedback feedback, @PathVariable("transactionId") Integer transactionId) {
		model.addAttribute("fact", factDao.generateRandomFact());
		try {
			Feedback newFeedback = feedback;
			Transaction transaction = transactionDao.findtransactionById(transactionId);
			feedbackDao.newFeedback(newFeedback);
			model.addAttribute("message", "Feedback sent!");
			
			if(feedback.isShopperToSeller()){
				transaction.setShopperRated(true);
				mailer.sendFeedback(userDao.findUserById(transaction.getShopperId()), userDao.findUserById(transaction.getSellerId()), newFeedback, itemDao.findItemById(transaction.getItemId()));
			}else{
				transaction.setSellerRated(true);
				mailer.sendFeedback(userDao.findUserById(transaction.getSellerId()), userDao.findUserById(transaction.getShopperId()), newFeedback, itemDao.findItemById(transaction.getItemId()));
			}
			transactionDao.updateTransaction(transaction);
			model.addAttribute("pageTitle","Feedback Created");
			return "confirmMessage";
		} catch (Exception e) {
			model.addAttribute("exception", e);
			model.addAttribute("pageTitle","Error");
			return "error";
		}
	}
	
	@RequestMapping(value = "/{type}/{id}", method = RequestMethod.GET)
	public String feedbackList(Model model, @PathVariable("id") Integer id, @PathVariable("type") String type) {
		List<Feedback> list = new ArrayList<Feedback>();
		Map<String, Integer> rating = new HashMap<String, Integer>();
		if(type.equals("seller")){
			list = feedbackDao.findAllSellerFeedback(id);
			rating = feedbackDao.getSellerAverageRating(id);
			model.addAttribute("name", userDao.findUserById(id));
			model.addAttribute("pageTitle","Feedback for seller: " + userDao.findUserById(id).getUsername());
		}else if(type.equals("shopper")){
			list = feedbackDao.findAllShopperFeedback(id);
			rating = feedbackDao.getShopperAverageRating(id);
			model.addAttribute("name", userDao.findUserById(id));
			model.addAttribute("pageTitle","Feedback for shopper: " + userDao.findUserById(id).getUsername());
		}else{	//type=="item"
			list = feedbackDao.findAllFeedbackForItem(id);
			rating = feedbackDao.getItemAverageRating(id);
			model.addAttribute("name", itemDao.findItemById(id));
			model.addAttribute("pageTitle","Feedback for '" + itemDao.findItemById(id).getItemName() + "'");
		}
		model.addAttribute("averageRating", rating.get("averageRating"));
		model.addAttribute("reviews", rating.get("reviews"));
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("type", type);
		model.addAttribute("feedback",list);
		return "feedbackList";
	}
}
