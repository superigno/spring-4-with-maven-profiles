package com.pc.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pc.mapper.AcquirerSettlementMapper;
import com.pc.model.AcquirerRecon;
import com.pc.model.AppProperties;
import com.pc.util.ReconUtil;

/**
 * @author gino.q
 *
 */

@Repository
public class AcquirerReconDaoImpl implements ReconDao<AcquirerRecon, Object[]> {

	private static final Logger logger = LogManager.getLogger(AcquirerReconDaoImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	AppProperties appProperties;
	
	@Override
	public List<AcquirerRecon> getList(Object[] p) {
		
		String[] merchantIds = (String[]) p[0];
		String startDate = p[1].toString();
		String endDate = p[2].toString();
		boolean isMerchantIdEmpty = merchantIds == null || merchantIds.length == 0;
		
		logger.info("Looking for match in acquirertransaction table...");
		logger.debug("Merchant IDs: "+Arrays.toString(merchantIds));
		logger.debug("Settle Start Date: "+startDate);
		logger.debug("Settle End Date: "+endDate);
		
		/*
		String sql = "SELECT DISTINCT a.id acquirer_id, "
				+ "a.card_number acquirer_card_number, "
				+ "b.card_number settlement_card_number, "
				+ "a.card_currency acquirer_card_currency, "
				+ "IF(b.card_currency IS NULL or b.card_currency = '', 'AUD', b.card_currency) settlement_card_currency, "
				+ " b.merchant_id, "  
				+ "	b.terminal_id, "  
				+ "	b.base_amount, " 
				+ "	b.rrn, " 
				+ " b.trx_id, "
				+ " b.file_name, "
				+ " a.trx_currency acquirer_trx_currency,  "
				+ " IF(b.trx_currency IS NULL or b.trx_currency = '', 'AUD', b.trx_currency) settlement_trx_currency,  "
				+ " a.trx_amount acquirer_trx_amount, "
				+ " b.trx_amount settlement_trx_amount, "
				+ " a.exchange_rate acquirer_exchange_rate, "
				+ " b.exchange_rate settlement_exchange_rate, "
				+ " a.authorisation_code acquirer_authorisation_code, "
				+ " b.authorisation_code settlement_authorisation_code "
				+ " FROM acquirertransaction a, settlementfile b "
			    + " WHERE a.merchant_id = b.merchant_id "
			    + " AND a.terminal_id = b.terminal_id "
			    + " AND a.base_amount = b.base_amount "
			    + " AND a.authorisation_code = b.authorisation_code "
			    + " AND DATE(a.client_time) = DATE(b.terminal_transaction_time) "
			    + " AND DATE(a.settle_time) BETWEEN :start_date AND :end_date "
			    + " AND RIGHT(a.card_number,4) = RIGHT(b.card_number,4) ";
		*/
		
		String sql = "SELECT DISTINCT acquirer_id, " + 
				"			 acquirer_card_number, " + 
				"			 settlement_card_number, " + 
				"			 acquirer_card_currency, " + 
				"		     settlement_card_currency,   " +
				"		     merchant_id,   " +
				"			 terminal_id,  " + 
				"			 base_amount,  " + 
				"			 settlement_rrn rrn, " + 
				"			 settlement_trx_id trx_id, " + 
				"			 settlement_file_name file_name, " +
				"			 acquirer_trx_currency, " +
				//"			 IF(settlement_trx_currency IS NULL or settlement_trx_currency = '', 'AUD', settlement_trx_currency) settlement_trx_currency, " + //SLOW
				"            settlement_trx_currency, " +
				"            acquirer_trx_amount, " +
				"            settlement_trx_amount, " +
				"            acquirer_exchange_rate, " +
				"            settlement_exchange_rate, " +
				"            acquirer_authorisation_code, " +
				"            settlement_authorisation_code " +
				"       FROM settlementfile_acquirertransaction " +
				"      WHERE DATE(client_time) = DATE(terminal_transaction_time) " +
				"        AND settlement_authorisation_code = acquirer_authorisation_code";
		
		List<String> ids = Arrays.asList(merchantIds);		
		MapSqlParameterSource params = new MapSqlParameterSource();
		if (!isMerchantIdEmpty) {
			sql = sql+" AND a.merchant_id IN (:merchant_id)";
			params.addValue("merchant_id", ids);
		}
		params.addValue("start_date", startDate);
		params.addValue("end_date", endDate);
		
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());		
		List<AcquirerRecon> list = new ArrayList<>();
		
		try {
			list = template.query(sql, new AcquirerSettlementMapper());
		}catch (Exception e) {
			logger.error(e);
		}
		
		logger.info("Matches found: "+list.size());
				
		return list;
	}

