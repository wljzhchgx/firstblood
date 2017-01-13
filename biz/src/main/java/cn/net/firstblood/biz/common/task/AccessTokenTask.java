package cn.net.firstblood.biz.common.task;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import cn.net.firstblood.dal.dao.ConfigDao;
import cn.net.firstblood.dal.enums.ConfigType;
import cn.net.firstblood.dal.model.ConfigDO;
import cn.net.firstblood.framework.model.BaseJsonPO;
import cn.net.firstblood.framework.notifier.model.ConfigStore;

/**
 * 从数据库加载配置任务
 * @author gangxiang.chengx
 *
 */
public class AccessTokenTask extends TimerTask implements InitializingBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenTask.class);
    
    private Timer timer;
    
    private ConfigDao configDao;
    
    /**
     * 执行timer
     */
    protected void init() {
        timer = new Timer();
        LOGGER.info("延迟10s执行，每10s重新加载配置");
        timer.schedule(this, 10*1000, 10*1000);
    }

    @Override
    public void run() {
        try {
            //load();
        } catch (Exception e) {
            LOGGER.error("重新加载Config配置发生异常", e);
        }
    }
    
	/**
     * 加载Config配置
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
     */
	private void load() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
    	for(ConfigType type : ConfigType.values()){
    		ConfigDO config = configDao.queryByType(type);
    		if(config == null){
    			continue;
    		}
    		Object obj = Class.forName(type.getClassName()).newInstance();
    		BaseJsonPO<?> jsonObj = (BaseJsonPO<?>)obj;
    		ConfigStore.setConfig(jsonObj.getObjectFromJson(config.getContext()));
    	}
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("容器启动完成，加载Config配置");
        try {
            load();
        } catch (Exception e) {
            // 初始化时出现问题，抛出异常
            LOGGER.error("初始化加载Config配置发生异常", e);
        }
    }

	public void setConfigDao(ConfigDao configDao) {
		this.configDao = configDao;
	}

}
