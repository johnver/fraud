/**
 * 
 */
package com.dg.fraud.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author johnver
 *
 */
public class TransactionTest {

	private TransactionTestFixture transactionTestFixture;
	
	@Before
	public void setUp() {
		this.transactionTestFixture = new TransactionTestFixture();
	}

	@Test
	public void should_create_valid_transaction() {
		this.transactionTestFixture.given_a_valid_transaction_details();
		this.transactionTestFixture.when_transaction_is_instantiated();
		this.transactionTestFixture.then_assert_transaction_details_are_set();
		this.transactionTestFixture.then_assert_actual_is_equal_to_expected();
		this.transactionTestFixture.then_assert_instantation_succeed();
	}
	
	@Test
	public void should_not_accept_null_inputs() {
		this.transactionTestFixture.given_null_inputs();
		this.transactionTestFixture.when_transaction_is_instantiated();
		this.transactionTestFixture.then_assert_instantation_failed();
	}
	
	private class TransactionTestFixture {
		
		private Transaction actualTransaction;
		
		private String hashNumber;
		private Timestamp timestamp;
		private BigDecimal price;
		
		private boolean instantationResult = false;
		
		public void given_a_valid_transaction_details() {
			this.hashNumber = "10d7ce2f43e35fa57d1bbf8b1e2";
			Date currentDate = new Date();
			this.timestamp = new Timestamp(currentDate.getTime());
			this.price = new BigDecimal(100);
		}
		
		public void given_null_inputs() {
			this.hashNumber = null;
			this.timestamp = null;
			this.timestamp = null;
			this.price = null;
		}
		
		public void when_transaction_is_instantiated() {
			try {
				this.actualTransaction = 
						new Transaction(hashNumber,
								timestamp, price);
				instantationResult = true;
			}
			catch (Exception e) {
				instantationResult = false;
			}
		}
		
		public void then_assert_transaction_details_are_set() {
			Assert.assertNotNull(this.actualTransaction.getCreditCardDetail());
			Assert.assertNotNull(this.actualTransaction.getPrice());
			Assert.assertNotNull(this.actualTransaction.getTimestamp());
		}
		
		public void then_assert_actual_is_equal_to_expected() {
			Assert.assertEquals(this.actualTransaction.
					getCreditCardDetail().getHashNumber(), this.hashNumber);
			Assert.assertEquals(this.actualTransaction.
					getPrice(), this.price);
			Assert.assertEquals(this.actualTransaction.getTimestamp(), 
					this.timestamp);
		}
		
		public void then_assert_instantation_succeed() {
			Assert.assertEquals(this.instantationResult, 
					true);
		}
		
		public void then_assert_instantation_failed() {
			Assert.assertEquals(this.instantationResult, 
					false);
		}
	}

}
