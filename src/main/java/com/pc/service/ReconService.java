package com.pc.service;

import java.io.File;
import java.util.List;

import com.pc.model.IdCardNumberPair;

/**
 * @author gino.q
 *
 */
public interface ReconService {
	
	public void cleanupSettlementTable();
	public void insertSettlementFileToDb(File file);
	public List<IdCardNumberPair> getIdCardMappingList(String[] merchantIds, String startDate, String endDate);
	public int updateAcquirerCardNumber(IdCardNumberPair p);

}
