package com.pc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pc.model.SettlementInfo;

/**
 * @author gino.q
 *
 */
public class ReconUtil {

	private static final Logger logger = LogManager.getLogger(ReconUtil.class);
	private static final String REGEX_PIPE_TRIMMED = "\\s*\\|\\s*";
	private static final int MANDATORY_PARAM_COUNT = 15;
	private static String transId;
	
	public static List<SettlementInfo> getSettlementList(File f) {
		FileReader fr = null;
		BufferedReader br = null;
		String s = "";
		List<SettlementInfo> settlementList = new ArrayList<>();

		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			while ((s = br.readLine()) != null) {
				String[] items = doParse(s);
				if (items.length >= MANDATORY_PARAM_COUNT) {					
					settlementList.add(new SettlementInfo(items, f.getName()));
				}				
			}			
		} catch (Exception e) {
			logger.error("Error in reading file: ", e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("Error in closing reader: ", e);
			}
		}
		
		return settlementList;
	}
	
	private static String[] doParse(String line) {
		String[] items = line.split(REGEX_PIPE_TRIMMED);
		return items;
	}
	
	public static String getTransId() {
		if (transId == null || transId.equals("")) {
			generateNewTransId();
		}
		return transId;
	}
	
	public static void generateNewTransId() {
		transId = String.format("%06d", new Random().nextInt(999999));
	}

}
