/**
 * 
 */
package com.dg.fraud.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.dg.fraud.model.Transaction;

/**
 * @author johnver
 *
 */
public class TransactionParser {
	
	private static final int EXPECTED_TOKEN_COUNT = 3;

	private static final Logger logger = Logger
			.getLogger(TransactionParser.class.getName());
	
	private static final String SEPARATOR = ",";
	private final String transactionString;
	
	private String hashNumber;
	private Timestamp timestamp;
	private BigDecimal price;

	public TransactionParser(String transactionString) {
		this.transactionString = transactionString;
		
	}
	
	private String trimToNull(String entry) {
		String ret = null;
		if (entry != null) {
			ret = entry.trim();
			if ("".equals(ret)) {
				ret = null;
			}
		}
		
		return ret;
	}
	
	private Timestamp parseTimestamp(String timestampStr) throws java.text.ParseException {
		Timestamp ret = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date parsedDate = dateFormat.parse(timestampStr);
		ret = new Timestamp(parsedDate.getTime());
		return ret;
	}
	
	private BigDecimal parsePrice(String priceStr) {
		BigDecimal ret = new BigDecimal(priceStr);
		return ret;
	}
	
	private Transaction parse() throws com.dg.fraud.exception.ParseException {
		Transaction ret = null;
		try {
			StringTokenizer tokenizer = new StringTokenizer(transactionString, SEPARATOR);
			if (tokenizer.countTokens() > EXPECTED_TOKEN_COUNT) {
				throw new com.dg.fraud.exception.ParseException(
						"Transaction string is not in correct format: " + transactionString);
			}
			
			String hashNumberStr = trimToNull(tokenizer.nextToken());
			String timestampStr = trimToNull(tokenizer.nextToken());
			String priceStr = trimToNull(tokenizer.nextToken());
			
			this.hashNumber = hashNumberStr;
			this.timestamp = parseTimestamp(timestampStr);
			this.price = parsePrice(priceStr);
			
			ret = new Transaction(this.hashNumber, this.timestamp, this.price);
		}
		catch (Exception e) {
			throw new com.dg.fraud.exception.ParseException(e.getMessage(), e);
		}
		
		return ret;
	}
	
	public Transaction getTransaction() throws com.dg.fraud.exception.ParseException {
		
		Transaction transaction = parse();
		
		return transaction;
	}
}
