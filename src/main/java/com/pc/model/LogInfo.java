package com.pc.model;

import java.util.Date;

public class LogInfo {
	
	private String tableName;
	private long tableId;
	private String columnName;
	private String columnBeforeValue;
	private String columnAfterValue;
	private Date transactionDate;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public long getTableId() {
		return tableId;
	}
	public void setTableId(long tableId) {
		this.tableId = tableId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnBeforeValue() {
		return columnBeforeValue;
	}
	public void setColumnBeforeValue(String columnBeforeValue) {
		this.columnBeforeValue = columnBeforeValue;
	}
	public String getColumnAfterValue() {
		return columnAfterValue;
	}
	public void setColumnAfterValue(String columnAfterValue) {
		this.columnAfterValue = columnAfterValue;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
