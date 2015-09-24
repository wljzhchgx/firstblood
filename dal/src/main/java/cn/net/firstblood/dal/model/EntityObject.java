/**
 * 
 */
package cn.net.firstblood.dal.model;

import java.util.Date;

/**
 * @author gangxiang.chengx
 *
 */
public class EntityObject {
	/**
     * 主键
     */
	private Long              id;
    
    /**
     * 创建人
     */
	private String            creator = "[SYS]";
    
    /**
     * 创建时间
     */
	private Date              gmtCreate;
    
    /**
     * 修改人
     */
	private String            modifier = "[SYS]";
    
    /**
     * 修改时间
     */
	private Date              gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
}
