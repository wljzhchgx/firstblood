/**
 * 
 */
package cn.net.firstblood.framework.util;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gangxiang.chengx
 *
 */
public class LoggerUtil {
	private LoggerUtil(){
	}
	
	public static final Logger COMMON =  LoggerFactory.getLogger("common");
	
	/**
	 * 
	 * @param logObj
	 * @return
	 */
	public static String object4Log(Object logObj){
		try{
			String s = ReflectionToStringBuilder.toString(logObj);
			if(s.contains("[")){
				s =  s.substring(s.indexOf('['));
			}
			return s ;
		}catch(Exception e){
			return e.getMessage();
		}
	}
}
