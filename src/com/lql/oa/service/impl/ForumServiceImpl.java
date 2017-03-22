package com.lql.oa.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lql.ao.base.impl.DaoSupportImpl;
import com.lql.oa.domain.Forum;
import com.lql.oa.domain.Topic;
import com.lql.oa.service.ForumService;

@Service
@Transactional
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements
		ForumService {

	@Override
	public List<Forum> findAll() {

		return getSession().createQuery//
				("FROM Forum f ORDER BY f.position")//
				.list();
	}

	@Override
	public void save(Forum forum) {
		
		//保存
		super.save(forum);
		//设置position值为当前最大值+1
		forum.setPosition(forum.getId().intValue());
	}
	public void moveUp(Long id) {
		Forum forum = getById(id);// 当前要移动的forum
		//SELECT * FROM tb_forum where position<4 ORDER BY position desc LIMIT 0,1
		
		Forum other =(Forum) getSession().createQuery//
				("FROM Forum f WHERE f.position < ? ORDER BY position DESC ")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//第一个结果
				.setMaxResults(1)//有一个
				.uniqueResult();// 当前上面的forum
		
		
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		// 更新数据库(可以不写，因为对象现在是持久化状态.)
		getSession().update(forum);
		getSession().update(other);
	}

	public void moveDown(Long id) {
		Forum forum = getById(id);// 当前要移动的forum
		//SELECT * FROM tb_forum where position>4 ORDER BY position asc LIMIT 0,1
		
		Forum other =(Forum) getSession().createQuery//
				("FROM Forum f WHERE f.position >? ORDER BY position ASC")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//第一个结果
				.setMaxResults(1)//有一个
				.uniqueResult();// 当前上面的forum
		
		
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		// 更新数据库(可以不写，因为对象现在是持久化状态.)
		getSession().update(forum);
		getSession().update(other);
	}


}