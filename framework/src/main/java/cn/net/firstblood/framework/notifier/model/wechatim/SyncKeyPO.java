package cn.net.firstblood.framework.notifier.model.wechatim;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import cn.net.firstblood.framework.model.BaseMapPO;

/**
 * @author gangxiang.chengx
 * @version $Id: SyncKeyPO.java, v 0.1 2017年2月8日 下午3:37:15 gangxiang.chengx Exp $
 */
public class SyncKeyPO {
	@JSONField(name = "Count")
	private int count;
	
	@JSONField(name = "List")
	private List<BaseMapPO> list;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<BaseMapPO> getList() {
		return list;
	}

	public void setList(List<BaseMapPO> list) {
		this.list = list;
	}
	
}
