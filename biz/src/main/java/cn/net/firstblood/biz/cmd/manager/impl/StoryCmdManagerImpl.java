/**
 * 
 */
package cn.net.firstblood.biz.cmd.manager.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.net.firstblood.biz.cmd.manager.CmdManager;
import cn.net.firstblood.biz.life.story.StoryTool;
import cn.net.firstblood.framework.util.WeChatViewUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class StoryCmdManagerImpl implements CmdManager {
	private static final Map<String,Object> ZHUTI_MAP = new HashMap<String,Object>();
	static{
		ZHUTI_MAP.put("renshengganwu", "人生感悟");
		ZHUTI_MAP.put("lizhigushi", "励志故事");
		ZHUTI_MAP.put("zheligushi", "哲理故事");
		ZHUTI_MAP.put("renshengzheli", "人生哲理");
		ZHUTI_MAP.put("xiaogushi", "小故事");
	}

	@Override
	public String exeCmd(String srcCmd) {
		String[] cmdArray = srcCmd.split("-");
		if(cmdArray.length == 1){
			return WeChatViewUtil.parseToOneColumn(ZHUTI_MAP);
		}
		if(cmdArray.length == 2){
			return StoryTool.getStoryList(getValueByIndex(Integer.valueOf(cmdArray[1])));
		}
		return StoryTool.getStoryDetail(getValueByIndex(Integer.valueOf(cmdArray[1])),Integer.valueOf(cmdArray[2]));
	}
	
	private String getValueByIndex(int index){
		int i = 0;
		for(Entry<String,Object> entry : ZHUTI_MAP.entrySet()){
			if(i == index){
				return entry.getKey();
			}
			i ++;
		}
		return null;
	}
	
}
