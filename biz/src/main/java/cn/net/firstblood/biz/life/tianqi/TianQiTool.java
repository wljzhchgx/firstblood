/**
 * 
 */
package cn.net.firstblood.biz.life.tianqi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class TianQiTool {
	private static final Map<Integer,String> I_MAP = new HashMap<Integer,String>();
	static{
		I_MAP.put(0, "今天");
		I_MAP.put(1, "明天");
		I_MAP.put(2, "后天");
	}
	public static String getTianQi(){
		String result = "| 日期 |   天气&温度   |"+"\n";
		try{
			for(int i=0; i<3; i++){
				String html = HttpClientUtil.doGet("http://php.weather.sina.com.cn/xml.php?city="+URLEncoder.encode("杭州", "GBK")+"&password=DJOYnieT8234jlsK&day="+i,HttpClientUtil.UTF_8);
				result += "| "+ I_MAP.get(i)+" |";
				result+=getCnStr(getProperty(html,"status1"))+","+getCnStr(getProperty(html,"status2"))+"|";
				result+=getEnStr(getProperty(html,"temperature1"),true)+","+getEnStr(getProperty(html,"temperature2"),false)+"|";
				if(i<2){
					result = result+"\n";
				}
			}
		}catch(Exception e){
			LoggerUtil.COMMON.info("天气失败",e);
		}
		return result;
	}
	
	private static String getCnStr(String src){
		if(src.length()==1){
			return src+"天";
		}
		return src;
	}
	
	private static String getEnStr(String src,boolean isLeftBlank){
		if(src.length()==1){
			return isLeftBlank?" "+src:src+" ";
		}
		return src;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		System.out.println(getTianQi());
		String a= "a";
		a+="b";
		System.out.println(a);
		System.out.println("多".length());
	}
	
	private static String getProperty(String src ,String tag){
		int indexBegin = src.indexOf("<"+tag+">");
		int indexEnd = src.indexOf("</"+tag+">");
		return src.substring(indexBegin+tag.length()+2, indexEnd);
	}
}
