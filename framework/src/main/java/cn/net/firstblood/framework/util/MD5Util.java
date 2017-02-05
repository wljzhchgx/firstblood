/**
 * 
 */
package cn.net.firstblood.framework.util;

import java.security.MessageDigest;

import cn.net.firstblood.framework.exception.FbRunTimeException;

/**
 * 
 * @author gangxiang.chengx
 * @version $Id: MD5Util.java, v 0.1 2017年2月4日 下午3:00:25 gangxiang.chengx Exp $
 */
public class MD5Util {
	/**
	 * MD5签名
	 * @param src
	 * @return
	 */
	public final static String signMD5(String src) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = src.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			
			return new String(str);
		} catch (Exception e) {
			throw new FbRunTimeException(e);
		}
	}
}