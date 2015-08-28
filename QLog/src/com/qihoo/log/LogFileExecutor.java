package com.qihoo.log;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by zhaomingming-s on 2015/8/27.
 */
public class LogFileExecutor implements IFileExecutor {

    private static final String TAG = "LogFileExecutor";

    private String mFilePath;

    private static final int DEFAULT_WRITER_CACHE = 1024 * 10;//默认BufferedWriter的cache空间大小为10KB

    private int mWriterCache = DEFAULT_WRITER_CACHE;

    private BufferedWriter mWriter;

    private File mOutFile;

    private boolean isAvailable = true;

    /**
     * @param filePath 文件路径
     * @param cache    文件输出流缓冲池大小
     */
    public LogFileExecutor(String filePath, int cache) {
        if (!checkFilePath(filePath)) {
            isAvailable = false;
            return;
        }

        mFilePath = filePath;
        if (cache > 0)
            mWriterCache = cache;
        if (!initWriter()) {
            isAvailable = false;
        }
    }


    /**
     * 检查文件路径是否可用
     *
     * @param filePath
     * @return
     */
    private boolean checkFilePath(String filePath) {
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
        if (!checkFilePath(filePath)) {
            isAvailable = false;
            return isAvailable;
        }
        mFilePath = filePath;
        if (cache > 0)
            mWriterCache = cache;
        isAvailable = initWriter();

        return isAvailable;
    }


//    /**
//     * 向文件追加字段
//     * @param head 每行的标题头：{date}|{class_name}|{method_name}
//     * @param data 日志内容
//     */
//    @Override
//    public void append(String head, String data) {
//        if (mFilePath == null || mFilePath.isEmpty())
//            return;
//        BufferedWriter writer;
//        BufferedReader reader;
//        String divLine = "------------------------------------------";
//        boolean isDrawLine = true;
//        try {
//            reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
//            writer = new BufferedWriter(new FileWriter(mFilePath), mWriterCache);
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (isDrawLine) {//画分割线
//                    writer.append(divLine);
//                    writer.newLine();
//                    isDrawLine = false;
//                }
//                line = head + line;
//                writer.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//        }
//    }


    /**
     * 向文件按行追加字段
     *
     * @param tag  标识
     * @param data 日志内容
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
    }

    /**
     * 刷新writer缓冲空间数据写入到文件中
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