/**
 * 
 */
package cn.net.firstblood.framework.notifier;

import java.io.File;
import java.security.Security;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import cn.net.firstblood.framework.enums.WeChatMsgType;
import cn.net.firstblood.framework.notifier.model.ConfigStore;
import cn.net.firstblood.framework.notifier.model.WeChatIMConfPO;
import cn.net.firstblood.framework.notifier.model.wechatim.UpLoadMediaResPO;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.LoggerUtil;
import cn.net.firstblood.framework.util.MD5Util;

/**
 * @author gangxiang.chengx
 *
 */
public class WeChatIM {
	public static final Map<Integer,String> EMOJI_TIME = new HashMap<Integer,String>();
	static{
		EMOJI_TIME.put(0, "\ue02f");
		EMOJI_TIME.put(1, "\ue024");
		EMOJI_TIME.put(2, "\ue025");
		EMOJI_TIME.put(3, "\ue026");
		EMOJI_TIME.put(4, "\ue027");
		EMOJI_TIME.put(5, "\ue028");
		EMOJI_TIME.put(6, "\ue029");
		EMOJI_TIME.put(7, "\ue02a");
		EMOJI_TIME.put(8, "\ue02b");
		EMOJI_TIME.put(9, "\ue02c");
		EMOJI_TIME.put(10, "\ue02d");
		EMOJI_TIME.put(11, "\ue02e");
	}
	
	private static AtomicLong msgId = new AtomicLong(0);
	//通知
	private static final String UID = "2685885737";
	//本人
	private static final String ME_UID = "1704670422";
	static{
		double random = Math.random();
		while(random*1000<100){
			random = Math.random();
		}
		msgId = new AtomicLong((long)(random*1000)*100000000000000L);
		LoggerUtil.COMMON.info("msgId="+msgId);
	}
	
	private static Date lastSendTime = new Date();
	/**
	 * 发送消息
	 * @param content
	 * @return
	 */
	public static synchronized String notify(String content,WeChatMsgType msgType) {
		if(DateUtil.addTime(lastSendTime, 30).after(new Date())){
			LoggerUtil.COMMON.info("疲劳度控制不发送微信消息,上次发送时间:[lastSendTime:"+DateUtil.format(lastSendTime)+"]");
			//return null;
		}
		msgId.addAndGet(1);
		CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
		WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
		try {
			String postUrl="";
			if(msgType == WeChatMsgType.TEXT){
				postUrl="https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?"+weChatIMConfPO.getPassTicket();
				content = content+"\n["+DateUtil.format(new Date())+"]";
			}
			String mediaId = "";
			if(msgType == WeChatMsgType.IMAGE){
				postUrl="https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsgimg?fun=async&f=json";
				mediaId=content;
				content = "";
			}
			StringEntity entity;
			entity = new StringEntity("{\"BaseRequest\":{\"Uin\":"+UID+",\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\"e541241359894677\"},\"Msg\":{\"Type\":"+msgType.getKey()+",\"Content\":\""+content+"\",\"MediaId\":\""+mediaId+"\",\"FromUserName\":\""+weChatIMConfPO.getFromName()+"\",\"ToUserName\":\""+weChatIMConfPO.getToName()+"\",\"LocalID\":\""+msgId.get()+"\",\"ClientMsgId\":\""+msgId.get()+"\"}}","utf-8");
			entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json"); 
	        
	        HttpPost post = new HttpPost(postUrl); 
	        post.setEntity(entity);
	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
	        post.addHeader(new BasicHeader("Cookie",weChatIMConfPO.getCookie()));
	        Security.insertProviderAt(new BouncyCastleProvider(),1);
	        String result = HttpClientUtil.paseResponse(client.execute(post));
	        lastSendTime = new Date();
	        return result;
		} catch (Exception e) {
			LoggerUtil.COMMON.error("发送微信消息异常",e);
		}
		return null;
	}
	
