/**
 * 
 */
package cn.net.firstblood.framework.notifier;

import java.util.Map;

import cn.net.firstblood.framework.enums.ChannelType;
import cn.net.firstblood.framework.notifier.service.IMService;

/**
 * 通知器
 * @author gangxiang.chengx
 *
 */
public class IMFacade {
	private Map<ChannelType,IMService> imMap;
	
	/**
	 * 通知，有疲劳度
	 * @param channelType
	 * @param msg
	 * @return
	 */
	public String send(ChannelType channelType , String msg){
		return imMap.get(channelType).notify(msg);
	}
	
	/**
	 * 通知，无疲劳度
	 * @param channelType
	 * @param msg
	 * @return
	 */
	public String sendIm(ChannelType channelType , String msg){
		return imMap.get(channelType).notifyIm(msg);
	}

	public void setImMap(Map<ChannelType, IMService> imMap) {
		this.imMap = imMap;
	}
	
}
