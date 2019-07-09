package com.xqd.myapplication.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密处理
 * 
 * @author 刘攀
 *
 */

public class EncryptUtil {

	/**
	 * md5加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String md5(String inputText) {
		return encrypt(inputText, "md5");
	}

	/**
	 * sha加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	/**
	 * md5或者sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte[] s = m.digest();
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	/**
	 * 返回十六进制字符串
	 * 
	 * @param arr
	 * @return
	 */
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	/*
	 * 展示了一个生成指定算法密钥的过程 初始化HMAC密钥
	 * 
	 * @return
	 * 
	 * @throws Exception
	 * 
	 * public static String initMacKey() throws Exception { //得到一个 指定算法密钥的密钥生成器
	 * KeyGenerator KeyGenerator keyGenerator
	 * =KeyGenerator.getInstance(MAC_NAME); //生成一个密钥 SecretKey secretKey
	 * =keyGenerator.generateKey(); return null; }
	 */

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param encryptText
	 *            被签名的字符串
	 * @param encryptKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String hmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);

		byte[] text = encryptText.getBytes(ENCODING);
		// 完成 Mac 操作
		return byte2hex(mac.doFinal(text));
	}

	public static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs.append('0');
			}
			hs.append(stmp);
		}
		return hs.toString();
	}

	/**
	 * 获取应用签名
	 * @param context
	 * @return
	 */
	public static String getSignature(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();

			Signature[] signatures= new Signature[0];
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
				PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
				signatures = packageInfo.signingInfo.getSigningCertificateHistory();
			}else{
				PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
				signatures = packageInfo.signatures;
			}
			return signatures[0].toCharsString();

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 获取base64编码后的字符串
	 * @param oldWord
	 * @return
	 */
	public static String getBase64Encode(String oldWord){
		String encodeWord = null;
		try {
			encodeWord = Base64.encodeToString(oldWord.getBytes("utf-8"), Base64.NO_WRAP);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeWord;
	}

	/**
	 * 获取base64解码后的数据
	 * @param encodeWord
	 * @return
	 */
	public static String getBase64Decode(String encodeWord){
		String decodeWord = null;
		try {
			decodeWord = new String(Base64.decode(encodeWord, Base64.NO_WRAP), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodeWord;
	}

}
