/**
 * 
 */
package cn.net.firstblood.web.zcb.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.net.firstblood.biz.job.CheckZcbJob;
import cn.net.firstblood.biz.model.ZcbPO;
import cn.net.firstblood.dal.dao.ConfigDao;
import cn.net.firstblood.dal.enums.ConfigType;
import cn.net.firstblood.dal.model.ConfigDO;
import cn.net.firstblood.framework.enums.WeChatMsgType;
import cn.net.firstblood.framework.notifier.WeChatIM;
import cn.net.firstblood.framework.notifier.model.WeChatIMConfPO;
import cn.net.firstblood.framework.util.DateUtil;

/**
 * @author gangxiang.chengx
 *
 */
@Controller
public class ZcbAction {
	@Autowired
	private ConfigDao configDao;
	
	@RequestMapping("/zcb/action/doUpdateConfig.do")
    public String doSetConfig(HttpServletRequest request,ModelMap model) {
		ZcbPO zcbPO = buildZcbPO(request);
		
		for(ZcbPO zcbConfig : CheckZcbJob.zcbList){
			if(!zcbConfig.getEnName().equals(zcbPO.getEnName())){
				continue;
			}
			if(zcbPO.getWarnRate()!=null){
				zcbConfig.setWarnRate(zcbPO.getWarnRate());
			}
			if(zcbPO.getIsQQNotify()!=null){
				zcbConfig.setIsQQNotify(zcbPO.getIsQQNotify());
			}
			if(zcbPO.getIsWeChatNotify()!=null){
				zcbConfig.setIsWeChatNotify(zcbPO.getIsWeChatNotify());
			}
		}
		
		model.put("result", zcbPO.getEnName()+"恭喜设置成功");
		return "/result";
	}
	
	@RequestMapping("/zcb/action/doUpdateWeChatConfig.do")
    public String doUpdateWeChatConfig(HttpServletRequest request,ModelMap model) {
		ConfigDO config = bulidWeChatConfig(request);
		if(configDao.updateByType(config) == 1){
			model.put("result", ConfigType.WECHAT_IM_CONFIG.getDesc()+"恭喜设置成功");
		}else{
			model.put("result", ConfigType.WECHAT_IM_CONFIG.getDesc()+"恭喜设置失败");
		}
		
		return "/result";
	}
	
	@RequestMapping("/zcb/action/doNotify.do")
    public String doNotify(HttpServletRequest request,ModelMap model) {
		int hour = DateUtil.getHourOfDay();
		if(hour >= 12){
			hour -= 12;
		}
		model.put("result", WeChatIM.notify("我很健康"+WeChatIM.EMOJI_TIME.get(hour),WeChatMsgType.TEXT));
		return "/result";
	}
	
	private ConfigDO bulidWeChatConfig(HttpServletRequest request){
		WeChatIMConfPO weChatIMConfPO = new WeChatIMConfPO();
		weChatIMConfPO.setCookie(getString(request,"cookie"));
		weChatIMConfPO.setFromName(getString(request,"fromName"));
		weChatIMConfPO.setPassTicket(getString(request,"passTicket"));
		weChatIMConfPO.setReceiveAddr(getString(request,"receiveAddr"));
		weChatIMConfPO.setSid(getString(request,"sid"));
		weChatIMConfPO.setSkey(getString(request,"skey"));
		weChatIMConfPO.setToName(getString(request,"toName"));
		
		ConfigDO configDO = new ConfigDO();
		configDO.setType(ConfigType.WECHAT_IM_CONFIG);
		configDO.setContext(weChatIMConfPO.getJsonFromObject(weChatIMConfPO));
		return configDO;
	}
	
	private ZcbPO buildZcbPO(HttpServletRequest request){
		ZcbPO zcbPO = new ZcbPO();
		zcbPO.setEnName(getString(request,"enName"));
		zcbPO.setWarnRate(getDouble(request,"warnRate"));
		zcbPO.setIsQQNotify(getBoolean(request,"isQQNotify"));
		zcbPO.setIsWeChatNotify(getBoolean(request,"isWeChatNotify"));
		
		return zcbPO;
	}
	
	private String getString(HttpServletRequest request,String key){
		return request.getParameter(key)==null?null:String.valueOf(request.getParameter(key));
	}
	
	private Double getDouble(HttpServletRequest request,String key){
		return request.getParameter(key)==null?null:Double.valueOf(request.getParameter(key));
	}
	
	private Boolean getBoolean(HttpServletRequest request,String key){
		return request.getParameter(key)==null?null:Boolean.valueOf(request.getParameter(key));
	}
}
