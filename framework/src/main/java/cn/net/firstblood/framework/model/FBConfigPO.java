package cn.net.firstblood.framework.model;

/**
 * @author gangxiang.chengx
 * @version $Id: FBConfigPO.java, v 0.1 2017年2月23日 下午7:54:05 gangxiang.chengx Exp $
 */
public class FBConfigPO {
	private String smtpUserName;
	
	private String smtpPassWord;

	public String getSmtpUserName() {
		return smtpUserName;
	}

	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}

	public String getSmtpPassWord() {
		return smtpPassWord;
	}

	public void setSmtpPassWord(String smtpPassWord) {
		this.smtpPassWord = smtpPassWord;
	}
	
}
