package com.lql.oa.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.lql.ao.base.impl.DaoSupportImpl;
import com.lql.oa.domain.User;
import com.lql.oa.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends DaoSupportImpl<User> implements
		UserService {

	public User finByLogingNameAndPassword(String loginName, String password) {
		// 使用MD5摘要进行对比
		String md5Hex = DigestUtils.md5Hex(password);

		return (User) getSession().createQuery//
				("FROM User u WHERE  loginName = ? AND password = ?")
				//
				.setParameter(0, loginName)//
				.setParameter(1, md5Hex)//
				.uniqueResult();
	}

}
