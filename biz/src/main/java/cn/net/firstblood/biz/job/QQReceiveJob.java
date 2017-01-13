/**
 * 
 */
package cn.net.firstblood.biz.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import cn.net.firstblood.framework.notifier.QQIM;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * qq消息接收
 * @author gangxiang.chengx
 *
 */
public class QQReceiveJob implements StatefulJob {
	public static String qqReceive = "";
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		try{
			LoggerUtil.COMMON.info("QQReceiveJob start");
			qqReceive = QQIM.receive()+"[date:"+DateUtil.format(new Date())+"]";
			LoggerUtil.COMMON.info("QQReceiveJob receive:"+qqReceive);
			LoggerUtil.COMMON.info("QQReceiveJob end");
		}catch(Exception e){
			LoggerUtil.COMMON.error("QQReceiveJob error",e);
		}
	}
	
}
