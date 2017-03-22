package com.lql.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lql.ao.base.BaseAction;
import com.lql.oa.domain.Forum;
import com.lql.oa.domain.Reply;
import com.lql.oa.domain.Topic;
import com.lql.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic> {
	
	private Long forumId;
	/**
	 * 显示单个主题
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		//准备数据  topic
		Topic topic = topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		//准备数据replyList
		/*List<Reply> replyList= replyService.finByTopic(topic);
		ActionContext.getContext().put("replyList", replyList);*/
		/*PageBean pageBean = replyService.getPageBeanTopic(pageNum,pageSize,topic);*/
		/*String hql ="From Reply r WHERE r.topic =? ORDER BY r.postTime ASC";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(topic);
		PageBean pageBean = replyService.getPageBean(pageNum, pageSize, hql, parameters);
		ActionContext.getContext().getValueStack().push(pageBean);*/
		// 准备分页信息, 最终版
				new QueryHelper(Reply.class, "r")//
						.addCondition("r.topic=?", topic)//
						.addOrderProperty("r.postTime", true)//
						.preparePageBean(replyService, pageNum, pageSize);
		
		return "show";
	}

	/**
	 * 发表新主题页面
	 * 
	 * @return
	 * @throws Exception
	 */

	public String addUI() throws Exception {
		//准备数据
		Forum forum = forumService.getById(forumId); 
		ActionContext.getContext().put("forum", forum);
		return "addUI";
	}

	/**
	 * 发表新主题
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// 封装
				// >> 表单参数，已经封装了title, content
				// model.setTitle(title);
				// model.setContent(content);
				model.setForum(forumService.getById(forumId));
				// >> 当前直接获取的信息
				model.setAuthor(getCurrentUser()); // 当前登录用户
				model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr()); // 当前请求中的IP
				model.setPostTime(new Date()); // 当前时间

				// 保存
				topicService.save(model);

				return "toShow"; // 转到新主题的显示页面
	}

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
	
	
}
