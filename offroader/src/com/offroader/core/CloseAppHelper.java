package com.offroader.core;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 应用关闭
 * 
 * @author li.li
 *
 */
public class CloseAppHelper {
	private final static String CLOSE_APP_ACTION = "com.xs.readnovel.close.app";
	private Activity act;
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			act.finish();
		}
	};

	public CloseAppHelper(Activity act) {
		this.act = act;

	}

	public static CloseAppHelper newInstance(Activity act) {
		return new CloseAppHelper(act);
	}

	/**
	 * 注册关闭行为
	 */
	public void register() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(CLOSE_APP_ACTION);
		act.registerReceiver(this.broadcastReceiver, filter);
	}

	/**
	 * 取消注册关闭行为
	 */
	public void unRegister() {
		act.unregisterReceiver(broadcastReceiver);
	}

	/**
	 * 关闭App
	 */
	public static void closeApp() {
		Intent intent = new Intent();
		intent.setAction(CLOSE_APP_ACTION);
		OffRoaderApp.getInstance().sendBroadcast(intent);
	}
}
