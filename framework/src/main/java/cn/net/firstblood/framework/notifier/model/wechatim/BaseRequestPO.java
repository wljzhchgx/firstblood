package cn.net.firstblood.framework.notifier.model.wechatim;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author gangxiang.chengx
 * @version $Id: BaseRequestPO.java, v 0.1 2017年2月8日 下午3:29:29 gangxiang.chengx Exp $
 */
public class BaseRequestPO {
	
	@JSONField(name = "Uin")
	private String uin;
	
	@JSONField(name = "Sid")
	private String sid;

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
}
