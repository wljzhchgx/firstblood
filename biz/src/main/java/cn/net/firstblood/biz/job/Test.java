/**
 * mybank.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package cn.net.firstblood.biz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author gangxiang.chengx
 * @version $Id: Test.java, v 0.1 2017年2月9日 上午11:44:00 gangxiang.chengx Exp $
 */
public class Test {

	/**
	 * @param args
	 * @throws JobExecutionException 
	 */
	public static void main(String[] args) throws JobExecutionException {
		System.out.println("begin");
		new WeChatReceiveJob().init();
		new WeChatReceiveJob().init();
	}

}
