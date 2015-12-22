package edu.iastate.cs309.dao;

import java.util.List;

import edu.iastate.cs309.domain.Transaction;

/**
 * The Data Access Object interface for transactions. Defines the methods that will be used.
 */
public interface TransactionDao {
	public void addNewTransaction(Transaction transaction); 
	public Integer createNewTransactionId();
	public Integer createNewOrderId();
	public List<Transaction> findAllTransactions();
	public List<Transaction> findAllSellerTransactions(Integer sellerId);
	public List<Transaction> findAllShopperTransactions(Integer shopperId);
	public Transaction findtransactionById(Integer transactionId);
	public void updateTransaction(Transaction transaction);
	public String getNewShippingNumber();
}
