package com.pc.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pc.mapper.SchemeSettlementMapper;
import com.pc.model.AppProperties;
import com.pc.model.SchemeSettleRecon;
import com.pc.util.ReconUtil;

/**
 * @author gino.q
 *
 */

@Repository
public class SchemeSettleReconDaoImpl implements ReconDao<SchemeSettleRecon> {
	
	private static final Logger logger = LogManager.getLogger(SchemeSettleReconDaoImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	AppProperties appProperties;

	@Override
	public List<SchemeSettleRecon> getList(SchemeSettleRecon t) {
		
		logger.info("Looking for match in schemesettlement table...");
		logger.info("Merchant ID: {}", t.getSettlementMerchantId());
		logger.info("Terminal ID: {}", t.getSettlementTerminalId());
		logger.info("Base Amount: {}", t.getSettlementBaseAmount());
		logger.info("Non DCC Reason Code: {}", t.getSettlementRrn());
		logger.info("Notes: {}", t.getSettlementTrxId());
		logger.debug("Payment Start Date: "+appProperties.getSettlementStartDate());
		logger.debug("Payment End Date: "+appProperties.getSettlementEndDate());
		
		final String SELECT_SQL = "SELECT id, trx_id, merchant_id, terminal_id, base_amount, nondcc_reason_code, notes " + 
				"FROM schemesettlement " + 
				"WHERE merchant_id = ? " + 
				"AND terminal_id = ? " + 
				"AND base_amount = ? " + 
				"AND nondcc_reason_code = ? " + 
				"AND notes = ? " +
				"AND payment_date BETWEEN ? AND ?";
		
		
		List<SchemeSettleRecon> list = new ArrayList<>();
		
		try {
			list = jdbcTemplate.query(SELECT_SQL, new Object[] {t.getSettlementMerchantId(), 
					  t.getSettlementTerminalId(), 
					  t.getSettlementBaseAmount(), 
					  t.getSettlementRrn(), 
					  t.getSettlementTrxId(),
					  appProperties.getSettlementStartDate(),
					  appProperties.getSettlementEndDate()}, new SchemeSettlementMapper());
		}catch (Exception e) {
			logger.error(e);
		}
		
		logger.info("Matches found: "+list.size());
				
		return list;
	}

	@Override
	public SchemeSettleRecon get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(SchemeSettleRecon t) {
		logger.debug("Updating trx_id for {}",t);
		
		final String SQL = "UPDATE schemesettlement " + 
				"SET trx_id = ? " + 
				"WHERE id = ?";
		
		int rowsUpdated = 0;
		
		if (appProperties.isProductionMode()) {
			try {
				rowsUpdated = jdbcTemplate.update(SQL, new Object[] {t.getAcquirerId(), t.getSchemeSettlementId()});
			} catch(Exception e) {
				logger.error(e);
			}
		}
		
		logger.info("Rows updated: "+rowsUpdated);		
		
		if (rowsUpdated > 0 || !appProperties.isProductionMode()) {
			log(t);
		}
		
		return rowsUpdated;
		
	}

	@Override
	public long insertAll(List<SchemeSettleRecon> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long insert(SchemeSettleRecon t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void log(SchemeSettleRecon t) {
		logger.info("Logging transaction...");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(new Date());
		try {
			jdbcTemplate.update(LOG_SQL, new Object[] {"schemesettlement", t.getSchemeSettlementId(), "trx_id", t.getSchemeSettlementTrxId(), t.getAcquirerId(), d, ReconUtil.getTransId(), t.getSettlementFilename()});
		} catch(Exception e) {
			logger.error(e);
		}
	}
	
}
