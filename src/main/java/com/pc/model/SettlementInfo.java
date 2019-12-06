package com.pc.model;

/**
 * @author gino.q
 *
 */
public class SettlementInfo {
	
	public SettlementInfo() {
		
	}
	
	private boolean isNullOrEmpty(String s) {
		if (s == null || s.equals("")) {
			return true;
		}
		return false;
	}
	
	public SettlementInfo(String[] info, String filename) {
		try {
			this.merchantId = info[0];
			this.terminalId = info[1];
			this.merchantRef = info[2];
			this.terminalTransactionTime = !isNullOrEmpty(info[3]) ? info[3] : null;
			this.acquirerTime = !isNullOrEmpty(info[4]) ? info[4] : null;
			this.trxType = info[5];
			this.baseCurrency = info[6];
			this.baseAmount = !isNullOrEmpty(info[7]) ? Long.parseLong(info[7].replace(".", "")) : 0;
			this.trxCurrency = info[8];
			this.trxAmount = !isNullOrEmpty(info[9]) ? Long.parseLong(info[9].replace(".", "")) : 0;
			this.exchangeRate = info[10];
			this.responseCode = info[11];
			this.responseMessage = info[12];
			this.authorisationCode = info[13];
			this.rrn = info[14];
			this.cardNumber = info[15];
			this.cardCurrency = info[16];
			this.cardType = info[17];
			this.grossMargin = info[18];
			this.netBankCommi = info[19];
			this.netPcCommi = info[20];
			this.netMerchantCommi = info[21];
			this.schemeSettlementRate = info[22];
			this.schemeSettlementAmount = !isNullOrEmpty(info[23]) ? Long.parseLong(info[23].replace(".", "")) : 0;
			this.settleTime = !isNullOrEmpty(info[24]) ? info[24] : null;
			this.repaymentFee = info[25];
			this.originator = null;
			this.nonDccReasonCode = info[26];
			this.cardNumberLength = !isNullOrEmpty(info[27]) ? Long.parseLong(info[27]) : 0;
			this.rateProgram = info[28];
			this.trxId = info[29];			
		} catch (IndexOutOfBoundsException e) {
			
		} finally {
			this.filename = filename;
		}
	}
	
	private String merchantId;
	private String terminalId;
	private String merchantRef;
	private String terminalTransactionTime;
	private String acquirerTime;
	private String trxType;
	private String baseCurrency;
	private long baseAmount;
	private String trxCurrency;
	private long trxAmount;
	private String exchangeRate;
	private String responseCode;
	private String responseMessage;
	private String authorisationCode;
	private String rrn;
	private String cardNumber;
	private String cardCurrency;
	private String cardType;
	private String grossMargin;
	private String netBankCommi;
	private String netPcCommi;
	private String netMerchantCommi;
	private String schemeSettlementRate;
	private long schemeSettlementAmount;
	private String settleTime;
	private String repaymentFee;
	private String originator;
	private String nonDccReasonCode;
	private long cardNumberLength;
	private String rateProgram;
	private String trxId;
	private String filename;
	
	public String getMerchantId() {
		return merchantId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public String getMerchantRef() {
		return merchantRef;
	}

	public String getTerminalTransactionTime() {
		return terminalTransactionTime;
	}

	public String getAcquirerTime() {
		return acquirerTime;
	}

	public String getTrxType() {
		return trxType;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public long getBaseAmount() {
		return baseAmount;
	}

	public String getTrxCurrency() {
		return trxCurrency;
	}

	public long getTrxAmount() {
		return trxAmount;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public String getAuthorisationCode() {
		return authorisationCode;
	}

	public String getRrn() {
		return rrn;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getCardCurrency() {
		return cardCurrency;
	}

	public String getCardType() {
		return cardType;
	}

	public String getGrossMargin() {
		return grossMargin;
	}

	public String getNetBankCommi() {
		return netBankCommi;
	}

	public String getNetPcCommi() {
		return netPcCommi;
	}

	public String getNetMerchantCommi() {
		return netMerchantCommi;
	}

	public String getSchemeSettlementRate() {
		return schemeSettlementRate;
	}

	public long getSchemeSettlementAmount() {
		return schemeSettlementAmount;
	}

	public String getSettleTime() {
		return settleTime;
	}

	public String getRepaymentFee() {
		return repaymentFee;
	}

	public String getOriginator() {
		return originator;
	}

	public String getNonDccReasonCode() {
		return nonDccReasonCode;
	}

	public long getCardNumberLength() {
		return cardNumberLength;
	}

	public String getRateProgram() {
		return rateProgram;
	}

	public String getTrxId() {
		return trxId;
	}
	
	public String getFilename() {
		return filename;
	}

	@Override
	public String toString() {
		return "SettlementInfo [merchantId=" + merchantId + ", terminalId=" + terminalId + ", merchantRef="
				+ merchantRef + ", terminalTransactionTime=" + terminalTransactionTime + ", acquirerTime="
				+ acquirerTime + ", trxType=" + trxType + ", baseCurrency=" + baseCurrency + ", baseAmount="
				+ baseAmount + ", trxCurrency=" + trxCurrency + ", trxAmount=" + trxAmount + ", exchangeRate="
				+ exchangeRate + ", responseCode=" + responseCode + ", responseMessage=" + responseMessage
				+ ", authorisationCode=" + authorisationCode + ", rrn=" + rrn + ", cardNumber=" + cardNumber
				+ ", cardCurrency=" + cardCurrency + ", cardType=" + cardType + ", grossMargin=" + grossMargin
				+ ", netBankCommi=" + netBankCommi + ", netPcCommi=" + netPcCommi + ", netMerchantCommi="
				+ netMerchantCommi + ", schemeSettlementRate=" + schemeSettlementRate + ", schemeSettlementAmount="
				+ schemeSettlementAmount + ", settleTime=" + settleTime + ", repaymentFee=" + repaymentFee
				+ ", originator=" + originator + ", nonDccReasonCode=" + nonDccReasonCode + ", cardNumberLength="
				+ cardNumberLength + ", rateProgram=" + rateProgram + ", trxId=" + trxId + ", filename=" + filename
				+ "]";
	}
}
