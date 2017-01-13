/**
 * 
 */
package cn.net.firstblood.dal.enums;

/**
 * @author gangxiang.chengx
 *
 */
public enum RecordType {
	/**
	 * 招财宝3-6个月最大变现利率
	 */
	ZCB_M0306_MAX_RATE("招财宝3-6个月最大变现利率"),
	
	/**
	 * 招财宝6-12个月最大变现利率
	 */
	ZCB_M0612_MAX_RATE("招财宝6-12个月最大变现利率"),
	
	/**
	 * 招财宝12-24个月最大变现利率
	 */
	ZCB_M1224_MAX_RATE("招财宝12-24个月最大变现利率"),
	
	/**
	 * 招财宝24个月以上最大变现利率
	 */
	ZCB_M249999_MAX_RATE("招财宝24个月以上最大变现利率");
	
	private String desc;
	
	private RecordType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
