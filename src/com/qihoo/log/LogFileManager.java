package com.qihoo.log;

import java.io.File;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by zhaomingming-s on 2015/8/27.
 */
public class LogFileManager {

	public static final String TAG = "FileManager";
	private Context mContext;
	private HandlerThread mHandlerThread;
	private WorkHandle mWorkHandle;
	private LogFileExecutor mFileExecutor;
//	private LogFileObserver mFileObserver;
	private String mFilePath;

	private static final String KEY_TAG = "tag";
	private static final String KEY_MESSAGE = "msg";

	public static final long LIMIT_FILE_SIZE = 10 * 1024 * 1024; // 文件限定最大值

	public LogFileManager(Context context) {
		mContext = context;
		mHandlerThread = new HandlerThread("work_thread");
		mHandlerThread.start();
		mWorkHandle = new WorkHandle(mHandlerThread.getLooper());
		mFilePath = getFilePath();
		Log.d(TAG, "  filepath  " + mFilePath);
		mFileExecutor = new LogFileExecutor(mFilePath, LogFileExecutor.DEFAULT_WRITER_CACHE);
		checkFileSize();
//		if (mFileExecutor.isAvailable()) {
//			mFileObserver = new LogFileObserver(mFilePath);
//			mFileObserver.startWatching();
//		}

	}

	private String getFilePath() {
		File sdCard = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			sdCard = Environment.getExternalStorageDirectory();
			return sdCard.getAbsolutePath() + Constant.LOG_PATH_NODE_ANDROID + Constant.LOG_PATH_NODE_DATA
					+ Constant.LOG_PATH_NODE_SLASH + mContext.getPackageName() + Constant.LOG_PATH_END_NODE;
		} else {
			return Environment.getDataDirectory().getPath() + Constant.LOG_PATH_NODE_DATA + Constant.LOG_PATH_NODE_SLASH
					+ mContext.getPackageName() + Constant.LOG_PATH_END_NODE;
		}
	}
	
	

	/**
	 * @param tag
	 *            日志TAG
	 * @param msg
	 *            日志信息
	 */
	public void writeLog(String tag, String msg) {
		Message message = mWorkHandle.obtainMessage(MSG_WRITE);
		Bundle data = new Bundle();
		data.putString(KEY_TAG, tag);
		data.putString(KEY_MESSAGE, msg);
		message.setData(data);
		mWorkHandle.sendMessage(message);
	}

	public void close() {
		mFileExecutor.close();
//		mFileObserver.stopWatching();
	}
	
	public void reset(){
		mFileExecutor.reset(getFilePath(), LogFileExecutor.DEFAULT_WRITER_CACHE);
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
				mFileExecutor.append(msg.getData().getString(KEY_TAG), msg.getData().getString(KEY_MESSAGE));
				break;
			default:
				break;

			}
			super.handleMessage(msg);
		}
	}
	
	private void checkFileSize(){
		File file = new File(mFilePath);
		if(file.exists()){
			if (file.length() > LIMIT_FILE_SIZE) // 如果文件大于预设值就清空文件
				mFileExecutor.clearData();
		}
	}

//	class LogFileObserver extends FileObserver {
//
//		public LogFileObserver(String path) {
//			super(path);
//		}
//
//		public LogFileObserver(String path, int mask) {
//			super(path, mask);
//		}
//
//		@Override
//		public void onEvent(int event, String path) {
//			final int action = event & FileObserver.ALL_EVENTS;
//			switch (action) {
//			case FileObserver.ACCESS: // 文件被访问
//				break;
//
//			case FileObserver.DELETE: // 文件被删除
//				break;
//
//			case FileObserver.OPEN: // 文件目录被打开
//				break;
//
//			case FileObserver.MODIFY: // 文件被修改
//				Log.d(TAG, ".zhq.debug...MODIFY.....");
//				File file = new File(mFilePath);
//				if (file.length() > LIMIT_FILE_SIZE) // 如果文件大于预设值就清空文件
//					mFileExecutor.clearData();
//				break;
//			}
//		}
//	}

}