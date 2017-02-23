package cn.net.firstblood.framework.notifier.model.wechatim;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取消息请求
 * @author gangxiang.chengx
 * @version $Id: WebwxSyncResPO.java, v 0.1 2017年2月8日 下午3:32:15 gangxiang.chengx Exp $
 */
public class WebwxSyncRespPO {
	
	@JSONField(name = "BaseRequest")
	private BaseRequestPO baseRequest;
	
	@JSONField(name = "AddMsgList")
	private List<MsgPO> addMsgList;
	
	@JSONField(name = "SyncKey")
	private SyncKeyPO syncKey;
	
	@JSONField(name = "SyncCheckKey")
	private SyncKeyPO syncCheckKey;

	public BaseRequestPO getBaseRequest() {
		return baseRequest;
	}

	public void setBaseRequest(BaseRequestPO baseRequest) {
		this.baseRequest = baseRequest;
	}

	public List<MsgPO> getAddMsgList() {
		return addMsgList;
	}

	public void setAddMsgList(List<MsgPO> addMsgList) {
		this.addMsgList = addMsgList;
	}

	public SyncKeyPO getSyncKey() {
		return syncKey;
	}

	public void setSyncKey(SyncKeyPO syncKey) {
		this.syncKey = syncKey;
	}

	public SyncKeyPO getSyncCheckKey() {
		return syncCheckKey;
	}

	public void setSyncCheckKey(SyncKeyPO syncCheckKey) {
		this.syncCheckKey = syncCheckKey;
	}
}
