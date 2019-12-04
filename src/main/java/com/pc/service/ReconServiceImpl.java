package com.pc.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pc.dao.ReconDao;
import com.pc.model.AcquirerRecon;
import com.pc.model.SettlementInfo;
import com.pc.util.SettlementUtil;

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
	
	@Override
	public void cleanupSettlementTable() {
		settlementDao.deleteAll();		
	}

	@Override
	public void insertSettlementFileToDb(File file) {
		List<SettlementInfo> list = SettlementUtil.getSettlementList(file);	
		settlementDao.insertAll(list);		
	}
	
	@Override
	public List<AcquirerRecon> getIdCardMappingList(String[] merchantIds, String startDate, String endDate) {
		return acquirerReconDao.getList(merchantIds, startDate, endDate);
	}

	@Override
	public long updateAcquirerCardNumber(AcquirerRecon p) {
		return acquirerReconDao.update(p.getAcquirerId(), p);		
	}

}
