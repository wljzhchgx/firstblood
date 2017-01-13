package cn.net.firstblood.dal.param;

import cn.net.firstblood.dal.enums.ConfigType;
import cn.net.firstblood.dal.model.ConfigDO;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class ConfigParam extends PageParam<ConfigDO> {
	/**
	 * 类型
	 */
	private ConfigType type;

	public ConfigType getType() {
		return type;
	}

	public void setType(ConfigType type) {
		this.type = type;
	}

}