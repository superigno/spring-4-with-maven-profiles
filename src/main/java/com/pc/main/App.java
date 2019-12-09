package com.pc.main;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.pc.configuration.ApplicationConfig;
import com.pc.model.AcquirerRecon;
import com.pc.model.AppProperties;
import com.pc.model.SchemeSettleRecon;
import com.pc.service.ReconService;
import com.pc.util.ReconUtil;

/**
 * @author gino.q
 *
 */
public class App {
	
	private static final Logger logger = LogManager.getLogger(App.class);
	private static AppProperties props;
	private static ReconService reconService;
	
	public static void main(String[] args) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleWithFixedDelay(new TyroRecon(), 0, props.getScanPeriodInMinutes(), TimeUnit.MINUTES);		
	}
	
	static {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		reconService = (ReconService) context.getBean("reconService");
		props = (AppProperties) context.getBean("appProperties");
		context.close();		
	}
	
	private static class TyroRecon implements Runnable {
		
		@Override
		public void run() {
			
			String appDirectory = props.getAppDirectory();
			String filesDir = appDirectory+"\\files";
			
			Path dir = Paths.get(filesDir);		
			File[] files = dir.toFile().listFiles();
			
			logger.info("Scanning directory {} ...", filesDir);
			logger.info("Files found: "+files.length);
			
			if (files.length > 0) {
				
				logger.info("**************************************** START ****************************************");
				
				ReconUtil.generateNewTransId();
				logger.info("Generated transaction ID: {}", ReconUtil.getTransId());
				
				reconService.cleanupSettlementTable();
				
				for (File file : files) {					
					logger.info("Filename: "+file.getName());					
			    	reconService.insertSettlementFileToDb(file);			    	
			    	file.delete();
			    }
				
				logger.info("-------------------------------------------------------------------------------------");
				
				List<AcquirerRecon> list = reconService.getAcquirerSettlementMappingList(props.getMerchantIds(), props.getSettlementStartDate(), props.getSettlementEndDate());
				int acquirerRowsUpdated = 0;
				int schemeSettleRowsUpdated = 0;
				int pendingCommissionDeleted = 0;
				int missingCommissionDeleted = 0;
				
				for (AcquirerRecon ar : list) {		
					
					acquirerRowsUpdated += reconService.updateAcquirerDetails(ar);
					
					List<SchemeSettleRecon> schemeList = reconService.getSchemeSettlementMappingList(ar.getMerchantId(), ar.getTerminalId(), ar.getBaseAmount(), ar.getRrn(), ar.getTrxId(), 
							props.getSettlementStartDate(), props.getSettlementEndDate());
					
					for (SchemeSettleRecon s : schemeList) {
						s.setAcquirerId(ar.getAcquirerId());
						s.setSettlementFilename(ar.getSettlementFilename());
						schemeSettleRowsUpdated += reconService.updateSchemeSettlementDetails(s);
						missingCommissionDeleted += reconService.deleteFromExtraMissingCommission(s.getSchemeSettlementId());
					}
					
					pendingCommissionDeleted += reconService.deleteFromExtraPendingCommission(ar.getAcquirerId());						
					
					logger.info("-------------------------------------------------------------------------------------");					
				}	
				
				logger.info("Total Acquirer rows updated: "+acquirerRowsUpdated);
				logger.info("Total Scheme Settlement rows updated: "+schemeSettleRowsUpdated);
				logger.info("Total Pending Commissions deleted: "+pendingCommissionDeleted);
				logger.info("Total Missing Commissions deleted: "+missingCommissionDeleted);
								
				logger.info("**************************************** END ****************************************");
				logger.info("");
			}
		}
	
	}
	

}
