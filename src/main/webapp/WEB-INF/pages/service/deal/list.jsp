<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="qiaosoftware" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户服务管理</title>
<body>

	<div class="page_title">
		客户服务管理
	</div>
	<div class="button_bar">
		<button class="common_button" onclick="window.location.href='${ctp}/service/create'">
			新建
		</button>
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp}/service/deal/list" method="post">
	
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>
					服务类型
				</th>
				<td>
					<input type="text" name="search_LIKES_serviceType" />
				</td>
				<th>
					概要
				</th>
				<td>
					<input type="text" name="search_LIKES_serviceTitle" />
				</td>
			</tr>
			<tr>
				<th>
					客户
				</th>
				<td>
					<input type="text" name="search_LIKES_customerName" />
				</td>
				<th>
					创建时间
				</th>
				<td>
					<input type="text" name="search_GED_minCreateDate" size="10" />
					-
					<input type="text" name="search_LED_maxCreateDate" size="10" />
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
						服务类型
					</th>
					<th>
						概要
					</th>
					<th>
						客户
					</th>
					<th>
						创建人
					</th>
					<th>
						创建时间
					</th>
					<th>
						操作
					</th>
				</tr>
				<c:forEach var="service" items="${page.list }">
					<tr>
						<td class="list_data_number">
							${service.id}
						</td>
						<td class="list_data_text">
							${service.serviceType}
						</td>
						<td class="list_data_ltext">
							${service.serviceTitle}
						</td>
	
						<td class="list_data_text">
							${service.customer.name}
						</td>
						<td class="list_data_text">
							${service.createdby.name}
						</td>
						<td class="list_data_text">
							<fmt:formatDate value="${service.createDate }" pattern="yyyy-MM-dd"/>
						</td>
						<td class="list_data_op">
							<img onclick="window.location.href='${ctp}/service/deal/${service.id}'" 
								title="处理" src="${ctp}/static/images/bt_deal.gif" class="op_button" />
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<qiaosoftware:page page="${page }" navigatorUrl="${ctp}/service/deal/list?search_LIKES_serviceType=${param.search_LIKES_serviceType }&search_LIKES_serviceTitle=${param.search_LIKES_serviceTitle }&search_LIKES_customerName=${param.search_LIKES_customerName }&search_GED_minCreateDate=${param.search_GED_minCreateDate }&search_LED_maxCreateDate=${param.search_LED_maxCreateDate }"></qiaosoftware:page>
			
		</c:if>
		<c:if test="${empty page.list }">
			没有任何数据！
		</c:if>
	</form>

</body>
</html>




