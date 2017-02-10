package cn.net.firstblood.framework.notifier.model.wechatim;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author gangxiang.chengx
 * @version $Id: WeChatIMUpLoadResPO.java, v 0.1 2017年2月4日 下午3:20:45 gangxiang.chengx Exp $
 */
public class InitRespPO {
	@JSONField(name = "BaseResponse")
	private BaseResponsePO baseResponse;
	
	@JSONField(name = "SyncKey")
	private SyncKeyPO syncKey;

	public BaseResponsePO getBaseResponse() {
		return baseResponse;
	}

	public void setBaseResponse(BaseResponsePO baseResponse) {
		this.baseResponse = baseResponse;
	}

	public SyncKeyPO getSyncKey() {
		return syncKey;
	}

	public void setSyncKey(SyncKeyPO syncKey) {
		this.syncKey = syncKey;
	}

}
