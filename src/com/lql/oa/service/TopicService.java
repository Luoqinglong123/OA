package com.lql.oa.service;

import java.util.List;

import com.lql.ao.base.DaoSupport;
import com.lql.oa.domain.Forum;
import com.lql.oa.domain.PageBean;
import com.lql.oa.domain.Topic;


public interface TopicService  extends DaoSupport<Topic> {
	/**
	 * 查询指定板块中的所有主题，排序：所有指定贴在最上面，并按最后更新时间排序，让新状态的在上面。
	 * @param byId
	 * @return
	 */
	List<Topic> finByForm(Forum byId);

	PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum);

}
