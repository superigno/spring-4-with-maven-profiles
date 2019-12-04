package com.pc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pc.model.IdCardNumberPair;

/**
 * @author gino.q
 *
 */
public class AcquirerSettlementMapper implements RowMapper<IdCardNumberPair> {

	@Override
	public IdCardNumberPair mapRow(ResultSet rs, int rowNum) throws SQLException {
		IdCardNumberPair pair = new IdCardNumberPair();
		pair.setId(rs.getString("id"));
		pair.setOldCardNumber(rs.getString("old_card_number"));
		pair.setCardNumber(rs.getString("card_number"));
		pair.setOldCardCurrency(rs.getString("old_card_currency"));
		pair.setCardCurrency(rs.getString("card_currency"));
		return pair;
	}
	
}
