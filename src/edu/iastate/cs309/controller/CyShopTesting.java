package edu.iastate.cs309.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import edu.iastate.cs309.dao.FactDaoImpl;
import edu.iastate.cs309.dao.FactDao;
import edu.iastate.cs309.dao.FeedbackDao;
import edu.iastate.cs309.dao.FeedbackDaoImpl;
import edu.iastate.cs309.dao.ItemDao;
import edu.iastate.cs309.dao.ItemDaoImpl;
import edu.iastate.cs309.dao.TransactionDao;
import edu.iastate.cs309.dao.TransactionDaoImpl;
import edu.iastate.cs309.dao.UserDao;
import edu.iastate.cs309.dao.UserDaoImpl;
import edu.iastate.cs309.domain.EmailHandler;
import edu.iastate.cs309.domain.Fact;
import edu.iastate.cs309.domain.Feedback;
import edu.iastate.cs309.domain.Item;
import edu.iastate.cs309.domain.Question;
import edu.iastate.cs309.domain.Transaction;
import edu.iastate.cs309.domain.User;
import edu.iastate.cs309.controller.AdminController;
import edu.iastate.cs309.controller.CategoriesController;
import edu.iastate.cs309.controller.FeedbackController;
import edu.iastate.cs309.controller.ItemController;
import edu.iastate.cs309.controller.MainController;
import edu.iastate.cs309.controller.SellerController;
import edu.iastate.cs309.controller.ShopperController;
import edu.iastate.cs309.controller.ShoppingCartController;
import edu.iastate.cs309.controller.UserController;



@RunWith(Suite.class)
@SuiteClasses({})
public class CyShopTesting {
	
	@Before
	public void testUser()
	{
		User Test = new User();
		Test.setUsername("TestUsername");
		Test.setUserId(12);
	}

	@Test
	public void deleteUserTest()
	{
		
	}
	
}
