package com.pc.service;

import java.io.File;
import java.util.List;

import com.pc.model.AcquirerRecon;

/**
 * @author gino.q
 *
 */
public interface ReconService {
	
	public void cleanupSettlementTable();
	public void insertSettlementFileToDb(File file);
	public List<AcquirerRecon> getIdCardMappingList(String[] merchantIds, String startDate, String endDate);
	public long updateAcquirerCardNumber(AcquirerRecon p);

}
