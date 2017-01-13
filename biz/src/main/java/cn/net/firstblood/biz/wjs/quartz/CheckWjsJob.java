/**
 * 
 */
package cn.net.firstblood.biz.wjs.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import cn.net.firstblood.biz.wjs.model.WjsCheckPO;
import cn.net.firstblood.framework.notifier.QQIM;
import cn.net.firstblood.framework.notifier.WeChatIM;
import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.JsonUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class CheckWjsJob implements StatefulJob{
	private static final Map<String,String> STATUS_MAP = new HashMap<String,String>();
	
	public static final List<WjsCheckPO> WJS_CONF_LIST= new ArrayList<WjsCheckPO>();
	//不能变现
	public static final String cashable = "0";
	static{
		STATUS_MAP.put("5", "回款中");
		STATUS_MAP.put("2", "倒计时");
		STATUS_MAP.put("1", "还有机会");
		STATUS_MAP.put("0", "立即投资");
		STATUS_MAP.put("3", "已售罄");
		
		WJS_CONF_LIST.add(new WjsCheckPO("尊享系列","WJS_ZXXL","https://www.wjs.com/web/product/dataList",true,true,"2"));
		WJS_CONF_LIST.add(new WjsCheckPO("网金巨献","WJS_WJJX","https://www.wjs.com/web/product/dataList",true,true,"0"));
		WJS_CONF_LIST.add(new WjsCheckPO("普惠系列","WJS_ZXXL","https://www.wjs.com/web/product/dataList",true,true,"1"));
		WJS_CONF_LIST.add(new WjsCheckPO("新手专享","WJS_XSZX","https://www.wjs.com/web/product/dataList",true,true,"3"));
	}
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		LoggerUtil.COMMON.info("网金社:[config:"+JsonUtil.beanToJson(WJS_CONF_LIST)+"]");
		for(WjsCheckPO wjsCheck : WJS_CONF_LIST){
			//analysis(wjsCheck);
		}
	}
	
	private void analysis(WjsCheckPO wjsCheck){
		try{
			String json = HttpClientUtil.doPost(wjsCheck.getUrl(),wjsCheck.getPostParam());
			int index = json.indexOf("productStatus");
			if(index == -1){
				LoggerUtil.COMMON.info(wjsCheck.getName()+"网金社当前无产品");
				return;
			}
			//status =5 回款中
			String status = json.substring(index+17, index+18);
			wjsCheck.setMsg(wjsCheck.getName()+":状态="+status+"  "+STATUS_MAP.get(status));
			LoggerUtil.COMMON.info(wjsCheck.getMsg());
			if(status.equals("5")){
				return;
			}
			if(wjsCheck.getIsQQNotify()){
				QQIM.notify(wjsCheck.getMsg());
			}
			if(wjsCheck.getIsWeChatNotify()){
				WeChatIM.notify(wjsCheck.getMsg());
			}
		}catch(Exception e){
			LoggerUtil.COMMON.error(wjsCheck.getName()+"网金社解析异常",e);
		}
	}
	
	public static void main(String args[]){
		WjsCheckPO wjsCheck = WJS_CONF_LIST.get(0);
		System.out.println(HttpClientUtil.doHttpsPost(wjsCheck.getUrl(),wjsCheck.getPostParam()));
	}
}
