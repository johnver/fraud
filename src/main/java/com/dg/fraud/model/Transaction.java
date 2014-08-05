/**
 * 
 */
package com.dg.fraud.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author johnver
 *
 */
public class Transaction {

	private final CreditCardDetail creditCardDetail;
	private final Timestamp timestamp;
	private final BigDecimal price;
	
	public Transaction(String hashNumber, 
			Timestamp timestamp, BigDecimal price) {
		this.validate(hashNumber, timestamp, price);
		this.creditCardDetail = new CreditCardDetail(hashNumber);
		this.timestamp = timestamp;
		this.price = price;
	}
	
	public Transaction(CreditCardDetail creditCardDetail, 
			Timestamp timestamp, BigDecimal price) {
		this.creditCardDetail = creditCardDetail;
		this.timestamp = timestamp;
		this.price = price;
	}
	
	private void validate(CreditCardDetail creditCardDetail, 
			Timestamp timestamp, BigDecimal price) {
		if (creditCardDetail == null || price == null 
				|| timestamp == null) {
			throw new IllegalArgumentException(
					"Parameters should not be null");
			
		}
		
	}
	
	private void validate(String hashNumber, 
			Timestamp timestamp, BigDecimal price) {
		validate(new CreditCardDetail(hashNumber), 
				timestamp, price);
		
	}
	
	
	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	
	/**
	 * @return the creditCardDetail
	 */
	public CreditCardDetail getCreditCardDetail() {
		return creditCardDetail;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((creditCardDetail == null) ? 0 : creditCardDetail.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (creditCardDetail == null) {
			if (other.creditCardDetail != null)
				return false;
		} else if (!creditCardDetail.equals(other.creditCardDetail))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	
	
	
}
