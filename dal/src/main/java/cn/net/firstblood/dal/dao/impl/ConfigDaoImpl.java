package cn.net.firstblood.dal.dao.impl;

import java.util.List;

import org.springframework.util.CollectionUtils;

import cn.net.firstblood.dal.dao.ConfigDao;
import cn.net.firstblood.dal.enums.ConfigType;
import cn.net.firstblood.dal.model.ConfigDO;
import cn.net.firstblood.dal.param.ConfigParam;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class ConfigDaoImpl extends BaseDaoImpl<ConfigDO> implements ConfigDao {

	public ConfigDaoImpl() {
		super(ConfigDO.class);
	}

	@Override
	public ConfigDO queryByType(ConfigType type) {
		if(type == null){
			return null;
		}
		ConfigParam param = new ConfigParam();
		param.setType(type);
		List<ConfigDO> resultList = this.queryByParam(param);
		if(CollectionUtils.isEmpty(resultList) || resultList.size() != 1){
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public Integer updateByType(ConfigDO config) {
		ConfigDO configDB = this.queryByType(config.getType());
		if(configDB == null){
			return 0;
		}
		config.setId(configDB.getId());
		return this.update(config);
	}
}