package com.lql.ao.base;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lql.oa.dao.RoleDao;
import com.lql.oa.dao.UserDao;
import com.lql.oa.dao.impl.RoleDaoImpl;
import com.lql.oa.dao.impl.UserDaoImpl;

public class BaseDaoTest {

	@Test
	public void testSave() {
		UserDao userDao = new UserDaoImpl();
		RoleDao roleDao = new RoleDaoImpl();
	}

}
