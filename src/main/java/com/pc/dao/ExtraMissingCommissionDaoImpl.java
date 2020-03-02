package com.pc.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pc.model.AppProperties;
import com.pc.util.ReconUtil;

/**
 * @author gino.q
 *
 */
@Repository("extraMissingCommissionDao")
public class ExtraMissingCommissionDaoImpl implements ReconDao<Object,String[]> {

	private static final Logger logger = LogManager.getLogger(AcquirerReconDaoImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	AppProperties appProperties;
	
	@Override
	public List<Object> getList(String[] p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String[] p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(Object t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long insertAll(List<Object> list) {
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
	public long delete(String[] p) {
		logger.trace("Deleting record from extramissingcommission table...");
		final String SQL = "DELETE FROM extramissingcommission WHERE id = ?;";
		long rowsDeleted = 0;
		
		if (appProperties.isProductionMode()) {
			try {
				rowsDeleted = jdbcTemplate.update(SQL, p[0]);
			} catch (Exception e) {
				logger.error(e);
			}
		} else {
			writeSqlToFile(SQL, p);
		}
						
		logger.trace("Total rows deleted: "+rowsDeleted);
		return rowsDeleted;
	}
	
	private void writeSqlToFile(String sql, Object[] params) {		
		logger.trace("Writing SQL to file...");
		
	    try {
	    	String dirPath = appProperties.getAppDirectory()+"/sql";
	    	String filename = "extramissingcommission_"+ReconUtil.getTransId()+".sql";
	    	String formattedSql = String.format(sql.replace("?", "%s"), params[0]);	    	
	    	logger.trace("Filename: {}", filename);
			logger.trace("SQL: {}", formattedSql);	    	
			ReconUtil.appendToFile(dirPath, filename, formattedSql);
		} catch (Exception e) {
			logger.error("Error in writing SQL statement to file", e);
		}	    
	}

	@Override
	public void log(Object t) {
		// TODO Auto-generated method stub
		
	}

}
