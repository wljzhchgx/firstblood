/**
 * 
 */
package cn.net.firstblood.biz.cmd.manager.impl;

import cn.net.firstblood.biz.cmd.manager.CmdManager;
import cn.net.firstblood.framework.enums.CmdType;

/**
 * @author gangxiang.chengx
 *
 */
public class CmdCmdManagerImpl implements CmdManager {

	/* (non-Javadoc)
	 * @see cn.net.firstblood.biz.cmd.CmdManager#exeCmd(cn.net.firstblood.framework.enums.CmdType)
	 */
	@Override
	public String exeCmd() {
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
