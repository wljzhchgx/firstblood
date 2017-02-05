package cn.net.firstblood.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author gangxiang.chengx
 * @version $Id: BeanUtil.java, v 0.1 2017年2月3日 下午4:53:30 gangxiang.chengx Exp $
 */
public class BeanUtil  implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext ;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		setServiceContain(applicationContext) ;
	}
	
	private final static void setServiceContain(ApplicationContext applicationContext){
		BeanUtil.applicationContext = applicationContext ;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getNean(String beanName) {
		return (T)applicationContext.getBean(beanName) ;
	}
}
