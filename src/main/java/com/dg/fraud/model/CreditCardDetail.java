/**
 * 
 */
package com.dg.fraud.model;

/**
 * @author johnver
 *
 */
public class CreditCardDetail {

	private final String hashNumber;
	

	public CreditCardDetail(String hashNumber) {
		this.validate(hashNumber);
		this.hashNumber = hashNumber;
	}
	
	private void validate(String hashNumber) {
		if (hashNumber == null) {
			throw new IllegalArgumentException(
					"Paramater should not be null");
			
		}
	}
	
	/**
	 * @return the hashNumber
	 */
	public String getHashNumber() {
		return hashNumber;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hashNumber == null) ? 0 : hashNumber.hashCode());
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
		CreditCardDetail other = (CreditCardDetail) obj;
		if (hashNumber == null) {
			if (other.hashNumber != null)
				return false;
		} else if (!hashNumber.equals(other.hashNumber))
			return false;
		return true;
	}
	
	
}
