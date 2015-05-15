package com.offroader_demo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.offroader.core.CloseAppHelper;
import com.offroader.core.OffRoaderActivity;
import com.offroader.ioc.annotation.ORContentView;
import com.offroader.ioc.annotation.ORViewInject;
import com.offroader.ioc.annotation.event.OROnClick;
import com.offroader.ioc.annotation.event.OROnKey;
import com.offroader.ioc.annotation.event.OROnLongClick;
import com.offroader.ioc.annotation.event.OROnTouch;

@ORContentView(R.layout.activity_main)
public class MainActivity extends OffRoaderActivity {

	@ORViewInject(R.id.text1)
	private TextView tv1;

	@ORViewInject(R.id.text2)
	private TextView tv2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tv1.setText("测试1");

		tv2.setText("测试2");
		tv2.setText("测试3");

	}

	@OROnClick({ R.id.text1, R.id.button1 })
	public void clickButton(View v) {
		Toast.makeText(this, "clickButton点击" + v.getId() + "|" + v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
	}

	@OROnLongClick({ R.id.text2, R.id.button2 })
	public boolean longClickButton(View v) {
		Toast.makeText(this, "longClickButton点击" + v.getId() + "|" + v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
		return true;
	}

	@OROnTouch(R.id.button3)
	public boolean touchButton(View v, MotionEvent me) {
		Toast.makeText(this, "touchButton点击" + v.getId() + "|" + v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
		return true;
	}

	@OROnKey(R.id.button4)
	public boolean keyButton(View v, int i, KeyEvent ke) {
		Toast.makeText(this, "keyButton点击" + v.getId() + "|" + v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
		return false;
	}

	@OROnClick(R.id.button5)
	public void closeApp(View view) {
		CloseAppHelper.closeApp();
	}
}
