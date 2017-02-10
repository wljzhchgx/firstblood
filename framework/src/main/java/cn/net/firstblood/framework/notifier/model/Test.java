package cn.net.firstblood.framework.notifier.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.net.firstblood.framework.model.BaseMapPO;
import cn.net.firstblood.framework.notifier.model.wechatim.SyncKeyPO;

import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "1_658780161%7C2_658780169%7C3_658780038%7C11_658780038%7C13_658780038%7C201_1486553441%7C1000_1486549082%7C1001_1486549112%7C1004_1484916511";
		System.out.println(URLDecoder.decode(str, "utf-8"));
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
		
		System.out.println(JSONObject.toJSONString(syncKey));
	}

}
