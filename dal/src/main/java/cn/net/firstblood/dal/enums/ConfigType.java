/**
 * 
 */
package cn.net.firstblood.dal.enums;

/**
 * @author gangxiang.chengx
 *
 */
public enum ConfigType {
	/**
	 * 微信配置
	 */
	WECHAT_IM_CONFIG("微信配置","cn.net.firstblood.framework.notifier.model.WeChatIMConfPO");
	
	private String desc;
	
	private String className;
	
	private ConfigType(String desc,String className) {
		this.desc = desc;
		this.className = className;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getClassName() {
		return className;
	}
}
