
package com.qihoo.log;

import android.os.Environment;

/**
 * Created by zhaomingming-s on 2015/8/27.
 */
public class Constant {

	// ------------日志文件路径sdcard/Android/data/{packagename}/log
	public static final String LOG_PATH_NODE_ANDROID = "/Android";
	public static final String LOG_PATH_NODE_SLASH = "/";
	public static final String LOG_PATH_NODE_DATA = "/data";
	public static final String LOG_PATH_END_NODE = "/log.txt";

	// ------------日志等级
	public static final int LOG_LEVEL_VERBOSE = 1;
	public static final int LOG_LEVEL_DEBUG = 2;
	public static final int LOG_LEVEL_INFO = 3;
	public static final int LOG_LEVEL_WARN = 4;
	public static final int LOG_LEVEL_ERROR = 5;

}