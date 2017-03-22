package com.lql.oa.service.impl;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lql.ao.base.impl.DaoSupportImpl;
import com.lql.oa.domain.Privilege;
import com.lql.oa.service.PrivilegeService;

@Service
@Transactional
public class PrivilegeServiceImpl extends DaoSupportImpl<Privilege> implements
		PrivilegeService {

	public List<Privilege> findTopList() {
		return getSession()
				.createQuery("FROM Privilege p WHERE p.parent IS NULL ")//
				.list();
	}

	public Collection<String> getAllPrivilegeUrls() {
		return  getSession()
				.createQuery("SELECT DISTINCT p.url FROM Privilege p WHERE p.url IS NOT NULL ")//
				.list();
	}

}
