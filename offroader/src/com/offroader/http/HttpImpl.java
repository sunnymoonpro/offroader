/**
 * 
 */
package com.offroader.http;

import java.io.File;
import java.util.Map;

import org.apache.http.HttpVersion;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;


/**
 * Http请求
 * 
 * @author li.li
 * 
 */
class HttpImpl {
	/** 从连接池中取连接的超时时间 */
	private static final int POOL_TIMEOUT = 5 * 1000;
	/** 连接超时 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
	/** 请求超时 */
	public static final int DEFAULT_SO_TIMEOUT = 40 * 1000;
	/** 每个路由(route)最大连接数 */
	private final static int DEFAULT_ROUTE_CONNECTIONS = Integer.MAX_VALUE;
	/** 连接池中的最多连接总数 */
	private final static int DEFAULT_MAX_CONNECTIONS = Integer.MAX_VALUE;
	/** Socket 缓存大小 */
	private final static int DEFAULT_SOCKET_BUFFER_SIZE = 1024;

	private static volatile HttpImpl instance;

	private DefaultHttpClient client;

	private HttpImpl() {
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);//禁止302重定向

		/** 以先发送部分请求（如：只发送请求头）进行试探，如果服务器愿意接收，则继续发送请求体 */
		HttpProtocolParams.setUseExpectContinue(httpParams, true);
		/**
		 * 即在有传输数据需求时，会首先检查连接池中是否有可供重用的连接，如果有，则会重用连接。
		 * 同时，为了确保该“被重用”的连接确实有效，会在重用之前对其进行有效性检查
		 */
		HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);

		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);

		// 设置我们的HttpClient支持HTTP和HTTPS两种模式
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		// schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		//线程安全连接池
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(httpParams, schReg);
		client = new DefaultHttpClient(conMgr, httpParams);

		// 设置拦截器
		client.addRequestInterceptor(new BaseHttpRequestInterceptor());
		client.addResponseInterceptor(new BaseHttpResponseInterceptor());

		// 设置请求重试控制器（服务器或网络故障重试）
		client.setHttpRequestRetryHandler((new DefaultHttpRequestRetryHandler(2, false)));

		ConnManagerParams.setTimeout(httpParams, POOL_TIMEOUT);
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(DEFAULT_ROUTE_CONNECTIONS));
		ConnManagerParams.setMaxTotalConnections(httpParams, DEFAULT_MAX_CONNECTIONS);

		HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_CONNECT_TIMEOUT);//连接超时(指的是连接一个url的连接等待时间)
		HttpConnectionParams.setSoTimeout(httpParams, DEFAULT_SO_TIMEOUT);//读取数据超时(指的是连接上一个url，获取response的返回等待时间)
		HttpConnectionParams.setTcpNoDelay(httpParams, true);//nagle算法默认是打开的，会引起delay的问题；所以要手工关掉。  
		HttpConnectionParams.setSocketBufferSize(httpParams, DEFAULT_SOCKET_BUFFER_SIZE);

	}

	public static HttpImpl getInstance() {

		if (instance == null) {
			synchronized (HttpImpl.class) {

				if (instance == null)
					instance = new HttpImpl();

			}
		}

		return instance;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Throwable
	 */
	public HttpResult get(String url, Map<String, String> headers, Map<String, String> params, String encoding) throws Throwable {

		HttpResult result = HttpUtils.get(url, headers, params, encoding, client);

		return result;
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Throwable
	 */
	public HttpResult post(String url, Map<String, String> headers, Map<String, String> params, String encoding) throws Throwable {

		HttpResult result = HttpUtils.post(url, headers, params, encoding, client);

		return result;
	}

	/**
	 * 上传-带进度
	 * @param url
	 * @param headers
	 * @param params
	 * @param files
	 * @param encoding
	 * @param listener
	 * @return
	 * @throws Throwable
	 */
	public HttpResult upload(String url, Map<String, String> headers, Map<String, String> params, Map<String, File> files, ProgressListener listener,
			String encoding) throws Throwable {

		return HttpUtils.upload(url, headers, params, files, encoding, client, listener);
	}

	public void download(String url, Map<String, String> headers, Map<String, String> params, File files, ProgressListener listener, String encoding) {

	}

	/**
	 * 释放资源
	 * @param httpResult
	 */
	public void releaseConnection(HttpResult httpResult) {
		if (httpResult != null)
			httpResult.getRequest().abort();
	}

}
