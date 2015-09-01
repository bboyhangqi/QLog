1.导入output目录下的xlogxxx.jar。

2.在AndroidManifset.xml <Application>标签下添加<meta-data>子标签：
<meta-data  android:name="xlog_level"  android:value="debug" />
android:name="xlog_level"：日志等级key值，保持唯一性不需要改动。
android:value="debug"：日志等级value值，取值范围verbose、debug、info、warm、error
如果未添加<meta-data>子标签，默认保存info级别日志。

3.使用方式
XLog log=XLog.getInstance(this);
log=XLog.getInstance(this);
log.d(tag,msg);
