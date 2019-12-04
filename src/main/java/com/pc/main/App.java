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
import com.pc.service.ReconService;

/**
 * @author gino.q
 *
 */
public class App {
	
	private static final Logger logger = LogManager.getLogger(App.class);
	private static AppProperties prop;
	private static ReconService reconService;
	
	public static void main(String[] args) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleWithFixedDelay(new TyroRecon(), 0, prop.getScanPeriodInMinutes(), TimeUnit.MINUTES);		
	}
	
	static {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		reconService = (ReconService) context.getBean("reconService");
		prop = (AppProperties) context.getBean("appProperties");
		context.close();		
	}
	
	private static class TyroRecon implements Runnable {
		
		@Override
		public void run() {
			
			String appDirectory = prop.getAppDirectory();
			String[] merchantIds = prop.getMerchantIds();
			String startDate = prop.getSettlementStartDate();
			String endDate = prop.getSettlementEndDate();
			String filesDir = appDirectory+"\\files";
			
			Path dir = Paths.get(filesDir);		
			File[] files = dir.toFile().listFiles();
			
			logger.info("Scanning directory {} ...", filesDir);
			logger.info("Files found: "+files.length);
			
			if (files.length > 0) {
				
				reconService.cleanupSettlementTable();
				
				for (File file : files) {
			    	logger.info("Filename: "+file.getName());	    	
			    	reconService.insertSettlementFileToDb(file);
			    	file.delete();
			    }
				
				List<AcquirerRecon> list = reconService.getIdCardMappingList(merchantIds, startDate, endDate);
				int rowsUpdated = 0;
				for (AcquirerRecon ar : list) {			
					rowsUpdated += reconService.updateAcquirerCardNumber(ar);
				}	
				logger.info("Total rows updated: "+rowsUpdated);
				logger.info("Done");
			}
		}
	
	}
	

}
