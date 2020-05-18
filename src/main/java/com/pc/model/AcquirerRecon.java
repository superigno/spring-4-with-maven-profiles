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
	private String merchantId;
	private String terminalId;
	private String baseAmount;
	private String rrn;
	private String trxId;
	private String settlementFilename;	
	private String acquirerTrxCurrency;
	private String settlementTrxCurrency;
	private String acquirerTrxAmount;
	private String settlementTrxAmount;
	private String acquirerExchangeRate;
	private String settlementExchangeRate;
	private String acquirerAuthorisationCode;
	private String settlementAuthorisationCode;
	
	public String getAcquirerTrxCurrency() {
		return acquirerTrxCurrency;
	}
	public void setAcquirerTrxCurrency(String acquirerTrxCurrency) {
		this.acquirerTrxCurrency = acquirerTrxCurrency;
	}
	public String getSettlementTrxCurrency() {
		if (settlementTrxCurrency == null || settlementTrxCurrency.trim().equals("")) {
			return "AUD";
		}
		return settlementTrxCurrency;
	}
	public void setSettlementTrxCurrency(String settlementTrxCurrency) {
		this.settlementTrxCurrency = settlementTrxCurrency;
	}
	public String getAcquirerTrxAmount() {
		return acquirerTrxAmount;
	}
	public void setAcquirerTrxAmount(String acquirerTrxAmount) {
		this.acquirerTrxAmount = acquirerTrxAmount;
	}
	public String getSettlementTrxAmount() {
		return settlementTrxAmount;
	}
	public void setSettlementTrxAmount(String settlementTrxAmount) {
		this.settlementTrxAmount = settlementTrxAmount;
	}
	public String getAcquirerExchangeRate() {
		return acquirerExchangeRate;
	}
	public void setAcquirerExchangeRate(String acquirerExchangeRate) {
		this.acquirerExchangeRate = acquirerExchangeRate;
	}
	public String getSettlementExchangeRate() {
		return settlementExchangeRate;
	}
	public void setSettlementExchangeRate(String settlementExchangeRate) {
		this.settlementExchangeRate = settlementExchangeRate;
	}
	public String getAcquirerAuthorisationCode() {
		return acquirerAuthorisationCode;
	}
	public void setAcquirerAuthorisationCode(String acquirerAuthorisationCode) {
		this.acquirerAuthorisationCode = acquirerAuthorisationCode;
	}
	public String getSettlementAuthorisationCode() {
		return settlementAuthorisationCode;
	}
	public void setSettlementAuthorisationCode(String settlementAuthorisationCode) {
		this.settlementAuthorisationCode = settlementAuthorisationCode;
	}
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
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getBaseAmount() {
		return baseAmount;
	}
	public void setBaseAmount(String baseAmount) {
		this.baseAmount = baseAmount;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getSettlementFilename() {
		return settlementFilename;
	}
	public void setSettlementFilename(String settlementFilename) {
		this.settlementFilename = settlementFilename;
	}
	
}
