package com.pc.dao;

import java.util.List;

public interface ReconDao<T> {
	
	public List<T> getList(String[] merchantIds, String startDate, String endDate);
	public T get(long id);
	public long update(long id, T t);
	public long insertAll(List<T> list);
	public long insert(T t);
	public long deleteAll();
	public long delete(long id);

}
