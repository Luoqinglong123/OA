package com.lql.oa.service;

import java.util.List;

import com.lql.ao.base.DaoSupport;
import com.lql.oa.domain.PageBean;
import com.lql.oa.domain.Reply;
import com.lql.oa.domain.Topic;

public interface ReplyService extends DaoSupport<Reply> {
	/***
	 * 查询指定主题中所有的回复列表，排序：按发表时间升序排序
	 * 
	 * @param topic
	 * @return
	 */
	List<Reply> finByTopic(Topic topic);

	/**
	 * 查询分页信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param topic
	 * @return
	 */
	@Deprecated
	PageBean getPageBeanTopic(int pageNum, int pageSize, Topic topic);

}
