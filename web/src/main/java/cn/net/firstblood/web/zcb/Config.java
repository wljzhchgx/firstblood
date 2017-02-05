/**
 * 
 */
package cn.net.firstblood.web.zcb;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.net.firstblood.biz.job.WeChatReceiveJob;
import cn.net.firstblood.framework.notifier.model.ConfigStore;
import cn.net.firstblood.framework.notifier.model.WeChatIMConfPO;

/**
 * @author gangxiang.chengx
 *
 */
@Controller("zcbConfigIndex")
public class Config {
	
	@RequestMapping("/zcb/config.htm")
    public String execute(HttpServletRequest request,ModelMap model) {
		model.put("weChatReceive", WeChatReceiveJob.weChatReceive);
		WeChatIMConfPO po = ConfigStore.getConfig(WeChatIMConfPO.class);
		if(po==null){
			model.put("weChatIMConfig", new WeChatIMConfPO());
		}else{
			model.put("weChatIMConfig", po);
		}
		return "/zcb/config";
	}
	
//	private Map<RecordType,List<RecordDO>> getChartsData(){
//		Map<RecordType,List<RecordDO>> result = new HashMap<RecordType,List<RecordDO>>();
//		List<Date> dateList = getDateList();
//		for(RecordType type : RecordType.values()){
//			result.put(type, getChartsDateByType(type,dateList));
//		}
//		return result;
//	}
}
