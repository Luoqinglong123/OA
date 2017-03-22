package com.lql.oa.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lql.oa.domain.User;

public class Springtest {
	private ApplicationContext context;

	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@Test
	public void testBean() {
		User user = (User) context.getBean("user");
		System.out.println(user);
	}

	@Test
	public void testSessionFactory() {
		SessionFactory bean = (SessionFactory) context
				.getBean("sessionFactory");
		Session currentSession = bean.openSession();
		User user = new User();
		user.setName("a");
		currentSession.save(user );
		System.out.println(bean);
	}

	/* 测试事务 */
	@Test
	public void testTX() {
		/*
		 * TestService testService = (TestService)
		 * context.getBean("testService"); testService.saveTwoUser();
		 */
		TestService service = new TestService();
		service.save300User();
	}
}
