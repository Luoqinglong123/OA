<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>
	<table border="1" cellspacing="0" cellpadding="10">
		<tr>
			<th>id</th>
			<th>name</th>
			<th>描述</th>
			<th>删除</th>
			<th>修改</th>
		</tr>
		<s:iterator value="#roleList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="description" /></td>
				<td><s:a action="role_delete?id=%{id }"
						onclick="return confirm('确定要删除吗？')">删除</s:a></td>
				<td><s:a action="role_editUI?id=%{id}">编辑</s:a></td>
			</tr>
		</s:iterator>
	</table>
	<s:a action="role_addUI">添加</s:a>
</body>
</html>
