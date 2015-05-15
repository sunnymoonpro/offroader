package com.offroader.http;

import java.io.File;
import java.util.Map;

import com.offroader.utils.JsonUtils;
import com.offroader.utils.LogUtils;
import com.offroader.utils.NetUtils;
import com.offroader.utils.NetUtils.NetType;
import com.offroader.utils.StringUtils;

/**
 * http请求帮助类
 * 
 * 提供将一个请求的结果返回一个对象
 * 如果返回的json数据是空的则返回空对象
 * 
 * @author li.li
 *
 * Dec 27, 2012
 */
public class HttpHelper {
	private static final HttpHelper instance = new HttpHelper();

	private HttpHelper() {
	}

	public static HttpHelper getInstance() {
		return instance;
	}

	/**
	 * post请求
	 * @param url 请求地址
	 * @param params 参数
	 * @param clazz 返回的对象实例类型
	 * @return
	 */
	public <T> T post(String url, Map<String, String> params, Class<T> clazz) {

		String result = post(url, params);

		if (StringUtils.isBlank(result))
			return null;

		T obj = JsonUtils.fromJson(result, clazz);

		return obj;
	}

	/**
	 * get请求
	 * @param url 请求地址
	 * @param params 参数
	 * @param clazz 返回的对象实例类型
	 * @return
	 */
	public <T> T get(String url, Map<String, String> params, Class<T> clazz) {

		String result = get(url, params);

		if (StringUtils.isBlank(result))
			return null;

		T obj = JsonUtils.fromJson(result, clazz);

		return obj;
	}

	/**
	 * 文件上传
	 * @param url
	 * @param params
	 * @param files
	 * @param listener
	 * @param clazz
	 * @return
	 */
	public <T> T upload(String url, Map<String, String> params, Map<String, File> files, ProgressListener listener, Class<T> clazz) {

		String result = upload(url, params, files, listener);

		if (StringUtils.isBlank(result))
			return null;

		T obj = JsonUtils.fromJson(result, clazz);

		return obj;
	}

	/**
	 * ********************************************************************
	 * 私有方法不对外
	 * ********************************************************************
	 */

	/**
	 * post请求
	 * @param url 请求地址
	 * @param params 参数
	 * @return
	 */
	private String post(String url, Map<String, String> params) {
		NetType netType = NetUtils.checkNet();

		if (NetType.TYPE_NONE.equals(netType))
			return StringUtils.EMPTY;

		HttpImpl httpProvider = null;
		String result = StringUtils.EMPTY;
		HttpResult httpResult = null;

		try {
			httpProvider = HttpImpl.getInstance();
			httpResult = httpProvider.post(url, null, params, HttpUtils.ENCODING);
			result = httpResult.httpEntityContent();

			return result;

		} catch (Throwable e) {
			LogUtils.error(e.getMessage(), e);
		} finally {
			if (httpProvider != null)
				httpProvider.releaseConnection(httpResult);
		}

		return result;
	}

	/**
	 * get请求
	 * @param url 请求地址
	 * @param params 参数
	 * @return
	 */
	private String get(String url, Map<String, String> params) {
		NetType netType = NetUtils.checkNet();

		if (NetType.TYPE_NONE.equals(netType))
			return StringUtils.EMPTY;

		HttpImpl httpProvider = null;
		String result = StringUtils.EMPTY;
		HttpResult httpResult = null;

		try {
			httpProvider = HttpImpl.getInstance();
			httpResult = httpProvider.get(url, null, params, HttpUtils.ENCODING);
			result = httpResult.httpEntityContent();

			return result;

		} catch (Throwable e) {
			LogUtils.error(e.getMessage(), e);
		} finally {

			if (httpProvider != null)
				httpProvider.releaseConnection(httpResult);
		}

		return result;
	}

	/**
	 * 文件上传
	 * @param url
	 * @param params
	 * @param files
	 * @param listener
	 * @return
	 */
	private String upload(String url, Map<String, String> params, Map<String, File> files, ProgressListener listener) {
		NetType netType = NetUtils.checkNet();

		if (NetType.TYPE_NONE.equals(netType))
			return StringUtils.EMPTY;

		HttpImpl httpProvider = null;
		String result = StringUtils.EMPTY;
		HttpResult httpResult = null;

		try {
			httpProvider = HttpImpl.getInstance();
			httpResult = httpProvider.upload(url, null, params, files, listener, HttpUtils.ENCODING);
			result = httpResult.httpEntityContent();

			return result;

		} catch (Throwable e) {
			LogUtils.error(e.getMessage(), e);
		} finally {

			if (httpProvider != null)
				httpProvider.releaseConnection(httpResult);
		}

		return result;
	}
}
