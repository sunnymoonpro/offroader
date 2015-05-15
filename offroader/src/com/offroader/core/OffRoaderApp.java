package com.offroader.core;

import android.app.Application;

public class OffRoaderApp extends Application {
	private static Application instance;

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

	}

	public static Application getInstance() {
		return instance;
	}

}
