package com.lql.oa.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lql.ao.base.impl.DaoSupportImpl;
import com.lql.oa.domain.Forum;
import com.lql.oa.domain.PageBean;
import com.lql.oa.domain.Topic;
import com.lql.oa.service.TopicService;

@Service
@Transactional
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements
		TopicService {

	public List<Topic> finByForm(Forum forum) {
		return getSession().createQuery// 所有指定贴在最上面，并按最后更新时间排序，让新状态的在上面。
				("FROM Topic t WHERE t.forum = ? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END )"//
						+ " DESC ,t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.list();
	}

	@Override
	public void save(Topic topic) {
		topic.setType(Topic.TYPE_NORMAL);  //默认为普通贴
		topic.setLastReply(null); 
		topic.setReplyCount(0);
		topic.setLastUpdateTime(topic.getPostTime());
	    getSession().save(topic);//保存
		//维护相关的特殊属性
		Forum forum = topic.getForum();
		forum.setTopicCount(forum.getTopicCount()+1);
		forum.setArticleCount(forum.getArticleCount()+1);
		forum.setLastTopic(topic);
		getSession().save(forum);
	}

	public PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum) {
		List recordList = getSession().createQuery
				("FROM Topic t WHERE t.forum = ? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END )"//
						+ " DESC ,t.lastUpdateTime DESC")//
				.setParameter(0, forum)
				.setFirstResult((pageNum - 1) * pageSize)//
				.setMaxResults(pageSize)//
				.list();
		Long recordCount = (Long) getSession().createQuery//
				("SELECT COUNT(*) From Topic t WHERE t.forum =? ")//
				.setParameter(0, forum)//
				.uniqueResult();
		return new PageBean(pageNum, pageSize, recordCount.intValue(), recordList);
	}
}
