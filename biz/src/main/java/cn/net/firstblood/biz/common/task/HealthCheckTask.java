package cn.net.firstblood.biz.common.task;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 应用健康检测
 * @author gangxiang.chengx
 *
 */
public class HealthCheckTask extends TimerTask implements InitializingBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckTask.class);
    
    private Timer timer;
    
    private BasicDataSource dataSource;
    /**
     * 执行timer
     */
    protected void init() {
        timer = new Timer();
        LOGGER.info("延迟10s执行，每10s健康检测");
        timer.schedule(this, 10*1000, 10*1000);
    }

    @Override
    public void run() {
        try {
        	check();
        } catch (Exception e) {
            LOGGER.error("健康检测发生异常", e);
        }
    }
    
    /**
     * 检测
     */
	private void check() {
		LOGGER.info("dataSource_url:"+dataSource.getUrl());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("容器启动完成，开始健康检测");
        try {
            check();
        } catch (Exception e) {
            // 初始化时出现问题，抛出异常
            LOGGER.error("健康检测发生异常", e);
        }
    }

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
