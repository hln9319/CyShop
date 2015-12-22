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
import org.springframework.web.bind.annotation.RequestParam;

import edu.iastate.cs309.dao.FactDaoImpl;
import edu.iastate.cs309.dao.FeedbackDaoImpl;
import edu.iastate.cs309.dao.ItemDaoImpl;
import edu.iastate.cs309.dao.TransactionDaoImpl;
import edu.iastate.cs309.dao.UserDaoImpl;
import edu.iastate.cs309.domain.EmailHandler;
import edu.iastate.cs309.domain.Item;

/**
 * The controller to populate the catalog.
 * 
 * @author SaldinBajric
 * 
 */
@Controller
@RequestMapping(value = "/categories")
public class CategoriesController {

	@Autowired UserDaoImpl userDao;
	@Autowired ItemDaoImpl itemDao;
	@Autowired TransactionDaoImpl transactionDao;
	@Autowired FeedbackDaoImpl feedbackDao;
	@Autowired FactDaoImpl factDao;
	@Autowired EmailHandler mailer;

	/**
	 * Returns a catalog of items with the specified category.
	 */
	@RequestMapping(value = "{category}", method = RequestMethod.GET)
	public String showCatalog(@PathVariable String category, HttpServletRequest req, HttpServletResponse resp, Model model) {
		List<Item> itemList = itemDao.findItemsByCategory(category);
		model.addAttribute("catalogItems", itemList);
		model.addAttribute(category + "Active", "active");
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", category.toUpperCase());
		return "catalog";
	}

	/**
	 * Returns a catalog with the search results.
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(@RequestParam String keyword, Model model) {
		List<Item> itemList = itemDao.search(keyword);
		model.addAttribute("catalogItems", itemList);
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle","Search results for '" + keyword + "'");
		model.addAttribute("keyword", keyword);
		return "catalog";
	}
}
