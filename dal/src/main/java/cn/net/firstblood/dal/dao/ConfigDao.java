package cn.net.firstblood.dal.dao;

import cn.net.firstblood.dal.enums.ConfigType;
import cn.net.firstblood.dal.model.ConfigDO;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public interface ConfigDao extends BaseDao<ConfigDO> {
	/**
	 * 通过类型查询配置
	 * @return
	 */
	ConfigDO queryByType(ConfigType type);
	
	/**
	 * 通过类型更新
	 * @param config
	 * @return
	 */
	Integer updateByType(ConfigDO config);
}