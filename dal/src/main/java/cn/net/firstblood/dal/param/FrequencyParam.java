package cn.net.firstblood.dal.param;

import cn.net.firstblood.dal.enums.FrequencyBizType;
import cn.net.firstblood.dal.model.FrequencyDO;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class FrequencyParam extends PageParam<FrequencyDO> {
	
	/**
	 * 控制唯一性key
	 * {@link FrequencyBizType#bulidUkKey(String)}
	 */
	private String ukKey;

	public String getUkKey() {
		return ukKey;
	}

	public void setUkKey(String ukKey) {
		this.ukKey = ukKey;
	}
	
}