	public static synchronized String uploadMedia(String absolutePath){
		try{
			WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
			
			File file = new File(absolutePath);
			FileBody fileBody = new FileBody(file);
			//StringBody stringBody1 = new StringBody("name", ContentType.MULTIPART_FORM_DATA);
			StringBody uploadmediarequest = new StringBody("{\"UploadType\":2,\"BaseRequest\":{\"Uin\":"+UID+",\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\"e541241359894677\"},\"ClientMediaId\":"+new Date().getTime()+",\"TotalLen\":"+file.length()+",\"StartPos\":0,\"DataLen\":"+file.length()+",\"MediaType\":4,\"FromUserName\":\""+weChatIMConfPO.getFromName()+"\",\"ToUserName\":\""+weChatIMConfPO.getToName()+"\",\"FileMd5\":\""+MD5Util.signMD5(absolutePath+DateUtil.format(DateUtil.getCurrentTime()))+"\"}", ContentType.MULTIPART_FORM_DATA);
			CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
			HttpPost post = new HttpPost("https://file.wx.qq.com/cgi-bin/mmwebwx-bin/webwxuploadmedia?f=json");
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.addPart("filename", fileBody);
			//builder.addPart("name", stringBody1);
			builder.addPart("uploadmediarequest", uploadmediarequest);
			HttpEntity entity = builder.build();
			
			post.setEntity(entity);
	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
	        post.addHeader(new BasicHeader("Cookie",weChatIMConfPO.getCookie()));
	        Security.insertProviderAt(new BouncyCastleProvider(),1);
	        String result = HttpClientUtil.paseResponse(client.execute(post));
	        UpLoadMediaResPO upLoadMediaResPO = UpLoadMediaResPO.getObjectFromJson(result);
			return upLoadMediaResPO.getMediaId();
		}catch (Exception e) {
			LoggerUtil.COMMON.error("发送微信消息异常",e);
		}
		return null;
	}
	
	/**
	 * 心跳检测
	 * @return
	 */
	public static String receive(){
		CloseableHttpClient closeableHttpClient = HttpClientUtil.createSSLClientDefault();
		WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
		String srcAddr = weChatIMConfPO.getReceiveAddr();
		int index = srcAddr.indexOf("r=")+2;
		String str1= srcAddr.substring(0,index);
		String str2 = srcAddr.substring(srcAddr.indexOf("&", index));
		String descAddr = str1+DateUtil.getCurrentTime().getTime()+str2;
		return HttpClientUtil.doHttpsGet(descAddr,closeableHttpClient,weChatIMConfPO.getCookie());
	}
	
