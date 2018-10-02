<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="qiaosoftware" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户贡献分析</title>
</head>
<body>

	<div class="page_title">
		客户贡献分析
	</div>
	<div class="button_bar">
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp}/report/pay" method="post">
		<div id="listView" style="display:block;">
			<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
				<tr>
					<th>
						客户名称
					</th>
					<td>
						<input type="text" name="search_LIKES_name" />
					</td>
					<th>
						日期
					</th>
					<td>
						<input type="text" name="search_GED_minOrderDate" size="10" />
						-
						<input type="text" name="search_LED_maxOrderDate" size="10" />
					</td>
				</tr>
			</table>
			<!-- 列表数据 -->
			<br />
			
			<c:if test="${!empty page.list }">
				<table class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
					<tr>
						
						<th>
							客户名称
						</th>
						<th>
							订单总金额（元）
						</th>
					</tr>
					<c:forEach var="item" items="${page.list }">
						<tr>
							
							<td align="center">
								${item["name"]}
							</td>
							<td align="center">
								${item["total_money"] }
							</td>
		
						</tr>
					</c:forEach>
				</table>
				
				<qiaosoftware:page navigatorUrl="${ctp}/report/pay?search_LIKES_name=${param.search_LIKES_name }&search_GED_minOrderDate=${param.search_GED_minOrderDate }&search_LED_maxOrderDate=${param.search_LED_maxOrderDate }" page="${page }"></qiaosoftware:page>
				
			</c:if>
			<c:if test="${empty page.list }">
				没有任何数据！
			</c:if>
		</div>
	</form>
	
	<img alt="" src="${ctp}/report/testJfreeChart">
	
</body>
</html>