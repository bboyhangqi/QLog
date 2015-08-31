package com.qihoo.log;

/**
 * Created by zhaomingming-s on 2015/8/27.
 */

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class QLog {

    private static final String TAG = "QLog";

    private static Map<String, QLog> logs = new HashMap<String, QLog>();//����ʵ���
    private Context mContext;


    private static final int LOG_LEVEL_DEFAULT = Constant.LOG_LEVEL_DEBUG;
    private int mLowestLevel = LOG_LEVEL_DEFAULT;
    private LogFileManager mLogFileManager;

    private QLog(Context context) {
    	Log.d(TAG," QLog  in ");
        mContext = context;
        mLogFileManager=new LogFileManager(context);
    }


    /**
     * 根据包名返回唯一instance(当一个pid针对多进程时)
     * @param context
     * @return QLog
     */
    public static synchronized QLog getInstance(Context context) {
        String pkgName=getPkgName(context);
        if(pkgName==null||pkgName.isEmpty()){
            Log.e(TAG," pkgName is  null ");
            return null;
        }

        QLog instance = logs.get(pkgName);
        if (instance == null) {
            instance = new QLog(context);
            logs.put(pkgName,instance);
        }
        return instance;
    }


    private static String getPkgName(Context context) {
        return context.getPackageName();
    }

    
    public void close() {
    	mLogFileManager.close();
    }
    
    
    /**
     * 设置日志打印等级
     */
    public void setLowstLevel(int lowestLevel) {
        mLowestLevel = lowestLevel;
    }

    private boolean isPermissibleLevel(int level){
        return level>=mLowestLevel;
    }

    /**
     * level VERBOSE
     */
    public void v(String tag,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_VERBOSE)){
        	mLogFileManager.writeLog(tag, msg);
        }
    }

    /**
     * level DEBUG
     */
    public void d(String tag,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_DEBUG)){
        	mLogFileManager.writeLog(tag, msg);
        }

    }

    /**
     * level INFO
     */
    public void i(String tag,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_INFO)){
        	mLogFileManager.writeLog(tag, msg);
        }

    }

    /**
     * level WARN
     */
    public void w(String tag,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_WARN)){
        	mLogFileManager.writeLog(tag, msg);
        }

    }

    /**
     * level ERROR
     */
    public void e(String tag,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_ERROR)){
        	mLogFileManager.writeLog(tag, msg);
        }

    }


}