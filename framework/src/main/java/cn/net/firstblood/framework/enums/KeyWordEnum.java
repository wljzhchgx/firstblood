package cn.net.firstblood.framework.enums;

/**
 * @author gangxiang.chengx
 * @version $Id: KeyWordEnum.java, v 0.1 2017年2月10日 下午3:02:29 gangxiang.chengx Exp $
 */
public enum KeyWordEnum {
	MOVIE("0","看电影"),
		
	TRAVEL_DOMESTIC("TRAVEL_DOMESTIC","国内旅游"),
	
	TRAVEL_FOREIGN("TRAVEL_FOREIGN","国外旅游"),
	
	MEAL("MEAL","吃饭"),
	
	OTHER("OTHER","其他");
	
	private String key;
	
	private String desc;
	
	private KeyWordEnum(String key,String desc) {
		this.key = key;
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getKey(){
		return key;
	}
	
	public static KeyWordEnum getByKey(String key){
		for(KeyWordEnum type : KeyWordEnum.values()){
			if(type.getKey().equals(key)){
				return type;
			}
		}
		return null;
	}

}
