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
public class SchemeSettleReconDaoImpl implements ReconDao<SchemeSettleRecon,String[]> {
	
	private static final Logger logger = LogManager.getLogger(SchemeSettleReconDaoImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	AppProperties appProperties;

	@Override
	public List<SchemeSettleRecon> getList(String[] p) {
		
		String merchantId = p[0];
		String terminalId = p[1];
		String baseAmount = p[2];
		String rrn = p[3];
		//String trxId = p[4];
		String startDate = p[5];
		String endDate = p[6];
		
		logger.trace("Looking for match in schemesettlement table...");
		logger.trace("Merchant ID: {}", merchantId);
		logger.trace("Terminal ID: {}", terminalId);
		logger.trace("Base Amount: {}", baseAmount);
		logger.trace("Non DCC Reason Code: {}", rrn);
		//logger.trace("Notes: {}", trxId);
		logger.trace("Payment Start Date: "+startDate);
		logger.trace("Payment End Date: "+endDate);
		
		final String SELECT_SQL = "SELECT id, trx_id, merchant_id, terminal_id, base_amount, nondcc_reason_code, notes " + 
				"FROM schemesettlement " + 
				"WHERE trx_id = 0 "+
				"AND merchant_id = ? " + 
				"AND terminal_id = ? " + 
				"AND base_amount = ? " + 
				"AND nondcc_reason_code = ? " + 
				//"AND notes = ? " +
				"AND payment_date BETWEEN ? AND ?";
		
		List<SchemeSettleRecon> list = new ArrayList<>();
		
		try {
			list = jdbcTemplate.query(SELECT_SQL, new Object[] {merchantId, terminalId, baseAmount, rrn, startDate, endDate}, new SchemeSettlementMapper());
		}catch (Exception e) {
			logger.error(e);
		}
		
		logger.trace("Matches found: "+list.size());
				
		return list;
	}

	@Override
	public SchemeSettleRecon get(String[] p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(SchemeSettleRecon t) {
		
		logger.trace("Updating trx_id for {}",t);
		
		final String SQL = "UPDATE schemesettlement " + 
				"SET trx_id = ? " + 
				"WHERE id = ?;";
		
		int rowsUpdated = 0;
		
		Object[] params = new Object[] {t.getAcquirerId(), t.getSchemeSettlementId()};
		
		if (appProperties.isProductionMode()) {
			try {
				rowsUpdated = jdbcTemplate.update(SQL, params);
			} catch(Exception e) {
				logger.error(e);
			}
		}
		
		logger.trace("Rows updated: "+rowsUpdated);		
		
		if (rowsUpdated > 0 || !appProperties.isProductionMode()) {			
			//log(t);			
			writeSqlToFile(SQL, params);
		}
		
		return rowsUpdated;
		
	}
	
	private void writeSqlToFile(String sql, Object[] params) {
		logger.trace("Writing SQL to file...");
		
	    try {
	    	String dirPath = appProperties.getAppDirectory()+"/sql";
	    	String filename = "schemesettlement_"+ReconUtil.getTransId()+".sql";
	    	String formattedSql = String.format(sql.replace("?", "%s"), params[0], params[1]);
	    	logger.trace("Filename: {}", filename);
	    	logger.trace("SQL: {}", formattedSql);
			ReconUtil.appendToFile(dirPath, filename, formattedSql);
		} catch (Exception e) {
			logger.error("Error in writing SQL statement to file", e);
		}	    
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
	public long delete(String[] p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void log(SchemeSettleRecon t) {
		logger.trace("Logging transaction...");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(new Date());
		try {
			jdbcTemplate.update(LOG_SQL, new Object[] {"schemesettlement", t.getSchemeSettlementId(), "trx_id", t.getSchemeSettlementTrxId(), t.getAcquirerId(), d, ReconUtil.getTransId(), t.getSettlementFilename()});
		} catch(Exception e) {
			logger.error(e);
		}
	}
	
}
