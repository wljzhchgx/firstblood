package cn.net.firstblood.framework.notifier.model.wechatim;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author gangxiang.chengx
 * @version $Id: WeChatIMUpLoadResPO.java, v 0.1 2017年2月4日 下午3:20:45 gangxiang.chengx Exp $
 */
public class SyncCheckRespPO {
	@JSONField(name = "BaseResponse")
	private BaseResponsePO baseResponse;
	
	@JSONField(name = "MsgID")
	private String msgID;
	
	@JSONField(name = "LocalID")
	private String localID;

	public BaseResponsePO getBaseResponse() {
		return baseResponse;
	}

	public void setBaseResponse(BaseResponsePO baseResponse) {
		this.baseResponse = baseResponse;
	}

	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}

	public String getLocalID() {
		return localID;
	}

	public void setLocalID(String localID) {
		this.localID = localID;
	}
}
