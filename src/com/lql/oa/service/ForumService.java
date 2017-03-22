package com.lql.oa.service;

import java.util.List;

import com.lql.ao.base.DaoSupport;
import com.lql.oa.domain.Forum;
import com.lql.oa.domain.Topic;

public interface ForumService extends DaoSupport<Forum> {
	/**
	 * 移动到最上面后不移动
	 * 
	 * @param id
	 */
	void moveUp(Long id);

	/**
	 * 移动到最下面不移动
	 * 
	 * @param id
	 */
	void moveDown(Long id);


}
