/**
 * 
 */
package com.dg.fraud.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.dg.fraud.manager.TransactionManager;
import com.dg.fraud.manager.TransactionManagerImpl;
import com.dg.fraud.model.Transaction;

/**
 * @author johnver
 *
 */
public class FraudDetectionHelper {
	
	private TransactionManager transactionManager;
	
	private static FraudDetectionHelper instance;
	
	private static final Logger logger = Logger
			.getLogger(FraudDetectionHelper.class.getName());
	
	private FraudDetectionHelper() {
		this.transactionManager = new TransactionManagerImpl();
	}
	
	public static FraudDetectionHelper getInstance() {
		if (instance == null) {
			instance = new FraudDetectionHelper();
		}
		return instance;
	}


	public static List<String>
		identifyFraudTransactions(List<String> transactions,
				Date date, BigDecimal treshold) {
		
		List<Transaction> parsedTransactions = new ArrayList<Transaction>();
		
		for (String trans : transactions) {
			try {
				TransactionParser parser = new TransactionParser(trans);
				Transaction transaction = parser.getTransaction();
				parsedTransactions.add(transaction);
			}
			catch (Exception e) {
				logger.info("Error while processing transaction: " + trans + " root cause: " + e.getMessage());
			}
		}
		
		FraudDetectionHelper helper = FraudDetectionHelper.getInstance();
		List<String> fraudTransactions = helper.getTransactionManager().identifyFraudTransactions(
			parsedTransactions, date, treshold);
				
		return fraudTransactions;
	}

	/**
	 * @return the transactionManager
	 */
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * @param transactionManager the transactionManager to set
	 */
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
