package com.pc.model;

/**
 * @author gino.q
 *
 */
public class AcquirerRecon {
	
	private long acquirerId;
	private String acquirerCardNumber;
	private String settlementCardNumber;
	private String acquirerCardCurrency;
	private String settlementCardCurrency;
	
	public long getAcquirerId() {
		return acquirerId;
	}
	public void setAcquirerId(long acquirerId) {
		this.acquirerId = acquirerId;
	}
	public String getAcquirerCardNumber() {
		return acquirerCardNumber;
	}
	public void setAcquirerCardNumber(String acquirerCardNumber) {
		this.acquirerCardNumber = acquirerCardNumber;
	}
	public String getSettlementCardNumber() {
		return settlementCardNumber;
	}
	public void setSettlementCardNumber(String settlementCardNumber) {
		this.settlementCardNumber = settlementCardNumber;
	}
	public String getAcquirerCardCurrency() {
		return acquirerCardCurrency;
	}
	public void setAcquirerCardCurrency(String acquirerCardCurrency) {
		this.acquirerCardCurrency = acquirerCardCurrency;
	}
	public String getSettlementCardCurrency() {
		return settlementCardCurrency;
	}
	public void setSettlementCardCurrency(String settlementCardCurrency) {
		this.settlementCardCurrency = settlementCardCurrency;
	}

}
