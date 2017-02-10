package cn.net.firstblood.framework.notifier.model.wechatim;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author gangxiang.chengx
 * @version $Id: BaseResponse.java, v 0.1 2017年2月4日 下午3:23:12 gangxiang.chengx Exp $
 */
public class BaseResponsePO {
	//不健康状态-发消息不成功
	public static final String RET_3 = "3";
	
	@JSONField(name = "Ret")
	private String ret;
	
	@JSONField(name = "ErrMsg")
	private String errMsg;

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
