package com.lql.ao.base.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.lql.ao.base.DaoSupport;
import com.lql.oa.domain.PageBean;
import com.lql.oa.util.QueryHelper;

@Transactional
@SuppressWarnings("unchecked")
public class DaoSupportImpl<T> implements DaoSupport<T> {
	@Resource
	private SessionFactory sessionFactory;
	private Class clazz = null;

	public DaoSupportImpl() {
		/* 使用反射技术得到T的真实类型 */
		// 获取new的对象的泛型的父类类型
		ParameterizedType genericSuperclass = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		// 获取第一个类型参数的真实类型
		clazz = (Class) genericSuperclass.getActualTypeArguments()[0];
		/*
		 * System.out.println(genericSuperclass); System.out.println(clazz);
		 */
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T enyity) {
		getSession().save(enyity);
	}

	public void delete(Long id) {
		Object obj = getById(id);
		if (obj != null) {
			getSession().delete(obj);
		}
	}

	public void update(T enyity) {
		getSession().update(enyity);
	}

	public T getById(Long id) {
		if (id == null) {
			return null;
		} else {
			return (T) getSession().get(clazz, id);
		}
	}

	public List<T> findAll() {
		return getSession().createQuery("FROM " + clazz.getSimpleName()).list();
	}

	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		} else {
			return getSession().createQuery(//
					"FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")//
					.setParameterList("ids", ids)//
					.list();

		}
	}

	// 公共查询分页信息的做法
	@Deprecated
	public PageBean getPageBean(int pageNum, int pageSize, String hql,
			List<Object> parameters) {
		System.out.println("====================getPageBean============================");
		Query query = getSession().createQuery(hql);// 创建查询对象
		if (parameters != null) {// 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		List list = query.setFirstResult((pageNum - 1) * pageSize)//
				.setMaxResults(pageSize).list();
		
		Query query2 = getSession().createQuery("SELECT COUNT(*)"+hql);
		if (parameters != null) {// 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				query2.setParameter(i, parameters.get(i));

			}
		}
		Long recordCount = (Long) query2.uniqueResult();//执行查询
		return new PageBean(pageNum, pageSize, recordCount.intValue(),
				list);
	}

	// 公共的查询分页信息的方法（最终版）
		public PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper) {
			System.out.println("-------> DaoSupportImpl.getPageBean( int pageNum, int pageSize, QueryHelper queryHelper )");

			// 参数列表
			List<Object> parameters = queryHelper.getParameters();

			// 查询本页的数据列表
			Query listQuery = getSession().createQuery(queryHelper.getListQueryHql()); // 创建查询对象
			if (parameters != null) { // 设置参数
				for (int i = 0; i < parameters.size(); i++) {
					listQuery.setParameter(i, parameters.get(i));
				}
			}
			listQuery.setFirstResult((pageNum - 1) * pageSize);
			listQuery.setMaxResults(pageSize);
			List list = listQuery.list(); // 执行查询

			// 查询总记录数量
			Query countQuery = getSession().createQuery(queryHelper.getCountQueryHql());
			if (parameters != null) { // 设置参数
				for (int i = 0; i < parameters.size(); i++) {
					countQuery.setParameter(i, parameters.get(i));
				}
			}
			Long count = (Long) countQuery.uniqueResult(); // 执行查询

			return new PageBean(pageNum, pageSize, count.intValue(), list);
		}
}
