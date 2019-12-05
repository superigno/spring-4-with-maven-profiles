package com.pc.dao;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pc.mapper.AcquirerSettlementMapper;
import com.pc.model.AcquirerRecon;

/**
 * @author gino.q
 *
 */

@Repository
@Qualifier("acquirerReconDao")
public class AcquirerReconDaoImpl implements ReconDao<AcquirerRecon> {

	private static final Logger logger = LogManager.getLogger(AcquirerReconDaoImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<AcquirerRecon> getList(String[] merchantIds, String startDate, String endDate) {
		
		logger.info("Looking for match...");
		logger.debug("Merchant IDs: "+Arrays.toString(merchantIds));
		logger.debug("Start Date: "+startDate);
		logger.debug("End Date: "+endDate);
		
		final String SQL = "SELECT a.id acquirer_id, "
				+ "a.card_number acquirer_card_number, "
				+ "b.card_number settlement_card_number, "
				+ "a.card_currency acquirer_card_currency, "
				+ "IF(b.card_currency IS NULL or b.card_currency = '', 'AUD', b.card_currency) settlement_card_currency, "
				+ " b.merchant_id, "  
				+ "	b.terminal_id, "  
				+ "	b.base_amount, " 
				+ "	b.rrn, " 
				+ " b.trx_id "
				+ " FROM acquirertransaction a, settlementfile b "
			    + " WHERE a.merchant_id = b.merchant_id "
			    + " AND a.terminal_id = b.terminal_id "
			    + " AND a.base_amount = b.base_amount "
			    + " AND a.authorisation_code = b.authorisation_code "
			    + " AND DATE(a.client_time) = DATE(b.terminal_transaction_time) "
			    + " AND a.merchant_id IN (:merchant_id) "
			    + " AND a.settle_time BETWEEN :start_date AND :end_date";
		
		List<String> ids = Arrays.asList(merchantIds);		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("merchant_id", ids);
		params.addValue("start_date", startDate);
		params.addValue("end_date", endDate);
		
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());		
		List<AcquirerRecon> list = template.query(SQL, params, new AcquirerSettlementMapper());
		
		logger.info("Matches found: "+list.size());
				
		return list;
	}

	@Override
	public AcquirerRecon get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(AcquirerRecon t) {
		
		logger.debug("Updating card_number and card_currency for id='{}'",t.getAcquirerId());
		logger.debug("Card number from '{}' to '{}'", t.getAcquirerCardNumber(), t.getSettlementCardNumber());
		logger.debug("Card currency from '{}' to '{}'", t.getAcquirerCardCurrency(), t.getSettlementCardCurrency());
		
		final String SQL = "UPDATE acquirertransaction SET card_number = ?, card_currency = ? WHERE id = ?";
		int rowsUpdated = 0;
		
		try {
			rowsUpdated = jdbcTemplate.update(SQL, new Object[] {t.getSettlementCardNumber(), t.getSettlementCardCurrency(), t.getAcquirerId()});
		} catch (Exception e) {
			logger.error(e);
		}
		
		logger.info("Row updated: "+rowsUpdated);
		
		if (rowsUpdated > 0) {
			log(t);
		}
		
		return rowsUpdated;
		
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
	public long delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void log(AcquirerRecon t) {
		logger.info("Logging transaction...");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(new Date());
		try {
			jdbcTemplate.update(LOG_SQL, new Object[] {"acquirertransaction", t.getAcquirerId(), "card_number", t.getAcquirerCardNumber(), t.getSettlementCardNumber(), d});
			jdbcTemplate.update(LOG_SQL, new Object[] {"acquirertransaction", t.getAcquirerId(), "card_currency", t.getAcquirerCardCurrency(), t.getSettlementCardCurrency(), d});
		} catch (Exception e) {
			logger.error(e);
		}
				
	}	

}
