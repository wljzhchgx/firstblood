package cn.net.firstblood.biz.manager.impl;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;

import cn.net.firstblood.biz.cmd.CmdFacade;
import cn.net.firstblood.biz.manager.WeChatManager;
import cn.net.firstblood.framework.enums.WeChatMsgType;
import cn.net.firstblood.framework.notifier.WeChatIM;
import cn.net.firstblood.framework.notifier.model.ConfigStore;
import cn.net.firstblood.framework.notifier.model.wechatim.InitRespPO;
import cn.net.firstblood.framework.notifier.model.wechatim.WebwxSyncRespPO;
import cn.net.firstblood.framework.util.BeanUtil;
import cn.net.firstblood.framework.util.DateUtil;
import cn.net.firstblood.framework.util.LoggerUtil;

/**
 * @author gangxiang.chengx
 * @version $Id: WeChatManagerImpl.java, v 0.1 2017年2月9日 下午2:23:22 gangxiang.chengx Exp $
 */
public class WeChatManagerImpl implements WeChatManager {
	
	private static final ExecutorService SYNC_CHECK_POOL = Executors.newSingleThreadExecutor();
	
	private boolean startFlag = false;
	@Override
	public synchronized void syncCheck() {
		if(!startFlag){
			LoggerUtil.COMMON.info("心跳任务未启动 即将开启");
			SYNC_CHECK_POOL.execute(new SyncCheckTask());
			startFlag = true;
			return;
		}
		Date date = (Date) ConfigStore.getSelfConfig(ConfigStore.WECHATRECEIVE_LATEST_TIME);
		if(date == null){
			LoggerUtil.COMMON.info("心跳任务启动中");
			return;
		}
		if(DateUtil.getCurrentTime().after(DateUtil.addTime(date, 60))){
			LoggerUtil.COMMON.info("心跳任务异常"+ConfigStore.getSelfConfig(ConfigStore.WECHATRECEIVE_LATEST_TIME));
		}else{
			LoggerUtil.COMMON.info("心跳任务正常"+ConfigStore.getSelfConfig(ConfigStore.WECHATRECEIVE_LATEST_TIME));
		}
	}
	
	class SyncCheckTask implements Runnable{
		private InitRespPO initRespPO;
		
		private CmdFacade	cmdFacade;
		
		@Override
		public void run() {
			while(true){
				try{
					LoggerUtil.COMMON.info("SyncCheckTask start");
					init();
					LoggerUtil.COMMON.info("SyncCheckTask begin syncKey"+JSON.toJSONString(initRespPO.getSyncKey()));
					String receiveSrc= WeChatIM.receive(initRespPO.getSyncKey());
					//掉线
					if(receiveSrc.indexOf("retcode:\"1101\"") != -1){
						LoggerUtil.COMMON.info("SyncCheckTask off line");
						Thread.sleep(60000);
						ConfigStore.setConfigNull(initRespPO);
						continue;
					}
					//正常
					if(receiveSrc.indexOf("selector:\"0\"") != -1){
						String weChatReceive = receiveSrc+"[date:"+DateUtil.format(new Date())+"]";
						ConfigStore.setSelfConfig(ConfigStore.WECHATRECEIVE, weChatReceive);
						ConfigStore.setSelfConfig(ConfigStore.WECHATRECEIVE_LATEST_TIME, new Date());
						LoggerUtil.COMMON.info("SyncCheckTask receive:"+weChatReceive);
						LoggerUtil.COMMON.info("SyncCheckTask end");
						continue;
					}
					//有消息同步
					if(receiveSrc.indexOf("selector:\"2\"") != -1){
						WebwxSyncRespPO webwxSyncResp = WeChatIM.receiveMsg(initRespPO.getSyncKey());
						if(webwxSyncResp.getAddMsgList()!=null && webwxSyncResp.getAddMsgList().size()>0){
							String cmdResult = cmdFacade.exeCmd(webwxSyncResp.getAddMsgList().get(0).getContent());
							WeChatIM.notify(cmdResult,WeChatMsgType.TEXT);
						}
						if(webwxSyncResp.getSyncCheckKey()!=null && webwxSyncResp.getSyncCheckKey().getCount()>0){
							initRespPO.setSyncKey(webwxSyncResp.getSyncCheckKey());
							LoggerUtil.COMMON.info("SyncCheckTask reset syncKey"+JSON.toJSONString(webwxSyncResp.getSyncCheckKey()));
						}else{
							ConfigStore.setConfigNull(initRespPO);
							LoggerUtil.COMMON.info("SyncCheckTask set init");
						}
						LoggerUtil.COMMON.info("SyncCheckTask receiveMsg end");
						continue;
					}
					ConfigStore.setConfigNull(initRespPO);
					LoggerUtil.COMMON.info("心跳检测异常 需要进行抢救[receiveSrc:"+receiveSrc+"]");
				}catch(Exception e){
					ConfigStore.setConfigNull(initRespPO);
					LoggerUtil.COMMON.error("SyncCheckTask error",e);
				}
			}
		}
		
		private void init(){
			initRespPO = ConfigStore.getConfig(InitRespPO.class);
			if(initRespPO == null){
				LoggerUtil.COMMON.info("心跳检测initRespPO==null");
				initRespPO = WeChatIM.init();
				ConfigStore.setConfig(initRespPO);
			}else{
				LoggerUtil.COMMON.info("心跳检测initRespPO!=null");
			}
			if(cmdFacade == null){
				cmdFacade = BeanUtil.getNean("cmdFacade");
			}
		}
	}
	
	public static void main(String args[]){
		Date date = (Date) ConfigStore.getSelfConfig(ConfigStore.WECHATRECEIVE_LATEST_TIME);
		System.out.println(date);
	}

}
