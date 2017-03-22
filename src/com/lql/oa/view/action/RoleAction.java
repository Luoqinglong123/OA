package com.lql.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lql.ao.base.BaseAction;
import com.lql.oa.domain.Privilege;
import com.lql.oa.domain.Role;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>  {

	private Long[] privilegeIds;
	// 列表
	public String list() throws Exception {
		List<Role> list = roleService.findAll();
		ActionContext.getContext().put("roleList", list);
		return "list";
	}

	// 删除
	public String delete() throws Exception {
		roleService.delete(model.getId());
		return "tolist";
	}

	// 添加页面
	public String addUI() throws Exception {

		return "saveUI";
	}

	// 添加
	public String add() throws Exception {
		roleService.save(model);
		return "tolist";
	}

	// 修改
	public String edit() throws Exception {
		Role role = roleService.getById(model.getId());
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		roleService.update(role);
		return "tolist";
	}

	// 修改页面
	public String editUI() throws Exception {
		Role role = roleService.getById(model.getId());
		//System.out.println(role);
		//把对象放到栈顶
		ActionContext.getContext().getValueStack().push(role);
		/*this.description=role.getDescription();
		this.name=role.getName();*/
		return "saveUI";
	}
	//设置权限页面
	public String setPrivilegeUI() throws Exception {
		Role role = roleService.getById(model.getId());
		//System.out.println(role);
		//把对象放到栈顶
		ActionContext.getContext().getValueStack().push(role);
		if(role.getPrivileges()!=null){
			 privilegeIds = new Long[role.getPrivileges().size()];
			 int index= 0;
			 for(Privilege privilege:role.getPrivileges()){
				 privilegeIds[index++]=privilege.getId();
			 }
		}
		//准备数据privilegeList数据
		List<Privilege> list = privilegeService.findAll();
		ActionContext.getContext().put("privilegeList", list);
		return "setPrivilegeUI";
		
	}
	//设置权限
	public String setPrivilege() throws Exception {
		Role role = roleService.getById(model.getId());
		List<Privilege> privilegeList= privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		roleService.update(role);
		return "tolist";
	}

	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}


}
