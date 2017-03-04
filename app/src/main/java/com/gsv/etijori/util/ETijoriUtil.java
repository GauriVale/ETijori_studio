package com.gsv.etijori.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class ETijoriUtil {

	public static final String ET_ACCOUNT_TYPE = "ETijori";
	public static final String ET_USER_NAME = "ETijori";
	
	public static final String ET_APPLICATION_TAG = "ETIJORI";
	private static final String HEX = "0123456789ABCDEF";

	/*
	TODO: use blowfish algorithm
	 */

	public static String encrypt(String data, int key) {
		if (null == data || data.trim().length() <= 0) {
			return null;
		}
		
		key = key % 8;
		
		if (0 == key || 8 == key) {
			key = 4;
		}
		
		byte[] plainData = data.getBytes();
		byte[] encryptedData = new byte[plainData.length];
		
		int[] mask = new int[]{0x00, 0x80, 0xC0, 0xE0, 0xF0, 0xF8, 0xFC, 0xFE};
		int[] unmask = new int[]{0xFF, 0x7F, 0x3F, 0x1F, 0x0F, 0x07, 0x03, 0x01};
		
		for (int i = 0; i < plainData.length; i++) {
			encryptedData[i] = plainData[i];
			
			int temp = encryptedData[i];
			temp >>= key;
			temp &= unmask[key];
			encryptedData[i] <<= (8 - key);
			encryptedData[i] &= mask[key];
			encryptedData[i] |= temp;
		}
		
		return encode(encryptedData);
	}
	
	public static byte[] decrypt(String data, int key) {
		if (null == data || data.trim().length() <= 0) {
			return null;
		}
		
		key = key % 8;
		if (0 == key || 8 == key) {
			key = 4;
		}
		
		int[] unmask = new int[]{0xFF, 0x7F, 0x3F, 0x1F, 0x0F, 0x07, 0x03, 0x01};
		byte[] decoded = getByteArray(data);
		byte[] plainData = new byte[decoded.length];
		for (int i = 0; i < decoded.length; i++) {
			plainData[i] = decoded[i];
			byte temp = decoded[i];
			temp <<= key;
			
			plainData[i] >>= (8 - key);
			plainData[i] &= unmask[8 - key];
			plainData[i] |= temp;
		}
		
		return plainData;
	}
	
	private static byte[] getByteArray(String str) {
		if (null == str) {
			return null;
		}
		
		int len = str.length();
		byte[] b = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
		b[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i+1), 16));
		}
		
		return b;
	}
	
	public static String encode(byte[] buffer) {
		if (null == buffer || buffer.length <= 0) {
			return null;
		}
		StringBuffer result = new StringBuffer(2 * buffer.length);
		for (int i = 0; i < buffer.length; i++) {
			appendHex(result, buffer[i]);
		}
		
		return result.toString();
	}
	
	private static final void appendHex(StringBuffer buffer, byte b) {
		buffer.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	public static byte[] decode(String data) {
		if (null == data || data.trim().length() <= 0) {
			return null;
		}
		int len = data.length();
		byte[] result = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			result[i / 2] = (byte) ((Character.digit(data.charAt(i), 16) << 4) + Character
					.digit(data.charAt(i + 1), 16));
		}
		return result;
	}
	
	public static byte[] calculateMessageDigest(String data) {
		if (null == data || data.trim().length() <= 0) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(data.getBytes());
			return messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			Log.e(ET_APPLICATION_TAG, "" + e);
		}
		return null;
	}
	
	public static int generateKey(byte[] data) {
		if (null == data || data.length <= 0) {
			return -1;
		}
		int key = 0;
		
		for (int i = 0; i < data.length; i++) {
			key += data[i];
		}
		
		do {
			int temp = key % 10;
			key = key / 10;
			key = key + temp;
		} while (key > 9);
		
		return key;
	}
}
