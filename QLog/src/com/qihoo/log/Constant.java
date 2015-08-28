package com.qihoo.log;

import java.security.PublicKey;

/**
 * Created by zhaomingming-s on 2015/8/27.
 */
public class Constant {


    //------------log保存本路径、节点
    //------------日志路径格式sdcard/Android/data/{packagename}/log
    public static final String BASE_LOG_PATH = "sdcard/Android/data/";
    public static final String LOG_PATH_NODE = "/log";

    //------------日志等级
    public static final int LOG_LEVEL_VERBOSE = 1;
    public static final int LOG_LEVEL_DEBUG = 2;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_WARN = 4;
    public static final int LOG_LEVEL_ERROR = 5;

    //------------日志格式相关
    public static final String KEY_HEAD_CLASSNAME = "class_name";
    public static final String KEY_HEAD_METHODNAME = "method_name";


}