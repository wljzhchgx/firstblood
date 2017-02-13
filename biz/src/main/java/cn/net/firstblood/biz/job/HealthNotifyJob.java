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
import cn.net.firstblood.framework.util.FbConstant;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * 健康通知
 * @author gangxiang.chengx
 *
 */
public class HealthNotifyJob implements StatefulJob {
	public static final String FILE_ABSOLUTEPATH = FbConstant.PIC_ROOT_DIR_PATH+"other/health/check_notify.png";
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		try{
			LoggerUtil.COMMON.info("HealthNotifyJob start");
			int hour = DateUtil.getHourOfDay();
			if(hour >= 12){
				hour -= 12;
			}
			String mediaId = WeChatIM.uploadMedia(FILE_ABSOLUTEPATH);
			String picMsg = WeChatIM.notify(mediaId, WeChatMsgType.IMAGE);
			String msg = WeChatIM.notify("我很健康"+WeChatIM.EMOJI_TIME.get(hour),WeChatMsgType.TEXT);
			LoggerUtil.COMMON.info("HealthNotifyJob receive:"+msg+"\n"+picMsg);
			LoggerUtil.COMMON.info("HealthNotifyJob end");
		}catch(Exception e){
			LoggerUtil.COMMON.error("HealthNotifyJob error",e);
		}
	}
	
}
