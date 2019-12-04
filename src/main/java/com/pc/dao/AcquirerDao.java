package com.pc.dao;

import java.util.List;

import com.pc.model.IdCardNumberPair;

/**
 * @author gino.q
 *
 */
public interface AcquirerDao {
	
	public List<IdCardNumberPair> getIdCardMappingList(String[] merchantIds, String startDate, String endDate);
	public int updateAcquirerCardNumber(IdCardNumberPair p);

}
