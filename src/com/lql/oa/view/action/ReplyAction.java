package com.lql.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lql.ao.base.BaseAction;
import com.lql.oa.domain.Reply;
import com.lql.oa.domain.Topic;
import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class ReplyAction  extends BaseAction<Reply>{
	private Long topicId;
	/**
	 * 发表新回复
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		//已经封装了
		/*model.setTitle(title);
		model.setContent(content);*/
		model.setTopic(topicService.getById(topicId));
		
 		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		replyService.save(model);
		return "toTopicShow";//转到新回复所在主题的页面显示
	}
	/**
	 * 发表新回复页面
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		Topic topic = topicService.getById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
}
