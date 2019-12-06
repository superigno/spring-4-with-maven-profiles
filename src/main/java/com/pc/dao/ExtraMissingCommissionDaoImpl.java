package com.pc.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pc.model.AppProperties;

/**
 * @author gino.q
 *
 */
@Repository("extraMissingCommissionDao")
@SuppressWarnings("rawtypes")
public class ExtraMissingCommissionDaoImpl implements ReconDao {

	private static final Logger logger = LogManager.getLogger(AcquirerReconDaoImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	AppProperties appProperties;
	
	@Override
	public List getList(Object t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(Object t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long insertAll(List list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long insert(Object t) {
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
		logger.info("Deleting record from extramissingcommission table...");
		final String SQL = "DELETE FROM extramissingcommission WHERE id = ?";
		long rowsDeleted = 0;
		
		if (appProperties.isProductionMode()) {
			try {
				rowsDeleted = jdbcTemplate.update(SQL, id);
			} catch (Exception e) {
				logger.error(e);
			}
		}
						
		logger.info("Total rows deleted: "+rowsDeleted);
		return rowsDeleted;
	}

	@Override
	public void log(Object t) {
		// TODO Auto-generated method stub
		
	}

}
