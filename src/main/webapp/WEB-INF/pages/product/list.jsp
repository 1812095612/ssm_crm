<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="qiaosoftware" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>产品查询</title>
</head>
<body>
	
	<div class="page_title">
		产品管理
	</div>
	<div class="button_bar">
		<button class="common_button" onclick="window.location.href='${ctp}/product/create'">
			产品添加
		</button>
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp}/product/list">
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>
					名称
				</th>
				<td>
					<input type="text" name="search_LIKE_name" />
				</td>
				<th>
					型号
				</th>
				<td>
					<input type="text" name="search_LIKE_type" />
				</td>
				<th>
					批次
				</th>
				<td>
					<input type="text" name="search_LIKE_batch" />
				</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
	</form>	
		
	<c:if test="${!empty page.list }">	
	<table class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
		<tr>
			<th>
				编号
			</th>
			<th>
				名称
			</th>
			<th>
				型号
			</th>
			<th>
				等级/批次
			</th>
			<th>
				单位
			</th>
			<th>
				单价（元）
			</th>
			<th>
				备注
			</th>
			<th>
				操作
			</th>
		</tr>
		<c:forEach var="product" items="${page.list }">
			<tr>
				<td class="list_data_number">
					${product.id}
				</td>
				<td class="list_data_ltext">
					${product.name}
				</td>
				<td class="list_data_text">
					${product.type}
				</td>
				<td class="list_data_text">
					${product.batch}
				</td>
	
				<td class="list_data_text">
					${product.unit}
				</td>
				<td class="list_data_number">
					${product.price}
				</td>
				<td class="list_data_ltext">
					${product.memo}
				</td>
				<td class="list_data_op">
					<img onclick="window.location.href='${ctp}/product/edit/${product.id }'" 
						title="编辑" src="${ctp}/static/images/bt_edit.gif" class="op_button" />
					<img onclick="window.location.href='${ctp}/product/delete/${product.id }'" 
						title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" />
				</td>
			</tr>
		</c:forEach>
	</table>
		
		<qiaosoftware:page page="${page }" navigatorUrl="${ctp}/product/list?search_LIKE_name=${param.search_LIKE_name }&search_LIKE_type=${param.search_LIKE_type }&search_LIKE_batch=${param.search_LIKE_batch }"></qiaosoftware:page>
	
		<%-- <tags:pagination page="${page}" paginationSize="5"/> --%>
	</c:if>
	<c:if test="${empty page.list }">
		没有任何数据！
	</c:if>
		
</body>
</html>