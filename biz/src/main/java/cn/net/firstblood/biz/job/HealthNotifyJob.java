/**
 * 
 */
package cn.net.firstblood.biz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import cn.net.firstblood.framework.enums.WeChatMsgType;
import cn.net.firstblood.framework.notifier.WeChatIM;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * 健康通知
 * @author gangxiang.chengx
 *
 */
public class HealthNotifyJob implements StatefulJob {
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		try{
			LoggerUtil.COMMON.info("HealthNotifyJob start");
			int hour = DateUtil.getHourOfDay();
			if(hour >= 12){
				hour -= 12;
			}
			String msg = WeChatIM.notify("我很健康"+WeChatIM.EMOJI_TIME.get(hour),WeChatMsgType.TEXT);
			LoggerUtil.COMMON.info("HealthNotifyJob receive:"+msg);
			LoggerUtil.COMMON.info("HealthNotifyJob end");
		}catch(Exception e){
			LoggerUtil.COMMON.error("HealthNotifyJob error",e);
		}
	}
	
}
