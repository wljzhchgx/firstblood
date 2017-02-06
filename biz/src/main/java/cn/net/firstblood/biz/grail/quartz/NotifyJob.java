/**
 * 
 */
package cn.net.firstblood.biz.grail.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import cn.net.firstblood.biz.grail.GrailTool;
import cn.net.firstblood.framework.enums.WeChatMsgType;
import cn.net.firstblood.framework.notifier.WeChatIM;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class NotifyJob implements StatefulJob {
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		WeChatIM.notify(GrailTool.getHuSS("sz399006", "创业板指数")+"\n"+GrailTool.getHuSS("sh000001", "上证综合指数"),WeChatMsgType.TEXT);
		//WeChatIM.notify(getHuSS());
	}
}
