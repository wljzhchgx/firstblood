package cn.net.firstblood.dal.dao;

import cn.net.firstblood.dal.enums.FrequencyBizType;
import cn.net.firstblood.dal.model.FrequencyDO;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public interface FrequencyDao extends BaseDao<FrequencyDO> {
	/**
	 * 增加一次
	 * @param fcBiz
	 * @param bizKey
	 * @return
	 */
	boolean addOneTimeByUkKey(FrequencyBizType fcBiz,String bizKey);
}