package com.qihoo.log;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by zhaomingming-s on 2015/8/27.
 */
public class LogFileExecutor implements IFileExecutor {

	private static final String TAG = "LogFileExecutor";

	private String mFilePath;

	public static final int DEFAULT_WRITER_CACHE = 1024 * 10;// 文件写入流缓冲区默认大小10KB

	private int mWriterCache = DEFAULT_WRITER_CACHE;

	private BufferedWriter mWriter;

	private File mOutFile;

	private boolean isAvailable = true;

	private boolean isWait;

	/**
	 * @param filePath
	 *            文件路径
	 * @param cacheSize
	 *            文件写入流缓冲区size
	 */
	public LogFileExecutor(String filePath, int cacheSize) {
		if (!checkFilePath(filePath)) {
			isAvailable = false;
			return;
		} else {
			if (!creatLogFile(filePath)) {
				Log.e(TAG, "  creat file fault  ");
				isAvailable = false;
				return;
			}
		}

		Log.e(TAG, "  LogFileExecutor  ");
		mFilePath = filePath;
		if (cacheSize > 0)
			mWriterCache = cacheSize;
		if (!initWriter()) {
			isAvailable = false;
		}
	}

	private boolean creatLogFile(String filePath) {
		Log.d(TAG, "  creatLogFile  filePath  " + filePath);
		File file = new File(filePath);
		boolean ret = file.exists();
		try {
			if (!file.exists()) {
				makeDir(file.getParentFile());
				ret = file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private void makeDir(File dir) {
		Log.d(TAG, "  parent  path  " + dir.getAbsolutePath());
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}

	/**
	 * 检测文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	private boolean checkFilePath(String filePath) {
		Log.e(TAG, "  checkFilePath   filePath   " + filePath);
		if (filePath == null || filePath.isEmpty()) {
			Log.e(TAG, "  file  path  error  ");
			return false;
		}
		return true;
	}

	private boolean initWriter() {
		mOutFile = new File(mFilePath);
		try {
			mWriter = new BufferedWriter(new FileWriter(mOutFile, true), mWriterCache);
		} catch (FileNotFoundException e2) {
			Log.e(TAG, "  file  not  exists  error  ");
			return false;
		} catch (IOException e) {
			Log.e(TAG, "  catch  IOException  " + e.toString());
			return false;
		}
		return true;
	}

	@Override
	public boolean reset(String filePath, int cache) {
		if (isAvailable) {
			close();
		}
		if (!checkFilePath(filePath)) {
			isAvailable = false;
			return isAvailable;
		}
		mFilePath = filePath;
		if (cache > 0)
			mWriterCache = cache;
		isWait = false;
		isAvailable = initWriter();

		return isAvailable;
	}

	// /**
	// * ���ļ�׷���ֶ�
	// * @param head ÿ�еı���ͷ��{date}|{class_name}|{method_name}
	// * @param data ��־����
	// */
	// @Override
	// public void append(String head, String data) {
	// if (mFilePath == null || mFilePath.isEmpty())
	// return;
	// BufferedWriter writer;
	// BufferedReader reader;
	// String divLine = "------------------------------------------";
	// boolean isDrawLine = true;
	// try {
	// reader = new BufferedReader(new InputStreamReader(new
	// ByteArrayInputStream(data.getBytes(Charset.forName("utf8"))),
	// Charset.forName("utf8")));
	// writer = new BufferedWriter(new FileWriter(mFilePath), mWriterCache);
	// String line;
	// while ((line = reader.readLine()) != null) {
	// if (isDrawLine) {//���ָ���
	// writer.append(divLine);
	// writer.newLine();
	// isDrawLine = false;
	// }
	// line = head + line;
	// writer.append(line);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// }
	// }

	/**
	 * 追加日志信息到文件中
	 * 
	 * @param tag
	 *            日志TAG
	 * @param data
	 *            日志信息
	 */
	@Override
	public void append(String tag, String data) {
		if (!isAvailable)
			return;
		try {
			String date = DateHelper.getInstance().getFormatDate(new Date());
			String[] dataSplits = data.split("\n");
			String line;
			for (int i = 0; i < dataSplits.length; i++) {
				while (isWait) {
					Log.d(TAG, " wait ");
					Thread.sleep(100);
				}
				line = dataSplits[i];
				line = "date:" + date + "  tag:" + tag + "  msg:" + line;
				mWriter.append(line);
				mWriter.newLine();
			}
		} catch (Exception e) {
			if (mWriter != null)
				close();
			e.printStackTrace();
		}
	}

	@Override
	public void clearData() {
		if (!isAvailable)
			return;
		isWait = true;
		File file = new File(mFilePath);
		try {
			if (file.exists()) {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write("");
				fileWriter.flush();
				fileWriter.close();
			}
			isWait = false;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			isWait = false;
		}

	}

	/**
	 * 将writer缓冲区数据写入文件中
	 */
	@Override
	public void flush() {
		if (!isAvailable)
			return;
		if (mWriter != null) {
			try {
				mWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void close() {
		if (!isAvailable)
			return;
		if (mWriter != null) {
			try {
				flush();
				mWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isAvailable() {
		return isAvailable;
	}

}