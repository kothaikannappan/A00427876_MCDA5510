package com.mcda5510.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SimpleLogger {
	public static Logger logger = Logger.getAnonymousLogger();
	private static FileHandler fh;

	public SimpleLogger() {
		// Create log file path
		File file = new File("/Users/mcda/Desktop/MCDA5510_Assignments-master/A00427876_MCDA5510/logfile.log");
		file.getParentFile().mkdirs();

		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler("logfile.log", true);
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}