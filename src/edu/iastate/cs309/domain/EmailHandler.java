package edu.iastate.cs309.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * The service that handles sending emails.
 * @author SaldinBajric
 *
 */
public class EmailHandler {
	@Autowired private MailSender mailSender;

	/**
	 * Sends an email to a newly created user to confirm their account.
	 */
	public void sendNewUserEmail(User user) {
		String confirmLink = "http://http://proj-309-r14.cs.iastate.edu/CyShop/controller/user/confirm/" + user.getUserId();
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("CyShop <309r14@gmail.com>"); // CyShop's email
		message.setTo(user.getEmail());
		message.setText("Congratulations! You have just created a new account with CyShop!\n\n" + "Your account type: "
				+ user.getUserType() + "\nYour username is: " + user.getUsername() + "\n\nPlease confirm your account with this link: "
				+ confirmLink + "\n\nWe welcome you and hope you find everything you need.\n\nCyShop\nSeek. Find. Enjoy.\n");
		message.setSubject("CyShop - New Account");
		mailSender.send(message);
	}

	/**
	 * Sends a password reminder to the user who requests it.
	 */
	public void sendForgottenPassword(User user) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("CyShop <309r14@gmail.com>"); // CyShop's email
		message.setTo(user.getEmail());
		message.setText("Hello " + user.getUsername() + ",\n\nApparently you forgot your password!\n\n" + "Your password is: " + user.getPassword()
				+ "\n\nPlease try to remember it this time :P\n\nCyShop\nSeek. Find. Enjoy.\n");
		message.setSubject("CyShop - Forgotten Password");
		mailSender.send(message);
	}

	/**
	 * Sends an email notifying the shopper who made the purchase.
	 */
	public void sendOrderConfirmationEmail(User shopper, List<Transaction> transactions) {
		Double total = 0.0;
		String body = "Congratulations, " + shopper.getUsername() + "!\n\nYour recent purchase has been completed.\n\n" + "Order Details:\n";
		for(Transaction trx : transactions) {
			body += "\nItem Name: " + trx.getItemName() + "\nDescription " + trx.getItemDescription() +  "\nPrice: " + trx.getCost() + "\n";
			total += trx.getCost();
		}
		body += "\nOrder Total: $" + (Math.round(total* 100.0) / 100.0);
		body += "\n\nCyShop\nSeek. Find. Enjoy.\n";
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("CyShop <309r14@gmail.com");
		message.setTo(shopper.getEmail());
		message.setText(body);
		message.setSubject("CyShop - Order Confirmation");
		mailSender.send(message);
	}

	/**
	 * Sends an email to the seller from the shopper about their question for an item.
	 */
	public void askSellerQuestion(User shopper, User seller, Item item, String question) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("CyShop <309r14@gmail.com>");
		message.setTo(seller.getEmail());
		message.setText("Hello " + seller.getUsername() + ",\n\n" + shopper.getUsername() + " has a question about " + item.getItemName() + ". This is their question:\n"
				+ question + "\n\nYou may reply to them at " + shopper.getEmail() + ".\n\nCyShop\nSeek. Find. Enjoy.\n");
		message.setSubject("CyShop - Question about " + item.getItemName());
		mailSender.send(message);
	}

	/**
	 * Sends an email to the seller from the shopper about some feedback for the item.
	 */
	public void sendFeedback(User shopper, User seller, Feedback feedback, Item item) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("CyShop <309r14@gmail.com>");
		message.setTo(seller.getEmail());
		message.setText("Hello " + seller.getUsername() + ",\n\n" + shopper.getUsername() + " has rated your product (" + item.getItemName()
				+ "). Their rating for it is " + feedback.getRating() + " stars because " + feedback.getComment() + "."
				+ "\n\nYou may reply to them at " + shopper.getEmail() + ".\n\nCyShop\nSeek. Find. Enjoy.\n");
		message.setSubject("CyShop - Feedback for " + item.getItemName());
		mailSender.send(message);
	}
}
