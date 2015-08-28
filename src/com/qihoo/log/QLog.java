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

    private static Map<String, QLog> logs = new HashMap<String, QLog>();//所有实例集合
    private Context mContext;


    private static final int LOG_LEVEL_DEFAULT = Constant.LOG_LEVEL_DEBUG;
    private int mLowestLevel = LOG_LEVEL_DEFAULT;

    private QLog(Context context) {
        mContext = context;
    }


    /**
     * 根据包名返回指定包名的唯一实例instance
     *
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

    /**
     * 设置记录最低日志等级
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
    public void v(String module,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_VERBOSE)){

        }
    }

    /**
     * level DEBUG
     */
    public void d(String module,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_DEBUG)){

        }

    }

    /**
     * level INFO
     */
    public void i(String module,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_INFO)){

        }

    }

    /**
     * level WARN
     */
    public void w(String module,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_WARN)){

        }

    }

    /**
     * level ERROR
     */
    public void e(String module,String msg) {
        if(isPermissibleLevel(Constant.LOG_LEVEL_ERROR)){

        }

    }


}