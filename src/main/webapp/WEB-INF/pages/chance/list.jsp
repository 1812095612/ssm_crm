<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>销售机会管理</title>
<script type="text/javascript">
	$(function(){
		$("#new").click(function(){
			window.location.href = "${ctp}/chance/";
			return false;
		});
		
		$("img[id^='delete-']").click(function(){
			
			var id = $(this).next(":hidden").val();
			
			var custName = $("#custName-" + id).text();
			
			var flag = confirm("您真的要删除【" + custName + "】的信息吗？");
			
			if(flag){
				
				var url = "${ctp}/chance/" + id;
				
				$("#hiddenForm").attr("action", url);
				$("#_method").attr("value", "DELETE");
				$("#hiddenForm").submit();
			}
			
			return false;
		});
	})
</script>
</head>
<body class="main">
	<form id="command" action="${ctp}/chance/list" method="post">
		
		<div class="page_title">
			销售机会管理
		</div>
		
		<div class="button_bar">
			<button class="common_button" id="new">新建</button>
			<button class="common_button" onclick="document.forms[1].submit();">
				查询
			</button>
		</div>
		
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th class="input_title">
					客户名称
				</th>
				<td class="input_content">
					<input type="text" name="search_LIKE_custName"  />
				</td>
				
				<th class="input_title">
					概要
				</th>
				<td class="input_content">
					<input type="text" name="search_LIKE_title" />
				</td>
				
				<th class="input_title">
					联系人
				</th>
				<td class="input_content">
					<input type="text" name="search_LIKE_contact" />
				</td>
			</tr>
		</table>
		<br />
		
		<!-- 列表数据 -->
		<c:if test="${empty page.list }">
			没有任何数据！
		</c:if>
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
						概要
					</th>
					<th>
						联系人
					</th>
					<th>
						联系人电话
					</th>
					<th>
						创建时间
					</th>
					<th>
						操作
					</th>
				</tr>
				<c:forEach var="item" items="${page.list }">
					<tr>
						<td class="list_data_number">${item.id }</td>
						<td id="custName-${item.id }" class="list_data_text">${item.custName }</td>
						<td class="list_data_text">${item.title }</td>
						<td class="list_data_text">${item.contact }</td>
						<td class="list_data_text">${item.contactTel }</td>
						<td class="list_data_text">
							<fmt:formatDate value="${item.createDate }" pattern="yyyy-MM-dd"/>
						</td>
						<td class="list_data_op">
							<img onclick="window.location.href='${ctp}/chance/dispatch/${item.id }'" 
								title="指派" src="${ctp}/static/images/bt_linkman.gif" class="op_button" />
							<img onclick="window.location.href='${ctp}/chance/${item.id }'" 
								title="编辑" src="${ctp}/static/images/bt_edit.gif" class="op_button" />
							
							<img id="delete-${item.id }" title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" />
							<input type="hidden" value="${item.id }">
							
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:set value="${ctp}/chance/list?search_LIKE_custName=${param.search_LIKE_custName }&search_LIKE_title=${param.search_LIKE_title }&search_LIKE_contact=${param.search_LIKE_contact }" var="navigatorUrl" scope="page"></c:set>
			<%@ include file="/commons/navigator.jsp" %>
			
		</c:if>
	</form>
	
</body>
</html>
