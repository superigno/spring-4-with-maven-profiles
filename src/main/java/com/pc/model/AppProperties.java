package com.pc.model;

/**
 * @author gino.q
 *
 */
public class AppProperties {
	
	private String appDirectory;
	private String[] merchantIds;
	private String settlementStartDate;
	private String settlementEndDate;
	private int scanPeriodInMinutes;
	private boolean isProductionMode;
	private String baseCurrency;
	
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public String getAppDirectory() {
		return appDirectory;
	}
	public boolean isProductionMode() {
		return isProductionMode;
	}
	public void setProductionMode(boolean isProductionMode) {
		this.isProductionMode = isProductionMode;
	}
	public void setAppDirectory(String appDirectory) {
		this.appDirectory = appDirectory;
	}
	public String[] getMerchantIds() {
		return merchantIds;
	}
	public void setMerchantIds(String[] merchantIds) {
		this.merchantIds = merchantIds;
	}
	public String getSettlementStartDate() {
		return settlementStartDate;
	}
	public void setSettlementStartDate(String settlementStartDate) {
		this.settlementStartDate = settlementStartDate;
	}
	public String getSettlementEndDate() {
		return settlementEndDate;
	}
	public void setSettlementEndDate(String settlementEndDate) {
		this.settlementEndDate = settlementEndDate;
	}
	public int getScanPeriodInMinutes() {
		return scanPeriodInMinutes;
	}
	public void setScanPeriodInMinutes(int scanPeriodInMinutes) {
		this.scanPeriodInMinutes = scanPeriodInMinutes;
	}	

}
