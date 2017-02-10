package cn.net.firstblood.framework.notifier.model.wechatim;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author gangxiang.chengx
 * @version $Id: MsgPO.java, v 0.1 2017年2月8日 下午8:14:43 gangxiang.chengx Exp $
 */
public class MsgPO {
	@JSONField(name = "FromUserName")
	private String fromUserName;
	
	@JSONField(name = "ToUserName")
	private String toUserName;
	
	@JSONField(name = "Content")
	private String content;

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
