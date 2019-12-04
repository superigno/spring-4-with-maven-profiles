package com.pc.dao;

import java.util.Arrays;
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
import com.pc.model.IdCardNumberPair;

/**
 * @author gino.q
 *
 */
@Repository
@Qualifier("acquirerDao")
public class AcquirerDaoImpl implements AcquirerDao {
	
	private static final Logger logger = LogManager.getLogger(AcquirerDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<IdCardNumberPair> getIdCardMappingList(String[] merchantIds, String startDate, String endDate) {
		logger.info("Looking for match...");
		logger.debug("Merchant IDs: "+Arrays.toString(merchantIds));
		logger.debug("Start Date: "+startDate);
		logger.debug("End Date: "+endDate);
		
		final String SQL = "SELECT a.id, a.card_number old_card_number, b.card_number, a.card_currency old_card_currency, IF(b.card_currency IS NULL or b.card_currency = '', 'AUD', b.card_currency) card_currency"
				+ " FROM acquirertransaction a, settlement_info b "+
			    " WHERE a.merchant_id = b.merchant_id "+
				" AND a.terminal_id = b.terminal_id "+
				" AND a.base_amount = b.base_amount "+
				" AND a.authorisation_code = b.authorisation_code "+
				" AND a.client_time = b.terminal_transaction_time "+
				" AND a.merchant_id IN (:merchant_id) "+
				" AND a.settle_time BETWEEN :start_date AND :end_date";
		
		List<String> ids = Arrays.asList(merchantIds);		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("merchant_id", ids);
		params.addValue("start_date", startDate);
		params.addValue("end_date", endDate);
		
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());		
		List<IdCardNumberPair> list = template.query(SQL, params, new AcquirerSettlementMapper());
		
		logger.info("Matches found: "+list.size());
				
		return list;
	}

	@Override
	public int updateAcquirerCardNumber(IdCardNumberPair p) {	
		logger.debug("Updating card_number and card_currency for id='{}'", p.getId());
		logger.debug("Card number from '{}' to '{}'", p.getOldCardNumber(), p.getCardNumber());
		logger.debug("Card currency from '{}' to '{}'", p.getOldCardCurrency(), p.getCardCurrency());
		final String SQL = "UPDATE acquirertransaction SET card_number = ?, card_currency = ? WHERE id = ?";
		int rowsUpdated = jdbcTemplate.update(SQL, new Object[] {p.getCardNumber(), p.getCardCurrency(), p.getId()});
		logger.info("Row updated: "+rowsUpdated);
		return rowsUpdated;
	}

}
