/**
 * 
 */
package cn.net.firstblood.framework.notifier.service.impl;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;

import cn.net.firstblood.framework.notifier.model.ConfigStore;
import cn.net.firstblood.framework.notifier.model.WeChatIMConfPO;
import cn.net.firstblood.framework.notifier.service.IMService;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class WeChatIMServiceImpl implements IMService{
	private static AtomicLong msgId = new AtomicLong(0);
	static{
		double random = Math.random();
		while(random*1000<100){
			random = Math.random();
		}
		msgId = new AtomicLong((long)(random*1000)*100000000000000L);
		LoggerUtil.COMMON.info("msgId="+msgId);
	}
	
	private static Date lastSendTime = new Date();
	@Override
	public String notify(String msg) {
		if(DateUtil.addTime(lastSendTime, 30).after(new Date())){
			LoggerUtil.COMMON.info("疲劳度控制不发送微信消息,上次发送时间:[lastSendTime:"+DateUtil.format(lastSendTime)+"]");
			return null;
		}
		String result = getNotifyResult(msg);
		lastSendTime = new Date();
		return result;
	}

	@Override
	public String notifyIm(String msg) {
		return getNotifyResult(msg);
	}
	
	private String getNotifyResult(String sendMsg){
		msgId.addAndGet(1);
		CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
		WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
		try {
			StringEntity entity;
			entity = new StringEntity("{\"BaseRequest\":{\"Uin\":2685885737,\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\"e541241359894677\"},\"Msg\":{\"Type\":1,\"Content\":\""+sendMsg+"\",\"FromUserName\":\""+weChatIMConfPO.getFromName()+"\",\"ToUserName\":\""+weChatIMConfPO.getToName()+"\",\"LocalID\":\""+msgId.get()+"\",\"ClientMsgId\":\""+msgId.get()+"\"}}","utf-8");
			entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json"); 
	        HttpPost post = new HttpPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?"+weChatIMConfPO.getPassTicket()); 
	        post.setEntity(entity);
	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
	        post.addHeader(new BasicHeader("Cookie",weChatIMConfPO.getCookie()));
	        String result = HttpClientUtil.paseResponse(client.execute(post));
	        
	        return result;
		} catch (Exception e) {
			LoggerUtil.COMMON.error("发送微信消息异常",e);
		}
		return null;
	}

}
