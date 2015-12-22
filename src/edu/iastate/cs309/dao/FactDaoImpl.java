package edu.iastate.cs309.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mysql.jdbc.Connection;

import edu.iastate.cs309.domain.Fact;

/**
 * The Data Access Object implementation for facts. Talks to the database.
 */
public class FactDaoImpl implements FactDao {
	@Autowired JdbcTemplate jdbcTemplate;
	
	/**
	 * Finds the biggest fact id in the database and adds 1 to it to create a new unique fact id.
	 */
	@Override
	public Integer createNewFactId() {
		Integer id = jdbcTemplate.queryForObject("select max(fact_id) + 1 from db309R14.fact", Integer.class);
		if(id != null)
			return id;
		else
			return 1;
	}

	/**
	 * Returns a list of all facts in the database.
	 */
	@Override
    public List<Fact> findAllFacts() {
        return jdbcTemplate.query("SELECT * from db309R14.fact", new FactRowMapper());
    }
	
	/**
	 * Returns a list of all facts in the database.
	 */
	@Override
    public Fact findFactById(Integer factId) {
		
        return jdbcTemplate.queryForObject("SELECT * from db309R14.fact where fact_id=?", new Object[] {factId}, new FactRowMapper());
    }
	
	/**
	 * Inserts a new fact record into the database.
	 */
	@Override
	public void addNewFact(String fact) {
		String sql = "insert into db309R14.fact (fact_id,fact) values (?,?);";
		Object[] params = new Object[]{createNewFactId(), fact};
		jdbcTemplate.update(sql, params);
	}
	
	/**
	 * Updates a fact record in the database.
	 */
	@Override
	public void editFact(Fact fact) {
		String sql = "update db309R14.fact set fact=? where fact_id=?;";
		Object[] params = new Object[]{fact.getFact(), fact.getFactId()};
		jdbcTemplate.update(sql, params);
	}
	
	/**
	 * Deletes a fact record from the database.
	 */
	@Override
	public void deleteFact(Fact fact) {
		String sql = "delete from db309R14.fact where fact_id=?;";
		Object[] params = new Object[]{fact.getFactId()};
		jdbcTemplate.update(sql, params);
	}
	
	/**
	 * Generates a random fact for the home page.
	 */
	@Override
	public Fact generateRandomFact() {
		Random randomGenerator = new Random();
		List<Fact> factsToChoose = findAllFacts();
		int index = randomGenerator.nextInt(factsToChoose.size());
		Fact fact = factsToChoose.get(index);
		return fact;
	}
	
	/**
	 * Maps a result set of Fact objects.
	 * @author SaldinBajric
	 *
	 */
	private class FactRowMapper implements RowMapper<Fact> {
		@Override
        public Fact mapRow(ResultSet rs, int rowNumber) throws SQLException {
			Fact fact = new Fact();
            fact.setFactId(rs.getInt("fact_id"));
            fact.setFact(rs.getString("fact"));
            return fact;
        }
	}
}
