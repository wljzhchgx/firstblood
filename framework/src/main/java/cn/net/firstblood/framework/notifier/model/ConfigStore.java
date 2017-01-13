/**
 * 
 */
package cn.net.firstblood.framework.notifier.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gangxiang.chengx
 *
 */
public class ConfigStore {
	private static final Map<String,Object> CONFIG_MAP = new HashMap<String,Object>();
	
	/**
	 * 获取配置
	 * @param calss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T  getConfig(Class<T> calss){
		if(CONFIG_MAP.get(calss.getCanonicalName()) == null){
			return null;
		}
		return (T)CONFIG_MAP.get(calss.getCanonicalName());
	}
	
	/**
	 * 设置配置
	 * @param value
	 */
	public static void setConfig(Object value){
		CONFIG_MAP.put(value.getClass().getCanonicalName(), value);
	}
	
	public static void main(String args[]){
		WeChatIMConfPO test = new WeChatIMConfPO();
		test.setCookie("cookie");
		test.setFromName("fromName");
		test.setPassTicket("passTicket");
		test.setReceiveAddr("receiveAddr");
		test.setSid("sid");
		test.setSkey("skey");
		test.setToName("toName");
		System.out.println(test.getJsonFromObject(test));
	}
	
	
}
