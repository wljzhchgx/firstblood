package cn.net.firstblood.dal.param;

import java.util.Date;

import cn.net.firstblood.dal.model.MemorialDayDO;

/**
 * 
 * @author gangxiang.chengx
 * @version $Id: MemorialDayParam.java, v 0.1 2017年2月4日 下午4:44:25 gangxiang.chengx Exp $
 */
public class MemorialDayParam extends PageParam<MemorialDayDO> {
	private Date dateBegin;
	
	private Date dateEnd;

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	
}
