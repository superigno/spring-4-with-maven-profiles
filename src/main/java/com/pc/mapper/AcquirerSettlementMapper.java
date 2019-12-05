package com.pc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pc.model.AcquirerRecon;

/**
 * @author gino.q
 *
 */
public class AcquirerSettlementMapper implements RowMapper<AcquirerRecon> {

	@Override
	public AcquirerRecon mapRow(ResultSet rs, int rowNum) throws SQLException {
		AcquirerRecon recon = new AcquirerRecon();
		recon.setAcquirerId(rs.getLong("acquirer_id"));
		recon.setAcquirerCardNumber(rs.getString("acquirer_card_number"));
		recon.setSettlementCardNumber(rs.getString("settlement_card_number"));
		recon.setAcquirerCardCurrency(rs.getString("acquirer_card_currency"));
		recon.setSettlementCardCurrency(rs.getString("settlement_card_currency"));
		recon.setMerchantId(rs.getString("merchant_id"));
		recon.setTerminalId(rs.getString("terminal_id"));
		recon.setBaseAmount(rs.getString("base_amount"));
		recon.setRrn(rs.getString("rrn"));
		recon.setTrxId(rs.getString("trx_id"));
		return recon;
	}
	
}
