package com.lql.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lql.ao.base.BaseAction;
import com.lql.oa.domain.Forum;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumManageAction extends BaseAction<Forum> {

	/* 列表 */
	public String list() throws Exception {
		List<Forum> list = forumService.findAll();
		ActionContext.getContext().put("forumList", list);
		return "list";
	}

	/* 删除 */
	public String delete() throws Exception {
		forumService.delete(model.getId());
		return "tolist";
	}

	/* 添加 */
	public String add() throws Exception {
		forumService.save(model);
		return "tolist";
	}

	/* 添加页面 */
	public String addUI() throws Exception {

		return "saveUI";
	}

	/* 编辑 */
	public String edit() throws Exception {
		forumService.update(model);
		return "tolist";
	}

	/* 编辑页面 */
	public String editUI() throws Exception {
		// 准备回显的数据
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";
	}

	// 上移
	public String moveUp() throws Exception {
			forumService.moveUp(model.getId());
		return "tolist";
	}

	// 下移
	public String moveDown() throws Exception {
		forumService.moveDown(model.getId());
		return "tolist";
	}

}
