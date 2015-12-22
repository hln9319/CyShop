package edu.iastate.cs309.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.iastate.cs309.dao.FactDaoImpl;
import edu.iastate.cs309.dao.FeedbackDaoImpl;
import edu.iastate.cs309.dao.ItemDaoImpl;
import edu.iastate.cs309.dao.TransactionDaoImpl;
import edu.iastate.cs309.dao.UserDaoImpl;
import edu.iastate.cs309.domain.EmailHandler;
import edu.iastate.cs309.domain.Item;
import edu.iastate.cs309.domain.Question;
import edu.iastate.cs309.domain.User;

/**
 * The controller for items and posts.
 */
@Controller
@RequestMapping(value="/item")
public class ItemController {

	@Autowired UserDaoImpl userDao;
	@Autowired ItemDaoImpl itemDao;
	@Autowired TransactionDaoImpl transactionDao;
	@Autowired FeedbackDaoImpl feedbackDao;
	@Autowired FactDaoImpl factDao;
	@Autowired EmailHandler mailer;
	
	/**
	 * Returns the specified item's description page.
	 */
	@RequestMapping(value = "details/{id}", method = RequestMethod.GET)
	public String showItemDescription(@PathVariable Integer id, Model model) {
		Item item = itemDao.findItemById(id);
		Map<String, Integer> rating = feedbackDao.getItemAverageRating(item.getItemId());
		model.addAttribute("averageRating", rating.get("averageRating"));
		model.addAttribute("reviews", rating.get("reviews"));
		model.addAttribute("item", item);
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", item.getItemName());
		return "itemDescription";
	}
	
	/**
	 * Returns the question form to ask a seller.
	 */
	@RequestMapping(value = "ask/{itemId}/{sellerId}/{shopperId}", method = RequestMethod.GET)
	public String questionForm(@PathVariable("itemId") Integer itemId, @PathVariable("sellerId") Integer sellerId,
			@PathVariable("shopperId") Integer shopperId, Model model) {
		model.addAttribute("question", new Question());
		model.addAttribute("item", itemDao.findItemById(itemId));
		model.addAttribute("seller", userDao.findUserById(sellerId));
		model.addAttribute("shopper", userDao.findUserById(shopperId));
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Question");
		return "questionForm";
	}

	/**
	 * Returns the question form to ask a seller.
	 */
	@RequestMapping(value = "question/{itemId}/{sellerId}/{shopperId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String sendQuestion(HttpServletRequest req, @PathVariable("itemId") Integer itemId, @PathVariable("sellerId") Integer sellerId,
			@PathVariable("shopperId") Integer shopperId, Model model) {
		Item item = itemDao.findItemById(itemId);
		User seller = userDao.findUserById(sellerId);
		User shopper = userDao.findUserById(shopperId);
		mailer.askSellerQuestion(shopper, seller, item, req.getParameter("question"));
		model.addAttribute("fact", factDao.generateRandomFact());
		return "questionSent";
	}
	
	/**
	 * Allows a seller or admin to delete a post. 
	 */
	@RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
	public String deleteItem(@PathVariable Integer id, @RequestParam(value = "admin", required = false) final boolean isAdmin, Model model) {
		this.itemDao.deleteItem(this.itemDao.findItemById(id));
		if(isAdmin) {
			return "redirect:/controller/admin/view";
		}
		return "redirect:/controller/seller/view";
	}
	
	/**
	 * Returns the edit item/post page.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String addEditItem(@PathVariable Integer id, HttpServletRequest request, Model model) {
		Item item = null;
		if(id == 0) { //if the id is 0, then that means the user wants to add a new item
			item = new Item();
			item.setItemId(0);
			item.setSellerId((Integer) request.getSession().getAttribute("userId"));
			item.setSellerUsername((String) request.getSession().getAttribute("username"));
			item.setQuantity(1);
		} else { //finds the item the user wants to edit
			item = itemDao.findItemById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("fact", factDao.generateRandomFact());
		model.addAttribute("pageTitle", "Edit Item");
		return "addEditItem";
	}
	
	/**
	 * Allows the seller to make a new item/post.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public String saveItem(@PathVariable Integer id, @ModelAttribute final Item item, Model model, HttpServletRequest request,
            @RequestParam CommonsMultipartFile[] fileUpload) throws Exception{
		if(id==0){
			itemDao.addNewItem(item);
		}else{
			item.setItemId(id);;
		}
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
            		newFileName = itemDao.findItemById(id).getImageSource();
            		
            	}else{
            		newFileName = item.getItemId().toString() + extension;
            		String saveDirectory = request.getSession().getServletContext().getRealPath("/img/"+ newFileName);	// file path
                	if(saveDirectory.contains("/usr/share/tomcat/webapps/")){	//if saveDirectory is on server
                		saveDirectory = "/usr/share/tomcat/webapps/CyShopImages/"+newFileName;	//save file in the folder other than the project folder. 
                	}
                    if (!aFile.getOriginalFilename().equals("")) {
                        aFile.transferTo(new File(saveDirectory));	// move file to selected folder.
                    }
            	}
            	item.setImageSource(newFileName);
            }
        }
		itemDao.updateItem(item);
		model.addAttribute("fact", factDao.generateRandomFact());
		return "redirect:/controller/seller/view";
	}
}