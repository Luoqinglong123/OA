package com.lql.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.lql.ao.base.impl.DaoSupportImpl;
import com.lql.oa.domain.Department;
import com.lql.oa.service.DeparmentService;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class DepartmentServiceImpl extends DaoSupportImpl<Department> implements
		DeparmentService {
	@Resource
	private SessionFactory sessionFactory;

	/*public List<Department> findAll() {
		return departmentDao.findAll();
	}

	public void delete(Long id) {
		departmentDao.delete(id);
	}

	public Department getById(Long id) {
		return departmentDao.getById(id);
	}

	public void update(Department department) {
		departmentDao.update(department);
	}

	public void save(Department department) {
		departmentDao.save(department);
	}
*/	
	public List<Department> findTopList() {
		return sessionFactory.getCurrentSession().createQuery(//
				"FROM Department d WHERE d.parent IS NULL").list();
	}

	public List<Department> findChildrenList(Long parentId) {
		return sessionFactory.getCurrentSession().createQuery(//
				"FROM Department d WHERE d.parent.id = ?")//
				.setParameter(0, parentId).list();
	}

}
