package cn.net.firstblood.framework.notifier.model.wechatim;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取消息请求
 * @author gangxiang.chengx
 * @version $Id: WebwxSyncResPO.java, v 0.1 2017年2月8日 下午3:32:15 gangxiang.chengx Exp $
 */
public class WebwxSyncReqPO {
	
	@JSONField(name = "BaseRequest")
	private BaseRequestPO baseRequest;
	
	@JSONField(name = "rr")
	private String rr;
	
	@JSONField(name = "SyncKey")
	private SyncKeyPO syncKey;

	public BaseRequestPO getBaseRequest() {
		return baseRequest;
	}

	public void setBaseRequest(BaseRequestPO baseRequest) {
		this.baseRequest = baseRequest;
	}

	public String getRr() {
		return rr;
	}

	public void setRr(String rr) {
		this.rr = rr;
	}

	public SyncKeyPO getSyncKey() {
		return syncKey;
	}

	public void setSyncKey(SyncKeyPO syncKey) {
		this.syncKey = syncKey;
	}
	
}
