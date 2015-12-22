package edu.iastate.cs309.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.iastate.cs309.dao.FactDaoImpl;
import edu.iastate.cs309.dao.FeedbackDaoImpl;
import edu.iastate.cs309.dao.ItemDaoImpl;
import edu.iastate.cs309.dao.TransactionDaoImpl;
import edu.iastate.cs309.dao.UserDaoImpl;
import edu.iastate.cs309.domain.EmailHandler;
import edu.iastate.cs309.domain.Feedback;
import edu.iastate.cs309.domain.User;

/**
 * The controller for user account actions.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired UserDaoImpl userDao;
	@Autowired ItemDaoImpl itemDao;
	@Autowired TransactionDaoImpl transactionDao;
	@Autowired FeedbackDaoImpl feedbackDao;
	@Autowired FactDaoImpl factDao;
	@Autowired EmailHandler mailer;

	/**
	 * Returns the create account page.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateAccount(HttpServletRequest req, HttpServletResponse resp, Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "New Account");
		return "createAccount";
	}

	/**
	 * Creates the new user account. Also sends an email to confirm their
	 * account with a confirmation link.
	 */
	@RequestMapping(value = "/saveCreateAccount", method = RequestMethod.POST)
	public String saveCreateAccount(@ModelAttribute("user") final User user, HttpServletRequest req, HttpServletResponse resp, Model model) {
		model.addAttribute("fact", factDao.generateRandomFact());
		try {
			User newUser = userDao.createUserAccount(user);
			mailer.sendNewUserEmail(newUser);
			model.addAttribute("pageTitle", "Confirmation");
			return "accountCreated";
		} catch (Exception e) {
			model.addAttribute("exception", e);
			model.addAttribute("pageTitle", "Error");
			return "error";
		}
	}
	
	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	@ResponseBody
	public boolean isValidEmail(@RequestParam(value="email", required=true) String email) {
		return !this.userDao.checkIfEmailExist(email);
	}
	
	@RequestMapping(value = "/usernameCheck", method = RequestMethod.POST)
	@ResponseBody
	public boolean isValidUsername(@RequestParam(value="username", required=true) String username) {
		return !this.userDao.checkIfUsernameExist(username);
	}

	/**
	 * Confirms the user account.
	 */
	@RequestMapping(value = "/confirm/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String confirmAccount(@PathVariable("id") Integer userId, Model model) {
		userDao.confirmUser(userId);
		model.addAttribute("message","Thank you. Your account has been confirmed, please log in!");
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "New Account Confirmation");
		return "confirmMessage";
	}

	/**
	 * Redirects the user here when they attempt to login but have not confirmed
	 * their account.
	 */
	@RequestMapping(value = "/accountNotConfirmed", method = RequestMethod.GET)
	public String accountNotConfirmed(HttpServletRequest req, HttpServletResponse resp, Model model) {
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Account Not Confirmed");
		return "accountNotConfirmed";
	}

	/**
	 * Sends the user their forgotten password.
	 */
	@RequestMapping(value = "/forgotPassword/{username}", method = { RequestMethod.GET, RequestMethod.POST })
	public void forgotPassword(HttpServletRequest req, HttpServletResponse resp, @PathVariable String username, Model model) throws IOException, ServletException {
		User user = userDao.findUserByUsername(username);
		mailer.sendForgottenPassword(user);
	}

	/**
	 * Logs the user into CyShop.
	 */
	@RequestMapping(value = "/logUserIn", method = RequestMethod.POST)
	public String logUserIn(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException, ServletException {
		model.addAttribute("fact", factDao.generateRandomFact());
		try {
			User loggedInUser = userDao.logUserIn(req.getParameter("username"), req.getParameter("password"));
			if (loggedInUser.getConfirmed() == null || !loggedInUser.getConfirmed().equalsIgnoreCase("yes")) {
				return "redirect:/controller/user/accountNotConfirmed";
			} else {
				HttpSession session = req.getSession();
				session.setAttribute("username", loggedInUser.getUsername());
				session.setAttribute("userId", loggedInUser.getUserId());
				session.setAttribute("userType", loggedInUser.getUserType());
				session.setAttribute("loggedIn", true);
				session.setAttribute("profilePicture", loggedInUser.getImageSource());

				if (loggedInUser.getUserType().equals("Seller"))
					return "redirect:/controller/seller/view";
				if (loggedInUser.getUserType().equals("Shopper"))
					return "redirect:/controller/store/view";
				if (loggedInUser.getUserType().equals("Administrator"))
					return "redirect:/controller/admin/view";
			}
		} catch (Exception e) {
			model.addAttribute("exception", e);
			model.addAttribute("pageTitle", "Error");
			return "error";
		}
		return "catalog";
	}

	/**
	 * Logs the user out of CyShop.
	 */
	@RequestMapping(value = "/logUserOut/{userId}", method = RequestMethod.GET)
	public void logUserOut(HttpServletRequest req, HttpServletResponse resp, Model model, @PathVariable Integer userId) throws IOException {
		model.addAttribute("fact", factDao.generateRandomFact());
		userDao.logUserOut(userId);
		HttpSession session = req.getSession();
		session.setAttribute("username", "guest");
		session.setAttribute("userId", "0");
		session.setAttribute("userType", "Guest");
		session.setAttribute("loggedIn", false);

		// invalidate the session if exists
		session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		resp.sendRedirect("/CyShop/controller/store/view");
	}

	/**
	 * Returns the user's profile page.
	 */
	@RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET)
	public String profile(@ModelAttribute("user") final User user, Model model, @PathVariable Integer userId) {
		User profile = userDao.findUserById(userId);
		model.addAttribute("user", profile);
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Profile for " + profile.getUsername());
		return "userProfile";
	}

	/**
	 * Saves the user's edited profile.
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value = "/editProfile/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String editProfile(@ModelAttribute("user") final User user, @PathVariable("id") Integer userId, Model model, HttpServletRequest request, @RequestParam CommonsMultipartFile[] fileUpload) throws IllegalStateException, IOException {
		user.setUserId(userId);
		if (fileUpload != null && fileUpload.length > 0) {
            for (CommonsMultipartFile aFile : fileUpload){	// This is a loop for all files uploaded. Currently have only 1 file, in future may have multiple files.
            	String extension = "";
            	for(int i=aFile.getOriginalFilename().length()-2;i>0;i-=1){	//find out extension of the uploaded file, assume it always has an extension.
            		if(aFile.getOriginalFilename().charAt(i)=='.'){
            			extension = aFile.getOriginalFilename().substring(i);
            			break;
            		}
            	}
            	String newFileName = "";
            	if(extension.isEmpty()){
            		newFileName = userDao.findUserById(userId).getImageSource();
            	}else{
            		newFileName = user.getUserId().toString() + extension;
            		String saveDirectory = request.getSession().getServletContext().getRealPath("/img/"+ newFileName);	// file path
                	if(saveDirectory.contains("/usr/share/tomcat/webapps/")){	//if saveDirectory is on server
                		saveDirectory = "/usr/share/tomcat/webapps/CyShopUserImages/"+newFileName;	//save file in the folder other than the project folder. 
                	}
                    if (!aFile.getOriginalFilename().equals("")) {
                        aFile.transferTo(new File(saveDirectory));	// move file to selected folder.
                    }
            	}
            	user.setImageSource(newFileName);
            }
        }
		userDao.saveProfile(user);
		model.addAttribute("user", user);
		model.addAttribute("fact", factDao.generateRandomFact());
		return "redirect:/controller/user/profile/" + user.getUserId();
	}
	
	/**
	 * Returns the user's public profile page.
	 */
	@RequestMapping(value = "/publicProfile/{userId}", method = RequestMethod.GET)
	public String publicProfile(@ModelAttribute("user") final User user, Model model, @PathVariable Integer userId) {
		User profile = userDao.findUserById(userId);
		model.addAttribute("user", profile);
		List<Feedback> shopperFeedback = feedbackDao.findAllShopperFeedback(userId);
		model.addAttribute("shopperFeedback", shopperFeedback);
		List<Feedback> sellerFeedback = feedbackDao.findAllSellerFeedback(userId);
		model.addAttribute("sellerFeedback", sellerFeedback);
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", profile.getUsername() + "'s Profile");
		return "publicProfile";
	}

	/**
	 * Converts dates to the MM/dd/yyyy format automatically.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
