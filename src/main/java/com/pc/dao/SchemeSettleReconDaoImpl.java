package com.pc.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

	@Override
	public List<SchemeSettleRecon> getList(String[] merchantIds, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SchemeSettleRecon get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(SchemeSettleRecon t) {
		logger.debug("Updating trx_id for {}",t);
		
		final String SELECT_SQL = "SELECT CONCAT(id,',',trx_id) id_trxid " + 
				"FROM schemesettlement " + 
				"WHERE merchant_id = ? " + 
				"AND terminal_id = ? " + 
				"AND base_amount = ? " + 
				"AND nondcc_reason_code = ? " + 
				"AND notes = ?";
		
		String idTrxId = null;
		
		try {
		   idTrxId = jdbcTemplate.queryForObject(SELECT_SQL, 
				new Object[] {t.getSettlementMerchantId(), 
							  t.getSettlementTerminalId(), 
							  t.getSettlementBaseAmount(), 
							  t.getSettlementRrn(), 
							  t.getSettlementTrxId()}, String.class);
		} catch(EmptyResultDataAccessException  e) {
			logger.info("No record found");
			return 0;
		} catch(Exception e) {
			logger.error(e);
		}		
		
		String[] s = idTrxId.split(",");
		String id = s[0];
		String trxId = s[1];
		
		logger.info("Found ID: {} Trx ID: {}", id, trxId);
		
		t.setSchemeSettlementId(Long.parseLong(id));
		t.setSchemeSettlementTrxId(trxId);
		
		final String SQL = "UPDATE schemesettlement " + 
				"SET trx_id = ? " + 
				"WHERE id = ?";
		
		int rowsUpdated = 0;
		
		try {
			rowsUpdated = jdbcTemplate.update(SQL, new Object[] {t.getAcquirerId(), id});
		} catch(Exception e) {
			logger.error(e);
		}
		
		logger.info("Rows updated: "+rowsUpdated);		
		
		if (rowsUpdated > 0) {
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
