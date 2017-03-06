/**
 * mybank.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package cn.net.firstblood.framework.notifier;

import java.util.Date;

import org.apache.commons.mail.HtmlEmail;

import cn.net.firstblood.framework.model.FBConfigPO;
import cn.net.firstblood.framework.util.BeanUtil;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 * @version $Id: EmailNotifier.java, v 0.1 2017年2月23日 下午7:28:17 gangxiang.chengx Exp $
 */
public class EmailNotifier {
	private static int count = 0;
	
	public static synchronized void notify(String toAddress,String subject,String content){
		if(count>50){
			LoggerUtil.COMMON.warn("发送邮件超过上限 不再发送");
			return;
		}
		try{
		// 创建邮件渠道
		HtmlEmail htmlEmail = new HtmlEmail();
		// 这里是发送服务器的名字：，163的如下：
		htmlEmail.setHostName("smtpdm.aliyun.com");
		// 设置服务器端口号
		htmlEmail.setSmtpPort(25);
		// 编码集的设置
		htmlEmail.setCharset("UTF-8");
		// 如果需要认证信息的话，设置认证：用户名-密码。
		// 分别为发件人在邮件服务器上的注册名称和密码
		FBConfigPO fBConfigPO = BeanUtil.getNean("fBConfigPO");
		htmlEmail.setAuthentication(fBConfigPO.getSmtpUserName(), fBConfigPO.getSmtpPassWord());
		// 收件人的邮箱
		htmlEmail.addTo(toAddress);
		// 设置回复邮箱地址
		htmlEmail.addReplyTo("549891545@qq.com");
		// 发送人的邮箱
		htmlEmail.setFrom("laotie@firstblood.net.cn", "老铁");
		// 标题
		htmlEmail.setSubject(subject);
		// 要发送的信息
		htmlEmail.setMsg(content+"</br>["+DateUtil.format(new Date())+"]");

		LoggerUtil.COMMON.info("htmlEmail.send result[count:"+count+"]"+htmlEmail.send());
		} catch (Exception e) {
			LoggerUtil.COMMON.error("发送邮件消息异常[count"+count+"]",e);
		}finally{
			count ++;
		}
		
	}

	
}
