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
public class HuJinCmdManagerImpl implements CmdManager {
	@Override
	public String exeCmd() {
		return GrailTool.getHuJin();
	}

}
