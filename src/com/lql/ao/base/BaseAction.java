package com.lql.ao.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.lql.oa.domain.User;
import com.lql.oa.service.DeparmentService;
import com.lql.oa.service.ForumService;
import com.lql.oa.service.PrivilegeService;
import com.lql.oa.service.ReplyService;
import com.lql.oa.service.RoleService;
import com.lql.oa.service.TopicService;
import com.lql.oa.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	@Resource
	protected RoleService roleService;
	@Resource
	protected DeparmentService deparmentService;
	@Resource
	protected UserService userService;
	@Resource
	protected PrivilegeService privilegeService;
	@Resource
	protected ForumService forumService;
	@Resource
	protected TopicService topicService;
	@Resource
	protected ReplyService replyService;
	protected T model;
	
	public BaseAction(){
	try {
		//通过反射获取model的真实类型
		ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz= (Class<T>) pt.getActualTypeArguments()[0];
		model = clazz.newInstance();
	} catch (Exception e) {
		throw new RuntimeException();
	}
	}
	public T getModel() {
		
		return model;
	}
	protected User getCurrentUser(){
		return  (User) ActionContext.getContext().getSession().get("user");
	}
	protected int pageNum=1;//当前页
	protected int pageSize=10;//每页显示多少记录
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
