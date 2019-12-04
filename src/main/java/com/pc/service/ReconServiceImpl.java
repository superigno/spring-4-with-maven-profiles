package com.pc.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pc.dao.AcquirerDao;
import com.pc.dao.SettlementDao;
import com.pc.model.IdCardNumberPair;
import com.pc.model.SettlementInfo;
import com.pc.util.SettlementUtil;

/**
 * @author gino.q
 *
 */
@Service("reconService")
public class ReconServiceImpl implements ReconService {

	@Autowired
	private AcquirerDao acquirerDao;
	@Autowired
	private SettlementDao settlementDao;
	
	@Override
	public void cleanupSettlementTable() {
		settlementDao.cleanupTable();		
	}

	@Override
	public void insertSettlementFileToDb(File file) {
		List<SettlementInfo> list = SettlementUtil.getSettlementList(file);	
		settlementDao.batchInsert(list);		
	}
	
	@Override
	public List<IdCardNumberPair> getIdCardMappingList(String[] merchantIds, String startDate, String endDate) {
		return acquirerDao.getIdCardMappingList(merchantIds, startDate, endDate);
	}

	@Override
	public int updateAcquirerCardNumber(IdCardNumberPair p) {
		return acquirerDao.updateAcquirerCardNumber(p);		
	}

}
