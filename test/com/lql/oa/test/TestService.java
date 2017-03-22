package com.lql.oa.test;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lql.ao.base.impl.DaoSupportImpl;
import com.lql.oa.domain.User;

@Service
public class TestService extends DaoSupportImpl<User> {
	@Transactional
	public void save300User() {
		for (int i = 0; i < 200; i++) {
			User user = new User();
			user.setName("user" + ('A' + 1));
			getSession().save(user);
		}

	}
}
