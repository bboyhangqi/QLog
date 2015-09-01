package com.qihoo.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhaomingming-s on 2015/8/27.
 */

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.util.Log;

/**
 *
 */
public class XLog {

	private static final String TAG = "XLog";

	private static Map<String, XLog> logs = new HashMap<String, XLog>();// 所有XLog实例
	private Context mContext;

	private static final int LOG_LEVEL_DEFAULT = Constant.LOG_LEVEL_INFO;
	private int mLowestLevel = LOG_LEVEL_DEFAULT;
	private static final String FILE = "/data/log.properties";
	private LogFileManager mLogFileManager;

	private XLog(Context context) {
		mContext = context;
		mLogFileManager = new LogFileManager(context);
		mLowestLevel=getLogLevel();
		Log.d(TAG, "  mLowestLevel  "+mLowestLevel);
	}

	/**
	 * 根据包名返回唯一instance(当一个pid针对多进程时)
	 * 
	 * @param context
	 * @return QLog
	 */
	public static synchronized XLog getInstance(Context context) {
		String pkgName = getPkgName(context);
		if (pkgName == null || pkgName.isEmpty()) {
			Log.e(TAG, " pkgName is  null ");
			return null;
		}

		XLog instance = logs.get(pkgName);
		if (instance == null) {
			instance = new XLog(context);
			logs.put(pkgName, instance);
		}
		return instance;
	}

	private static String getPkgName(Context context) {
		return context.getPackageName();
	}

	public void close() {
		mLogFileManager.close();
	}
	
	public void reset() {
		mLogFileManager.reset();
	}

	/**
	 * 设置日志打印等级
	 */
	public void setLowstLevel(int lowestLevel) {
		mLowestLevel = lowestLevel;
	}

	private boolean isPermissibleLevel(int level) {
		return level >= mLowestLevel;
	}

	/**
	 * level VERBOSE
	 */
	public void v(String tag, String msg) {
		if (isPermissibleLevel(Constant.LOG_LEVEL_VERBOSE)) {
			mLogFileManager.writeLog(tag, msg);
		}
	}

	/**
	 * level DEBUG
	 */
	public void d(String tag, String msg) {
		if (isPermissibleLevel(Constant.LOG_LEVEL_DEBUG)) {
			mLogFileManager.writeLog(tag, msg);
		}

	}

	/**
	 * level INFO
	 */
	public void i(String tag, String msg) {
		if (isPermissibleLevel(Constant.LOG_LEVEL_INFO)) {
			mLogFileManager.writeLog(tag, msg);
		}

	}

	/**
	 * level WARN
	 */
	public void w(String tag, String msg) {
		if (isPermissibleLevel(Constant.LOG_LEVEL_WARN)) {
			mLogFileManager.writeLog(tag, msg);
		}

	}

	/**
	 * level ERROR
	 */
	public void e(String tag, String msg) {
		if (isPermissibleLevel(Constant.LOG_LEVEL_ERROR)) {
			mLogFileManager.writeLog(tag, msg);
		}

	}

	private int getLogLevel() {
		try {
			ApplicationInfo appInfo=mContext.getPackageManager()
			        .getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
			String levelStr=appInfo.metaData.getString("xlog_level");
			Log.d(TAG, "  xlog  level  "+levelStr);
			return Level.getLevel(levelStr).intValue;
		} catch (NameNotFoundException e2) {
			Log.e(TAG, "  xlog_level  does  not  define  in  AndroidManifest.xml  ");
			return LOG_LEVEL_DEFAULT;
		}
	}

	enum Level {
		
		verbose(Constant.LOG_LEVEL_VERBOSE),debug(Constant.LOG_LEVEL_DEBUG), info(Constant.LOG_LEVEL_INFO), warn(Constant.LOG_LEVEL_WARN), error(Constant.LOG_LEVEL_ERROR);

		int intValue=LOG_LEVEL_DEFAULT;

		private Level(int intValue) {
			this.intValue = intValue;
		}

		public static Level getLevel(String key) {
			if (key == null)
				return null;
			try {
				return Level.valueOf(key.toLowerCase());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public static Level getLevel(int intValue) {
			Level[] levels = Level.values();
			for (int i = 0; i < levels.length; i++) {
				Level l = levels[i];
				if (l.intValue == intValue)
					return l;
			}
			return null;
		}
	}

}