package com.pc.dao;

import java.util.List;

/**
 * @author gino.q
 *
 * @param <T>
 * @param <P>
 */
public interface ReconDao<T,P> {
	
	public static final String LOG_SQL = "INSERT INTO settlementreconlog (table_name, table_id, column_name, before_value, after_value, transaction_date, transaction_id, file_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	public List<T> getList(P p);
	public T get(P p);
	public long update(T t);
	public long insertAll(List<T> list);
	public long insert(T t);
	public long deleteAll();
	public long delete(P p);
	public void log(T t);

}
