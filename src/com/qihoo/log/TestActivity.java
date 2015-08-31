package com.qihoo.log;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn=(Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				start();
			}
		});
	}
	
	private void start(){
		QLog log=QLog.getInstance(this);
		log=QLog.getInstance(this);
		log=QLog.getInstance(this);
		log=QLog.getInstance(this);
		for (int i = 0; i < 10000; i++) {
			log.e("TestActivity", "1.测试阿萨德就发啦见识到了开\n2.房间SD卡机房阿萨德连开机费垃圾\n3.收电费塑料袋机房来得及是否垃圾\n4."
					+ "收电费垃圾收电费垃圾啊死定\n了房间阿里山的机房");
		}
	}
	
	
	
}
