package com.pc.dao;

import java.util.List;

import com.pc.model.SettlementInfo;

/**
 * @author gino.q
 *
 */
public interface SettlementDao {

	public void insert(SettlementInfo info);
	public void batchInsert(List<SettlementInfo> list);
	public void cleanupTable();
	
}
