package cn.net.firstblood.framework.util;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.CollectionUtils;

/**
 * @author gangxiang.chengx
 * @version $Id: WeChatViewUtil.java, v 0.1 2017年2月14日 上午11:41:37 gangxiang.chengx Exp $
 */
public class WeChatViewUtil {
	/**
	 * 转换
	 * @param map
	 * @return
	 */
	public static String parseToOneColumn(Map<String,Object> map){
		if(CollectionUtils.isEmpty(map)){
			return "";
		}
		String result = "";
		int i = 0;
		for(Entry<String,Object> entry : map.entrySet()){
			result = result + i+"."+entry.getValue()+"\n";
			i++;
		}
		if(result.length()>1){
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
	
	/**
	 * 转换
	 * @param map
	 * @return
	 */
	public static String parseOneColumn(Map<String,Object> map){
		if(CollectionUtils.isEmpty(map)){
			return "";
		}
		String result = "";
		for(Entry<String,Object> entry : map.entrySet()){
			result = result + entry.getKey()+"."+entry.getValue()+"\n";
		}
		if(result.length()>1){
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
}
