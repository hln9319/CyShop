package edu.iastate.cs309.dao;

import java.util.List;

import edu.iastate.cs309.domain.User;

/**
 * The Data Access Object interface for users. Defines the methods that will be used.
 */
public interface UserDao {
	public Integer createNewUserId();
	public List<User> findAllUsers();
	public User createUserAccount(User user) throws Exception;
	public User deleteUserAccount(User user);
	public User logUserIn(String username, String password) throws Exception;
	public User logUserOut(Integer userId);
	public User confirmUser(Integer userId);
	public User findUserById(Integer userId);
	public User findUserByUsername(String username);
	public User saveProfile(User user);
	public boolean checkIfEmailExist(String email);
	public boolean checkIfUsernameExist(String username);
}
