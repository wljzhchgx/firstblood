package cn.net.firstblood.dal.param;

import cn.net.firstblood.dal.model.ApplicationDO;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class ApplicationParam extends PageParam<ApplicationDO> {
	
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
