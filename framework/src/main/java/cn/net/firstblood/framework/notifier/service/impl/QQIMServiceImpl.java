package cn.net.firstblood.framework.notifier.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import cn.net.firstblood.framework.notifier.service.IMService;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.JsonUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

public class QQIMServiceImpl implements IMService{
	private static AtomicLong msgId = new AtomicLong(0);
	static{
		double random = Math.random();
		while(random*1000<100){
			random = Math.random();
		}
		msgId = new AtomicLong((long)(random*1000)*100000);
		LoggerUtil.COMMON.info("msgId="+msgId);
	}
	
	public static final String TO = "1594740467";
	public static final String PSESSIONID = "8368046764001d636f6e6e7365727665725f77656271714031302e3133392e372e313630000053ce000014d6036e04009107d1186d0000000a40333479657350314d6b6d00000028ff4e92d8e52f1a8b937be059d8420564397ce4dd66c2e881e83cfa1595c0e442f044bb854b87c105";
	public static final String COOKIE = "pgv_pvid=2010687370; _ga=GA1.2.1396463380.1442479853; 3g_guest_id=-9105595419252989952; ts_refer=www.baidu.com/link; ts_uid=5740711748; pt2gguin=o0416352145; o_cookie=416352145; ptui_loginuin=416352145; ptcz=b37285a5cd8a7665df8c4748a76ee442e6592b3e78cfc912819ffef1e2dab519; RK=RTXi2tl5EA; pgv_pvi=2875625472; pgv_info=ssid=s5524464480; pt_clientip=14377f000001302d; pt_serverip=28830a93196facd6; ptisp=ctc; uin=o0416352145; skey=@34yesP1Mk; ptwebqq=a1797fdc3cec71a0ee93a1ee5dc7d4c81dcbd0bf7763400048d022a84fe69547; p_uin=o0416352145; p_skey=HJRvbuklMXGn1*wAqk7H5KcRq3GnLQQsH-HjrzjDYiQ_; pt4_token=N1CQLy69GeOLhg6fQd2yMg__";
	//reveive
	public static final String PTWEBQQ = "a1797fdc3cec71a0ee93a1ee5dc7d4c81dcbd0bf7763400048d022a84fe69547";
	
	public static final  String REFERER = "http://d.web2.qq.com/proxy.html?v=20130916001&callback=1&id=2";
	
	private static Date lastSendTime = new Date();
	
	@Override
	public String notify(String msg) {
		if(DateUtil.addTime(lastSendTime, 20).after(new Date())){
			LoggerUtil.COMMON.info("疲劳度控制不发送qq消息,上次发送时间:[lastSendTime:"+DateUtil.format(lastSendTime)+"]");
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
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("to", Long.valueOf(TO));
		jsonMap.put("content", "[\""+sendMsg+"\",[\"font\",{\"name\":\"宋体\",\"size\":10,\"style\":[0,0,0],\"color\":\"000000\"}]]");
		jsonMap.put("face", Long.valueOf("645"));
		jsonMap.put("clientid", Long.valueOf("53999199"));
		jsonMap.put("msg_id", msgId.get());
		jsonMap.put("psessionid", PSESSIONID);
		
		//创建HttpClientBuilder  
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
        //HttpClient  
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		Map<String, String> params = new HashMap<String, String>();
		params.put("r", JsonUtil.mapToJson(jsonMap));
		HttpPost post = HttpClientUtil.getPost("http://d.web2.qq.com/channel/send_buddy_msg2",params);
		post.addHeader(new BasicHeader("Cookie",COOKIE));
		post.addHeader(new BasicHeader("Referer",REFERER));
		try {
			return HttpClientUtil.paseResponse(closeableHttpClient.execute(post));
		} catch (ClientProtocolException e) {
			LoggerUtil.COMMON.error("",e);
		} catch (IOException e) {
			LoggerUtil.COMMON.error("",e);
		}
		return null;
	}

}
