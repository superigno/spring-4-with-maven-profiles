package com.pc.service;

import java.io.File;
import java.util.List;

import com.pc.model.AcquirerRecon;
import com.pc.model.SchemeSettleRecon;

/**
 * @author gino.q
 *
 */
public interface ReconService {
	
	public void cleanupSettlementTable();
	public void insertSettlementFileToDb(File file);	
	public List<AcquirerRecon> getAcquirerSettlementMappingList(String[] merchantIds, String settlementStartDate, String settlementEndDate);
	public List<SchemeSettleRecon> getSchemeSettlementMappingList(String merchantId, String terminalId, String baseAmount, String rrn, String trxId, String settlementStartDate, String settlementEndDate);
	public long updateAcquirerDetails(AcquirerRecon a);
	public long updateAcquirerDetails2(AcquirerRecon a);
	public long updateSchemeSettlementDetails(SchemeSettleRecon s);
	public long deleteFromExtraPendingCommission(long id);
	public long deleteFromExtraMissingCommission(long id);
	
}
