package com.joegaudet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

	private static Logger log = LoggerFactory.getLogger("RemoteCompute");
	static {
	}
	public static void trace(String msg) {
		log.trace(msg);
	}

	public static void debug(String msg) {
		log.debug(msg);
	}

	public static void info(String msg) {
		log.info(msg);
	}

	public static void warn(String msg) {
		log.warn(msg);
	}

	public static void error(String msg) {
		log.error(msg);
	}

	public static void error(Exception e) {
		log.error("Exception: ", e);
	}

}
