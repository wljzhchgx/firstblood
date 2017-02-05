/**
 * 
 */
package cn.net.firstblood.dal.model;

import java.util.Date;


/**
 * 纪念日
 * @author gangxiang.chengx
 * @version $Id: MemorialDayDO.java, v 0.1 2017年2月4日 下午4:36:38 gangxiang.chengx Exp $
 */
public class MemorialDayDO extends EntityObject{
	/**
	 * 主题
	 */
	private String subject;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 纪念日期
	 */
	private Date date;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	} 
	
}
