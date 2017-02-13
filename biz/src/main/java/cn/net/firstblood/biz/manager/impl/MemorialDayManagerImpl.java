package cn.net.firstblood.biz.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.net.firstblood.biz.manager.MemorialDayManager;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.FbConstant;

/**
 * 
 * @author gangxiang.chengx
 * @version $Id: MemorialDayManagerImpl.java, v 0.1 2017年2月10日 下午3:46:11 gangxiang.chengx Exp $
 */
public class MemorialDayManagerImpl implements MemorialDayManager {

	@Override
	public Map<String,String> statisticsBase() {
		Map<String,String> result = new HashMap<String,String>();
		Date today = DateUtil.getMorning(DateUtil.getCurrentTime());
		
		//与老婆已认识
		result.put("与老婆已认识", getCell("\ue428",DateUtil.getTimeIntervalDays(FbConstant.CONTACT_DAY,today)+"天"));
		result.put("距离老婆生日还有", getCell("\ue34B",getBirthdayIntervalDays(FbConstant.WIFE_BIRTHDAY)+"天"));
		return result;
	}
	
	private String getCell(String emoji,String content){
		while(content.length()<8){
			content = "-"+content;
		}
		return emoji+content;
	}
	
	/**
	 * 获取生日到期日
	 * @param birthday
	 * @return
	 */
	private int getBirthdayIntervalDays(Date birthday){
		Date today = DateUtil.getMorning(DateUtil.getCurrentTime());
		Date currBirthday = DateUtil.setYear(birthday, DateUtil.getYear());
		if(today.before(currBirthday)){
			return DateUtil.getTimeIntervalDays(today,currBirthday);
		}
		if(today.after(currBirthday)){
			currBirthday = DateUtil.addYear(currBirthday, 1);
			return DateUtil.getTimeIntervalDays(currBirthday,today);
		}
		return 0;
	}
	
	public static void main(String args[]){
		
		
		MemorialDayManagerImpl thisObj = new MemorialDayManagerImpl();
		System.out.println(thisObj.getBirthdayIntervalDays(FbConstant.WIFE_BIRTHDAY));
	}
	
}
