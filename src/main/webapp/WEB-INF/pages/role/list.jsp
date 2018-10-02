<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="qiaosoftware" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>角色管理</title>
</head>

<body class="main">

	<div class="page_title">
		角色管理
	</div>
	
	<div class="button_bar">
		<button class="common_button" onclick="window.location.href='${ctp}/role/create'">
			新建
		</button>
	</div>
	
	<form action="role-list">

		<!-- 列表数据 -->
		<br />
		<c:if test="${!empty page.list }">
			<table class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
				<tr>
					<th class="data_title" >
						编号
					</th>
					<th class="data_title" >
						角色名
					</th>
					<th class="data_title" >
						角色描述
					</th>
					<th class="data_title">
						状态
					</th>
					<th class="data_title">
						操作
					</th>
				</tr>
				
				<c:forEach var="role" items="${page.list }">
					<tr>
						<td class="data_cell" style="text-align:right;padding:0 10px;">${role.id}</td>
						<td class="data_cell" style="text-align:center;">${role.name}</td>
						<td class="data_cell" style="text-align:left;">${role.description}</td>
						<td class="data_cell" style="text-align:center;">${role.enabled?"有效":"无效"}</td>
						<td class="data_cell">
							<img onclick="window.location.href='${ctp}/role/assign/${role.id}'" title="分配权限" src="${ctp}/static/images/bt_linkman.gif" class="op_button" />
							<img onclick="window.location.href='${ctp}/role/delete/${role.id}'" title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" />
						</td>
					</tr>
				</c:forEach>
				
			</table>
			
			<qiaosoftware:page page="${page }" navigatorUrl="${ctp}/role/list?1=1"></qiaosoftware:page>
			
			<%-- <tags:pagination page="${page}" paginationSize="5"/> --%>
		</c:if>
		
		<c:if test="${empty page.list }">
			没有任何数据！
		</c:if>
		
	</form>
</body>
</html>

