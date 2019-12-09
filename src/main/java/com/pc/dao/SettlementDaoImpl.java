package com.pc.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.pc.model.SettlementInfo;

/**
 * @author gino.q
 *
 */
@Repository
public class SettlementDaoImpl implements ReconDao<SettlementInfo,String[]> {

	private static final Logger logger = LogManager.getLogger(SettlementDaoImpl.class);
	private static final String INSERT_SQL = "INSERT INTO settlementfile (merchant_id, terminal_id, merchant_ref, terminal_transaction_time, acquirer_time, trx_type, base_currency, base_amount, trx_currency, trx_amount, exchange_rate, response_code, response_message, authorisation_code, RRN, card_number, card_currency, card_type, gross_margin, net_bank_commi, net_pc_commi, net_merchant_commi, scheme_settle_rate, scheme_settle_amount, settle_time, repayment_fee, originator, non_dcc_reason_code, card_number_length, rate_program, trx_id, file_name) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final int BATCH_INSERT_LIMIT = 1000;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public long insert(SettlementInfo info) {
		
		Object[] params = new Object[] { info.getMerchantId(), info.getTerminalId(), info.getMerchantRef(), info.getTerminalTransactionTime(), info.getAcquirerTime(), info.getTrxType(),
				info.getBaseCurrency(), info.getBaseAmount(), info.getTrxCurrency(), info.getTrxAmount(), info.getExchangeRate(), info.getResponseCode(), info.getResponseMessage(), 
				info.getAuthorisationCode(), info.getRrn(), info.getCardNumber(), info.getCardCurrency(), info.getCardType(), info.getGrossMargin(), info.getNetBankCommi(), info.getNetPcCommi(), 
				info.getNetMerchantCommi(), info.getSchemeSettlementRate(), info.getSchemeSettlementAmount(), info.getSettleTime(), info.getRepaymentFee(), info.getOriginator(), info.getNonDccReasonCode(), 
				info.getCardNumberLength(), info.getRateProgram(), info.getTrxId(), info.getFilename()};
		
		long rowsInserted = 0;
		try {
			rowsInserted = jdbcTemplate.update(INSERT_SQL, params);
		} catch(Exception e) {
			logger.error(e);
		}
		logger.info("Rows inserted: " + rowsInserted);
		return rowsInserted;
	}

	@Override
	public long insertAll(List<SettlementInfo> list) {
		logger.info("Inserting to database...");
		
		int[][] updateCounts = null;
		
		try {
			updateCounts = jdbcTemplate.batchUpdate(INSERT_SQL, list, BATCH_INSERT_LIMIT,
					new ParameterizedPreparedStatementSetter<SettlementInfo>() {
						public void setValues(PreparedStatement ps, SettlementInfo info) throws SQLException {
							ps.setString(1, info.getMerchantId());
							ps.setString(2, info.getTerminalId()); 
							ps.setString(3, info.getMerchantRef()); 
							ps.setString(4, info.getTerminalTransactionTime());
							ps.setString(5, info.getAcquirerTime());
							ps.setString(6, info.getTrxType());
							ps.setString(7, info.getBaseCurrency());
							ps.setLong(8, info.getBaseAmount());
							ps.setString(9, info.getTrxCurrency()); 
							ps.setLong(10, info.getTrxAmount());
							ps.setString(11, info.getExchangeRate()); 
							ps.setString(12, info.getResponseCode());
							ps.setString(13, info.getResponseMessage());
							ps.setString(14, info.getAuthorisationCode());
							ps.setString(15, info.getRrn());
							ps.setString(16, info.getCardNumber());
							ps.setString(17, info.getCardCurrency()); 
							ps.setString(18, info.getCardType());
							ps.setString(19, info.getGrossMargin()); 
							ps.setString(20, info.getNetBankCommi()); 
							ps.setString(21, info.getNetPcCommi());
							ps.setString(22, info.getNetMerchantCommi());
							ps.setString(23, info.getSchemeSettlementRate());
							ps.setLong(24, info.getSchemeSettlementAmount());
							ps.setString(25, info.getSettleTime());
							ps.setString(26, info.getRepaymentFee());
							ps.setString(27, info.getOriginator());
							ps.setString(28, info.getNonDccReasonCode());
							ps.setLong(29, info.getCardNumberLength()); 
							ps.setString(30, info.getRateProgram());
							ps.setString(31, info.getTrxId());
							ps.setString(32, info.getFilename());
						}
					});
		} catch (Exception e) {
			logger.error(e);
		}
		
		
		int batchCtr = 1;
		long totalRowsInserted = 0;
		for (int[] rows : updateCounts ) {
			int insertedCtr = 0;			
			logger.trace("Batch: "+batchCtr);
			for (int i : rows) {
				if (i==1) {
					insertedCtr++;
					totalRowsInserted++;
				}
			}
			logger.info("Rows inserted: " + insertedCtr);
			batchCtr++;
		}		
		
		logger.info("Total rows inserted: "+totalRowsInserted);
		return totalRowsInserted;
	}

	@Override
	public long deleteAll() {
		logger.info("Cleaning up table...");
		final String SQL = "DELETE FROM settlementfile;";
		long rowsDeleted = 0;
		try {
			rowsDeleted = jdbcTemplate.update(SQL);
		} catch (Exception e) {
			logger.error(e);
		}						
		logger.info("Total rows deleted: "+rowsDeleted);
		return rowsDeleted;
	}
	
	@Override
	public List<SettlementInfo> getList(String[] p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SettlementInfo get(String[] p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(SettlementInfo t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long delete(String[] p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void log(SettlementInfo t) {
		// TODO Auto-generated method stub
		
	}	

}
