/**
 * 
 */
package cn.net.firstblood.biz.life.memorialday;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.util.CollectionUtils;

import cn.net.firstblood.dal.dao.MemorialDayDao;
import cn.net.firstblood.dal.model.MemorialDayDO;
import cn.net.firstblood.dal.param.MemorialDayParam;
import cn.net.firstblood.dal.param.PageParam;
import cn.net.firstblood.framework.enums.WeChatMsgType;
import cn.net.firstblood.framework.notifier.WeChatIM;
import cn.net.firstblood.framework.util.BeanUtil;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.FbConstant;
import cn.net.firstblood.framework.util.FileUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * 纪念日通知
 * @author gangxiang.chengx
 * @version $Id: MemorialDayNotifyJob.java, v 0.1 2017年2月4日 下午5:06:21 gangxiang.chengx Exp $
 */
public class MemorialDayNotifyJob implements StatefulJob {
	
	private MemorialDayDao	memorialDayDao;
	
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		try{
			LoggerUtil.COMMON.info("MemorialDayNotifyJob start");
			init();
			doNotify();
			LoggerUtil.COMMON.info("MemorialDayNotifyJob end");
		}catch(Exception e){
			LoggerUtil.COMMON.error("MemorialDayNotifyJob error",e);
		}
	}
	
	private void init(){
		if(memorialDayDao == null){
			memorialDayDao = BeanUtil.getNean("memorialDayDao");
		}
	}
	
	private void doNotify(){
		int beginYear = 2016;
		String day_str = DateUtil.format(DateUtil.getCurrentTime(), "-MM-dd 00:00:00");
		boolean hasNotify = false;
		for(int year=beginYear;year<=DateUtil.getYear();year++){
			Date dateBegin = DateUtil.parseDate(year+day_str);
			Date dateEnd = DateUtil.addDays(dateBegin, 1);
			MemorialDayParam memorialDayParam = new MemorialDayParam();
			memorialDayParam.setPageIndex(1);
			memorialDayParam.setPageSize(PageParam.MAX_PAGE_SIZE);
			memorialDayParam.setDateBegin(dateBegin);
			memorialDayParam.setDateEnd(dateEnd);
			List<MemorialDayDO> memorialDayList = memorialDayDao.queryByParam(memorialDayParam);
			if(CollectionUtils.isEmpty(memorialDayList)){
				continue;
			}
			MemorialDayDO memorialDay = memorialDayList.get(0);
			WeChatIM.notify(year+"年的今天,"+memorialDay.getSubject()+"\n"+memorialDay.getContent(),WeChatMsgType.TEXT);
			hasNotify = true;
			String fileDirPath = FbConstant.PIC_ROOT_DIR_PATH+year+DateUtil.format(DateUtil.getCurrentTime(), "/MMdd");
			List<File> fileList = FileUtil.getFileList(fileDirPath);
			for(File file : fileList){
				LoggerUtil.COMMON.info("MemorialDayNotifyJob [file:"+file.getAbsolutePath()+"]");
				String mediaId = WeChatIM.uploadMedia(file.getAbsolutePath());
				WeChatIM.notify(mediaId, WeChatMsgType.IMAGE);
			}
		}
		if(!hasNotify){
			WeChatIM.notify("今日无纪念", WeChatMsgType.TEXT);
		}
	}
}
