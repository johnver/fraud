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
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.dg.fraud.model.Transaction;

/**
 * @author johnver
 *
 */
public class TransactionManagerImplTest {

	private TransactionManagerImplTestFixture transactionManagerImplTestFixture;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.transactionManagerImplTestFixture = new TransactionManagerImplTestFixture();
	}

	/**
	 * Test method for {@link com.dg.fraud.manager.TransactionManagerImpl#identifyFraudTransactions(java.util.List, java.util.Date, java.math.BigDecimal)}.
	 */
	@Test
	public void should_identify_two_fraud_transactions() {
		this.transactionManagerImplTestFixture.given_two_fraud_transaction();
		this.transactionManagerImplTestFixture.when_identify_fraud_transactions_is_invoked();
		this.transactionManagerImplTestFixture.then_assert_method_call_succeed();
		this.transactionManagerImplTestFixture.
			then_assert_actual_result_should_return_a_given_number(2);
	}
	
	@Test
	public void should_identify_one_fraud_transaction_from_three() {
		this.transactionManagerImplTestFixture.given_three_transaction_with_one_fraud_transaction();
		this.transactionManagerImplTestFixture.when_identify_fraud_transactions_is_invoked();
		this.transactionManagerImplTestFixture.then_assert_method_call_succeed();
		this.transactionManagerImplTestFixture.
			then_assert_actual_result_should_return_a_given_number(1);
	}
	
	@Test
	public void should_identify_one_fraud_transaction_from_three_diff_day_transactions() {
		this.transactionManagerImplTestFixture.given_three_fraud_transaction_from_different_days();
		this.transactionManagerImplTestFixture.when_identify_fraud_transactions_is_invoked();
		this.transactionManagerImplTestFixture.then_assert_method_call_succeed();
		this.transactionManagerImplTestFixture.
			then_assert_actual_result_should_return_a_given_number(1);
	}
	
	@Test
	public void should_identify_zero_fraud_transaction_from_three() {
		this.transactionManagerImplTestFixture.given_three_transaction_with_zero_fraud_transaction();
		this.transactionManagerImplTestFixture.when_identify_fraud_transactions_is_invoked();
		this.transactionManagerImplTestFixture.then_assert_method_call_succeed();
		this.transactionManagerImplTestFixture.
			then_assert_actual_result_should_return_a_given_number(0);
	}
	
	private class TransactionManagerImplTestFixture {
		
		TransactionManager transactionManager = new TransactionManagerImpl();
		
		private List<Transaction> givenTransactions;
		private Date date;
		private BigDecimal priceTreshold;
		
		private List<String> actualResult;
		
		private boolean hasException = false;
		
		public void given_two_fraud_transaction() {
			
			this.givenTransactions = new ArrayList<Transaction>();
			
			Date currentDate = new Date(); 
			Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
			BigDecimal price1 = new BigDecimal(50);
			Transaction transaction1 = new Transaction(
					"hash1", currentTimestamp, 
					price1);
			this.givenTransactions.add(transaction1);
			
			BigDecimal price2 = new BigDecimal(51);
			Transaction transaction2 = new Transaction(
					"hash1", currentTimestamp, 
					price2);
			this.givenTransactions.add(transaction2);
			
			BigDecimal price3 = new BigDecimal(101);
			Transaction transaction3 = new Transaction(
					"hash2", currentTimestamp, 
					price3);
			this.givenTransactions.add(transaction3);
			
			
			this.priceTreshold = new BigDecimal(100);
			this.date = new Date();
		}
		
		public void given_three_transaction_with_one_fraud_transaction() {
			
			this.givenTransactions = new ArrayList<Transaction>();
			
			Date currentDate = new Date(); 
			Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
			BigDecimal price1 = new BigDecimal(50);
			Transaction transaction1 = new Transaction(
					"hash1", currentTimestamp, 
					price1);
			this.givenTransactions.add(transaction1);
			
			BigDecimal price2 = new BigDecimal(51);
			Transaction transaction2 = new Transaction(
					"hash2", currentTimestamp, 
					price2);
			this.givenTransactions.add(transaction2);
			
			BigDecimal price3 = new BigDecimal(101);
			Transaction transaction3 = new Transaction(
					"hash3", currentTimestamp, 
					price3);
			this.givenTransactions.add(transaction3);
			
			
			this.priceTreshold = new BigDecimal(100);
			this.date = new Date();
		}
		
		public void given_three_fraud_transaction_from_different_days() {
			
			this.givenTransactions = new ArrayList<Transaction>();
			
			
			Calendar currentCalendar = new GregorianCalendar();
			Timestamp currentTimestamp = new Timestamp(currentCalendar.getTimeInMillis());
			BigDecimal price1 = new BigDecimal(101);
			Transaction transaction1 = new Transaction(
					"hash1", currentTimestamp, 
					price1);
			this.givenTransactions.add(transaction1);
			
			Calendar previousCalendar= new GregorianCalendar();
			previousCalendar.setTimeInMillis(currentCalendar.getTimeInMillis());
			previousCalendar.add(Calendar.DAY_OF_YEAR, -1);
			Timestamp previousTimestamp = new Timestamp(previousCalendar.getTimeInMillis());
			BigDecimal price2 = new BigDecimal(102);
			Transaction transaction2 = new Transaction(
					"hash2", previousTimestamp, 
					price2);
			this.givenTransactions.add(transaction2);
			
			Calendar nextCalendar= new GregorianCalendar();
			nextCalendar.setTimeInMillis(currentCalendar.getTimeInMillis());
			nextCalendar.add(Calendar.DAY_OF_YEAR, -1);
			Timestamp nextTimestamp = new Timestamp(nextCalendar.getTimeInMillis());
			BigDecimal price3 = new BigDecimal(103);
			Transaction transaction3 = new Transaction(
					"hash3", nextTimestamp, 
					price3);
			this.givenTransactions.add(transaction3);
			
			
			this.priceTreshold = new BigDecimal(100);
			this.date = new Date();
		}
		
		public void given_three_transaction_with_zero_fraud_transaction() {
			
			this.givenTransactions = new ArrayList<Transaction>();
			
			Date currentDate = new Date(); 
			Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
			BigDecimal price1 = new BigDecimal(50);
			Transaction transaction1 = new Transaction(
					"hash1", currentTimestamp, 
					price1);
			this.givenTransactions.add(transaction1);
			
			BigDecimal price2 = new BigDecimal(51);
			Transaction transaction2 = new Transaction(
					"hash2", currentTimestamp, 
					price2);
			this.givenTransactions.add(transaction2);
			
			BigDecimal price3 = new BigDecimal(99);
			Transaction transaction3 = new Transaction(
					"hash3", currentTimestamp, 
					price3);
			this.givenTransactions.add(transaction3);
			
			
			this.priceTreshold = new BigDecimal(100);
			this.date = new Date();
		}
		
		public void when_identify_fraud_transactions_is_invoked() {
			try {
				actualResult = this.transactionManager.
						identifyFraudTransactions(givenTransactions, 
						date, priceTreshold);
				hasException = false;
			}
			catch (Exception e) {
				hasException = true;
			}
		}
		
		public void then_assert_method_call_succeed() {
			Assert.assertEquals(hasException, false);
		}
		
		public void then_assert_method_call_failed() {
			Assert.assertEquals(hasException, true);
		}
		
		public void then_assert_actual_result_should_return_a_given_number(int expectedResult) {
			Assert.assertEquals(expectedResult, this.actualResult.size());
		}
	}

}
