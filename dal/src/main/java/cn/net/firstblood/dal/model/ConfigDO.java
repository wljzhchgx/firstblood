/**
 * 
 */
package cn.net.firstblood.dal.model;

import cn.net.firstblood.dal.enums.ConfigType;

/**
 * @author gangxiang.chengx
 *
 */
public class ConfigDO extends EntityObject{
	/**
	 * 类型
	 */
	private ConfigType type;
	
	/**
	 * 配置
	 */
	private String context;

	public ConfigType getType() {
		return type;
	}

	public void setType(ConfigType type) {
		this.type = type;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
