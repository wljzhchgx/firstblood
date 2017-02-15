package cn.net.firstblood.dal.dao;

import java.util.Date;

import cn.net.firstblood.dal.model.MemorialDayDO;

/**
 * 
 * @author gangxiang.chengx
 * @version $Id: MemorialDayDao.java, v 0.1 2017年2月4日 下午5:00:13 gangxiang.chengx Exp $
 */
public interface MemorialDayDao extends BaseDao<MemorialDayDO> {
	/**
	 * 获取指定日期的内容
	 * @param date
	 * @return
	 */
	MemorialDayDO getUniqeMDayByDate(Date date);
}