package com.qihoo.log;

/**
 * Created by zhaomingming-s on 2015/8/27.
 */
public interface IFileExecutor {


    void append(String tag,String data);

    void clearData();

    void flush();

    void close() ;

    boolean isAvailable();

    boolean reset(String filePath, int cacheSize);

}