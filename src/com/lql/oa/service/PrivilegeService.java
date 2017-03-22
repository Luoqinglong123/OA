package com.lql.oa.service;

import java.util.Collection;
import java.util.List;

import com.lql.ao.base.DaoSupport;
import com.lql.oa.domain.Privilege;

public interface PrivilegeService extends DaoSupport<Privilege> {

	List<Privilege> findTopList();

	Collection<String> getAllPrivilegeUrls();

}
