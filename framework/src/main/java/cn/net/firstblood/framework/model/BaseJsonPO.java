/**
 * 
 */
package cn.net.firstblood.framework.model;

import cn.net.firstblood.framework.util.JsonUtil;

/**
 * @author gangxiang.chengx
 * @param <T>
 *
 */
public class BaseJsonPO<T> {
	private Class<T> extendClass;
	
	public BaseJsonPO(Class<T> extendClass){
		this.extendClass = extendClass;
	}
	
	public T getObjectFromJson(String jsonString){
		return (T)JsonUtil.jsonToBean(jsonString,extendClass);
	}
	
	public String getJsonFromObject(Object obj){
		return  JsonUtil.beanToJson(obj);
	}
}
