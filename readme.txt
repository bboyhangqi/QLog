1.����outputĿ¼�µ�xlogxxx.jar��

2.��AndroidManifset.xml <Application>��ǩ�����<meta-data>�ӱ�ǩ��
<meta-data  android:name="xlog_level"  android:value="debug" />
android:name="xlog_level"����־�ȼ�keyֵ������Ψһ�Բ���Ҫ�Ķ���
android:value="debug"����־�ȼ�valueֵ��ȡֵ��Χverbose��debug��info��warm��error
���δ���<meta-data>�ӱ�ǩ��Ĭ�ϱ���info������־��

3.ʹ�÷�ʽ
XLog log=XLog.getInstance(this);
log=XLog.getInstance(this);
log.d(tag,msg);
