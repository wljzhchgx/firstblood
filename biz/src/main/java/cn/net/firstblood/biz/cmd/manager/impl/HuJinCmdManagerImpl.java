/**
 * 
 */
package cn.net.firstblood.biz.cmd.manager.impl;

import java.util.Map;

import cn.net.firstblood.biz.cmd.manager.CmdManager;
import cn.net.firstblood.biz.common.notifier.EmailNotifier;
import cn.net.firstblood.biz.manager.MemorialDayManager;

/**
 * @author gangxiang.chengx
 *
 */
public class HuJinCmdManagerImpl implements CmdManager {
	private MemorialDayManager	memorialDayManager;
	
	@Override
	public String exeCmd(String srcCmd) {
		//return GrailTool.getHuJin();
		String str =  getStrStatisticsBase(memorialDayManager.statisticsBase());
		//EmailNotifier.notify("549891545@qq.com", "命令通知", str.replaceAll("\n", "</br>"));
		return str;
	}
	
	/**
	 *  |  99999天|    365天|
		|  99999天|    365天|
		|  99999天|    365天|
		|  99999天|    365天|
		-----------------------------
	 * @param msgMap
	 * @return
	 */
	private String getStrStatisticsBase(Map<String,String> msgMap){
		String result = "";
		int i = 0;
		for(Map.Entry<String,String> entry : msgMap.entrySet()){
			result = result+"|"+entry.getValue();
			if(i>0 && i%2 == 1){
				result = result + "|\n";
			}
			i++;
		}
		if(result.length()>2){
			result = result.substring(0, result.length()-1);
		}
		return result;
	}

	public void setMemorialDayManager(MemorialDayManager memorialDayManager) {
		this.memorialDayManager = memorialDayManager;
	}

}
