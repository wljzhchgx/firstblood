package cn.net.firstblood.dal.dao.impl;

import cn.net.firstblood.dal.dao.RecordDao;
import cn.net.firstblood.dal.model.RecordDO;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class RecordDaoImpl extends BaseDaoImpl<RecordDO> implements RecordDao {

	public RecordDaoImpl() {
		super(RecordDO.class);
	}
}