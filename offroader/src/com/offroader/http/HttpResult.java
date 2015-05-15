package com.offroader.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.offroader.utils.LogUtils;

/**
 * Http请求结果
 * 
 * @author li.li
 * 
 */

public class HttpResult {

	private int statusCode;
	private HttpEntity httpEntity;
	private HttpUriRequest request;

	public HttpUriRequest getRequest() {
		return request;
	}

	public void setRequest(HttpUriRequest request) {
		this.request = request;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public HttpEntity getHttpEntity() {
		return httpEntity;
	}

	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}

	public String httpEntityContent() {

		return httpEntityContent(HTTP.UTF_8);
	}

	public String httpEntityContent(String encode) {

		try {
			return EntityUtils.toString(httpEntity, encode);
		} catch (Throwable e) {
			LogUtils.error(e.getMessage(), e);
		}

		return null;
	}
}
