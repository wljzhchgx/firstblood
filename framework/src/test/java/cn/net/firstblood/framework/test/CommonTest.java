package cn.net.firstblood.framework.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.net.firstblood.framework.enums.ChannelType;
import cn.net.firstblood.framework.notifier.IMFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/firstblood-framework-test.xml" })
public class CommonTest extends AbstractJUnit4SpringContextTests {
	@Resource
	private IMFacade	iMFacade;
	
	@Test
	public void testIM(){
		System.out.println(iMFacade.send(ChannelType.WE_CHAT, "test"));
	}
	
	public static void main(String args[]){
		System.out.println(new Date().getTime());
	}
}
