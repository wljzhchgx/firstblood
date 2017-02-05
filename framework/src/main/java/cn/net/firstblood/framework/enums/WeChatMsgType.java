package cn.net.firstblood.framework.enums;

/**
 * @author gangxiang.chengx
 * @version $Id: WeChatMsgType.java, v 0.1 2017年2月4日 上午11:21:17 gangxiang.chengx Exp $
 */
public enum WeChatMsgType {
	TEXT("1","文字消息"),
	
	IMAGE("3","图片消息");
	
	private String key;
	
	private String desc;
	
	private WeChatMsgType(String key,String desc) {
		this.key = key;
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getKey(){
		return key;
	}
	
	public static WeChatMsgType getByKey(String key){
		for(WeChatMsgType type : WeChatMsgType.values()){
			if(type.getKey().equals(key)){
				return type;
			}
		}
		return null;
	}
}
