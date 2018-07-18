package com.Request;

import java.math.BigDecimal;


/**
 * Request Class for Transaction API
 *
 */
public class ServiceRequest {

	private BigDecimal amount = BigDecimal.ZERO;

	private long timestamp;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	

}
