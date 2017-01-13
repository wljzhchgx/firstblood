/**
 * 
 */
package cn.net.firstblood.biz.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import cn.net.firstblood.framework.notifier.WeChatIM;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * 微信消息接收
 * @author gangxiang.chengx
 *
 */
public class WeChatReceiveJob implements StatefulJob {
	public static String weChatReceive = "";
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		try{
			LoggerUtil.COMMON.info("WeChatReceiveJob start");
			weChatReceive = WeChatIM.receive()+"[date:"+DateUtil.format(new Date())+"]";
			LoggerUtil.COMMON.info("WeChatReceiveJob receive:"+weChatReceive);
			LoggerUtil.COMMON.info("WeChatReceiveJob end");
		}catch(Exception e){
			LoggerUtil.COMMON.error("WeChatReceiveJob error",e);
		}
	}
	
}
