/**
 * 
 */
package com.dg.fraud.util;

import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.dg.fraud.model.Transaction;

/**
 * @author johnver
 *
 */
public class TransactionParserTest {
	
	private static final Logger logger = Logger
			.getLogger(FraudDetectionHelper.class.getName());
	
	private TransactionParserTestFixture testFixture;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.testFixture = new TransactionParserTestFixture();
	}

	@Test
	public void should_parse_valid_transaction() {
		this.testFixture.given_a_valid_transaction_string();
		this.testFixture.when_get_transaction_is_invoke();
		this.testFixture.then_assert_transaction_is_set();
		this.testFixture.then_assert_method_invocation_succeed();
	}
	
	@Test
	public void should_not_parse_invalid_date() {
		this.testFixture.given_an_invalid_date_format();
		this.testFixture.when_get_transaction_is_invoke();
		this.testFixture.then_assert_method_invocation_failed();
		
	}
	
	@Test
	public void should_not_parse_invalid_price() {
		this.testFixture.given_an_invalid_price_format();
		this.testFixture.when_get_transaction_is_invoke();
		this.testFixture.then_assert_method_invocation_failed();
		
	}
	
	@Test
	public void should_not_parse_invalid_transaction() {
		this.testFixture.given_an_invalid_transaction_format();
		this.testFixture.when_get_transaction_is_invoke();
		this.testFixture.then_assert_method_invocation_failed();
		
	}
	
	@Test
	public void should_not_parse_invalid_transaction_with_missing_field() {
		this.testFixture.given_an_invalid_transaction_format_with_no_timestamp();
		this.testFixture.when_get_transaction_is_invoke();
		this.testFixture.then_assert_method_invocation_failed();
		
	}

	private class TransactionParserTestFixture {
		
		private TransactionParser parser;
		
		private Transaction actualTransaction;
		private boolean hasException = false;
		
		public void given_a_valid_transaction_string() {
			String validTransactionStr = 
					"10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00";
			parser = new TransactionParser(validTransactionStr);
		}
		
		public void given_an_invalid_date_format() {
			String validTransactionStr = 
					"10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29, 10.00";
			parser = new TransactionParser(validTransactionStr);
		}
		
		public void given_an_invalid_price_format() {
			String validTransactionStr = 
					"10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, A.00";
			parser = new TransactionParser(validTransactionStr);
		}
		
		public void given_an_invalid_transaction_format() {
			String validTransactionStr = 
					"10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00, two";
			parser = new TransactionParser(validTransactionStr);
		}
		
		public void given_an_invalid_transaction_format_with_no_timestamp() {
			String validTransactionStr = 
					"10d7ce2f43e35fa57d1bbf8b1e2, 10.00";
			parser = new TransactionParser(validTransactionStr);
		}
		
		public void when_get_transaction_is_invoke() {
			try {
				actualTransaction = this.parser.getTransaction();
				hasException = false;
			}
			catch (Exception e) {
				logger.info(e.getMessage());
				hasException = true;
			}
		}
		
		public void then_assert_transaction_is_set() {
			Assert.assertNotNull(actualTransaction);
		}

		public void then_assert_method_invocation_succeed() {
			Assert.assertEquals(hasException, false);
		}
		
		public void then_assert_method_invocation_failed() {
			Assert.assertEquals(hasException, true);
		}
	}
}
