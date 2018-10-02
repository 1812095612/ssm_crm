<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>联系人管理</title>
<script type="text/javascript">
	$(function(){
		$(".delete").click(function(){
			
			var tSize = $("#contactTable").find("tr").size();
			alert(tSize);
			
			var size = ${pageListSize};
			
			if(size == 1){
				alert("该联系人不能被删除！");	
				return;
			}
			
			if(tSize == 2){
				alert("该联系人不能被删除！");	
				return;
			}
			
			var contactId = $(this).next(":hidden").val();
			
			var $this = $(this);
			
			var url = "${ctp}/contact/delete/"+contactId+"/${customer.id }";
			var args = {"time":new Date()};
			
			$.post(url, args, function(data){
				if(data = "1"){
					alert("删除成功！");
					$this.parent().parent().remove();
					return false;
				}
			});
			
			return false;
		});
	});
</script>
</head>

<body>

	<div class="page_title">
		联系人管理
	</div>
	<div class="button_bar">

		<button class="common_button" onclick="window.location.href='${ctp}/contact/create/${customer.id }'">
			新建
		</button>
		<button class="common_button" onclick="javascript:history.go(-1);">
			返回
		</button>
	</div>
	
	<form action="${ctp}/contact/list" method="post">
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>
					客户编号
				</th>
				<td>${customer.no}</td>
				<th>
					客户名称
				</th>
				<td>${customer.name}</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		
		<c:if test="${!empty page.list }">
			<table id="contactTable" class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
				<tr>
					<th>
						姓名
					</th>
					<th>
						性别
					</th>
					<th>
						职位
					</th>
					<th>
						办公电话
					</th>
					<th>
						手机
					</th>
					<th>
						备注
					</th>
					<th>
						操作
					</th>
				</tr>
	
				<c:forEach var="contact" items="${page.list }">
					<tr>
						<td class="list_data_text">
							${contact.name}
						</td>
						<td class="list_data_text">
							${contact.sex}
						</td>
						<td class="list_data_text">
							${contact.position}
						</td>
						<td class="list_data_text">
							${contact.tel}
						</td>
						<td class="list_data_text">
							${contact.mobile}
						</td>

						<td class="list_data_ltext">
							${contact.memo}
						</td>
						<td class="list_data_op">
							<img onclick="window.location.href='${ctp}/contact/edit/${contact.id }'" 
								title="编辑" src="${ctp}/static/images/bt_edit.gif" class="op_button" />
							<img class="delete" title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" />
							<input type="hidden" value="${contact.id }">
							
							<%-- onclick="window.location.href='${ctp}/contact/delete/${contact.id }/${customer.id }'" --%>
							
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:set value="${ctp}/contact/list/${customer.id }?1=1" var="navigatorUrl" scope="page"></c:set>
			<%@ include file="/commons/navigator.jsp" %>
			
			<%-- <tags:pagination page="${page}" paginationSize="5"/> --%>
		</c:if>
		<c:if test="${empty page.list}">
			没有任何数据！
		</c:if>
	</form>
</body>
</html>