package com.lql.oa.util;

import com.lql.oa.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//如果未登陆，就转到登录页面
		User user=(User) ActionContext.getContext().getSession().get("user");
		String namespace=invocation.getProxy().getNamespace();
		String actionName=invocation.getProxy().getActionName();
		String privUrl=namespace+actionName;//对应的权限url
		if(user==null){
			//如果是去登陆，就放行
			if(privUrl.startsWith("/user_login")){
				return invocation.invoke();
			} // /user_loginUI, user_login
			//如果不是去登陆，则转到登陆页面
			return "loginUI";
		}
		else{
		 if(privUrl.startsWith("/home_index")){
			 return invocation.invoke();
		 }
			if(user.hasPrivilegeByUrl(privUrl)){
				return invocation.invoke();
			}else{
				return "noPrivilegeError";
			}
		}
		//如果已经登陆，就判断权限
				//如果有权限就放行
				//如果没有权限就转到提示页面
		// 放行
		/*
		 * System.out.println("拦截钱======================"); String string =
		 * invocation.invoke(); System.out.println("拦截后======================");
		 */
		
	}

}