	@Override
	public AcquirerRecon get(Object[] p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(AcquirerRecon t) {
		
		logger.trace("Updating card_number and card_currency for id='{}'", t.getAcquirerId());
		logger.trace("Card number from '{}' to '{}'", t.getAcquirerCardNumber(), t.getSettlementCardNumber());
		logger.trace("Card currency from '{}' to '{}'", t.getAcquirerCardCurrency(), t.getSettlementCardCurrency());
		
		int rowsUpdated = 0;	
		final String SQL = "UPDATE acquirertransaction SET card_number = ?, card_currency = ? WHERE id = ? AND card_number = ? AND card_currency = ?;";
		
		Object[] params = new Object[] {t.getSettlementCardNumber(), t.getSettlementCardCurrency(), t.getAcquirerId(), t.getAcquirerCardNumber(), t.getAcquirerCardCurrency()};
		
		if (appProperties.isProductionMode()) {
			try {
				rowsUpdated = jdbcTemplate.update(SQL, params);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		
		logger.trace("Row updated: "+rowsUpdated);
		
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
	    	String filename = "acquirertransaction_"+ReconUtil.getTransId()+".sql";
	    	String formattedSql = String.format(sql.replace("?", "%s"), "'"+params[0]+"'", "'"+params[1]+"'", params[2], "'"+params[3]+"'", "'"+params[4]+"'");	    	
	    	logger.trace("Filename: {}", filename);
			logger.trace("SQL: {}", formattedSql);	    	
			ReconUtil.appendToFile(dirPath, filename, formattedSql);
		} catch (Exception e) {
			logger.error("Error in writing SQL statement to file", e);
		}	    
	}

	
	
	public long update2(AcquirerRecon t) {
		
		logger.trace("Updating details for id='{}'", t.getAcquirerId());
		logger.trace("Card number from '{}' to '{}'", t.getAcquirerCardNumber(), t.getSettlementCardNumber());
	    logger.trace("Card currency from '{}' to '{}'", t.getAcquirerCardCurrency(), t.getSettlementCardCurrency());
	    logger.trace("Trx currency from '{}' to '{}'", t.getAcquirerTrxCurrency(), t.getSettlementTrxCurrency());
	    logger.trace("Trx amount from '{}' to '{}'", t.getAcquirerTrxAmount(), t.getSettlementTrxAmount());
	    logger.trace("Exchange rate from '{}' to '{}'", t.getAcquirerExchangeRate(), t.getSettlementExchangeRate());
	    logger.trace("Authorisation code from '{}' to '{}'", t.getAcquirerAuthorisationCode(), t.getSettlementAuthorisationCode());
	    
		int rowsUpdated = 0;	
		final String SQL = "UPDATE acquirertransaction SET card_number = ?, card_currency = ?, trx_currency = ?, trx_amount = ?, exchange_rate = ?, authorisation_code = ?"
				+ " WHERE id = ? AND card_number = ? AND card_currency = ? AND trx_currency = ? AND trx_amount = ? AND exchange_rate = ? AND authorisation_code = ?;";
		
		Object[] params = new Object[] { t.getSettlementCardNumber(), t.getSettlementCardCurrency(),
				t.getSettlementTrxCurrency(), t.getSettlementTrxAmount(), t.getSettlementExchangeRate(),
				t.getSettlementAuthorisationCode(), t.getAcquirerId(), t.getAcquirerCardNumber(),
				t.getAcquirerCardCurrency(), t.getAcquirerTrxCurrency(), t.getAcquirerTrxAmount(),
				t.getAcquirerExchangeRate(), t.getAcquirerAuthorisationCode() };
		
		if (appProperties.isProductionMode()) {
			try {
				rowsUpdated = jdbcTemplate.update(SQL, params);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		
		logger.trace("Row updated: "+rowsUpdated);
		
		if (rowsUpdated > 0 || !appProperties.isProductionMode()) {
			//log(t);
			writeSqlToFile2(SQL, params);
		}
		
		return rowsUpdated;
		
	}
	
	private void writeSqlToFile2(String sql, Object[] params) {		
		logger.trace("Writing SQL to file...");
		
	    try {
	    	String dirPath = appProperties.getAppDirectory()+"/sql";
	    	String filename = "acquirertransaction2_"+ReconUtil.getTransId()+".sql";
			String formattedSql = String.format(sql.replace("?", "%s"), "'" + params[0] + "'", "'" + params[1] + "'",
					"'" + params[2] + "'", 	params[3], params[4], "'" + params[5] + "'",
					params[6], "'" + params[7] + "'", "'" + params[8] + "'", "'" + params[9] + "'", params[10], params[11], "'" + params[12] + "'");	    	
	    	logger.trace("Filename: {}", filename);
			logger.trace("SQL: {}", formattedSql);	    	
			ReconUtil.appendToFile(dirPath, filename, formattedSql);
		} catch (Exception e) {
			logger.error("Error in writing SQL statement to file", e);
		}	    
	}

	@Override
	public long insertAll(List<AcquirerRecon> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long insert(AcquirerRecon t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long delete(Object[] p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void log(AcquirerRecon t) {
		logger.trace("Logging transaction...");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(new Date());
		try {
			jdbcTemplate.update(LOG_SQL, new Object[] {"acquirertransaction", t.getAcquirerId(), "card_number", t.getAcquirerCardNumber(), t.getSettlementCardNumber(), d, ReconUtil.getTransId(), t.getSettlementFilename()});
			jdbcTemplate.update(LOG_SQL, new Object[] {"acquirertransaction", t.getAcquirerId(), "card_currency", t.getAcquirerCardCurrency(), t.getSettlementCardCurrency(), d, ReconUtil.getTransId(), t.getSettlementFilename()});
		} catch (Exception e) {
			logger.error(e);
		}
				
	}	

}
