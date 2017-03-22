package com.lql.oa.service;

import com.lql.ao.base.DaoSupport;
import com.lql.oa.domain.User;

public interface UserService  extends DaoSupport<User>{

	User finByLogingNameAndPassword(String loginName, String password);
	
}
