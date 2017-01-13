/**
 * 
 */
package cn.net.firstblood.framework.enums;

/**
 * @author gangxiang.chengx
 *
 */
public enum ChannelType {
	/**
	 * 微信
	 */
	WE_CHAT("微信"),
	
	/**
	 * 微信
	 */
	QQ("QQ");
	
	private String desc;
	
	private ChannelType(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
}
