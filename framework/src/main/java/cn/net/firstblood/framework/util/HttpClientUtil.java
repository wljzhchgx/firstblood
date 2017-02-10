package cn.net.firstblood.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class HttpClientUtil {
	public static final String UTF_8="UTF-8";
	public static final String GBK="GBK";
	static {
		System.setProperty ("jsse.enableSNIExtension", "false");
		LoggerUtil.COMMON.info("HttpClientUtil 系统属性设置jsse.enableSNIExtensio=false");
	}
	/**
	 * post
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static HttpPost getPost(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.COMMON.error("doPost error", e);
		}

		return httpost;
	}
	
	/**
	 * 无需cookie Referer等header的post
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, Map<String, String> params) {
		HttpPost post = getPost("url",params);
		//创建HttpClientBuilder  
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
        //HttpClient  
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        try {
			String result = HttpClientUtil.paseResponse(closeableHttpClient.execute(post));
			return result;
		} catch (ClientProtocolException e) {
			LoggerUtil.COMMON.error("",e);
		} catch (IOException e) {
			LoggerUtil.COMMON.error("",e);
		}
		return null;
	}
	
	/**
	 * 无需cookie Referer等header的post
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doHttpsPost(String url, Map<String, String> params) {
		HttpPost post = getPost("url",params);
		post.addHeader(new BasicHeader("Referer","https://www.wjs.com/web/product/index?rows=6&page=1&orderFields=&series=2"));
		CloseableHttpClient client = HttpClientUtil.createSSLClientDefault();
        try {
			String result = HttpClientUtil.paseResponse(client.execute(post));
			return result;
		} catch (ClientProtocolException e) {
			LoggerUtil.COMMON.error("",e);
		} catch (IOException e) {
			LoggerUtil.COMMON.error("",e);
		}
		return null;
	}
	
	public static HttpPost getJsonPost(String url,JSONObject jsonParam){
		//解决中文乱码问题 
		StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");
        entity.setContentEncoding("UTF-8");    
        entity.setContentType("application/json"); 
        HttpPost post = new HttpPost(url); 
        post.setEntity(entity);
        return post;
	}

	/**
	 * get
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url,String encode) {
		BufferedReader in = null;
		String content = null;
		try {
			// 创建HttpClientBuilder
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			// HttpClient
			CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
			
			// 实例化HTTP方法
			HttpGet request = new HttpGet();
			//request.addHeader("Content-Type", "text/html;charset=GBK");  
			request.setURI(new URI(url));
			HttpResponse response = closeableHttpClient.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent(), encode));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static String doHttpsGet(String url,CloseableHttpClient client,String cookie) {
		BufferedReader in = null;
		String content = null;
		try {
			// 实例化HTTP方法
			HttpGet request = new HttpGet();
			request.addHeader(new BasicHeader("Referer","https://wx.qq.com/"));
			request.addHeader(new BasicHeader("Cookie",cookie));
			request.setURI(new URI(url));
			Security.insertProviderAt(new BouncyCastleProvider(),1);
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent(), "GBK"));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public static String paseResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String body = null;
		try {
			body = EntityUtils.toString(entity);
		} catch (ParseException e) {
			LoggerUtil.COMMON.error("paseResponse error", e);
		} catch (IOException e) {
			LoggerUtil.COMMON.error("paseResponse error", e);
		}

		return body;
	}
	
	public static String paseResponseUTF8(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String body = null;
		try {
			body = EntityUtils.toString(entity,"utf-8");
		} catch (ParseException e) {
			LoggerUtil.COMMON.error("paseResponse error", e);
		} catch (IOException e) {
			LoggerUtil.COMMON.error("paseResponse error", e);
		}

		return body;
	}

	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
}
