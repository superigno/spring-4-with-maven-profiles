package com.pc.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pc.dao.ReconDao;
import com.pc.model.AcquirerRecon;
import com.pc.model.SchemeSettleRecon;
import com.pc.model.SettlementInfo;
import com.pc.util.ReconUtil;

/**
 * @author gino.q
 *
 */
@Service("reconService")
public class ReconServiceImpl implements ReconService {
	
	@Autowired
	private ReconDao<AcquirerRecon,Object[]> acquirerReconDao;
	
	@Autowired
	private ReconDao<SettlementInfo,String[]> settlementDao;
	
	@Autowired
	private ReconDao<SchemeSettleRecon,String[]> schemeSettleReconDao;
	
	@Autowired
	private ReconDao<Object,String[]> extraPendingCommissionDao;
	
	@Autowired
	private ReconDao<Object,String[]> extraMissingCommissionDao;
	
	@Override
	public void cleanupSettlementTable() {
		settlementDao.deleteAll();		
	}

	@Override
	public void insertSettlementFileToDb(File file) {
		List<SettlementInfo> list = ReconUtil.getSettlementList(file);	
		settlementDao.insertAll(list);		
	}
	 
	@Override
	public List<AcquirerRecon> getAcquirerSettlementMappingList(String[] merchantIds, String settlementStartDate, String settlementEndDate) {
		Object[] params = {merchantIds, settlementStartDate, settlementEndDate};
		return acquirerReconDao.getList(params);
	}
	
	@Override
	public List<SchemeSettleRecon> getSchemeSettlementMappingList(String merchantId, String terminalId, String baseAmount, String rrn, String trxId, String settlementStartDate, String settlementEndDate) {		
		String[] params = {merchantId, terminalId, baseAmount, rrn, trxId, settlementStartDate, settlementEndDate};
		return schemeSettleReconDao.getList(params);
	}

	@Override
	public long updateAcquirerDetails(AcquirerRecon a) {
		return acquirerReconDao.update(a);		
	}

	@Override
	public long updateSchemeSettlementDetails(SchemeSettleRecon s) {
		return schemeSettleReconDao.update(s);
	}

	@Override
	public long deleteFromExtraPendingCommission(long id) {
		return extraPendingCommissionDao.delete(new String[] {String.valueOf(id)});
	}

	@Override
	public long deleteFromExtraMissingCommission(long id) {
		return extraMissingCommissionDao.delete(new String[] {String.valueOf(id)});
	}	
	
}
