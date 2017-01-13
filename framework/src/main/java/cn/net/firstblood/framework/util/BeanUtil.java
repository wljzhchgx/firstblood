package cn.net.firstblood.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
