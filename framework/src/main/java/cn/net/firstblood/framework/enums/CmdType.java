/**
 * 
 */
package cn.net.firstblood.framework.enums;

/**
 * @author gangxiang.chengx
 *
 */
public enum CmdType {
	CMD("0","查看指令"),
	
	HU_JIN("1","沪金"),
	
	HU_SS("2","上证指数"),
	
	TIAN_QI("3","天气预报");
	
	private String key;
	
	private String desc;
	
	private CmdType(String key,String desc) {
		this.key = key;
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getKey(){
		return key;
	}
	
	public static CmdType getByKey(String key){
		for(CmdType type : CmdType.values()){
			if(type.getKey().equals(key)){
				return type;
			}
		}
		return null;
	}
	
}
