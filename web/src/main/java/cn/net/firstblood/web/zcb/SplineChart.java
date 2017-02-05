/**
 * 
 */
package cn.net.firstblood.web.zcb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.net.firstblood.dal.dao.RecordDao;
import cn.net.firstblood.dal.enums.DirType;
import cn.net.firstblood.dal.enums.RecordType;
import cn.net.firstblood.dal.model.RecordDO;
import cn.net.firstblood.dal.param.RecordParam;
import cn.net.firstblood.framework.util.DateUtil;

/**
 * @author gangxiang.chengx
 *
 */
@Controller
public class SplineChart {
//	@Autowired
	private RecordDao recordDao;
	
	@RequestMapping("/zcb/splineChart.htm")
    public String execute(HttpServletRequest request,ModelMap model) {
		RecordType type = RecordType.valueOf(request.getParameter("type"));
		List<Date> dateList = getDateList();
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
		return "/zcb/splineChart";
	}
	
	/**
	 * 最近24小时 每隔5分钟
	 * @return
	 */
	private List<Date> getDateList(){
		Date now = DateUtil.parseDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:00"));
		List<Date> dateList= new ArrayList<Date>();
		for(int i = 1 ;i<=60*24/5 ;i++){
			dateList.add(DateUtil.addTime(now, -60*5*(60*24/5-i)));
		}
		return dateList;
	}
	
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

}
