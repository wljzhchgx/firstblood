/**
 * 
 */
package cn.net.firstblood.biz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import cn.net.firstblood.framework.util.JsonUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class ClientTest {
	public static void main(String args[]){
		String s = executeGet("https://zhaocaibao.alipay.com/pf/productQuery.htm?pageNum=1&minMonth=6&maxMonth=12&minAmount=&danbao=1");
		//System.out.println(s);
		
		int index = s.indexOf("class=\"f-18\"");
		System.out.println(s.substring(index+13, index+17));
	}
	
	private static HttpPost doPost(String url, Map<String, String> params){  
        
        HttpPost httpost = new HttpPost(url);  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
          
        Set<String> keySet = params.keySet();  
        for(String key : keySet) {  
            nvps.add(new BasicNameValuePair(key, params.get(key)));  
        }  
          
        try {  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
          
        return httpost;  
    } 
	
	private static String paseResponse(HttpResponse response) {  
        HttpEntity entity = response.getEntity();  
          
        String charset = EntityUtils.getContentCharSet(entity);  
          
        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return body;  
    } 
	
	
	
	public static String executeGet(String url)  {  
        BufferedReader in = null;  
  
        String content = null;  
        try{
        	//创建HttpClientBuilder  
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
            //HttpClient  
            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
            // 实例化HTTP方法  
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = closeableHttpClient.execute(request);  
  
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"GBK"));  
            StringBuffer sb = new StringBuffer("");  
            String line = "";  
            String NL = System.getProperty("line.separator");  
            while ((line = in.readLine()) != null) {  
                sb.append(line + NL);  
            }  
            in.close();  
            content = sb.toString(); 
        }catch(Exception e){
        	e.printStackTrace();
        }
        return content;  
    }
}
