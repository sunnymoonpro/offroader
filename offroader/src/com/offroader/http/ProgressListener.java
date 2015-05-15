package com.offroader.http;

/**
 * 进度接口
 * @author Administrator
 *
 */
public interface ProgressListener {
	void transferred(long totalSize, long num);
}