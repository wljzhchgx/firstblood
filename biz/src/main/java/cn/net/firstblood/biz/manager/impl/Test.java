/**
 * mybank.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package cn.net.firstblood.biz.manager.impl;

import java.util.Date;

import cn.net.firstblood.framework.notifier.model.ConfigStore;
import cn.net.firstblood.framework.notifier.model.wechatim.BaseResponsePO;
import cn.net.firstblood.framework.notifier.model.wechatim.InitRespPO;

/**
 * @author gangxiang.chengx
 * @version $Id: Test.java, v 0.1 2017年2月22日 下午5:51:43 gangxiang.chengx Exp $
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitRespPO initRespPO = new InitRespPO();
		ConfigStore.setConfig(initRespPO);
		
		System.out.println(initRespPO.getBaseResponse());
		System.out.println(ConfigStore.getConfig(InitRespPO.class).getBaseResponse());
		
		
		initRespPO.setBaseResponse(new BaseResponsePO());
		System.out.println(initRespPO.getBaseResponse());
		System.out.println(ConfigStore.getConfig(InitRespPO.class).getBaseResponse());
		ConfigStore.setConfigNull(initRespPO);
		initRespPO =ConfigStore.getConfig(InitRespPO.class);
		System.out.println(initRespPO);
		
		System.out.println(new Date(1487771390L));
	}

}
