package com.pc.model;

/**
 * @author gino.q
 *
 */
public class SchemeSettleRecon {

	private long schemeSettlementId;
	private String schemeSettlementTrxId;
	private String settlementMerchantId;
	private String settlementTerminalId;
	private String settlementBaseAmount;
	private String settlementRrn;
	private String settlementTrxId;
	private long acquirerId;
	
	public SchemeSettleRecon() {
		
	}
	
	public SchemeSettleRecon(String settlementMerchantId, String settlementTerminalId, String settlementBaseAmount,
			String settlementRrn, String settlementTrxId, long acquirerId) {
		this.settlementMerchantId = settlementMerchantId;
		this.settlementTerminalId = settlementTerminalId;
		this.settlementBaseAmount = settlementBaseAmount;
		this.settlementRrn = settlementRrn;
		this.settlementTrxId = settlementTrxId;
		this.acquirerId = acquirerId;
	}
	
	public String getSettlementMerchantId() {
		return settlementMerchantId;
	}
	public void setSettlementMerchantId(String settlementMerchantId) {
		this.settlementMerchantId = settlementMerchantId;
	}
	public String getSettlementTerminalId() {
		return settlementTerminalId;
	}
	public void setSettlementTerminalId(String settlementTerminalId) {
		this.settlementTerminalId = settlementTerminalId;
	}
	public String getSettlementBaseAmount() {
		return settlementBaseAmount;
	}
	public void setSettlementBaseAmount(String settlementBaseAmount) {
		this.settlementBaseAmount = settlementBaseAmount;
	}
	public String getSettlementRrn() {
		return settlementRrn;
	}
	public void setSettlementRrn(String settlementRrn) {
		this.settlementRrn = settlementRrn;
	}
	public String getSettlementTrxId() {
		return settlementTrxId;
	}
	public void setSettlementTrxId(String settlementTrxId) {
		this.settlementTrxId = settlementTrxId;
	}
	public long getAcquirerId() {
		return acquirerId;
	}
	public void setAcquirerId(long acquirerId) {
		this.acquirerId = acquirerId;
	}
	
	public long getSchemeSettlementId() {
		return schemeSettlementId;
	}

	public void setSchemeSettlementId(long schemeSettlementId) {
		this.schemeSettlementId = schemeSettlementId;
	}

	public String getSchemeSettlementTrxId() {
		return schemeSettlementTrxId;
	}

	public void setSchemeSettlementTrxId(String schemeSettlementTrxId) {
		this.schemeSettlementTrxId = schemeSettlementTrxId;
	}

	@Override
	public String toString() {
		return "SchemeSettleRecon [schemeSettlementId=" + schemeSettlementId + ", schemeSettlementTrxId="
				+ schemeSettlementTrxId + ", settlementMerchantId=" + settlementMerchantId + ", settlementTerminalId="
				+ settlementTerminalId + ", settlementBaseAmount=" + settlementBaseAmount + ", settlementRrn="
				+ settlementRrn + ", settlementTrxId=" + settlementTrxId + ", acquirerId=" + acquirerId + "]";
	}

}
