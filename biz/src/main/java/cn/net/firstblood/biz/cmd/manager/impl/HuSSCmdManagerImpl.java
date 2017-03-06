/**
 * 
 */
package cn.net.firstblood.biz.cmd.manager.impl;

import cn.net.firstblood.biz.cmd.manager.CmdManager;
import cn.net.firstblood.biz.grail.GrailTool;

/**
 * @author gangxiang.chengx
 *
 */
public class HuSSCmdManagerImpl implements CmdManager {

	@Override
	public String exeCmd(String srcCmd) {
		return GrailTool.getHuSS("sz399006", "创业板指数")+"\n"+GrailTool.getHuSS("sh000001", "上证综合指数");
	}

}
