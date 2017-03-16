/**
 * 
 */
package cn.net.firstblood.biz.cmd.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.net.firstblood.biz.cmd.manager.CmdManager;
import cn.net.firstblood.biz.life.tianqi.TianQiTool;
import cn.net.firstblood.dal.dao.MemorialDayDao;
import cn.net.firstblood.dal.model.MemorialDayDO;
import cn.net.firstblood.dal.param.MemorialDayParam;
import cn.net.firstblood.framework.enums.KeyWordEnum;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.WeChatViewUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class EventCmdManagerImpl implements CmdManager {
	private static final Map<String,Object> OPT = new HashMap<String,Object>();
	private static final Map<String,Object> OPT_KEYWORD = new HashMap<String,Object>();
	
	static{
		OPT.put("0", "保存(主题#内容#日期yyyyMMdd#1.电影 2.旅游 3.吃饭 4.其他 5.个人)");
		OPT.put("1", "查询(日期yyyyMMdd)");
		OPT.put("2", "删除(日期yyyyMMdd)");
		OPT.put("3", "追加(日期yyyyMMdd#内容)");
		
		OPT_KEYWORD.put("1", KeyWordEnum.MOVIE.getKey());
		OPT_KEYWORD.put("2", KeyWordEnum.TRAVEL.getKey());
		OPT_KEYWORD.put("3", KeyWordEnum.MEAL.getKey());
		OPT_KEYWORD.put("4", KeyWordEnum.OTHER.getKey());
		OPT_KEYWORD.put("5", KeyWordEnum.PERSONAL.getKey());
	}
	
	private MemorialDayDao memorialDayDao;
	
	@Override
	public String exeCmd(String srcCmd) {
		String result = doCmd(srcCmd);
		int total = memorialDayDao.countByParam(new MemorialDayParam());
		return result+"\n"+"Total:"+total;
	}
	
	private String doCmd(String srcCmd){
		String[] cmdArray = srcCmd.split("-");
		if(cmdArray.length == 1){
			return WeChatViewUtil.parseOneColumn(OPT);
		}
		if(cmdArray.length != 3){
			return "暂不支持的功能";
		}
		if("0".equals(cmdArray[1])){
			String[] contentArray = cmdArray[2].split("#");
			if(contentArray.length != 4){
				return "保存失败-保存数据不完整";
			}
			String dateStr = contentArray[2];
			if("0".equals(dateStr)){
				dateStr = DateUtil.format(new Date(), "yyyyMMdd");
			}
			if(memorialDayDao.getUniqeMDayByDate(DateUtil.parseDate(dateStr, "yyyyMMdd")) != null){
				return "保存失败-当天数据已存在";
			}
			MemorialDayDO memorialDayDO = new MemorialDayDO();
			memorialDayDO.setSubject(contentArray[0]);
			memorialDayDO.setContent(contentArray[1]+" ["+DateUtil.format(new Date())+"]");
//			memorialDayDO.setGroupCode(groupCode);
			memorialDayDO.setDate(DateUtil.parseDate(dateStr, "yyyyMMdd"));
			memorialDayDO.setKeyWord(OPT_KEYWORD.get(contentArray[3]).toString());
			memorialDayDao.insert(memorialDayDO);
			return "保存成功";
		}
		if("1".equals(cmdArray[1])){
			String dateStr = cmdArray[2];
			Date date = null;
			if("0".equals(dateStr)){
				date = new Date();
			}else{
				date = DateUtil.parseDate(dateStr, "yyyyMMdd");
			}
			MemorialDayDO mDay = memorialDayDao.getUniqeMDayByDate(date);
			if(mDay == null){
				return "查询无当天数据";
			}
			return mDay.toString();
		}
		if("2".equals(cmdArray[1])){
			String dateStr = cmdArray[2];
			Date date = null;
			if("0".equals(dateStr)){
				date = new Date();
			}else{
				date = DateUtil.parseDate(dateStr, "yyyyMMdd");
			}
			MemorialDayDO mDay = memorialDayDao.getUniqeMDayByDate(date);
			if(mDay == null){
				return "删除失败-无当天数据";
			}
			if(memorialDayDao.deleteById(mDay.getId()) == 1){
				return "删除成功";
			}
			return "删除失败";
		}
		if("3".equals(cmdArray[1])){
			String[] contentArray = cmdArray[2].split("#");
			if(contentArray.length != 2){
				return "追加失败-追加数据不完整";
			}
			String dateStr = contentArray[0];
			Date date = null;
			if("0".equals(dateStr)){
				date = new Date();
			}else{
				date = DateUtil.parseDate(dateStr, "yyyyMMdd");
			}
			MemorialDayDO mDay = memorialDayDao.getUniqeMDayByDate(date);
			if(mDay == null){
				return "追加失败-无当天数据";
			}
			MemorialDayDO mDay4update = new MemorialDayDO();
			mDay4update.setId(mDay.getId());
			mDay4update.setContent(mDay.getContent()+"\n"+contentArray[1]+" ["+DateUtil.format(new Date())+"]");
			if(memorialDayDao.update(mDay4update) == 1){
				return "追加成功";
			}
			return "追加失败";
		}
		return "暂不支持的功能";
	}
	
	public static void main(String[] args){
		System.out.println(TianQiTool.getTianQi());
	}

	public void setMemorialDayDao(MemorialDayDao memorialDayDao) {
		this.memorialDayDao = memorialDayDao;
	}

}
