package edu.iastate.cs309.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import edu.iastate.cs309.domain.Fact;
import edu.iastate.cs309.domain.User;

/**
 * The controller for Administrator actions.
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired UserDaoImpl userDao;
	@Autowired ItemDaoImpl itemDao;
	@Autowired TransactionDaoImpl transactionDao;
	@Autowired FeedbackDaoImpl feedbackDao;
	@Autowired FactDaoImpl factDao;
	@Autowired EmailHandler mailer;
	
	/**
	 * Returns the admin's home page with all CyShop information from the database.
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest req, HttpServletResponse resp, Model model) {
		ModelAndView mav = new ModelAndView("adminHome");
		mav.addObject("allUsers", userDao.findAllUsers());
		mav.addObject("allItems", itemDao.findAllItems());
		mav.addObject("allTransactions", transactionDao.findAllTransactions());
		mav.addObject("allFeedback", feedbackDao.findAllFeedback());
		mav.addObject("allFacts", factDao.findAllFacts());
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle","Admin Home");
		return mav;
	}
	
	/**
	 * Allows the admin to delete a user.
	 */
	@RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.POST)
	public ModelAndView deleteUser(@PathVariable("userId") Integer userId) {
		User user = new User();
		user.setUserId(userId);
		userDao.deleteUserAccount(user);
		return new ModelAndView("adminHome");
	}
	
	/**
	 * Allows the admin to add a new fact.
	 */
	@RequestMapping(value = "/newFact/{fact}", method = { RequestMethod.GET, RequestMethod.POST })
	public void newFact(HttpServletRequest req, HttpServletResponse resp, @PathVariable String fact, Model model) throws IOException, ServletException {
		factDao.addNewFact(fact);
	}
	
	/**
	 * Allows the admin to edit an existing fact.
	 */
	@RequestMapping(value = "/editFact/{factId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void editFact(HttpServletRequest req, HttpServletResponse resp, @PathVariable Integer factId, Model model) throws IOException, ServletException {
		Fact fact = factDao.findFactById(factId);
		factDao.editFact(fact);
	}
	
	/**
	 * Allows the admin to delete a fact.
	 */
	@RequestMapping(value = "/deleteFact/{factId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void deleteFact(HttpServletRequest req, HttpServletResponse resp, @PathVariable Integer factId, Model model) throws IOException, ServletException {
		Fact fact = factDao.findFactById(factId);
		factDao.deleteFact(fact);
	}
	
}
