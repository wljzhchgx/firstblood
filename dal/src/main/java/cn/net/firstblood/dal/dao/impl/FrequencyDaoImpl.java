package cn.net.firstblood.dal.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

import cn.net.firstblood.dal.dao.FrequencyDao;
import cn.net.firstblood.dal.enums.FrequencyBizType;
import cn.net.firstblood.dal.model.FrequencyDO;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class FrequencyDaoImpl extends BaseDaoImpl<FrequencyDO> implements FrequencyDao {

	public FrequencyDaoImpl() {
		super(FrequencyDO.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean addOneTimeByUkKey(FrequencyBizType fcBiz,String bizKey) {
		try{
			FrequencyDO frequency4Save = new FrequencyDO();
			frequency4Save.setUkKey(fcBiz.bulidUkKey(bizKey));
			frequency4Save.setTimes(1L);
			this.insert(frequency4Save);
			return true;
		}catch(RuntimeException e){
			if (e instanceof DataIntegrityViolationException
	                && e.getMessage().contains("Duplicate entry")) {
				//DuplicateKeyException
				Map<String, Object> param = new HashMap<String, Object>();
		        param.put("ukKey", fcBiz.bulidUkKey(bizKey));
		        param.put("maxLimit", fcBiz.getMaxLimit());
				return sqlMapTemplate.update(getQualifiedStatementName("addOneTimeByUkKey"), param) == 1;
			}
			throw e;
		}
	}

}