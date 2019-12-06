package com.pc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pc.model.SchemeSettleRecon;

/**
 * @author gino.q
 *
 */
public class SchemeSettlementMapper implements RowMapper<SchemeSettleRecon> {

	@Override
	public SchemeSettleRecon mapRow(ResultSet rs, int rowNum) throws SQLException {
		SchemeSettleRecon recon = new SchemeSettleRecon();
		recon.setSchemeSettlementId(rs.getLong("id"));
		recon.setSchemeSettlementTrxId(rs.getString("trx_id"));
		recon.setSettlementMerchantId(rs.getString("merchant_id"));
		recon.setSettlementTerminalId(rs.getString("terminal_id"));
		recon.setSettlementBaseAmount(rs.getString("base_amount"));
		recon.setSettlementRrn(rs.getString("nondcc_reason_code"));
		recon.setSettlementTrxId(rs.getString("notes")); 
		return recon;
	}
	
}
