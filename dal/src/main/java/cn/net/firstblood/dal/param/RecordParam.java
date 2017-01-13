package cn.net.firstblood.dal.param;

import java.util.Date;
import java.util.List;

import cn.net.firstblood.dal.enums.RecordType;
import cn.net.firstblood.dal.model.RecordDO;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class RecordParam extends PageParam<RecordDO> {
	/**
	 * 类型
	 */
	private RecordType type;
	
	private Date gmtCreate;
	
	private List<Date> gmtCreateList;

	public RecordType getType() {
		return type;
	}

	public void setType(RecordType type) {
		this.type = type;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public List<Date> getGmtCreateList() {
		return gmtCreateList;
	}

	public void setGmtCreateList(List<Date> gmtCreateList) {
		this.gmtCreateList = gmtCreateList;
	}
	
}
