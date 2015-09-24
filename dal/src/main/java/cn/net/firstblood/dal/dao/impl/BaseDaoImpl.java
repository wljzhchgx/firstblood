package cn.net.firstblood.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.CollectionUtils;

import cn.net.firstblood.dal.dao.BaseDao;
import cn.net.firstblood.dal.model.EntityObject;
import cn.net.firstblood.dal.param.PageParam;

import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * @author gangxiang.chengx
 *
 * @param <T>
 */
public class BaseDaoImpl<T extends EntityObject> implements BaseDao<T> {

    private String persistentClassShortName;
    
    protected SqlMapClientTemplate sqlMapTemplate ;

    public BaseDaoImpl(Class<T> persistentClass) {
    	String name = persistentClass.getSimpleName();
    	if (EntityObject.class.isAssignableFrom(persistentClass)) {
    		this.persistentClassShortName = name.substring(0, name.length() - 2);
    	}
    }
    
    public BaseDaoImpl(String persistentClassShortName) {
		this.persistentClassShortName = persistentClassShortName;
	}

	public void setSqlMapTemplate(SqlMapClientTemplate sqlMapTemplate) {
		this.sqlMapTemplate = sqlMapTemplate;
	}

    @Override
    public Integer deleteById(Long id) {
    	return sqlMapTemplate.delete(getQualifiedStatementName("deleteById"), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getById(Long id) {
        return (T) sqlMapTemplate.queryForObject(getQualifiedStatementName("getById"), id);
    }

    @Override
    public T insert(T entity) {
        Object pk = sqlMapTemplate.insert(getQualifiedStatementName("insert"), entity);
        entity.setId(Long.valueOf(pk.toString()));
        return entity;
    }

    @Override
    public Integer update(T entity) {
    	return sqlMapTemplate.update(getQualifiedStatementName("update"), entity);
    }
    
    @Override
    public Integer countByParam(PageParam<T> param) {
    	return (Integer) sqlMapTemplate.queryForObject(getQualifiedStatementName("countByParam"), param);
    }
    
	@Override
	@SuppressWarnings("unchecked")
    public List<T> queryByParam(PageParam<T> param) {
    	return sqlMapTemplate.queryForList(getQualifiedStatementName("queryByParam"), param);
    }

    protected String getQualifiedStatementName(String statementName) {
        return new StringBuilder(persistentClassShortName).append(".").append(statementName).toString();
    }

	@Override
	public void insertBatch(final List<T> entityList) {
		if(CollectionUtils.isEmpty(entityList)){
			return;
		}
		sqlMapTemplate.execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for (T entity : entityList) {
					insert(entity);
				}
				return executor.executeBatch();
			}
		});
	}
	
	@Override
	public void updateBatch(final List<T> entityList) {
		if(CollectionUtils.isEmpty(entityList)){
			return;
		}
		sqlMapTemplate.execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(final SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for (final T entity : entityList) {
					update(entity);
				}
				return executor.executeBatch();
			}
		});
	}
	
	@Override
	public void deleteBatch(final List<T> entityList) {
		if(CollectionUtils.isEmpty(entityList)){
			return;
		}
		sqlMapTemplate.execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for (T entity : entityList) {
					deleteById(entity.getId());
				}
				return executor.executeBatch();
			}
		});
	}
}
