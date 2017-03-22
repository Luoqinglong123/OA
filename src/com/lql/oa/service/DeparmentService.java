	package com.lql.oa.service;

import java.util.List;

import com.lql.ao.base.DaoSupport;
import com.lql.oa.domain.Department;

public interface DeparmentService  extends DaoSupport<Department>{

/*	List<Department> findAll();

	void delete(Long id);

	void save(Department department);

	Department getById(Long id);

	void update(Department department);
*/
	List<Department> findChildrenList(Long parentId);

	List<Department> findTopList();

}
