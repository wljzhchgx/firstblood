package cn.net.firstblood.dal.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import cn.net.firstblood.dal.dao.MemorialDayDao;
import cn.net.firstblood.dal.model.MemorialDayDO;
import cn.net.firstblood.dal.param.MemorialDayParam;
import cn.net.firstblood.framework.exception.FbRunTimeException;
import cn.net.firstblood.framework.util.DateUtil;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class MemorialDayDaoImpl extends BaseDaoImpl<MemorialDayDO> implements MemorialDayDao {

	public MemorialDayDaoImpl() {
		super(MemorialDayDO.class);
	}

	@Override
	public MemorialDayDO getUniqeMDayByDate(Date date) {
		Date begin = DateUtil.getMorning(date);
		Date end = DateUtil.addTime(DateUtil.addDays(begin, 1), -1);
		MemorialDayParam param = new MemorialDayParam();
		param.setDateBegin(begin);
		param.setDateEnd(end);
		List<MemorialDayDO> mDayList = this.queryByParam(param);
		if(CollectionUtils.isEmpty(mDayList)){
			return null;
		}
		if(mDayList.size() > 1){
			throw new FbRunTimeException("mDayList.size() > 1");
		}
		
		return mDayList.get(0);
	}
}