package cn.net.firstblood.web.api.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.net.firstblood.biz.model.InputMessage;
import cn.net.firstblood.framework.util.LoggerUtil;

public class Test {

	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("北京", "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
