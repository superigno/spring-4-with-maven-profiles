package com.pc.main;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
		//ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		//scheduler.scheduleWithFixedDelay(new TyroRecon(), 0, props.getScanPeriodInMinutes(), TimeUnit.MINUTES);
		new TyroRecon().run(); //run one time
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
			
//			String appDirectory = props.getAppDirectory();
//			String filesDir = appDirectory+"/files";
//			
//			Path dir = Paths.get(filesDir);		
//			File[] files = dir.toFile().listFiles();
//			
//			logger.info("Scanning directory {} for settlement files...", filesDir);
//			logger.info("Files found: "+files.length);
//			
//			if (files.length > 0) {			
//				reconService.cleanupSettlementTable();
//				for (File file : files) {					
//					logger.info("Filename: "+file.getName());					
//			    	reconService.insertSettlementFileToDb(file);			    	
//			    	file.delete();
//			    }				
//			}
				
			logger.info("**************************************** START ****************************************");
			
			long startTime = System.currentTimeMillis(); // Get the start Time
	        long endTime = 0;
	        
	        logger.info("Start time: {}", startTime);
			
	        ReconUtil.generateNewTransId();
			logger.info("Generated transaction ID: {}", ReconUtil.getTransId());
			
			//String sqlDir = props.getAppDirectory()+"/sql";
			//ReconUtil.cleanupDirectory(sqlDir);
			
			logger.info("-------------------------------------------------------------------------------------");
			
			List<AcquirerRecon> list = reconService.getAcquirerSettlementMappingList(props.getMerchantIds(), props.getSettlementStartDate(), props.getSettlementEndDate());
			int acquirerRowsUpdated = 0;
			int schemeSettleRowsUpdated = 0;
			int pendingCommissionDeleted = 0;
			int missingCommissionDeleted = 0;
			
			logger.info("Processing...");
			
			for (AcquirerRecon ar : list) {		
				
				boolean acquirerLocalSettlementDcc = !props.getBaseCurrency().equals(ar.getSettlementTrxCurrency().trim()) && props.getBaseCurrency().equals(ar.getAcquirerTrxCurrency().trim()); 
				boolean isDifferent = !ar.getAcquirerCardNumber().equals(ar.getSettlementCardNumber()) || !ar.getAcquirerCardCurrency().equals(ar.getSettlementCardCurrency());
				
				if (acquirerLocalSettlementDcc) {
					acquirerRowsUpdated += reconService.updateAcquirerDetails2(ar);
					pendingCommissionDeleted += reconService.deleteFromExtraPendingCommission(ar.getAcquirerId());					
				} else if (isDifferent) {
					acquirerRowsUpdated += reconService.updateAcquirerDetails(ar);
					pendingCommissionDeleted += reconService.deleteFromExtraPendingCommission(ar.getAcquirerId());
				} else {
					logger.trace("Values equal, skipped.");
				}
				
				/** Look for match in schemesettlement table (for opt-in transactions only) **/
				if (!props.getBaseCurrency().equals(ar.getSettlementTrxCurrency().trim())) {
				
					List<SchemeSettleRecon> schemeList = reconService.getSchemeSettlementMappingList(ar.getMerchantId(), ar.getTerminalId(), ar.getBaseAmount(), ar.getRrn(), ar.getTrxId(), 
							props.getSettlementStartDate(), props.getSettlementEndDate());
					
					for (SchemeSettleRecon s : schemeList) {
						s.setAcquirerId(ar.getAcquirerId());
						s.setSettlementFilename(ar.getSettlementFilename());
						schemeSettleRowsUpdated += reconService.updateSchemeSettlementDetails(s);
						missingCommissionDeleted += reconService.deleteFromExtraMissingCommission(s.getSchemeSettlementId());
					}
					
				}
				
				logger.trace("-------------------------------------------------------------------------------------");					
			}	
			
			logger.info("Total Acquirer rows updated: "+acquirerRowsUpdated);
			logger.info("Total Scheme Settlement rows updated: "+schemeSettleRowsUpdated);
			logger.info("Total Pending Commissions deleted: "+pendingCommissionDeleted);
			logger.info("Total Missing Commissions deleted: "+missingCommissionDeleted);
			
			endTime = System.currentTimeMillis(); //Get the end Time

		    logger.info("End time: {}", endTime);
		    logger.info("Total duration: {}mins", (endTime-startTime)/1000/60);
							
			logger.info("**************************************** END ****************************************");
			
		}
	
	}
	

}
