/**
 * 
 */
package cn.net.firstblood.biz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import cn.net.firstblood.biz.manager.WeChatManager;
import cn.net.firstblood.framework.util.BeanUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * 微信消息接收
 * @author gangxiang.chengx
 *
 */
public class WeChatReceiveJob implements StatefulJob {
	
	private WeChatManager	weChatManager;
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		try{
			LoggerUtil.COMMON.info("WeChatReceiveJob start");
			init();
			weChatManager.syncCheck();
			LoggerUtil.COMMON.info("WeChatReceiveJob end");
		}catch(Exception e){
			LoggerUtil.COMMON.error("WeChatReceiveJob error",e);
		}
	}
	
	public void init(){
		if(weChatManager == null){
			weChatManager = BeanUtil.getNean("weChatManager");
		}
	}
	
}
