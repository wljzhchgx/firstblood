package cn.net.firstblood.framework.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author gangxiang.chengx
 * @version $Id: BaseMapPO.java, v 0.1 2017年2月8日 下午3:35:44 gangxiang.chengx Exp $
 */
public class BaseMapPO {
	@JSONField(name = "Key")
	private String key;
	
	@JSONField(name = "Val")
	private String val;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
	
}
