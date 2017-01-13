/**
 * 
 */
package cn.net.firstblood.biz.wjs.model;

import java.util.HashMap;
import java.util.Map;

import cn.net.firstblood.biz.common.CheckPO;

/**
 * @author gangxiang.chengx
 *
 */
public class WjsCheckPO extends CheckPO {
	
	public WjsCheckPO(String name,String enName,String url,Boolean isQQNotify,Boolean isWeChatNotify,String series){
		this.setEnName(enName);
		this.setIsQQNotify(isQQNotify);
		this.setIsWeChatNotify(isWeChatNotify);
		this.setName(enName);
		this.setUrl(url);
		this.postParam = new HashMap<String,String>();
		postParam.put("page", "1");
		postParam.put("rows", "6");
		postParam.put("orderFields", "");
		postParam.put("series", series);
	}
	
	/**
	 * post参数
	 */
	private Map<String,String> postParam;
	
	/**
	 * 信息
	 */
	private String msg;
	
	public Map<String, String> getPostParam() {
		return postParam;
	}

	public void setPostParam(Map<String, String> postParam) {
		this.postParam = postParam;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
