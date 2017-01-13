/**
 * 
 */
package cn.net.firstblood.framework.notifier;

import java.net.URLEncoder;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;

import cn.net.firstblood.framework.notifier.model.ConfigStore;
import cn.net.firstblood.framework.notifier.model.WeChatIMConfPO;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class WeChatIM {
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
	public static synchronized String notify(String content) {
		if(DateUtil.addTime(lastSendTime, 30).after(new Date())){
			LoggerUtil.COMMON.info("疲劳度控制不发送微信消息,上次发送时间:[lastSendTime:"+DateUtil.format(lastSendTime)+"]");
			return null;
		}
		msgId.addAndGet(1);
		CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
		WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
		try {
			StringEntity entity;
			entity = new StringEntity("{\"BaseRequest\":{\"Uin\":2685885737,\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\"e541241359894677\"},\"Msg\":{\"Type\":1,\"Content\":\""+content+"\",\"FromUserName\":\""+weChatIMConfPO.getFromName()+"\",\"ToUserName\":\""+weChatIMConfPO.getToName()+"\",\"LocalID\":\""+msgId.get()+"\",\"ClientMsgId\":\""+msgId.get()+"\"}}","utf-8");
			entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json"); 
	        HttpPost post = new HttpPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?"+weChatIMConfPO.getPassTicket()); 
	        post.setEntity(entity);
	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
	        post.addHeader(new BasicHeader("Cookie",weChatIMConfPO.getCookie()));
	        String result = HttpClientUtil.paseResponse(client.execute(post));
	        lastSendTime = new Date();
	        return result;
		} catch (Exception e) {
			LoggerUtil.COMMON.error("发送微信消息异常",e);
		}
		return null;
	}
	
	public static synchronized String getMsg() {
		if(DateUtil.addTime(lastSendTime, 30).after(new Date())){
			LoggerUtil.COMMON.info("疲劳度控制不发送微信消息,上次发送时间:[lastSendTime:"+DateUtil.format(lastSendTime)+"]");
			return null;
		}
		msgId.addAndGet(1);
		CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
		WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
		try {
			StringEntity entity;
			entity = new StringEntity("{\"BaseRequest\":{\"Uin\":1704670422,\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\"e541241359894677\"},\"SyncKey\":{\"Count\":6,\"List\":[{\"Key\":1,\"Val\":640241013},{\"Key\":2,\"Val\":640241022},{\"Key\":3,\"Val\":640241013},{\"Key\":11,\"Val\":640241012},{\"Key\":201,\"Val\":"+new Date().getTime()+"},{\"Key\":1000,\"Val\":1447309230}]},\"rr\":89027555}","utf-8");
			entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json"); 
	        HttpPost post = new HttpPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid="+weChatIMConfPO.getSid()+"&skey="+weChatIMConfPO.getSkey()+"&pass_ticket="+weChatIMConfPO.getPassTicket()); 
	        post.setEntity(entity);
	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
	        post.addHeader(new BasicHeader("Cookie",weChatIMConfPO.getCookie()));
	        String result = HttpClientUtil.paseResponse(client.execute(post));
	        lastSendTime = new Date();
	        return result;
		} catch (Exception e) {
			LoggerUtil.COMMON.error("发送微信消息异常",e);
		}
		return null;
	}
	
	public static String receive(){
		CloseableHttpClient closeableHttpClient = HttpClientUtil.createSSLClientDefault();
		WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
		return HttpClientUtil.doHttpsGet(weChatIMConfPO.getReceiveAddr(),closeableHttpClient,weChatIMConfPO.getCookie());
	}
	
	public static void main(String args[]){
		WeChatIMConfPO config = new WeChatIMConfPO();
		config.setCookie("pgv_pvid=2010687370; _ga=GA1.2.1396463380.1442479853; 3g_guest_id=-9105595419252989952; pt2gguin=o0549891545; o_cookie=549891545; ptcz=b37285a5cd8a7665df8c4748a76ee442e6592b3e78cfc912819ffef1e2dab519; ts_refer=www.baidu.com/link; ts_uid=6959170510; pgv_pvi=3987995648; RK=mFeuaf2oE2; pgv_si=s4940883968; webwx_data_ticket=AQZ3QDXDUfyOBnKUiSUuE+ou; mm_lang=zh_CN");
		config.setFromName("@dce78e088366bdd8710f40a2f5defa980f3c201dcf1f8c4873363032eba7c0b7");
		config.setPassTicket("pass_ticket=afcT7nagDK5uP6%2BP%2FKj27gMJbjxKKCLL93%2BMsP0H0gpS4ZM9M%2FtEVofDOXv1RzAr");
		config.setReceiveAddr("https://webpush.weixin.qq.com/cgi-bin/mmwebwx-bin/synccheck?r="+new Date().getTime()+"&skey=%40crypt_adab0c28_5f7dec74ca62b3cdb5ff003f651a54b5&sid=svBt%2BOpUV%2FR3h6o7&uin=2685885737&deviceid=e083429773003918&synckey=1_628900409%7C2_628900581%7C3_628900538%7C11_628900038%7C201_1446984527%7C1000_1446977973&_="+(new Date().getTime()-1000));
		config.setSid("svBt+OpUV/R3h6o7");
		config.setSkey("@crypt_adab0c28_5f7dec74ca62b3cdb5ff003f651a54b5");
		config.setToName("@842ea1599a599f7936208d354ca09d4cd6f1cddf9d8ab559dab6d4655e45a439");
		ConfigStore.setConfig(config);
		
		lastSendTime = DateUtil.parseDate("2015-01-01 00:00:00");
		System.out.println(DateUtil.format(new Date()));
		//System.out.println(notify("你好1223"));
		//System.out.println(receive());
		//System.out.println(getMsg());
		//System.out.println(URLEncoder.encode("svBt+OpUV/R3h6o7"));
		System.out.println(doPost("https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid="+URLEncoder.encode("svBt+OpUV/R3h6o7")+"&r="+new Date().getTime()+"&skey="+URLEncoder.encode("@crypt_adab0c28_5f7dec74ca62b3cdb5ff003f651a54b5")));
		System.out.println(DateUtil.format(new Date()));
		
//		System.out.println(DateUtil.format(new Date(1447231656411L)));
//		System.out.println(DateUtil.format(new Date(1447228674240L)));
	}
	
	private static String doPost(String nul){
		CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
		WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
		try {
			StringEntity entity;
			entity = new StringEntity("{\"BaseRequest\":{\"Uin\":1704670422,\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\"e541241359894677\"},\"SyncKey\":{\"Count\":7,\"List\":[{\"Key\":1,\"Val\":640241012},{\"Key\":2,\"Val\":640241012},{\"Key\":3,\"Val\":640241012},{\"Key\":11,\"Val\":640241012},{\"Key\":201,\"Val\":1447314848},{\"Key\":1000,\"Val\":1447309230},{\"Key\":1001,\"Val\":1447309230}]},\"rr\":89027555}","utf-8");
			entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json"); 
	        HttpPost post = new HttpPost(nul); 
	        //post.setEntity(entity);
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
