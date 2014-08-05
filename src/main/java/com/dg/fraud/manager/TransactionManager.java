/**
 * 
 */
package com.dg.fraud.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dg.fraud.model.Transaction;

/**
 * @author johnver
 *
 */
public interface TransactionManager {
	
	public List<String> identifyFraudTransactions(
			List<Transaction> transactions, Date date,
			BigDecimal priceTreshold);

}
