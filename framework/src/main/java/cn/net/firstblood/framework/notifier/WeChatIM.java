/**
 * 
 */
package cn.net.firstblood.framework.notifier;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import cn.net.firstblood.framework.model.BaseMapPO;
import cn.net.firstblood.framework.notifier.model.ConfigStore;
import cn.net.firstblood.framework.notifier.model.WeChatIMConfPO;
import cn.net.firstblood.framework.notifier.model.wechatim.BaseRequestPO;
import cn.net.firstblood.framework.notifier.model.wechatim.InitRespPO;
import cn.net.firstblood.framework.notifier.model.wechatim.SyncKeyPO;
import cn.net.firstblood.framework.notifier.model.wechatim.UpLoadMediaResPO;
import cn.net.firstblood.framework.notifier.model.wechatim.WebwxSyncReqPO;
import cn.net.firstblood.framework.notifier.model.wechatim.WebwxSyncRespPO;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.LoggerUtil;
import cn.net.firstblood.framework.util.MD5Util;

import com.alibaba.fastjson.JSONObject;

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
	
	private static final String DEVICEID = "e842799103176466";
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
				content = content.replaceAll("\"", "'");
				content = content+"\n["+DateUtil.format(new Date())+"]";
			}
			String mediaId = "";
			if(msgType == WeChatMsgType.IMAGE){
				postUrl="https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsgimg?fun=async&f=json";
				mediaId=content;
				content = "";
			}
			StringEntity entity;
			entity = new StringEntity("{\"BaseRequest\":{\"Uin\":"+UID+",\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\""+DEVICEID+"\"},\"Msg\":{\"Type\":"+msgType.getKey()+",\"Content\":\""+content+"\",\"MediaId\":\""+mediaId+"\",\"FromUserName\":\""+weChatIMConfPO.getFromName()+"\",\"ToUserName\":\""+weChatIMConfPO.getToName()+"\",\"LocalID\":\""+msgId.get()+"\",\"ClientMsgId\":\""+msgId.get()+"\"}}","utf-8");
			entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json"); 
	        
	        HttpPost post = new HttpPost(postUrl); 
	        post.setEntity(entity);
	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
	        post.addHeader(new BasicHeader("Cookie",weChatIMConfPO.getCookie()));
	        Security.insertProviderAt(new BouncyCastleProvider(),1);
	        String result = HttpClientUtil.paseResponse(client.execute(post));
	        LoggerUtil.COMMON.info("发送微信消息结束[content:"+content+"],[msgType:"+msgType.getDesc()+"]");
	        lastSendTime = new Date();
	        statusNotify();
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
			StringBody uploadmediarequest = new StringBody("{\"UploadType\":2,\"BaseRequest\":{\"Uin\":"+UID+",\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\""+DEVICEID+"\"},\"ClientMediaId\":"+new Date().getTime()+",\"TotalLen\":"+file.length()+",\"StartPos\":0,\"DataLen\":"+file.length()+",\"MediaType\":4,\"FromUserName\":\""+weChatIMConfPO.getFromName()+"\",\"ToUserName\":\""+weChatIMConfPO.getToName()+"\",\"FileMd5\":\""+MD5Util.signMD5(absolutePath+DateUtil.format(DateUtil.getCurrentTime()))+"\"}", ContentType.MULTIPART_FORM_DATA);
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
		String descAddr = replaceAddr(srcAddr,"r=",String.valueOf(DateUtil.getCurrentTime().getTime()));
		return HttpClientUtil.doHttpsGet(descAddr,closeableHttpClient,weChatIMConfPO.getCookie());
	}
	
	/**
	 * 心跳检测
	 * @return
	 */
	public static String receive(SyncKeyPO syncKeyObj){
		String synckey = urlEncodeSynckey(syncKeyObj.getList());
		CloseableHttpClient closeableHttpClient = HttpClientUtil.createSSLClientDefault();
		WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
		String srcAddr = weChatIMConfPO.getReceiveAddr();
		String descAddr = replaceAddr(srcAddr,"r=",String.valueOf(DateUtil.getCurrentTime().getTime()));
		descAddr = replaceAddr(descAddr,"synckey=",synckey);
		return HttpClientUtil.doHttpsGet(descAddr,closeableHttpClient,weChatIMConfPO.getCookie());
	}
	
	private static String urlEncodeSynckey(List<BaseMapPO> synckeyList){
		try{
			String synckey = "";
			for(int i = 0;i<synckeyList.size();i++){
				BaseMapPO baseMapPO = synckeyList.get(i);
				synckey += baseMapPO.getKey()+"_"+baseMapPO.getVal();
				if(i < synckeyList.size()-1){
					synckey += "|";
				}
			}
			return URLEncoder.encode(synckey, "UTF-8");
		}catch (Exception e) {
			LoggerUtil.COMMON.error("发送微信消息 urlEncodeSynckey异常",e);
		}
		return null;
	}
	
	private static SyncKeyPO decode(String str){
		try{
			String deStr = URLDecoder.decode(str, "utf-8");
			String[] strArray = deStr.split("\\|");
			SyncKeyPO syncKey = new SyncKeyPO();
			syncKey.setCount(strArray.length);
			List<BaseMapPO> list = new ArrayList<BaseMapPO>();
			syncKey.setList(list);
			for(String keyValue : strArray){
				BaseMapPO map = new BaseMapPO();
				list.add(map);
				map.setKey(keyValue.split("_")[0]);
				map.setVal(keyValue.split("_")[1]);
			}
			return syncKey;
		}catch (Exception e) {
			LoggerUtil.COMMON.error("发送微信消息 urlEncodeSynckey异常",e);
		}
		return null;
		
	}
	
	private static String replaceAddr(String srcAddr,String beginFlag,String replaceStr){
		int index = srcAddr.indexOf(beginFlag)+beginFlag.length();
		String str1= srcAddr.substring(0,index);
		String str2 = srcAddr.substring(srcAddr.indexOf("&", index));
		return str1+replaceStr+str2;
	}
	
	/**
	 * 接收消息
	 * @param syncKey
	 * @return
	 */
	public static WebwxSyncRespPO receiveMsg(SyncKeyPO syncKey){
		try{
			WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
			CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
			
			WebwxSyncReqPO webwxSyncReq = new WebwxSyncReqPO();
			webwxSyncReq.setRr(String.valueOf(DateUtil.getCurrentTime().getTime()));
			BaseRequestPO baseRequest = new BaseRequestPO();
			baseRequest.setSid(weChatIMConfPO.getSid());
			baseRequest.setUin(UID);
			webwxSyncReq.setBaseRequest(baseRequest);
			webwxSyncReq.setSyncKey(syncKey);
			
			StringEntity entity;
			entity = new StringEntity(JSONObject.toJSONString(webwxSyncReq),"utf-8");
			entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json"); 
	        
	        HttpPost post = new HttpPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid="+weChatIMConfPO.getSid()+"&r="+DateUtil.getCurrentTime().getTime()); 
	        post.setEntity(entity);
	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
	        post.addHeader(new BasicHeader("Cookie",weChatIMConfPO.getCookie()));
	        Security.insertProviderAt(new BouncyCastleProvider(),1);
	        String msgJson = HttpClientUtil.paseResponseUTF8(client.execute(post));
	        LoggerUtil.COMMON.info("接受微信消息:"+msgJson);
	        return JSONObject.parseObject(msgJson, WebwxSyncRespPO.class);
		}catch (Exception e) {
			LoggerUtil.COMMON.error("接受微信消息异常",e);
		}
		return null; 
	}
	
	/**
	 * 初始化
	 * @return
	 */
	public static InitRespPO init(){
		try{
			WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
			CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
			StringEntity entity;
			entity = new StringEntity("{\"BaseRequest\":{\"Uin\":"+UID+",\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\""+DEVICEID+"\"}}","utf-8");
			entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json"); 
	        
	        HttpPost post = new HttpPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r="+DateUtil.getCurrentTime().getTime()); 
	        post.setEntity(entity);
	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
	        post.addHeader(new BasicHeader("Cookie",weChatIMConfPO.getCookie()));
	        Security.insertProviderAt(new BouncyCastleProvider(),1);
	        String result = HttpClientUtil.paseResponseUTF8(client.execute(post));
	        InitRespPO initRespPO = JSONObject.parseObject(result, InitRespPO.class);
	        LoggerUtil.COMMON.info("初始化微信消息成功 [initRespPO:"+initRespPO+"],[result:"+result+"]");
			return initRespPO;
		}catch (Exception e) {
			LoggerUtil.COMMON.error("初始化微信消息异常",e);
		}
		return null; 
	}
	
	public static String statusNotify(){
		try{
			WeChatIMConfPO weChatIMConfPO = ConfigStore.getConfig(WeChatIMConfPO.class);
			CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
			StringEntity entity;
			entity = new StringEntity("{\"BaseRequest\":{\"Uin\":"+UID+",\"Sid\":\""+weChatIMConfPO.getSid()+"\",\"Skey\":\""+weChatIMConfPO.getSkey()+"\",\"DeviceID\":\""+DEVICEID+"\"},\"Code\":1,\"FromUserName\":\""+weChatIMConfPO.getFromName()+"\",\"ToUserName\":\""+weChatIMConfPO.getToName()+"\",\"ClientMsgId\":"+new Date().getTime()+"}","utf-8");
			entity.setContentEncoding("UTF-8");    
	        entity.setContentType("text/plain"); 
	        
	        HttpPost post = new HttpPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxstatusnotify?"+weChatIMConfPO.getPassTicket()); 
	        post.setEntity(entity);
	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
	        post.addHeader(new BasicHeader("Cookie",weChatIMConfPO.getCookie()));
	        Security.insertProviderAt(new BouncyCastleProvider(),1);
	        String result = HttpClientUtil.paseResponseUTF8(client.execute(post));
	        LoggerUtil.COMMON.info("微信状态通知成功 [result:"+result+"]");
			return result;
		}catch (Exception e) {
			LoggerUtil.COMMON.error("微信状态通知成功",e);
		}
		return null;
	}
	
	public static void main(String args[]){
		WeChatIMConfPO config = new WeChatIMConfPO();
		config.setCookie("pgv_pvid=2010687370; _ga=GA1.2.1396463380.1442479853; 3g_guest_id=-9105595419252989952; pt2gguin=o0549891545 ; o_cookie=549891545; ptcz=b37285a5cd8a7665df8c4748a76ee442e6592b3e78cfc912819ffef1e2dab519; eas_sid =21n4266159U233X9i7I1j0L5l3; pac_uid=1_549891545; tvfe_boss_uuid=cb4c775cb666d394; pgv_pvi=9328928768 ; RK=BFfmP92jEU; pgv_si=s3122881536; pgv_info=ssid=s9125598808; douyu_loginKey=dd7c11f2fc6665e9fdaaeadde1b5bfc6 ; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; wxuin=2685885737; webwxuvid=fe61714eae72c333b61c69c231b9208faa72f9b023dbd9a9ed397c8aa33ad590c5662bbf425d491ab671266e483b019b ; webwx_auth_ticket=CIsBENqzpvcPGoABsQBsfqVS6gbnALvJEQeq+VlsaoSSoj2NNdILGvyd+3ZY9YpH+VNDKa+3tfRCpIBLp8VkRTfVoWM8YVSz /5b8Kf+C4kFiw9oNmtVjsGQrMa4cEJBABCKYFYVY0QUcHSLyA+EpMlqUWJDWdPiZWBy7HA0dvOK8h1VoldMMWNIipDM=; login_frequency =1; last_wxuin=2685885737; mm_lang=zh_CN; wxsid=wKaehZg/74/YQ4yb; wxloadtime=1487651870_expired; webwx_data_ticket =gScu1rzHZWoQMbIou3fLuzAb; wxpluginkey=1487638621");
		config.setFromName("@1d50a9dc2067c8a9522aa37f464750b890c9003156aea0c307954a19c340312e");
		config.setPassTicket("pass_ticket=Ws0z7qXbtbPX4pzKO%2BtFClt%2Bwsf%2FhXilfGISs944hsorGOTXbtLzu%2BmLfSbKe5P6");
		//config.setReceiveAddr("https://webpush.weixin.qq.com/cgi-bin/mmwebwx-bin/synccheck?r="+new Date().getTime()+"&skey=%40crypt_adab0c28_5f7dec74ca62b3cdb5ff003f651a54b5&sid=svBt%2BOpUV%2FR3h6o7&uin=2685885737&deviceid=e083429773003918&synckey=1_628900409%7C2_628900581%7C3_628900538%7C11_628900038%7C201_1446984527%7C1000_1446977973&_="+(new Date().getTime()-1000));
		config.setReceiveAddr("https://webpush.wx.qq.com/cgi-bin/mmwebwx-bin/synccheck?r=1487651937580&skey=%40crypt_adab0c28_2c5d2997ce1517aef0e09998e03a7c24&sid=wKaehZg%2F74%2FYQ4yb&uin=2685885737&deviceid=e654533380863752&synckey=1_658780677%7C2_658780854%7C3_658780668%7C11_658780038%7C13_658780038%7C201_1487651871%7C1000_1487638621%7C1001_1487638651%7C1002_1487575879%7C1004_1484917121&_=1487651797941");
		config.setSid("wKaehZg/74/YQ4yb");
		config.setSkey("@crypt_adab0c28_2c5d2997ce1517aef0e09998e03a7c24");
		config.setToName("@07105cdcba389c465b5c3bc954fdd38fc64cb3b99ac987eb654c0f5dce0e7631");
		ConfigStore.setConfig(config);
		
		lastSendTime = DateUtil.parseDate("2015-01-01 00:00:00");
		System.out.println(DateUtil.format(new Date()));
		int hour = DateUtil.getHourOfDay();
		if(hour >= 12){
			hour -= 12;
		}
		InitRespPO initResp = init();
		System.out.println(JSONObject.toJSON(initResp));
		statusNotify();
//		System.out.println("================");
//		System.out.println(receive(initResp.getSyncKey()));
//		System.out.println("==="+ receiveMsg(initResp.getSyncKey()).getAddMsgList().get(0).getContent()+"===");
		System.out.println(notify(parseMsg(),WeChatMsgType.TEXT));
//		String jsonString = uploadMedia("/Users/chengx/Downloads/002.png");
//		UpLoadMediaResPO upLoadMediaResPO = UpLoadMediaResPO.getObjectFromJson(jsonString);
//		System.out.println(upLoadMediaResPO.getMediaId());
		//System.out.println(notify("@crypt_7def54e_a1d39fa686c83a0fad32f9d581bad143464d6a2e72ed95da319df9aba333c9c2e11b834eee75aa3e985e2b227d27d16467de3ba47150f6be7dc9b5034c126266b287b405d3a7169c4b71670112fbc8d4147fe73266c444589edb7c147c1e954f6ec8ed9b7ea79112d7ecf94116df973b7c3dd94f9aeab2b864525c02405b06491be31c43250c15494fce65844a547388f4dabf9124654501e7f9a9b322f8c5f3c35fa7bee1afddb6d5adc98224c6a2243b64047cd5c81318c97693d12486c935baf6174b8b2fbac45a8912bda3ddf49f7a15fca362032e2bd9ee2b0d21885af790fc4989cdc57d658f562d274f83708dca9eda65c44412f4d141d4674380b76f03170c29e4c83775bae3ff13c4a9fd7951e1af34611babd754d6215f9bc14bd71109cf09f632ecf8c5e462ac81d44a981db0b2ba80c6d2783d997bee3afedae9",WeChatMsgType.IMAGE));
//		System.out.println(receive());
		//System.out.println(getMsg());
		//System.out.println(URLEncoder.encode("svBt+OpUV/R3h6o7"));
		//System.out.println(doPost("https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid="+URLEncoder.encode("svBt+OpUV/R3h6o7")+"&r="+new Date().getTime()+"&skey="+URLEncoder.encode("@crypt_adab0c28_5f7dec74ca62b3cdb5ff003f651a54b5")));
		System.out.println(DateUtil.format(new Date()));
		
//		System.out.println(DateUtil.format(new Date(1447231656411L)));
//		System.out.println(DateUtil.format(new Date(1447228674240L)));
	}
	
	private static String parseMsg(){
		String result = "";
		//27
		String hen =  "";
		String shu = "1.成功或失败的理由\n"+"                         [2012-09-09]";
		System.out.println(shu.length());
		result = shu+"\n"+shu+"\n"+shu+"\n"+shu+"\n";
		return result;
	}
	
}
