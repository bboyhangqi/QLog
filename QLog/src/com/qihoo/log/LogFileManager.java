package com.qihoo.log;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;


/**
 * Created by zhaomingming-s on 2015/8/27.
 */
public class LogFileManager {

    public static final String TAG = "FileManager";
    private Context mContext;
    private HandlerThread mHandlerThread;
    private WorkHandle mWorkHandle;

    private static final String KEY_MODULE = "module";
    private static final String KEY_MESSAGE = "msg";

    public LogFileManager(Context context) {
        mContext = context;
        mHandlerThread = new HandlerThread("work_thread");
        mHandlerThread.start();
        mWorkHandle = new WorkHandle(mHandlerThread.getLooper());
    }


    /**
     * @param module 模块标识
     * @param msg 日志消息体
     */
    public void writeLog(String module,String msg) {
        Message message =mWorkHandle.obtainMessage(MSG_WRITE);
        Bundle data =new Bundle();
        data.putString(KEY_MODULE,module);
        data.putString(KEY_MESSAGE,msg);
        message.setData(data);
        mWorkHandle.sendMessage(message);
    }


    private static final int MSG_WRITE = 0;


    class WorkHandle extends Handler {
        public WorkHandle(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WRITE:

                    break;
                default:
                    break;

            }
            super.handleMessage(msg);
        }
    }

    public class LogFileExecutor{

        public LogFileExecutor() {

        }
    }


}