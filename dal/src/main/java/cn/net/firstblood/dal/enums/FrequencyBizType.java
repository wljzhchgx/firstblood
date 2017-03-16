/**
 * 
 */
package cn.net.firstblood.dal.enums;

import java.util.Date;

import cn.net.firstblood.framework.exception.FbRunTimeException;
import cn.net.firstblood.framework.util.DateUtil;

/**
 * @author gangxiang.chengx
 *
 */
public enum FrequencyBizType {
	/**
	 * 邮件
	 */
	EMAIL_TOTAL_DAY("系统日邮件总投递量",200);
	
	private String desc;

	private Integer maxLimit;
	
	private FrequencyBizType(String desc,Integer maxLimit) {
		this.desc = desc;
		this.maxLimit = maxLimit;
	}

	public String getDesc() {
		return desc;
	}
	
	public Integer getMaxLimit() {
		return maxLimit;
	}

	public String bulidUkKey(String bizKey){
		String prefix = this.name()+"_";
		if(this==EMAIL_TOTAL_DAY){
			return prefix+DateUtil.format(new Date(), "yyyy-MM-dd 00:00:00");
		}
		throw new FbRunTimeException("not supprot");
	}
	
}
