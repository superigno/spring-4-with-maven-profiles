package com.pc.model;

/**
 * @author gino.q
 *
 */
public class IdCardNumberPair {
	
	private String id;
	private String oldCardNumber;
	private String cardNumber;
	private String oldCardCurrency;
	private String cardCurrency;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOldCardNumber() {
		return oldCardNumber;
	}
	public void setOldCardNumber(String oldCardNumber) {
		this.oldCardNumber = oldCardNumber;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getOldCardCurrency() {
		return oldCardCurrency;
	}
	public void setOldCardCurrency(String oldCardCurrency) {
		this.oldCardCurrency = oldCardCurrency;
	}
	public String getCardCurrency() {
		return cardCurrency;
	}
	public void setCardCurrency(String cardCurrency) {
		this.cardCurrency = cardCurrency;
	}	
	
}
