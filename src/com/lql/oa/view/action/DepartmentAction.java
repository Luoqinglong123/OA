package com.lql.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lql.ao.base.BaseAction;
import com.lql.oa.domain.Department;
import com.lql.oa.util.DepartmentUtils;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department> {
	private Long parentId;

	/* 列表 */
	public String list() {
		List<Department> list = null;
		if (parentId == null) {
			list = deparmentService.findTopList();
		} else {
			list = deparmentService.findChildrenList(parentId);
			Department parent = deparmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		ActionContext.getContext().put("departmentList", list);
		return "list";
	}

	/* 删除 */
	public String delete() {
		deparmentService.delete(model.getId());
		return "tolist";
	}

	/* 添加 */
	public String add() {
		Department department = deparmentService.getById(parentId);
		model.setParent(department);
		deparmentService.save(model);
		return "tolist";
	}

	/* 添加页面 */
	public String addUI() {
		// 准备数据 departmentList
		List<Department> departmentsTopList = deparmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(departmentsTopList);
		ActionContext.getContext().put("departmentAddList", departmentList);
		return "saveUI";
	}

	/* 编辑 */
	public String edit() {
		Department department = deparmentService.getById(model.getId());
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		if (model.getId() != parentId)
			department.setParent(deparmentService.getById(parentId));// 设置所属上级部门
		deparmentService.update(department);
		return "tolist";
	}

	/* 编辑页面 */
	public String editUI() {
		List<Department> departmentsTopList = deparmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(departmentsTopList);
		ActionContext.getContext().put("departmentAddList", departmentList);
		Department department = deparmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		if (department.getParent() != null) {
			parentId = department.getParent().getId();
		}
		return "saveUI";
	}


	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
