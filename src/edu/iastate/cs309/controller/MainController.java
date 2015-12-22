package edu.iastate.cs309.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.iastate.cs309.dao.FactDaoImpl;
import edu.iastate.cs309.dao.FeedbackDaoImpl;
import edu.iastate.cs309.dao.ItemDaoImpl;
import edu.iastate.cs309.dao.TransactionDaoImpl;
import edu.iastate.cs309.dao.UserDaoImpl;
import edu.iastate.cs309.domain.EmailHandler;
import edu.iastate.cs309.domain.Item;
import edu.iastate.cs309.domain.User;

/**
 * The controller for general functionality.
 */
@Controller
@RequestMapping(value="/store")
public class MainController {
	
	@Autowired UserDaoImpl userDao;
	@Autowired ItemDaoImpl itemDao;
	@Autowired TransactionDaoImpl transactionDao;
	@Autowired FeedbackDaoImpl feedbackDao;
	@Autowired FactDaoImpl factDao;
	@Autowired EmailHandler mailer;

	/**
	 * Returns the home page.
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(HttpServletRequest req, HttpServletResponse resp, Model model) {

		if(req.getSession().isNew()) {
			HttpSession session = req.getSession();
			session.setAttribute("username", "guest");
			session.setAttribute("userId", 0);
			session.setAttribute("userType", "Guest");
			session.setAttribute("loggedIn", false);
		} 
		
		if (req.getSession().getAttribute("userId") != null && !req.getSession().getAttribute("userId").equals(0)
				&& itemDao.findShopperPurchases((Integer) req.getSession().getAttribute("userId")).size() > 0) {
			buildModel(itemDao.generateSuggestedItems((Integer) req.getSession().getAttribute("userId")), model, "Suggested Items");
		} else {
			buildModel(itemDao.getHotItems(), model, "Hot! Items");
		}
		
		model.addAttribute("pageTitle", "Welcome");
		model.addAttribute("userType", req.getSession().getAttribute("userType"));
		model.addAttribute("fact", factDao.generateRandomFact());
		return "catalog";
	}
	
	private void buildModel(List<Item> items, Model model, String carouselHeader) {
		List<Item> items1 = buildItemsList(0, items);
		List<Item> items2 = buildItemsList(4, items);
		List<Item> items3 = buildItemsList(8, items);
		model.addAttribute("items1", items1);
		model.addAttribute("items2", items2);
		model.addAttribute("items3", items3);
		model.addAttribute("carouselHeader", carouselHeader);
	}

	private List<Item> buildItemsList(int startingIndex, List<Item> items) {
		List<Item> tmp = new ArrayList<Item>();
		for(int i = startingIndex; i < startingIndex+4; i++) {
			if(items.get(i) != null)
				tmp.add(items.get(i));
		}
		return tmp;
	}
	
	/**
	 * Returns the about page.
	 */
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about(HttpServletRequest req, HttpServletResponse resp, Model model) {
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "About");
		return "about";
	}
	
	/**
	 * Returns the login page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest req, HttpServletResponse resp, Model model) {
		model.addAttribute("logUserIn", new User());
		model.addAttribute("forgotPassword", new User());
		model.addAttribute("userProfile", new User());
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Login");
		return "login";
	}
	
}
