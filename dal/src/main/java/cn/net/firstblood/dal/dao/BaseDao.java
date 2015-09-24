/**
 * 
 */
package cn.net.firstblood.dal.dao;

import java.util.List;

import cn.net.firstblood.dal.model.EntityObject;
import cn.net.firstblood.dal.param.PageParam;

/**
 * @author gangxiang.chengx
 *
 */
public interface BaseDao<T extends EntityObject> {
	 /**
    * 新增记录
    * @param entity
    * @return
    */
   T insert(T entity);

   /**
    * 根据唯一标识删除记录
    * @param id
    * @return
    */
   Integer deleteById(Long id);

   /**
    * 修改记录
    * @param entity
    * @return
    */
   Integer update(T entity);

   /**
    * 根据唯一标识获取记录
    * @param id
    * @return
    */
   T getById(Long id);

   /**
    * 查询记录数
    * @param param
    * @return
    */
   Integer countByParam(PageParam<T> param);

   /**
    * 根据查询参数分页查询实体
    * @param param
    * @return
    */
   List<T> queryByParam(PageParam<T> param);

   /**
	 * 批量插入
	 * @param entityList
	 */
	void insertBatch(List<T> entityList);

	/**
	 * 批量修改
	 * @param entityList
	 */
	void updateBatch(List<T> entityList);

	/**
	 * 批量删除
	 * @param entityList
	 */
	void deleteBatch(List<T> entityList);
}
