/**
 * 
 */
package com.dg.fraud.manager;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dg.fraud.model.Transaction;

/**
 * @author johnver
 *
 */
public class TransactionManagerImpl implements TransactionManager {

	@Override
	public List<String> identifyFraudTransactions(
			List<Transaction> transactions, Date date, BigDecimal priceTreshold) {
		
		
		Calendar startTime = calculateStartTime(date);
		Calendar endTime = calculateEndTime(startTime);
		
		Map<String, BigDecimal> creditCardToPriceMap = new HashMap<String, BigDecimal>();
		for (Transaction transaction : transactions) {
			
			boolean isInRange = isInRange(transaction.getTimestamp(), 
					startTime.getTime(), endTime.getTime());
			if (isInRange) {
				
				BigDecimal totalPrice = getTotalTransactionPrice(
						creditCardToPriceMap, transaction);
				
				totalPrice = totalPrice.add(transaction.getPrice());
				
				updateTotalTransactionPrice(creditCardToPriceMap, transaction,
						totalPrice);
				
			}
			
		}
		
		// Identify if the total price exceeds the given treshold.
		Map<String, BigDecimal> fraudAccountMap = new HashMap<String, BigDecimal>();
		for (String mapKey : creditCardToPriceMap.keySet()) {
			BigDecimal totalPrice = creditCardToPriceMap.get(mapKey);
			if (totalPrice.compareTo(priceTreshold) > 0) {
				fraudAccountMap.put(mapKey, totalPrice);
			}
		}
		
		return new ArrayList<String>(fraudAccountMap.keySet());
	}
	


	private void updateTotalTransactionPrice(
			Map<String, BigDecimal> cardToPriceMap, Transaction transaction,
			BigDecimal totalPrice) {
		cardToPriceMap.put(
				transaction.getCreditCardDetail().getHashNumber(), totalPrice);
	}

	private BigDecimal getTotalTransactionPrice(
			Map<String, BigDecimal> cardToPriceMap, Transaction transaction) {
		BigDecimal totalPrice = cardToPriceMap.get(
				transaction.getCreditCardDetail().getHashNumber());
		if (totalPrice == null) {
			totalPrice = new BigDecimal(0);
		}
		return totalPrice;
	}

	private Calendar calculateEndTime(Calendar startTime) {
		Calendar endTime = new GregorianCalendar();
		endTime.setTime(startTime.getTime());
		endTime.add(Calendar.DAY_OF_YEAR, 1);
		return endTime;
	}
	
	private boolean isInRange(Timestamp timestamp, Date startTime, Date endTime) {
		return timestamp.after(startTime) &&
				timestamp.before(endTime);
	}

	private Calendar calculateStartTime(Date date) {
		Calendar startTime =  new GregorianCalendar();
		startTime.setTime(date);
		startTime.set(Calendar.HOUR_OF_DAY, 0);
		startTime.set(Calendar.MINUTE, 0);
		startTime.set(Calendar.SECOND, 0);
		return startTime;
	}

	
	

}
