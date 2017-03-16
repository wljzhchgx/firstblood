/**
 * 
 */
package cn.net.firstblood.dal.model;

import cn.net.firstblood.dal.enums.FrequencyBizType;


/**
 * @author gangxiang.chengx
 *
 */
public class FrequencyDO extends EntityObject{
	
	/**
	 * 控制唯一性key
	 * {@link FrequencyBizType#bulidUkKey(String)}
	 */
	private String ukKey;
	
	/**
	 * 次数
	 */
	private Long times;

	public String getUkKey() {
		return ukKey;
	}

	public void setUkKey(String ukKey) {
		this.ukKey = ukKey;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}
	
}
