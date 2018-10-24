package com.yunfeng.game.util;

import org.apache.log4j.Logger;

public class Log {
    enum LogLevel {
        TRACE(1), DEBUG(2), INFO(3), WARN(4), ERROR(5);

        int level;

        LogLevel(int level) {
            this.level = level;
        }
    }

    private static LogLevel logLevel = LogLevel.ERROR;
    private static final Logger loggerDebug = Logger.getLogger("logDebug");
    private static final Logger loggerErr = Logger.getLogger("logError");

    public static void t(Object msg) {
        // System.out.println(msg);
        if (logLevel.level <= LogLevel.TRACE.level) {
            if (null == msg) {
                msg = "null";
            }
            loggerDebug.trace(msg.toString());
        }
    }

    public static void d(Object msg) {
        // System.out.println(msg);
        if (logLevel.level <= LogLevel.DEBUG.level) {
            if (null == msg) {
                msg = "null";
            }
            loggerDebug.debug(msg.toString());
        }
    }

    public static void i(Object msg) {
        // System.out.println(msg);
        if (logLevel.level <= LogLevel.INFO.level) {
            if (null == msg) {
                msg = "null";
            }
            loggerDebug.warn(msg.toString());
        }
    }

    public static void w(Object msg) {
        // System.out.println(msg);
        if (logLevel.level <= LogLevel.WARN.level) {
            if (null == msg) {
                msg = "null";
            }
            loggerDebug.debug(msg.toString());
        }
    }

    public static void e(Object msg) {
        if (logLevel.level <= LogLevel.ERROR.level) {
            if (null == msg) {
                msg = "null";
            }
            loggerErr.error(msg.toString());
        }
    }

    public static void e(Object msg, Throwable cause) {
        if (logLevel.level <= LogLevel.ERROR.level) {
            if (null == msg) {
                msg = "null";
            }
            loggerErr.error(msg.toString(), cause);
        }
    }
}
