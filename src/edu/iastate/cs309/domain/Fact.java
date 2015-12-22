package edu.iastate.cs309.domain;

/**
 * A domain object for fact of the day.
 * @author SaldinBajric
 *
 */
public class Fact {
	private Integer factId;
	private String fact;
	
	public Fact() {}

	public Integer getFactId() {
		return factId;
	}

	public void setFactId(Integer factId) {
		this.factId = factId;
	}

	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}
}
