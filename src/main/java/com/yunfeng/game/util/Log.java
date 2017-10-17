package com.yunfeng.game.util;

import java.util.logging.Logger;

public class Log {

	private static final Logger loggerDebug = Logger.getLogger("logDebug");
	private static final Logger loggerErr = Logger.getLogger("logError");

	public static void d(Object msg) {
		// System.out.println(msg);
		loggerDebug.info(msg.toString());
	}

	public static void e(Object msg) {
		loggerErr.info(msg.toString());
	}
}
