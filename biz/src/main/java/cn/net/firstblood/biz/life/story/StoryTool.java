package cn.net.firstblood.biz.life.story;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 * @version $Id: StoryTool.java, v 0.1 2017年2月13日 下午5:09:50 gangxiang.chengx Exp $
 */
public class StoryTool {
	
	public static String getStoryList(String zhuti){
		String result = "";
		try{
	        // 获取根元素
	        Element root = getRootElement(zhuti);
	        // 获取所有子元素
	        @SuppressWarnings("unchecked")
			List<Element> childList = root.elements();
	        int i = 1;
	        for(Element liElement : childList){
	        	Element aElement = liElement.element("a");
	        	Element spElement = liElement.element("span");
	        	
	        	String title = aElement.getData().toString().trim();
	        	String date = "                         ["+spElement.getData().toString().trim()+"]";
	        	result = result + i+"."+title+"\n"+date+"\n";
	        	i++;
	        	if(i>10){
	        		break;
	        	}
	        }
	        if(result.length()>2){
				result = result.substring(0, result.length()-1);
			}
		}catch(Exception e){
			LoggerUtil.COMMON.info("read storyList fail",e);
		}
		return result;
	}
	
	private static Element getRootElement(String zhuti) throws DocumentException{
		String html = HttpClientUtil.doGet("http://www.rensheng5.com/"+zhuti+"/",HttpClientUtil.GBK);
		html = html.substring(html.indexOf("<ul class=\"p7\">"));
		html = html.substring(0,html.indexOf("</ul>")+"</ul>".length());
		
		SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(html.getBytes()));
        // 获取根元素
        return document.getRootElement();
	}
	
	public static String getStoryDetail(String zhuti,int index){
		try{
			String url = "";
			// 获取根元素
	        Element root = getRootElement(zhuti);
	        // 获取所有子元素
	        @SuppressWarnings("unchecked")
			List<Element> childList = root.elements();
	        int i = 1;
	        for(Element liElement : childList){
	        	if(index == i){
	        		Element aElement = liElement.element("a");
	        		url = aElement.attributeValue("href");
	        	}
	        	i++;
	        }
			String html = HttpClientUtil.doGet(url,HttpClientUtil.GBK);
			html = html.substring(html.indexOf("<p>")+"</p>".length());
			html = html.substring(0,html.indexOf("</p>"));
			html = html.replaceAll("<br />", "").replaceAll("&ldquo;", "“")
					.replaceAll("&rdquo;", "”")
					.replaceAll("&hellip;", "…")
					.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
			return removeSpaceRow(html);
		}catch(Exception e){
			LoggerUtil.COMMON.info("read storyList fail",e);
		}
		return null;
	}
	
	/**
	 * 去除空行
	 * @param src
	 * @return
	 */
	private static String removeSpaceRow(String src){
		String result = "";
		ByteArrayInputStream is=new ByteArrayInputStream(src.getBytes());
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		try {
			while(br.read()!=-1){
				String line = br.readLine();
				if(StringUtils.isBlank(line)){
					continue;
				}
				result = result + line.trim()+"\n";
			}
		} catch (IOException e) {
			LoggerUtil.COMMON.info("read story removeSpaceRow error",e);
		}
		if(result.length()>2){
			result = result.substring(0, result.length()-1);
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("开始");
		System.out.println(StoryTool.getStoryDetail("renshengganwu",1));
		System.out.println("结束");
	}

}
