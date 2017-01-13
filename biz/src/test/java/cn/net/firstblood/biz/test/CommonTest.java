package cn.net.firstblood.biz.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.net.firstblood.biz.cmd.CmdFacade;
import cn.net.firstblood.framework.enums.CmdType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/firstblood-biz-test.xml" })
public class CommonTest extends AbstractJUnit4SpringContextTests {
	@Resource
	private CmdFacade	cmdFacade;
	
	@Test
	public void testCmd(){
		System.out.println(cmdFacade.exeCmd(CmdType.CMD));
	}
}
