/**
 * 
 */
package cn.net.firstblood.biz.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import cn.net.firstblood.biz.model.ZcbPO;
import cn.net.firstblood.dal.enums.RecordType;
import cn.net.firstblood.framework.notifier.QQIM;
import cn.net.firstblood.framework.notifier.WeChatIM;
import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.JsonUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class CheckZcbJob implements StatefulJob {
	public static List<ZcbPO> zcbList;
	
	static{
		zcbList = new ArrayList<ZcbPO>();
		zcbList.add(new ZcbPO("3-6个月",RecordType.ZCB_M0306_MAX_RATE.name(),"https://zhaocaibao.alipay.com/pf/productQuery.htm?pageNum=1&minMonth=3&maxMonth=6&minAmount=&danbao=1",0d,5.4,true,true));
		zcbList.add(new ZcbPO("6-12个月",RecordType.ZCB_M0612_MAX_RATE.name(),"https://zhaocaibao.alipay.com/pf/productQuery.htm?pageNum=1&minMonth=6&maxMonth=12&minAmount=&danbao=1",0d,6.00,true,true));
		zcbList.add(new ZcbPO("12-24个月",RecordType.ZCB_M1224_MAX_RATE.name(),"https://zhaocaibao.alipay.com/pf/productQuery.htm?pageNum=1&minMonth=12&maxMonth=24&minAmount=&danbao=1",0d,6.99,true,true));
		zcbList.add(new ZcbPO("24个月以上",RecordType.ZCB_M249999_MAX_RATE.name(),"https://zhaocaibao.alipay.com/pf/productQuery.htm?pageNum=1&minMonth=24&maxMonth=9999&minAmount=&danbao=1",0d,6.99,true,true));
	}
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		LoggerUtil.COMMON.info("招财宝:[config:"+JsonUtil.beanToJson(zcbList)+"]");
		for(ZcbPO zcbPO : zcbList){
			analysis(zcbPO);
		}
	}
	
	private void analysis(ZcbPO zcb){
		try{
			String html = HttpClientUtil.doGet(zcb.getMaxRateUrl(),HttpClientUtil.GBK);
			int index = html.indexOf("class=\"f-18\"");
			if(index == -1){
				LoggerUtil.COMMON.info(zcb.getName()+"当前无产品");
				return;
			}
			String rateStr = html.substring(index+13, index+17);
			Double rate = Double.valueOf(rateStr);
			zcb.setCurMaxRate(rate);
			LoggerUtil.COMMON.info(zcb.getName()+"当前最高利率:"+rate);
			if(rate>=zcb.getWarnRate()){
				String notifyMsg = zcb.getName()+"当前利率高于设定利率:[warnRate:"+zcb.getWarnRate()+"],[curMaxRate:"+zcb.getCurMaxRate()+"]";
				if(zcb.getIsQQNotify()){
					QQIM.notify(notifyMsg);
				}
				if(zcb.getIsWeChatNotify()){
					WeChatIM.notify(notifyMsg);
				}
			}
		}catch(Exception e){
			LoggerUtil.COMMON.error(zcb.getName()+"招财宝利率解析异常",e);
		}
	}
	
	public static void main(String args[]){
		System.out.println(JsonUtil.beanToJson(zcbList));
	}

}
