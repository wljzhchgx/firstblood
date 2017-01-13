/**
 * 
 */
package cn.net.firstblood.framework.test;


/**
 * @author gangxiang.chengx
 *
 */
public class WeChatTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("begin");
//		CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
//		//HttpClientUtil.getJsonPost(\"\", null);
//		
//		StringEntity entity;
//		try {
//			entity = new StringEntity("{\"BaseRequest\":{\"Uin\":2685885737,\"Sid\":\"zDsFy6iXoZpaB1+J\",\"Skey\":\"@crypt_adab0c28_dee62c76ce9608ee5988b3d445a45569\",\"DeviceID\":\"e541241359894677\"},\"Msg\":{\"Type\":1,\"Content\":\"33\",\"FromUserName\":\"@dd0b99dff0c39f14ce793e8e66e8ff77fbf7fb22f038d83e5032feb841e4ae69\",\"ToUserName\":\"@3b451338b3d891b703bf8f9b3979656db30f4ef34f81d2f3ea50eb719a04b913\",\"LocalID\":\"14435790649430521\",\"ClientMsgId\":\"14435790649430521\"}}","utf-8");
//			entity.setContentEncoding("UTF-8");    
//	        entity.setContentType("application/json"); 
//	        HttpPost post = new HttpPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?pass_ticket=vkcOJwAiFWTt91QiCRjNkjv8K3xfMjbwyQekF8Fucv9wR4Lvy6tqNZbYFD4qV5R8"); 
//	        post.setEntity(entity);
//	        post.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
//	        post.addHeader(new BasicHeader("Cookie","pgv_pvid=2010687370; _ga=GA1.2.1396463380.1442479853; 3g_guest_id=-9105595419252989952; pt2gguin=o0416352145; o_cookie=416352145; ptui_loginuin=416352145; ptcz=b37285a5cd8a7665df8c4748a76ee442e6592b3e78cfc912819ffef1e2dab519; RK=RTXi2tl5EA; pgv_pvi=2875625472; pgv_si=s8603539456; pgv_info=ssid=s8441043840; mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; wxuin=2685885737; wxsid=zDsFy6iXoZpaB1+J; wxloadtime=1443578998_expired; webwx_data_ticket=AQY20sq11BY3kEoPXi8DDhXr; webwxuvid=a5579096faa863610496d54f43e2703bd0fff606cbcd9a361cfa86ab21efd9669ea2745f3dc890c895a0b9f55dc3c700; wxpluginkey=1443575111"));
//	        System.out.println(HttpClientUtil.paseResponse(client.execute(post)));
//	        
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
		//parseTest();
		System.out.println("end");
	}
	
//	private static void parseTest(){
//		WeChatSendMsgParam param = new WeChatSendMsgParam();
//		WeChatSendMsgParam.BaseRequest baseRequest = param.new BaseRequest();
//		baseRequest.setDeviceID("e541241359894677");
//		baseRequest.setSid("zDsFy6iXoZpaB1+J");
//		baseRequest.setSkey("@crypt_adab0c28_dee62c76ce9608ee5988b3d445a45569");
//		baseRequest.setUin(2685885737L);
//		WeChatSendMsgParam.Msg msg = param.new Msg();
//		msg.setClientMsgId("14435790649430520");
//		msg.setContent("你好");
//		msg.setFromUserName("@dd0b99dff0c39f14ce793e8e66e8ff77fbf7fb22f038d83e5032feb841e4ae69");
//		msg.setLocalID("14435790649430520");
//		msg.setToUserName("@3b451338b3d891b703bf8f9b3979656db30f4ef34f81d2f3ea50eb719a04b913");
//		msg.setType(1);
//		param.setBaseRequest(baseRequest);
//		param.setMsg(msg);
//		
//		System.out.println(JsonUtil.beanToJson(param));
//	}

}
