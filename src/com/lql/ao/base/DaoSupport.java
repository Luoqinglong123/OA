package com.lql.ao.base;

import java.util.List;

import com.lql.oa.domain.PageBean;
import com.lql.oa.util.QueryHelper;

public interface DaoSupport<T> {
	public void save(T enyity);

	public void delete(Long id);

	public void update(T enyity);

	public T getById(Long id);
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll();
	/**
	 *根据ID数组来查询
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(Long[] ids);
	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param hql 查询数据列表的hql
	 * @param parameters 参数列表，其顺序与hql中问号一一对应
	 * @return
	 */
	@Deprecated
	PageBean getPageBean(int pageNum, int pageSize, String hql,List<Object> parameters);

	/**
	 * 公共的查询分页信息的方法（最终版）
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param queryHelper
	 *            HQL语句与参数列表
	 * @return
	 */
	PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper);
}
