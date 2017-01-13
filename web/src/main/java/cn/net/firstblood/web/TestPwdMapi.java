package cn.net.firstblood.web;

import HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alipay.common.lang.signature.RSASignature;
import com.alipay.common.lang.signature.Signature;
import com.alipay.securitycore.common.service.facade.policy.domain.SecurityScene;
import com.alipay.securitycore.common.service.facade.policy.dto.ServiceContext;
import com.alipay.securitycore.common.service.facade.policy.utils.SecurityPolicyHelper;

public class TestPwdMapi {
	private static final String PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAoTK3peVThrK0AnCejIlYrV0LvU3oWBxlJgZHBjL"
			+ "32ey6eTa2mGtKbUKSGsVj2Wcdna2VM7sLUWktUtwF3r7l+wIDAQABAkB254hb0Ls6ApuRqSzqkW1eA+Ji8xLN27Qoxjyhr/rv"
			+ "PeemEIbvTm1Q94X1/B/+BDXtvOr6kdNyt4WeqWdHECSRAiEA5ZpoR1pSML4l6PoSjjaJvxNaORRJemKZUhUUA3fnC68CIQC"
			+ "zuwn5FoWoKP24BYVl+6OdoeWsbZTNkIuKlp1pcOshdQIhANGzRKpmg8qg4F74hxn8FbK+KhkXvkRY6U9ekPjZ+dthAiBmx+"
			+ "FuJqkMf1SIte+RYJQygvD66DeeTrjd6j/emop8OQIgVOa4Idia5oGD3lImc+hLnYk0zeij0JfxxPReWpict8U=";
	
	private static final String MAPI_URL = "http://mapi-1-64.test.alipay.net/cooperate/gateway.do?";
	
	public static void main(String[] args) throws Exception {
		Map<String, String> urlParam = getParam();

		//签名
		Signature signature = new RSASignature();
		String sign = signature.sign(getSignData(urlParam), PRIVATE_KEY,"UTF-8");
		urlParam.put("sign", sign);
		urlParam.put("sign_type", "RSA");
		
		String urls = MAPI_URL + mapToUrl(urlParam);
		System.out.println("urls:"+urls);
		
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(urls);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		int statusCode = httpClient.executeMethod(getMethod);
		System.out.println("returnCode:" + statusCode);

		String str = getMethod.getResponseBodyAsString();
		System.out.println("return:" + str);
//		
//		System.out.println(getSecurityScene().genSecurityId());
//		System.out.println(new URLCodec().encode(getSecurityScene().genSecurityId()));

	}
	
	/**
     * 构造安全场景
     *
     * @return 安全场景
     */
    private static SecurityScene getSecurityScene() {
        // 新建安全场景对象
        SecurityScene securityScene = new SecurityScene();

        // 设置场景信息必填项
        {
            securityScene.setAccessChannel("web");
            securityScene.setSystemName("ectradmgr");
            securityScene.setProductName("ectradmgr");
            securityScene.setProductNode("ectradmgr_ae_repay");
        }

//        // 其他业务参数设置
//        {
//            // 参考业务参数部分详细说明
//            securityScene.getParams().put("paramKey", "paramValue");
//        }
        return securityScene;
    }
    
    /**
     * 构造安全服务上下文
     *
     * @param request
     *            Http请求对象
     * @return 安全核心的服务上下文
     */
    private ServiceContext getServiceContext(HttpServletRequest request) {
        return SecurityPolicyHelper.getServiceContext(request);
    }
	
	private static Map<String, String> getParam(){
		Map<String, String> map = new HashMap<String, String>(); 
		//map.put("partner", "2088101122330232");
		 map.put("partner", "2088101112688185");
		// map.put("partner", "2088001159940003");
		map.put("provider_hostname","securitycore.d0572.alipay.net");
		map.put("_input_charset", "UTF-8");
		
		map.put("service", "alipay.security.core.policy.query");
		
		//数据
//		{
//		    "userId": "2088102164304454",
//		    "accessChannel": "web",
//		    "productNode": "productNode"
//		}
		// 设置用户ID
		map.put("service_param", "{}");
        // 与SecurityScene的accessChannel保持一致，大多数情况为web
//		map.put("accessChannel", "web");
//        // 与SecurityScene的productNode保持一致
//		map.put("productNode", "productNode");
		
		return map;
	}
	// ganmao 风控
	public static String getSignData(Map<String, String> params) {
		StringBuffer content = new StringBuffer();

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if ("sign".equals(key) || "sign_type".equals(key)) {
				continue;
			}
			String value = params.get(key);
			if (value != null) {
				content.append((i == 0 ? "" : "&") + key + "=" + value);
			} else {
				content.append((i == 0 ? "" : "&") + key + "=");
			}

		}
		return content.toString();
	}
	
	public static String mapToUrl(Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (isFirst) {
				sb.append(key + "=" + URLEncoder.encode(value, "utf-8"));
				isFirst = false;
			} else {
				if (value != null) {
					sb.append("&" + key + "="
							+ URLEncoder.encode(value, "utf-8"));
				} else {
					sb.append("&" + key + "=");
				}
			}
		}
		return sb.toString();
	}
}