	public static void main(String args[]){
		WeChatIMConfPO config = new WeChatIMConfPO();
		config.setCookie("pgv_pvid=2010687370; _ga=GA1.2.1396463380.1442479853; 3g_guest_id=-9105595419252989952; pt2gguin=o0549891545 ; o_cookie=549891545; ptcz=b37285a5cd8a7665df8c4748a76ee442e6592b3e78cfc912819ffef1e2dab519; webwxuvid =a5579096faa863610496d54f43e2703bd0fff606cbcd9a361cfa86ab21efd9669ea2745f3dc890c895a0b9f55dc3c700; eas_sid =21n4266159U233X9i7I1j0L5l3; pac_uid=1_549891545; tvfe_boss_uuid=cb4c775cb666d394; pgv_pvi=9328928768 ; RK=BFfmP92jEU; webwx_auth_ticket=CIsBEPPJiqEJGoABCcGAPJt2tA3pt7n1GD2UppMiuXvexNBLx928l31Rq4lgvlVHQronr /LpU18AyHyOuWfU2uRQP9QQGo8efJH+fr6t884FCQzh+yN535KMX5EfVWe+SdOB2Sq4Ecopyji07xIV4riiDdrmsdm9wKDIzc796SjgINjJOsDgpjH34d8 =; pgv_si=s5493210112; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; douyu_loginKey=5eb7229c53bff73a1285e84c796eaa78 ; mm_lang=zh_CN; wxuin=2685885737; wxsid=mLB7rlPQ/ozm9vxV; wxloadtime=1486129545_expired; webwx_data_ticket =gSeS2KN/2RcFl0+2OQ9c2zrt; login_frequency=1; last_wxuin=2685885737; wxpluginkey=1486116362");
		config.setFromName("@9b8692b9d7652bcf1b8ba27889b287f1f4cf11656cf511d9e20f30a7300faa65");
		config.setPassTicket("pass_ticket=S9CwyzuCenA23cPz5cN6bNahxCPr7y%2FNAxWOvhOfGiTAflunn2Sc%2F7Y%2Be8T2DnnC");
		//config.setReceiveAddr("https://webpush.weixin.qq.com/cgi-bin/mmwebwx-bin/synccheck?r="+new Date().getTime()+"&skey=%40crypt_adab0c28_5f7dec74ca62b3cdb5ff003f651a54b5&sid=svBt%2BOpUV%2FR3h6o7&uin=2685885737&deviceid=e083429773003918&synckey=1_628900409%7C2_628900581%7C3_628900538%7C11_628900038%7C201_1446984527%7C1000_1446977973&_="+(new Date().getTime()-1000));
		config.setReceiveAddr("https://webpush.wx.qq.com/cgi-bin/mmwebwx-bin/synccheck?r=1486129732444&skey=%40crypt_adab0c28_659c8d6aa78e3aa93f58703d72e400c7&sid=mLB7rlPQ%2Fozm9vxV&uin=2685885737&deviceid=e759155195163869&synckey=1_658780041%7C2_658780045%7C3_658780038%7C11_658780038%7C13_658780038%7C201_1486129546%7C1000_1486116362%7C1001_1486116392%7C1002_1485528723%7C1004_1484915380&_=1486129553775");
		config.setSid("mLB7rlPQ/ozm9vxV");
		config.setSkey("@crypt_adab0c28_659c8d6aa78e3aa93f58703d72e400c7");
		config.setToName("@4105564b39bc054663ed225b698a31ab58341b4cf797ee263a4bfffcd04cc559");
		ConfigStore.setConfig(config);
		
		lastSendTime = DateUtil.parseDate("2015-01-01 00:00:00");
		System.out.println(DateUtil.format(new Date()));
		int hour = DateUtil.getHourOfDay();
		if(hour >= 12){
			hour -= 12;
		}
		System.out.println(notify("我很健康"+EMOJI_TIME.get(hour),WeChatMsgType.TEXT));
//		String jsonString = uploadMedia("/Users/chengx/Downloads/002.png");
//		UpLoadMediaResPO upLoadMediaResPO = UpLoadMediaResPO.getObjectFromJson(jsonString);
//		System.out.println(upLoadMediaResPO.getMediaId());
		//System.out.println(notify("@crypt_7def54e_a1d39fa686c83a0fad32f9d581bad143464d6a2e72ed95da319df9aba333c9c2e11b834eee75aa3e985e2b227d27d16467de3ba47150f6be7dc9b5034c126266b287b405d3a7169c4b71670112fbc8d4147fe73266c444589edb7c147c1e954f6ec8ed9b7ea79112d7ecf94116df973b7c3dd94f9aeab2b864525c02405b06491be31c43250c15494fce65844a547388f4dabf9124654501e7f9a9b322f8c5f3c35fa7bee1afddb6d5adc98224c6a2243b64047cd5c81318c97693d12486c935baf6174b8b2fbac45a8912bda3ddf49f7a15fca362032e2bd9ee2b0d21885af790fc4989cdc57d658f562d274f83708dca9eda65c44412f4d141d4674380b76f03170c29e4c83775bae3ff13c4a9fd7951e1af34611babd754d6215f9bc14bd71109cf09f632ecf8c5e462ac81d44a981db0b2ba80c6d2783d997bee3afedae9",WeChatMsgType.IMAGE));
		//System.out.println(receive());
		//System.out.println(getMsg());
		//System.out.println(URLEncoder.encode("svBt+OpUV/R3h6o7"));
		//System.out.println(doPost("https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid="+URLEncoder.encode("svBt+OpUV/R3h6o7")+"&r="+new Date().getTime()+"&skey="+URLEncoder.encode("@crypt_adab0c28_5f7dec74ca62b3cdb5ff003f651a54b5")));
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
