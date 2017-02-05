/**
 * 
 */
package cn.net.firstblood.biz.cmd.manager.impl;

import cn.net.firstblood.biz.cmd.manager.CmdManager;
import cn.net.firstblood.biz.life.tianqi.TianQiTool;

/**
 * @author gangxiang.chengx
 *
 */
public class TianQiCmdManagerImpl implements CmdManager {

	@Override
	public String exeCmd() {
		return TianQiTool.getTianQi();
	}
	
	public static void main(String[] args){
		System.out.println(TianQiTool.getTianQi());
	}

}
