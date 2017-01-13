/**
 * 
 */
package cn.net.firstblood.biz.cmd;

import java.util.Map;

import cn.net.firstblood.biz.cmd.manager.CmdManager;
import cn.net.firstblood.framework.enums.CmdType;

/**
 * @author gangxiang.chengx
 *
 */
public class CmdFacade {
	private Map<CmdType,CmdManager> cmdMap;

	public void setCmdMap(Map<CmdType, CmdManager> cmdMap) {
		this.cmdMap = cmdMap;
	}
	
	public String exeCmd(CmdType type){
		return cmdMap.get(type).exeCmd();
	}
}
