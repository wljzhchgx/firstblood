/**
 * 
 */
package cn.net.firstblood.dal.model;


/**
 * 大盘记录
 * @author gangxiang.chengx
 *
 */
public class GrailRecordDO extends EntityObject{
	/**
	 * 代码
	 */
	private String code;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 值
	 */
	private Double value;
	
	/**
	 * 浮动值
	 */
	private Double floatValue;
	
	/**
	 * 浮动率
	 */
	private Double floatRate;
	
	/**
	 * 成交量 亿手
	 */
	private Double dealHands;
	
	/**
	 * 成交额 亿元
	 */
	private Double dealAmt;
	
	/**
	 * 时间戳 yyyy-MM-dd
	 */
	private String date;
	
	/**
	 * 均价
	 */
	private Double avgValue;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(Double floatValue) {
		this.floatValue = floatValue;
	}

	public Double getFloatRate() {
		return floatRate;
	}

	public void setFloatRate(Double floatRate) {
		this.floatRate = floatRate;
	}

	public Double getDealHands() {
		return dealHands;
	}

	public void setDealHands(Double dealHands) {
		this.dealHands = dealHands;
	}

	public Double getDealAmt() {
		return dealAmt;
	}

	public void setDealAmt(Double dealAmt) {
		this.dealAmt = dealAmt;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getAvgValue() {
		return avgValue;
	}

	public void setAvgValue(Double avgValue) {
		this.avgValue = avgValue;
	}
	
}
