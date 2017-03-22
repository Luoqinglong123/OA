package com.lql.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lql.oa.domain.Department;

public class DepartmentUtils {

	public static List<Department> getAllDepartments(
			List<Department> departmentsTopList) {
			List<Department> departments = new ArrayList<Department>();
			walkDepartmentTreeList(departmentsTopList,"┣",departments);
		return departments;
	}

	private static void walkDepartmentTreeList(Collection<Department> departmentsTopList,String prefix, List<Department> departments) {
			for(Department top:departmentsTopList){
				Department copy = new Department();
				copy.setId(top.getId());
				copy.setName(prefix+top.getName());
				departments.add(copy);
				walkDepartmentTreeList(top.getChildren(),"　"+prefix,departments);
			}
	}

}
