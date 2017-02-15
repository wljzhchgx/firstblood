/**
 * 
 */
package cn.net.firstblood.dal.model;

import java.util.Date;

import cn.net.firstblood.framework.util.DateUtil;


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
	
	/**
	 * 关键词  xxx,xxx
	 */
	private String keyWord;
	
	/**
	 * 分类码 一般为 时间戳+XXX
	 */
	private String groupCode;

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

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	} 
	
	@Override
	public String toString(){
		return "主题:"+this.getSubject()+"\n内容:"+this.getContent()
				+"\n关键词:"+this.getKeyWord()+"\n日期:"+DateUtil.format(this.getDate())
				+"\n创建时间:"+DateUtil.format(this.getGmtCreate());
	}
}
