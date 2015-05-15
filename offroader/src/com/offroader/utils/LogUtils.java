package com.offroader.utils;

import java.text.SimpleDateFormat;

import android.text.TextUtils;
import android.util.Log;

/**
 * log工具类
 */
public class LogUtils {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//日志输出开关
	private static final boolean LOG_IS_ON = true;
	//日志输出级别
	private static final int LOG_LEVEL = 3;//DEBUG:3,INFO:4,ERROR:6

	/**
	 * 输出 DEBUG 级别日志
	 */
	public static void debug(String msg) {
		doLog(Log.DEBUG, msg, null);
	}

	/**
	 * 输出 INOF 级别日志
	 */
	public static void info(String msg) {
		doLog(Log.INFO, msg, null);
	}

	/**
	 * 输出 ERROR 级别日志
	 */
	public static void error(String msg, Throwable e) {
		/*修复如果e为null,日志不能输出*/
		if (e == null && !TextUtils.isEmpty(msg)) {
			e = new Throwable(msg);
		}
		error(msg, e, false);
	}

	/**
	 * 输出 ERROR 级别日志
	 */
	public static void error(String msg, Throwable e, boolean isRemote) {
		if (StringUtils.isBlank(msg) || e == null) {//不处理
			return;
		}

		doLog(Log.ERROR, msg, e);

	}

	/**
	 * ***************************************************************************
	 * 以下私有方法不对外
	 * ***************************************************************************
	 */

	/**
	 * 控制台输出日志
	 */
	private static void doLog(int logLevel, String logMessage, Throwable throwable) {
		if (!LOG_IS_ON || logLevel < LOG_LEVEL)
			return;

		String tag = getLogTag();

		switch (logLevel) {
		case Log.DEBUG:
			Log.d(tag, logMessage, throwable);
			break;
		case Log.INFO:
			Log.i(tag, logMessage, throwable);
			break;
		case Log.ERROR:
			Log.e(tag, logMessage, throwable);
			break;
		}
	}

	/**
	 * 得到tag
	 * @return
	 */
	private static String getLogTag() {
		StackTraceElement stackTraceElement = getStackTraceElement(6);
		String className = stackTraceElement.getClassName();
		String methodName = stackTraceElement.getMethodName();
		int line = stackTraceElement.getLineNumber();
		String tag = className + "|" + methodName + "|" + line + "|";

		return tag;
	}

	/**
	 * 获取堆栈信息
	 */
	private static StackTraceElement getStackTraceElement(int i) {
		return Thread.currentThread().getStackTrace()[i];
	}

}
