<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="qiaosoftware" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑数据字典项</title>
</head>

<body class="main">
	<span class="page_title">编辑/新建数据字典项</span>
	<div class="button_bar">
		<button class="common_button" onclick="javascript:history.go(-1);">
			返回
		</button>
		<button class="common_button" onclick="document.forms[1].submit();">
			保存
		</button>
	</div>
	
	<form:form action="${ctp}/dict/saveOrUpdate/${dict.id }" method="POST" modelAttribute="dict">
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>
					编号
				</th>
				<td>
					<form:input path="id" readonly="true"/>
				</td>
				<th>
					类别
				</th>
				<td>
					<form:input path="type"/>
				</td>
			</tr>
			<tr>
				<th>
					条目
				</th>
				<td>
					<form:input path="item"/>
				</td>
				<th>
					值
				</th>
				<td>
					<form:input path="value"/>
				</td>
			</tr>
			<tr>
				<th>
					是否可编辑
				</th>
				<td>
					<% 
						Map<String, String> map = new HashMap<String, String>();
						map.put("1", "是");
						map.put("0", "否");
						request.setAttribute("editable", map);
					%>
					<form:select path="editable" items="${editable }"></form:select>
				</td>
				<th>
					&nbsp;
				</th>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>