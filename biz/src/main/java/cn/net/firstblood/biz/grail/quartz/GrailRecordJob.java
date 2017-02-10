/**
 * 
 */
package cn.net.firstblood.biz.grail.quartz;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import cn.net.firstblood.biz.grail.GrailTool;
import cn.net.firstblood.dal.dao.GrailRecordDao;
import cn.net.firstblood.dal.model.GrailRecordDO;
import cn.net.firstblood.framework.util.BeanUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * 大盘数据记录
 * @author gangxiang.chengx
 *
 */
public class GrailRecordJob implements StatefulJob {
	
	public static final Map<String,String> CODE_DESC_MAP = new HashMap<String,String>();
	static{
		CODE_DESC_MAP.put("sh000001", "上证综合指数");
		CODE_DESC_MAP.put("sz399006", "创业板指数");
	}
	
	private GrailRecordDao	grailRecordDao;
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		LoggerUtil.COMMON.info("GrailRecordJob start");
		init();
		for(Map.Entry<String,String> entry : CODE_DESC_MAP.entrySet()){
			GrailRecordDO grailRecord = GrailTool.getGrailDO(entry.getKey(), entry.getValue());
			grailRecordDao.insert(grailRecord);
		}
		LoggerUtil.COMMON.info("GrailRecordJob end");
	}
	
	private void init(){
		if(grailRecordDao == null){
			grailRecordDao = BeanUtil.getNean("grailRecordDao");
		}
	}
}
