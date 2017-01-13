/**
 * 
 */
package cn.net.firstblood.dal.model;

import cn.net.firstblood.dal.enums.RecordType;

/**
 * @author gangxiang.chengx
 *
 */
public class RecordDO extends EntityObject{
	/**
	 * 类型
	 */
	private RecordType type;
	
	/**
	 * 值
	 */
	private Double value;

	public RecordType getType() {
		return type;
	}

	public void setType(RecordType type) {
		this.type = type;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
}
