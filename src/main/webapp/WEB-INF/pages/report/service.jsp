<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="qiaosoftware" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>客户服务分析</title>
  <body>
	<div class="page_title">客户服务分析</div>
	<div class="button_bar">
		<button class="common_button" onclick="document.forms[1].submit();">查询</button>  
	</div>
  	<form action="${ctp}/report/service" method="post">
		<div id="listView" style="display:block;">
			<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
				<tr>
					<th>
						日期
					</th>
					<td>
						<input type="text" name="search_GED_minCreateDate" size="10" />
						-
						<input type="text" name="search_LED_maxCreateDate" size="10" />
					</td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
			</table>
			<!-- 列表数据 -->
			<br />
			
			<c:if test="${!empty page.list }">
				<table class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
				<tr>
					<th>序号</th>
					<th>客户名称</th>
					<th>服务次数</th>
				</tr>
					<c:forEach var="item" items="${page.list }" varStatus="status">
						<tr>
							<td class="list_data_number">${status.index + 1}</td>
							<td class="list_data_ltext">${item["customerName"]}</td>
							<td class="list_data_number">${item["number"]}</td>
						</tr>			
					</c:forEach>
				</table>
				
				<qiaosoftware:page navigatorUrl="${ctp}/report/service?search_GED_minCreateDate=${param.search_GED_minCreateDate }&search_LED_maxCreateDate=${param.search_LED_maxCreateDate }" page="${page }"></qiaosoftware:page>
			
			</c:if>
			<c:if test="${empty page.list }">
				没有任何数据！
			</c:if>
		</div>
	</form>	
  </body>
</html>