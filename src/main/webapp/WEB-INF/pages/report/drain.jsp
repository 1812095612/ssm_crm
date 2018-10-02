<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="qiaosoftware" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>客户流失分析</title>
  </head>  
  <body>
  	<div class="button_bar">
		<button class="common_button" onclick="document.forms[1].submit();">查询</button>  
	</div>
	
  	<form action="${ctp}/report/drain" method="post">
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
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		
		<c:if test="${!empty page.list }">	
			<table class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>			
				<th>序号</th>
				<th>确认流失时间</th>			
				<th>客户</th>
				<th>客户经理</th>
				<th>流失原因</th>
			</tr>
				<c:forEach var="drain" items="${page.list }" varStatus="status">
					<tr>
						<td class="list_data_number">${status.count}</td>				
						<td class="list_data_text"><fmt:formatDate value="${drain.drainDate }" pattern="yyyy-MM-dd"/></td>
						<td class="list_data_text">${drain.customer.name}</td>				
						<td class="list_data_text">${drain.customer.manager.name}</td>
						<td class="list_data_ltext">${drain.reason}</td>				
					</tr>			
				</c:forEach>
			</table>
			
			<qiaosoftware:page navigatorUrl="${ctp}/report/drain?search_LIKE_customerName=${param.search_LIKE_customerName }&search_LIKE_managerName=${param.search_LIKE_managerName }" page="${page }"></qiaosoftware:page>
			
		</c:if>
		<c:if test="${empty page.list }">
			没有任何数据！
		</c:if>
	</form>	
  </body>
</html>