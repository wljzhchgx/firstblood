/**
 * 
 */
package cn.net.firstblood.web.zcb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.net.firstblood.biz.job.CheckZcbJob;
import cn.net.firstblood.biz.job.QQReceiveJob;
import cn.net.firstblood.biz.job.WeChatReceiveJob;
import cn.net.firstblood.dal.dao.RecordDao;
import cn.net.firstblood.dal.enums.DirType;
import cn.net.firstblood.dal.enums.RecordType;
import cn.net.firstblood.dal.model.RecordDO;
import cn.net.firstblood.dal.param.RecordParam;
import cn.net.firstblood.framework.notifier.model.ConfigStore;
import cn.net.firstblood.framework.notifier.model.WeChatIMConfPO;
import cn.net.firstblood.framework.util.DateUtil;

/**
 * @author gangxiang.chengx
 *
 */
@Controller("zcbIndex")
public class Index {
//	@Autowired
	private RecordDao recordDao;
	
	@RequestMapping("/zcb/index.htm")
    public String execute(HttpServletRequest request,ModelMap model) {
		model.put("qqReceive", QQReceiveJob.qqReceive);
		model.put("weChatReceive", WeChatReceiveJob.weChatReceive);
		model.put("zcbList", CheckZcbJob.zcbList);
		List<Date> dateList = getDateList();
		String strType = request.getParameter("type");
		RecordType type = RecordType.ZCB_M0612_MAX_RATE;
		if(StringUtils.isNotBlank(strType) && RecordType.valueOf(strType)!=null){
			type = RecordType.valueOf(strType);
		}
		List<RecordDO> chartsData = getChartsDateByType(type,dateList);
		Double max = null;
		Double min = null;
		for(RecordDO record : chartsData){
			if(max == null){
				max = record.getValue();
			}
			if(min == null){
				min = record.getValue();
			}
			if(record.getValue()>max){
				max = record.getValue();
			}
			if(record.getValue()<min){
				min = record.getValue();
			}
		}
		
		model.put("chartsData", chartsData);
		model.put("type", type);
		model.put("min", min);
		model.put("max", max);
		model.put("weChatIMConfig", ConfigStore.getConfig(WeChatIMConfPO.class));
		return "/zcb/index";
	}
	
//	private Map<RecordType,List<RecordDO>> getChartsData(){
//		Map<RecordType,List<RecordDO>> result = new HashMap<RecordType,List<RecordDO>>();
//		List<Date> dateList = getDateList();
//		for(RecordType type : RecordType.values()){
//			result.put(type, getChartsDateByType(type,dateList));
//		}
//		return result;
//	}
	
	/**
	 * 获取
	 * @param type
	 * @param dateList
	 * @return
	 */
	private List<RecordDO> getChartsDateByType(RecordType type,List<Date> dateList){
		RecordParam param = new RecordParam();
		param.setPageSize(RecordParam.MAX_PAGE_SIZE);
		param.setType(type);
		param.setOrderField("GMT_CREATE");
		param.setGmtCreateList(dateList);
		param.setDir(DirType.ASC);
		return recordDao.queryByParam(param);
	}
	
	/**
	 * 最近24小时 每隔5分钟
	 * @return
	 */
	private static List<Date> getDateList(){
		Date now = DateUtil.parseDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:00"));
//		Date minu30mins = DateUtil.addTime(now, -60*30);
//		
//		Date beginDate = null;
//		if(nearlyHour.before(minu30mins)){
//			beginDate = nearlyHour;
//		}else{
//			beginDate = DateUtil.addTime(nearlyHour, 60*30);
//		}
		
		List<Date> dateList= new ArrayList<Date>();
		
		for(int i = 1 ;i<=60*24/5 ;i++){
			dateList.add(DateUtil.addTime(now, -60*5*(60*24/5-i)));
		}
		return dateList;
	}
	
	public static void main(String args[]){
		System.out.println(60*24/5);
		List<Date> dateList = getDateList();
		System.out.println(dateList.size());
		for(Date date : dateList){
			System.out.println(DateUtil.format(date));
		}
//		Map<RecordType,List<RecordDO>> result = new HashMap<RecordType,List<RecordDO>>();
//		for(Entry<RecordType, List<RecordDO>>  e: result.entrySet()){
//			System.out.println(e.getKey());
//			System.out.println(e.getValue());
//		}
//		System.out.println(DateUtil.format(new Date(),"yyyy,MM,dd,HH,mm,00"));
	}
}
