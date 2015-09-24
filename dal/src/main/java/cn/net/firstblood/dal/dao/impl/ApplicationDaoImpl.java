package cn.net.firstblood.dal.dao.impl;

import cn.net.firstblood.dal.dao.ApplicationDao;
import cn.net.firstblood.dal.model.ApplicationDO;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class ApplicationDaoImpl extends BaseDaoImpl<ApplicationDO> implements ApplicationDao {

	public ApplicationDaoImpl() {
		super(ApplicationDO.class);
	}
}