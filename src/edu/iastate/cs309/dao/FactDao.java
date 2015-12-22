package edu.iastate.cs309.dao;

import java.util.List;

import edu.iastate.cs309.domain.Fact;

public interface FactDao {
	public List<Fact> findAllFacts();
	public Fact findFactById(Integer factId);
	public void addNewFact(String fact);
	public Integer createNewFactId();
	public void editFact(Fact fact);
	public void deleteFact(Fact fact);
	public Fact generateRandomFact();
}
