/**
 * 
 */
package cn.net.firstblood.biz.cmd.manager.impl;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import cn.net.firstblood.biz.cmd.manager.CmdManager;
import cn.net.firstblood.biz.life.story.StoryTool;

/**
 * @author gangxiang.chengx
 *
 */
public class StoryCmdManagerImpl implements CmdManager {
	private static final String[] STORY_ARRAY = new String[5];
	static{
		STORY_ARRAY[0] = "renshengganwu";
		STORY_ARRAY[1] = "lizhigushi";
		STORY_ARRAY[2] = "zheligushi";
		STORY_ARRAY[3] = "renshengzheli";
		STORY_ARRAY[4] = "xiaogushi";
	}
	@Override
	public String exeCmd(String srcCmd) {
		String[] cmdArray = srcCmd.split("-");
		if(cmdArray.length == 1){
			return ReflectionToStringBuilder.toString(STORY_ARRAY.toString());
		}
		if(cmdArray.length == 2){
			return StoryTool.getStoryList(STORY_ARRAY[Integer.valueOf(cmdArray[1])]);
		}
		return StoryTool.getStoryDetail(STORY_ARRAY[Integer.valueOf(cmdArray[1])],Integer.valueOf(cmdArray[2]));
	}

}
