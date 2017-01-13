/**
 * 
 */
package cn.net.firstblood.biz.common;

/**
 * @author gangxiang.chengx
 *
 */
public abstract class CheckPO {
	/**
	 * 英文标识
	 */
	private String enName;
	
	/**
	 * 中文名
	 */
	private String name;
	
	/**
	 * url
	 */
	private String url;
	
	/**
	 * QQ通知
	 */
	private Boolean isQQNotify;
	
	/**
	 * 微信通知
	 */
	private Boolean isWeChatNotify;

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsQQNotify() {
		return isQQNotify;
	}

	public void setIsQQNotify(Boolean isQQNotify) {
		this.isQQNotify = isQQNotify;
	}

	public Boolean getIsWeChatNotify() {
		return isWeChatNotify;
	}

	public void setIsWeChatNotify(Boolean isWeChatNotify) {
		this.isWeChatNotify = isWeChatNotify;
	}
	
}
