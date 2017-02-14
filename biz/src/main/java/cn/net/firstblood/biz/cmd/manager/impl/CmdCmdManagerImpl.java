/**
 * 
 */
package cn.net.firstblood.biz.cmd.manager.impl;

import cn.net.firstblood.biz.cmd.manager.CmdManager;
import cn.net.firstblood.biz.job.HealthNotifyJob;
import cn.net.firstblood.framework.enums.CmdType;
import cn.net.firstblood.framework.enums.WeChatMsgType;
import cn.net.firstblood.framework.notifier.WeChatIM;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class CmdCmdManagerImpl implements CmdManager {

	/* (non-Javadoc)
	 * @see cn.net.firstblood.biz.cmd.CmdManager#exeCmd(cn.net.firstblood.framework.enums.CmdType)
	 */
	@Override
	public String exeCmd(String srcCmd) {
		String mediaId = WeChatIM.uploadMedia(HealthNotifyJob.FILE_ABSOLUTEPATH);
		String picMsg = WeChatIM.notify(mediaId, WeChatMsgType.IMAGE);
		LoggerUtil.COMMON.info("cmd receive:"+picMsg);
		String result = "命令:\n";
		for(CmdType c : CmdType.values()){
			result = result + c.getKey()+"."+c.getDesc()+"\n";
		}
		return result;
	}

	public static void main(String args[]){
		String result = "命令:\n";
		for(CmdType c : CmdType.values()){
			result = result + c.getKey()+"."+c.getDesc()+"\n";
		}
		
		System.out.println(result);
	}
}
