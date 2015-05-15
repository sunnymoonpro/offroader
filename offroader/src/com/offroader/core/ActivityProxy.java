package com.offroader.core;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

import com.offroader.R;
import com.offroader.ioc.ViewInjectUtils;

public class ActivityProxy {
	private static final float MIN_VELOCITY = 1000;
	private static final float MIN_DISTANCE = OffRoaderApp.getInstance().getResources().getDimension(R.dimen.min_distance);
	private GestureDetector gestureDetector;
	private Activity act;
	private boolean closeCustomTouchEvent;

	private CloseAppHelper closeAppHelper;

	public ActivityProxy(Activity act) {
		this.act = act;
	}

	public void onCreate(Bundle savedInstanceState) {

		//依赖注入
		ViewInjectUtils.inject(act);

		//手势识别
		gestureDetector = new GestureDetector(act, new GestureListener(act));
		closeAppHelper = CloseAppHelper.newInstance(act);
		closeAppHelper.register();

	}

	public void onResume() {

	}

	public void onPause() {
	}

	public void onStop() {
	}

	public void onLowMemory() {
	}

	public void onDestroy() {
		closeAppHelper.unRegister();
	}

	public void dispatchTouchEvent(MotionEvent ev) {
		if (!closeCustomTouchEvent)
			gestureDetector.onTouchEvent(ev);

	}

	public void setCloseCustomTouchEvent(boolean value) {
		this.closeCustomTouchEvent = value;
	}

	private final class GestureListener extends SimpleOnGestureListener {
		private Activity act;

		public GestureListener(Activity act) {
			this.act = act;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			float distance = e2.getRawX() - e1.getRawX();
			float distance_y = Math.abs(e2.getRawY() - e1.getRawY());
			if (distance > MIN_DISTANCE && distance_y < 30) {
				act.finish();
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (Math.abs(velocityY) < Math.abs(2 * velocityX) && velocityX > MIN_VELOCITY && Math.abs(e2.getRawY() - e1.getRawY()) < MIN_DISTANCE) {
				act.finish();
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}

}
