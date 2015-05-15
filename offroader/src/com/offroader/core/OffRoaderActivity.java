package com.offroader.core;

import android.app.Activity;
import android.os.Bundle;

public class OffRoaderActivity extends Activity {
	private ActivityProxy activityProxy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activityProxy = new ActivityProxy(this);
		activityProxy.onCreate(savedInstanceState);

	}

	@Override
	protected void onResume() {
		super.onResume();

		activityProxy.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

		activityProxy.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();

		activityProxy.onStop();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();

		activityProxy.onLowMemory();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		activityProxy.onDestroy();
	}
}
