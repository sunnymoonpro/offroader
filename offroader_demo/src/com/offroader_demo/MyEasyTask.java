package com.offroader_demo;

import java.util.Map;

import android.app.Activity;

import com.offroader.task.EasyTask;

public class MyEasyTask extends EasyTask<Activity, Void, Void, TestHttpBean> {
	private String url;
	private Map<String, String> paramMap;

	public MyEasyTask(Activity caller, String url, Map<String, String> paramMap) {
		super(caller);

		this.url = url;
		this.paramMap = paramMap;
	}

	@Override
	public void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	public TestHttpBean doInBackground(Void... params) {

		return null;
	}

	@Override
	public void onPostExecute(TestHttpBean httpBean) {
		super.onPostExecute(httpBean);

		if (httpBean != null) {

		} else {

		}

	}

}
