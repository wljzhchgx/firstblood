/**
 * 
 */
package cn.net.firstblood.biz.grail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.JsonUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class GrailTool {
	private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
	
	/**
	 * 沪金
	 * @return
	 */
	public static String getHuJin(){
		try{
			//jQuery1113033564959213765355_1446798950327({"markettime":"2015-11-06 15:00:00","servertime":"2015-11-0616:35:50","stockcode":"au1512","stockname":"au1512","New":227.800,"Open":227.200,"High":228.200,"Low":226.850,"YClose":228.350,"PreSettlementPrice":228.350,"price_max":239.750,"price_min":216.900,"Buy1":227.800,"Sell1":227.850,"BuyVol1":16,"SellVol1":2,"volume":111246,"amount":25315502700.0})
			String result = HttpClientUtil.doGet("http://hqd.jyzd.com/?opt=gethq&callback=jQuery111306044070358989378_1446789780086&name=au1512&_="+new Date().getTime(),HttpClientUtil.GBK);
			Map<String,Object> resultMap = JsonUtil.jsonToMap(result.substring(result.indexOf("(")+1, result.lastIndexOf(")")));
			BigDecimal preSettlementPrice = new BigDecimal (resultMap.get("PreSettlementPrice").toString());
			BigDecimal newPrice =new BigDecimal ( resultMap.get("New").toString());
			String msg = "[沪金目前价格:"+newPrice+"]";
			if(newPrice.compareTo(preSettlementPrice) > -1){
				msg += "[涨:+"+newPrice.subtract(preSettlementPrice)+",+"+newPrice.subtract(preSettlementPrice).multiply(ONE_HUNDRED).divide(preSettlementPrice,2,BigDecimal.ROUND_HALF_UP)+"%]";
			}else{
				msg += "[跌:-"+preSettlementPrice.subtract(newPrice)+",-"+preSettlementPrice.subtract(newPrice).multiply(ONE_HUNDRED).divide(preSettlementPrice,2,BigDecimal.ROUND_HALF_UP)+"%]";
			}
			LoggerUtil.COMMON.info(msg);
			return msg;
		}catch(Exception e){
			LoggerUtil.COMMON.error("沪金异常",e);
		}
		return null;
	}
	
	/**
	 * 上证
	 * @return
	 */
	public static String getHuSS(){
		try{
			//var hq_str_s_sh000001="上证指数,3590.032,67.213,1.91,4291670,54328220";
			String result = HttpClientUtil.doGet("http://hq.sinajs.cn/rn="+new Date().getTime()+"&list=s_sh000001",HttpClientUtil.GBK);
			LoggerUtil.COMMON.info("上证[result:"+result+"]");
			String[] resultArray = result.substring(result.indexOf("\"")+1, result.lastIndexOf("\"")).trim().split(",");
			String msg1 = "";
			if(new BigDecimal(resultArray[2]).compareTo(BigDecimal.ZERO) > -1){
				msg1 = "涨";
			}else{
				msg1 = "跌";
			}
			BigDecimal hands = new BigDecimal(resultArray[4]).divide(new BigDecimal("1000000")).setScale(2, RoundingMode.HALF_UP);
			BigDecimal amt = new BigDecimal(resultArray[5]).divide(new BigDecimal("10000")).setScale(2, RoundingMode.HALF_UP);
			String msg = "[上证指数:"+resultArray[1]+"]["+msg1+":"+resultArray[2]+","+resultArray[3]+"%]"
					+ "[成交量:"+hands+"亿手]"
					+ "[成交额:"+amt+"亿元]"+"[均价:"+amt.divide(hands,2,RoundingMode.HALF_UP)+"元/手]";
			LoggerUtil.COMMON.info(msg);
			return msg;
		}catch(Exception e){
			LoggerUtil.COMMON.error("上证异常",e);
		}
		return null;
	}
	
	public static void main(String[] args){
		System.out.println(getHuSS());
	}
}
