/**
 * 
 */
package cn.net.firstblood.framework.notifier.service;

/**
 * 即时通讯、实时传讯
 * @author gangxiang.chengx
 *
 */
public interface IMService {
	/**
	 * 通知，有疲劳度控制
	 * @param msg
	 * @return
	 */
	String notify(String msg);
	
	/**
	 * 马上发消息
	 * @param msg
	 * @return
	 */
	String notifyIm(String msg);
}
