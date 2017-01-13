package cn.net.firstblood.framework.notifier.model;

import cn.net.firstblood.framework.model.BaseJsonPO;

public class WeChatIMConfPO extends BaseJsonPO<WeChatIMConfPO>{
	
	public WeChatIMConfPO() {
		super(WeChatIMConfPO.class);
	}

	private String cookie;
	
	private String sid;
	
	private String skey;
	
	private String passTicket;
	
	private String fromName;
	
	private String toName;
	
	private String receiveAddr;

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public String getPassTicket() {
		return passTicket;
	}

	public void setPassTicket(String passTicket) {
		this.passTicket = passTicket;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getReceiveAddr() {
		return receiveAddr;
	}

	public void setReceiveAddr(String receiveAddr) {
		this.receiveAddr = receiveAddr;
	}
	
}
