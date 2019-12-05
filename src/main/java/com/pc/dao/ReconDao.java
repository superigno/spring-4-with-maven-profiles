package com.pc.dao;

import java.util.List;

public interface ReconDao<T> {
	
	public static final String LOG_SQL = "INSERT INTO settlementreconlog (table_name, table_id, column_name, before_value, after_value, transaction_date) VALUES (?, ?, ?, ?, ?, ?)";
	
	public List<T> getList(String[] merchantIds, String startDate, String endDate);
	public T get(long id);
	public long update(T t);
	public long insertAll(List<T> list);
	public long insert(T t);
	public long deleteAll();
	public long delete(long id);
	public void log(T t);

}
