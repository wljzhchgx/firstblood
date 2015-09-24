/**
 * 
 */
package cn.net.firstblood.dal.enums;

/**
 * @author gangxiang.chengx
 *
 */
public enum DirType {
	/**
	 * 升序
	 */
	ASC("升序"),
	
	/**
	 * 降序
	 */
	DESC("降序");
	
	private String desc;
	
	private DirType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
