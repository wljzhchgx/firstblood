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
import cn.net.firstblood.dal.dao.RecordDao;
import cn.net.firstblood.dal.enums.RecordType;
import cn.net.firstblood.dal.model.RecordDO;
import cn.net.firstblood.framework.util.BeanUtil;
import cn.net.firstblood.framework.util.HttpClientUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 *
 */
public class RecordJob implements StatefulJob {
	
	private RecordDao	recordDao;
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		recordZcb();
		
	}
	
	private void recordZcb(){
		try{
			LoggerUtil.COMMON.info("招财宝利率记录开始");
			init();
			List<RecordDO> recordList4Save = new ArrayList<RecordDO>();
			for(ZcbPO zcbPO : CheckZcbJob.zcbList){
				getRate(zcbPO);
				RecordDO record = new RecordDO();
				record.setType(RecordType.valueOf(zcbPO.getEnName()));
				record.setValue(getRate(zcbPO));
				recordList4Save.add(record);
			}
			recordDao.insertBatch(recordList4Save);
			LoggerUtil.COMMON.info("招财宝利率记录完毕");
		}catch(Exception e){
			LoggerUtil.COMMON.error("招财宝利率记录异常",e);
		}
	}
	
	private void init(){
		if(recordDao == null){
			recordDao = BeanUtil.getNean("recordDao");
		}
	}
	
	private Double getRate(ZcbPO zcb){
		try{
			String html = HttpClientUtil.doGet(zcb.getMaxRateUrl(),HttpClientUtil.GBK);
			int index = html.indexOf("class=\"f-18\"");
			if(index == -1){
				LoggerUtil.COMMON.info(zcb.getName()+"当前无产品");
				return 0D;
			}
			String rateStr = html.substring(index+13, index+17);
			return Double.valueOf(rateStr);
		}catch(Exception e){
			LoggerUtil.COMMON.error(zcb.getName()+"招财宝利率解析异常",e);
		}
		return null;
	}
	
	
}
