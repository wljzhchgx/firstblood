/**
 * 
 */
package cn.net.firstblood.framework.notifier.model;

import java.util.HashMap;
import java.util.Map;

import cn.net.firstblood.framework.notifier.model.wechatim.InitRespPO;

/**
 * @author gangxiang.chengx
 *
 */
public class ConfigStore {
	private static final Map<String,Object> CONFIG_MAP = new HashMap<String,Object>();
	
	private static final String PREFIX="SELF_";
	
	public static final String WECHATRECEIVE ="WECHATRECEIVE";
	
	public static final String WECHATRECEIVE_LATEST_TIME ="WECHATRECEIVE_LATEST_TIME";
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
	
	/**
	 * 设置空
	 * @param value
	 */
	public static void setConfigNull(Object value){
		if(CONFIG_MAP.get(value.getClass().getCanonicalName()) != null){
			CONFIG_MAP.remove(value.getClass().getCanonicalName());
		}
	}
	
	/**
	 * 设置配置
	 * @param value
	 */
	public static void setSelfConfig(String key,Object value){
		CONFIG_MAP.put(PREFIX+key, value);
	}
	
	/**
	 * 获取配置
	 * @param value
	 */
	public static Object getSelfConfig(String key){
		return CONFIG_MAP.get(PREFIX+key);
	}
	
	public static void main(String args[]){
//		WeChatIMConfPO test = new WeChatIMConfPO();
//		test.setCookie("cookie");
//		test.setFromName("fromName");
//		test.setPassTicket("passTicket");
//		test.setReceiveAddr("receiveAddr");
//		test.setSid("sid");
//		test.setSkey("skey");
//		test.setToName("toName");
//		System.out.println(test.getJsonFromObject(test));
		InitRespPO initRespPO = new InitRespPO();
		ConfigStore.setConfig(initRespPO);
		System.out.println(ConfigStore.getConfig(InitRespPO.class));
	}
	
	
}
