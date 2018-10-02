<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户基本信息管理</title>
<script type="text/javascript">
	$(function(){
		$("img[id^='delete-']").click(function(){
		
			var $this = $(this);
			
			var id = $(this).next(":hidden").val();
			
			var url = "${ctp}/customer/delete";
			var args = {"id":id,"time":new Date()};
			$.post(url, args, function(data){
				if(data = "1"){
					$this.parent().parent().find("td").eq(5).text("删除");
					$this.remove();
					alert("删除成功！");
					return false;
				}
				return false;
			});
			return false;
		});
	});
</script>
</head>
<body>

	<div class="page_title">客户基本信息管理</div>
	<div class="button_bar">
		<button class="common_button" onclick="document.forms[1].submit();">查询</button>
	</div>
	
	<form action="${ctp}/customer/list" method="POST">
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>客户名称</th>
				<td>
					<input type="text" name="search_LIKE_name"/>
				</td>
				<th>地区</th>
				<td>
					<select name="search_EQ_region">
						<option value="">全部</option>
						<c:forEach items="${regions }" var="region">
							<option value="${region }">${region }</option>
						</c:forEach>
					</select>
				</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>客户经理</th>
				<td><input type="text" name="search_LIKE_managerName" /></td>
				
				<th>客户等级</th>
				<td>
					<select name="search_EQ_level">
						<option value="">全部</option>
						<c:forEach items="${levels }" var="level">
							<option value="${level }">${level }</option>
						</c:forEach>
					</select>
				</td>
				
				<th>状态</th>
				<td>
					<select name="search_EQ_state">
						<option value="">全部</option>
						<option value="正常">正常</option>
						<option value="流失">流失</option>
						<option value="删除">删除</option>
						<option value="流失预警">流失预警</option>					
					</select>
				</td>
			</tr>
		</table>
		
		<!-- 列表数据 -->
		<br />
		
		<c:if test="${!empty page.list}">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>客户编号</th>
					<th>客户名称</th>
					<th>地区</th>
					<th>客户经理</th>
					<th>客户等级</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				
				<c:forEach var="customer" items="${page.list }">
					<tr>
						<td class="list_data_text">${customer.no }&nbsp;</td>
						<td class="list_data_ltext">${customer.name }&nbsp;</td>
						<td class="list_data_text">${customer.region }&nbsp;</td>
						<td class="list_data_text">${customer.manager.name }&nbsp;</td>
						<td class="list_data_text">${customer.level }&nbsp;</td>
						<td class="list_data_text">${customer.state}&nbsp;</td>
						<td class="list_data_op">
							<img onclick="window.location.href='${ctp}/customer/${customer.id}'"
								title="编辑" src="${ctp}/static/images/bt_edit.gif" class="op_button" alt="" /> 
							<img onclick="window.location.href='${ctp}/contact/list/${customer.id }'"
								title="联系人" src="${ctp}/static/images/bt_linkman.gif" class="op_button" alt="联系人信息" /> 
							<img onclick="window.location.href='${ctp}/activity/list/${customer.id }'"
								title="交往记录" src="${ctp}/static/images/bt_acti.gif" class="op_button" alt="交往记录" /> 
							<img onclick="window.location.href='${ctp}/order/list/${customer.id }'"
								title="历史订单" src="${ctp}/static/images/bt_orders.gif" class="op_button" alt="历史订单" /> 
								<c:if test="${customer.state != '删除' }">
									<img id="delete-${customer.id }" title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" alt="删除" />
									<input type="hidden" value="${customer.id }" >
									<%-- onclick="window.location.href='${ctp}/customer/delete/${customer.id}'" --%>
								</c:if>
							</td>					
					</tr>
				</c:forEach>
			</table>
			
			<c:set value="${ctp}/customer/list?search_LIKE_name=${param.search_LIKE_name }&search_EQ_region=${param.search_EQ_region }&search_LIKE_managerName=${param.search_LIKE_managerName }&search_EQ_level=${param.search_EQ_level }&search_EQ_state=${param.search_EQ_state }" var="navigatorUrl" scope="page"></c:set>
			<%@ include file="/commons/navigator.jsp" %>
			
			<%-- <tags:pagination page="${page}" paginationSize="5"/> --%>
		</c:if>
		<c:if test="${empty page.list }">
			没有任何数据！
		</c:if>
	</form>
	
</body>
</html>