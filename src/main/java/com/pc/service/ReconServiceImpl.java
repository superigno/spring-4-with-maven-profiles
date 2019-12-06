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
	private ReconDao<AcquirerRecon> acquirerReconDao;
	
	@Autowired
	private ReconDao<SettlementInfo> settlementDao;
	
	@Autowired
	private ReconDao<SchemeSettleRecon> schemeSettleReconDao;
	
	@Autowired
	private ReconDao<Object> extraPendingCommissionDao;
	
	@Autowired
	private ReconDao<Object> extraMissingCommissionDao;
	
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
	public List<AcquirerRecon> getAcquirerSettlementMappingList(String[] merchantIds, String startDate, String endDate) {
		return acquirerReconDao.getList(merchantIds, startDate, endDate);
	}

	@Override
	public long updateAcquirerDetails(AcquirerRecon p) {
		return acquirerReconDao.update(p);		
	}

	@Override
	public long updateSchemeSettlementDetails(SchemeSettleRecon s) {
		return schemeSettleReconDao.update(s);
	}

	@Override
	public long deleteFromExtraPendingCommission(long id) {
		return extraPendingCommissionDao.delete(id);
	}

	@Override
	public long deleteFromExtraMissingCommission(long id) {
		return extraMissingCommissionDao.delete(id);
	}	
	
}
