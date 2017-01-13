/**
 * 
 */
package cn.net.firstblood.biz.model;

/**
 * @author gangxiang.chengx
 *
 */
public class ZcbPO {
	public ZcbPO(){
		
	}
	
	public ZcbPO(String name,String enName,String maxRateUrl,Double curMaxRate,Double warnRate,Boolean isQQNotify,Boolean isWeChatNotify){
		this.name = name;
		this.enName = enName;
		this.maxRateUrl = maxRateUrl;
		this.curMaxRate = curMaxRate;
		this.warnRate = warnRate;
		this.isQQNotify = isQQNotify;
		this.isWeChatNotify = isWeChatNotify;
	}
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 英文名
	 */
	private String enName;
	
	/**
	 * 访问最大利率url
	 */
	private String maxRateUrl;
	
	/**
	 * 当前最大利率
	 */
	private Double curMaxRate;
	
	/**
	 * 通知阈值
	 */
	private Double warnRate;
	
	/**
	 * QQ通知
	 */
	private Boolean isQQNotify;
	
	/**
	 * 微信通知
	 */
	private Boolean isWeChatNotify;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getMaxRateUrl() {
		return maxRateUrl;
	}

	public void setMaxRateUrl(String maxRateUrl) {
		this.maxRateUrl = maxRateUrl;
	}

	public Double getCurMaxRate() {
		return curMaxRate;
	}

	public void setCurMaxRate(Double curMaxRate) {
		this.curMaxRate = curMaxRate;
	}

	public Double getWarnRate() {
		return warnRate;
	}

	public void setWarnRate(Double warnRate) {
		this.warnRate = warnRate;
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
