package com.lql.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;

import com.lql.ao.base.BaseAction;
import com.lql.oa.domain.Department;
import com.lql.oa.domain.Role;
import com.lql.oa.domain.User;
import com.lql.oa.util.DepartmentUtils;
import com.lql.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	private Long departmentId;
	private Long[] roleIds;

	/* 列表 */
	public String list() throws Exception {
		new QueryHelper(User.class,"u").preparePageBean(userService, pageNum, pageSize);
		return "list";
	}

	/* 删除 */
	public String delete() throws Exception {
		userService.delete(model.getId());
		return "tolist";
	}

	/* 添加 */
	public String add() throws Exception {
		Department department = deparmentService.getById(departmentId);
		model.setDepartment(department);
		List<Role> roles = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roles));
		byte[] bytes = "1234".getBytes();
		String md5 = DigestUtils.md5DigestAsHex(bytes);
		model.setPassword(md5);
		userService.save(model);
		return "tolist";
	}

	/* 添加页面 */
	public String addUI() throws Exception {
		// 准备数据 树状departmentList
		List<Department> departmentsTopList = deparmentService.findTopList();
		List<Department> departmentList = DepartmentUtils
				.getAllDepartments(departmentsTopList);
		ActionContext.getContext().put("departmentList", departmentList);
		// 准备roleList
		List<Role> list = roleService.findAll();
		ActionContext.getContext().put("roleList", list);
		return "saveUI";
	}

	/* 编辑 */
	public String edit() throws Exception {
		Department department = deparmentService.getById(departmentId);
		model.setDepartment(department);
		List<Role> roles = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roles));
		userService.update(model);
		return "tolist";
	}

	/* 编辑页面 */
	public String editUI() throws Exception {
		// 准备数据 树状departmentList
		List<Department> departmentsTopList = deparmentService.findTopList();
		List<Department> departmentList = DepartmentUtils
				.getAllDepartments(departmentsTopList);
		ActionContext.getContext().put("departmentList", departmentList);
		// 准备roleList
		List<Role> list = roleService.findAll();
		ActionContext.getContext().put("roleList", list);
		// 准备回显数据
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		if (user.getDepartment() != null) {
			departmentId = user.getDepartment().getId();
		}
		if (user.getRoles() != null) {
			roleIds = new Long[user.getRoles().size()];
			int index = 0;
			for (Role role : user.getRoles()) {
				roleIds[index++] = role.getId();
			}
		}
		return "saveUI";
	}

	// 初始化密码
	public String initPassword() throws Exception {
		User user = userService.getById(model.getId());
		// 81dc9bdb52d04dc20036dbd8313ed055
		byte[] bytes = "1234".getBytes();
		String md5 = DigestUtils.md5DigestAsHex(bytes);
		// 81dc9bdb52d04dc20036dbd8313ed055
		// String md5 = Str2MD5.MD5("1234");
		user.setPassword(md5);
		userService.update(user);
		return "tolist";
	}

	// 登录页面
	public String loginUI() throws Exception {
		return "loginUI";
	}

	// 登录
	public String login() throws Exception {
		User user = userService.finByLogingNameAndPassword(
				model.getLoginName(), model.getPassword());
		if (user == null) {
			addFieldError("login", "用户名或者密码错误");
			return "loginUI";
		} else {
			ActionContext.getContext().getSession().put("user", user);
		}
		return "toIndex";
	}

	// 注销
	public String logout() throws Exception {
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}
	
	
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

}
