package com.offroader.utils;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 编码工具类
 * @author li.li
 *
 */
public class EncodeUtils {
	public static final int XOR_KEY = 20000;//xor加密key

	/**
	 * md5加密
	 * @param s
	 * @return
	 */
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			LogUtils.error(e.getMessage(), e);
		}

		return "";
	}

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String toHexString(byte[] b) { // String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 数组异或运算
	 * @param data
	 * @return
	 */
	public static byte[] xor(byte[] data) {

		for (int i = 0; i < data.length; i++) {// 遍历字符数组
			data[i] = (byte) (data[i] ^ 20000);// 对每个数组元素进行异或运算
		}

		return data;
	}

	/**
	 * 加密
	 * @param data
	 * @return
	 */
	public static byte[] encodeXor(byte[] data) {

		for (int i = 0; i < data.length; i++) {// 遍历字符数组
			data[i] = xor(data[i]);// 对每个数组元素进行异或运算
		}

		return data;
	}

	/**'''''''''''''
	 * 解密
	 * @param data
	 * @return
	 */
	public static ByteBuffer decodeXor(ByteBuffer bb, int length) {

		int xorBodyLength = length;

		ByteBuffer tempData = ByteBuffer.allocate(xorBodyLength);

		for (int i = 0; i < length; i++) {// 跳过加密头，遍历字符数组
			tempData.put(xor(bb.get(i)));// 对每个数组元素进行异或运算
		}

		return tempData;
	}

	/**
	 * 异或运算
	 * @param data
	 * @return
	 */
	private static byte xor(byte b) {

		return (byte) (b ^ XOR_KEY);// 对每个数组元素进行异或运算
	}

}
