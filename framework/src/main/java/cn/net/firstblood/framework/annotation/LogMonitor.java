package cn.net.firstblood.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志监控
 * @author gangxiang.chengx
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited()
public @interface LogMonitor {
	/**
	 * 监控的业务类型 比如：红包
	 * @return
	 */
	String bizType() default "common";

    /**
     * 监控的业务动作名称 比如：send
     * @return
     */
    String name() default "-";
}
