/**
 * 
 */
package cn.net.firstblood.biz.zcb;

import cn.net.firstblood.biz.model.ZcbPO;
import cn.net.firstblood.dal.enums.RecordType;
import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class ZcbTool {
	
	public static String getMaxRate(ZcbPO zcb){
		String html = HttpClientUtil.doGet(zcb.getMaxRateUrl(),HttpClientUtil.GBK);
		int index = html.indexOf("class=\"f-18\"");
		if(index == -1){
			LoggerUtil.COMMON.info(zcb.getName()+"当前无产品");
			return null;
		}
		String rateStr = html.substring(index+13, index+17);
		Double rate = Double.valueOf(rateStr);
		zcb.setCurMaxRate(rate);
		LoggerUtil.COMMON.info(zcb.getName()+"当前最高利率:"+rate);
		return zcb.getName()+"当前最高利率:"+rate;
	}
	
	public static void main(String[] args){
		ZcbPO zcbPO = new ZcbPO("3-6个月",RecordType.ZCB_M0306_MAX_RATE.name(),"https://zhaocaibao.alipay.com/pf/productQuery.htm?pageNum=1&minMonth=3&maxMonth=6&minAmount=&danbao=1",0d,5.4,true,true);
		getMaxRate(zcbPO);
		System.out.println(zcbPO.getCurMaxRate());
	}
}
