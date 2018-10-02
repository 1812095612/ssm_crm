<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="qiaosoftware" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户流失管理</title>
</head>
<body>

	<div class="page_title">
		客户流失管理
	</div>
	<div class="button_bar">
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp}/drain/list" method="post"> 
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>
					客户名称
				</th>
				<td>
					<input type="text" name="search_LIKE_customerName" />
				</td>
				<th>
					客户经理
				</th>
				<td>
					<input type="text" name="search_LIKE_managerName" />
				</td>
				<th>
					&nbsp;
				</th>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		
		<c:if test="${!empty page.list }">
			<table class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
				<tr>
					<th>
						编号
					</th>
					<th>
						客户名称
					</th>
					<th>
						客户经理
					</th>
					<th>
						上次下单时间
					</th>
					<th>
						确认流失时间
					</th>
					<th>
						流失原因
					</th>
					<th>
						状态
					</th>
					<th>
						操作
					</th>
				</tr>
				<c:forEach var="drain" items="${page.list }">
					<tr>
						<td class="list_data_number">
							${drain.id}
						</td>
						<td class="list_data_ltext">
							${drain.customer.name}
						</td>
						<td class="list_data_text">
							${drain.customer.manager.name}
						</td>
						<td class="list_data_text">
							<fmt:formatDate value="${drain.lastOrderDate }" pattern="yyyy-MM-dd"/>
						</td>
						<td class="list_data_text">
							<fmt:formatDate value="${drain.drainDate }" pattern="yyyy-MM-dd"/>
						</td>
						<td class="list_data_ltext">
							${drain.reason}
						</td>
						<td class="list_data_text">
							${drain.status}
						</td>
						<td class="list_data_op">
							<c:if test="${drain.status == '流失预警'}">
								<img onclick="window.location.href='${ctp}/drain/confirm/${drain.id }/${drain.customer.id}'"
									title="确认流失" src="${ctp}/static/images/bt_confirm.gif" class="op_button" />
								<img onclick="window.location.href='${ctp}/drain/delay/${drain.id }'" 
									title="暂缓流失" src="${ctp}/static/images/bt_relay.gif" class="op_button" />
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<qiaosoftware:page page="${page }" navigatorUrl="${ctp}/drain/list?search_LIKE_customerName=${param.search_LIKE_customerName }&search_LIKE_managerName=${param.search_LIKE_managerName }"></qiaosoftware:page>
			
			<%-- <c:set value="${ctp}/drain/list?1=1" var="navigatorUrl" scope="page"></c:set>
			<%@ include file="/commons/navigator.jsp" %> --%>
			
			<%-- <tags:pagination page="${page}" paginationSize="5"/> --%>
		</c:if>
		<c:if test="${empty page.list }">
			没有任何数据！
		</c:if>
	</form>
</body>
</html>