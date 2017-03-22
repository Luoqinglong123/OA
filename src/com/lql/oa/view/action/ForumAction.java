package com.lql.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lql.ao.base.BaseAction;
import com.lql.oa.domain.Forum;
import com.lql.oa.domain.Topic;
import com.lql.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum> {
	/**
	 * 0 表示查看全部主题<br>
	 * 1 表示只看精华帖
	 */
	private int viewType = 0;

	/**
	 * 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)<br>
	 * 1 表示只按最后更新时间排序<br>
	 * 2 表示只按主题发表时间排序<br>
	 * 3 表示只按回复数量排序
	 */
	private int orderBy = 0;

	/**
	 * true 表示升序<br>
	 * false 表示降序
	 */
	private boolean asc = false;

	/*
	 * 板块列表
	 */
	public String list() throws Exception {
		List<Forum> forums = forumService.findAll();
		ActionContext.getContext().put("forumList", forums);
		return "list";
	}

	/**
	 * 显示单个板块
	 */
	public String show() throws Exception {
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		// 准备数据：topicList
		/*
		 * List<Topic> topics= topicService.finByForm(forum);
		 * ActionContext.getContext().put("topicList", topics);
		 */
		// 准备分页信息
		/*
		 * PageBean pageBean =
		 * topicService.getPageBeanByForum(pageNum,pageSize,forum);
		 * ActionContext.getContext().getValueStack().push(pageBean);
		 */
		/*
		 * String hql =
		 * "FROM Topic t WHERE t.forum = ? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END )"
		 * // + " DESC ,t.lastUpdateTime DESC"; List<Object> parameters = new
		 * ArrayList<Object>(); parameters.add(forum); PageBean pageBean =
		 * replyService.getPageBean(pageNum, pageSize, hql, parameters);
		 * ActionContext.getContext().getValueStack().push(pageBean);
		 */
		// 准备分页信息 ，最终版
		new QueryHelper(Topic.class, "t")//
				// 过滤条件
				.addCondition("t.forum=?", forum)//
				.addCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST) // 1
																			// 表示只看精华帖
				// 排序条件
				.addOrderProperty((orderBy == 1), "t.lastUpdateTime", asc) // 1
																			// 表示只按最后更新时间排序
				.addOrderProperty((orderBy == 2), "t.postTime", asc) // 2
																		// 表示只按主题发表时间排序
				.addOrderProperty((orderBy == 3), "t.replyCount", asc) // 3
																		// 表示只按回复数量排序
				.addOrderProperty((orderBy == 0),
						"(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)//
				.addOrderProperty((orderBy == 0), "t.lastUpdateTime", false) // 0
																				// 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
				.preparePageBean(topicService, pageNum, pageSize);

		return "show";
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

}
