package com.lql.oa.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lql.ao.base.impl.DaoSupportImpl;
import com.lql.oa.domain.Forum;
import com.lql.oa.domain.PageBean;
import com.lql.oa.domain.Reply;
import com.lql.oa.domain.Topic;
import com.lql.oa.service.ReplyService;

@Service
@Transactional
public class ReplyServiceImpl extends DaoSupportImpl<Reply> implements
		ReplyService {

	public List<Reply> finByTopic(Topic topic) {
		return getSession().createQuery//
				("From Reply r WHERE r.topic =? ORDER BY r.postTime ASC")//
				.setParameter(0, topic).list();
	}

	@Override
	public void save(Reply enyity) {
		// 1.保存
		getSession().save(enyity);

		// 2.维护相关的信息
		Topic topic = enyity.getTopic();
		Forum forum = topic.getForum();
		forum.setArticleCount(forum.getArticleCount() + 1);
		topic.setReplyCount(topic.getReplyCount() + 1);
		topic.setLastReply(enyity);
		topic.setLastUpdateTime(enyity.getPostTime());
		getSession().save(topic);
		getSession().save(forum);
	}
	@Deprecated
	public PageBean getPageBeanTopic(int pageNum, int pageSize, Topic topic) {
		List recordList = getSession()
				.createQuery(
						"From Reply r WHERE r.topic =? ORDER BY r.postTime ASC")//
				.setParameter(0, topic)//
				.setFirstResult((pageNum - 1) * pageSize)//
				.setMaxResults(pageSize).list();
		Long recordCount = (Long) getSession().createQuery//
				("SELECT COUNT(*) From Reply r WHERE r.topic =? ")//
				.setParameter(0, topic)//
				.uniqueResult();
		return new PageBean(pageNum, pageSize, recordCount.intValue(), recordList);
	}
}
