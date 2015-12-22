package edu.iastate.cs309.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.iastate.cs309.domain.User;

/**
 * The Data Access Object implementation for users. Talks to the database.
 */
public class UserDaoImpl implements UserDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * Authenticates the user and logs them in.
	 * 
	 * @throws Exception
	 */
	@Override
	public User logUserIn(String username, String password) throws Exception {
		try {
			User loggedInUser = jdbcTemplate.queryForObject("select * from db309R14.user where username=? and password=?", new Object[] {
					username, password }, new UserRowMapper());
			jdbcTemplate.update("update db309R14.user set logged_in=1 where user_id=?", loggedInUser.getUserId());
			return loggedInUser;
		} catch (Exception e) {
			throw new Exception("Username or password incorrect! Please try again.");
		}
	}

	/**
	 * Sets the user as confirmed.
	 */
	@Override
	public User confirmUser(Integer userId) {
		jdbcTemplate.update("update db309R14.user set confirmed='yes' where user_id=?", userId);
		return findUserById(userId);
	}

	/**
	 * Finds the user requested by his/her ID.
	 */
	@Override
	public User findUserById(Integer userId) {
		return jdbcTemplate.queryForObject("select * from db309R14.user where user_id=?", new Object[] { userId }, new UserRowMapper());
	}

	/**
	 * Finds the user requested by his/her email.
	 */
	@Override
	public User findUserByUsername(String username) {
		return jdbcTemplate.queryForObject("select * from db309R14.user where username=?", new Object[] { username }, new UserRowMapper());
	}

	/**
	 * Logs the user out.
	 */
	public User logUserOut(Integer userId) {
		User loggedOutUser = jdbcTemplate.queryForObject("select * from db309R14.user where user_id=?", new Object[] { userId },
				new UserRowMapper());
		jdbcTemplate.update("update db309R14.user set logged_in=0 where user_id=?", loggedOutUser.getUserId());
		return loggedOutUser;
	}

	/**
	 * Creates a new user id based on the biggest one currently in the database
	 * and adds 1 to it. This way there will not be duplicate ids.
	 */
	@Override
	public Integer createNewUserId() {
		return jdbcTemplate.queryForObject("select max(user_id) + 1 from db309R14.user", Integer.class);
	}

	/**
	 * Returns a list of all user accounts in the database.
	 */
	@Override
	public List<User> findAllUsers() {
		return jdbcTemplate.query("SELECT * from db309R14.user", new UserRowMapper());
	}

	/**
	 * Inserts a new user account record into the database.
	 * 
	 * @throws Exception
	 */
	@Override
	public User createUserAccount(User user) throws Exception {
		try {
			jdbcTemplate.update(
					"insert into db309R14.user (user_id, user_type, username, password, email, created) values (?,?,?,?,?, now());",
					new Object[] { createNewUserId(), user.getUserType(), user.getUsername(), user.getPassword(), user.getEmail() });
			return jdbcTemplate.queryForObject("select * from db309R14.user where username=?", new Object[] { user.getUsername() },
					new UserRowMapper());
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException)
				throw new Exception("Username or email already exists! Try another one.");
			else if (e instanceof DataIntegrityViolationException)
				throw new DataIntegrityViolationException("The username and password must be 8 characters max.");
			else
				throw new Exception(e);
		}
	}

	/**
	 * Deletes a user record from the database.
	 */
	@Override
	public User deleteUserAccount(User user) {
		jdbcTemplate.update("delete from db309R14.user where user_id=?", user.getUserId());
		return user;
	}

	/**
	 * Inserts a new user account record into the database.
	 */
	@Override
	public User saveProfile(User user) {
		jdbcTemplate
				.update("update db309R14.user set first_name=?,last_name=?,email=?,phone_number=?,street=?,city=?,state=?,zip_code=?,interests=?,card_holder=?,card_number=?,card_type=?,card_expire_date=?, image_source=? where user_id=?",
						new Object[] { user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getStreet(),
								user.getCity(), user.getState(), user.getZipCode(), user.getInterests(), user.getCardHolder(),
								user.getCardNumber(), user.getCardType(), user.getCardExpireDate(), user.getImageSource(), user.getUserId() });
		return jdbcTemplate.queryForObject("select * from db309R14.user where user_id=?", new Object[] { user.getUserId() },
				new UserRowMapper());
	}

	/**
	 * Maps a result set for a User object in the database.
	 * 
	 * @author SaldinBajric
	 * 
	 */
	private class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
			User user = new User();
			user.setUserId(rs.getInt("user_id"));
			user.setLoggedIn(rs.getInt("logged_in"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setUserType(rs.getString("user_type"));
			user.setCreated(rs.getDate("created"));
			user.setEmail(rs.getString("email"));
			user.setPhoneNumber(rs.getString("phone_number"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setStreet(rs.getString("street"));
			user.setCity(rs.getString("city"));
			user.setState(rs.getString("state"));
			user.setZipCode(rs.getString("zip_code"));
			user.setInterests(rs.getString("interests"));
			user.setConfirmed(rs.getString("confirmed"));
			user.setCardExpireDate(rs.getDate("card_expire_date"));
			user.setCardHolder(rs.getString("card_holder"));
			user.setCardNumber(rs.getString("card_number"));
			user.setCardType(rs.getString("card_type"));
			user.setImageSource(rs.getString("image_source"));
			return user;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean checkIfEmailExist(String email) {
		String sql = "select count(1) from db309R14.user where email = ?";
		int emailOccurances = this.jdbcTemplate.queryForInt(sql, email);
		if(emailOccurances > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean checkIfUsernameExist(String username) {
		String sql = "select count(1) from db309R14.user where username = ?";
		int usernameOccurances = this.jdbcTemplate.queryForInt(sql, username);
		if(usernameOccurances > 0) {
			return true;
		}
		return false;
	}
}
