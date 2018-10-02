<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>制定计划</title>
<script type="text/javascript">
	$(function(){
		
		$("#addNew").click(function(){
			
			var chanceId = $(this).next(":hidden").val();
			var date = $("#date").val();
			var todo = $("#todo").val();
			
			var url = "${ctp}/plan/create";
			var args = {"date":date, "todo":todo, "chance.id":chanceId, "time":new Date()};
			
			$.post(url, args, function(respData){
				//若返回的值是一个正整数, 则意味着成功!
				var reg = /^\d+$/;
				var flag = reg.test(respData);
				
				if(flag){
					var id = parseInt(respData);
					
					//alert(id);
					
					if(id > 0){
						var $tr = $("<tr id='plan-"+id+"'></tr>");
						$tr.append("<td class='list_data_text'>" + date + "</td>")
						   .append("<td class='list_data_ltext'><input size='50' id='todo-" + id + "' type='text' value='" + todo + "'/><button class='common_button' id='updateButton-" + id + "'>修改</button><input type='hidden' value='" + id + "'/><button class='common_button' id='removeButton-" + id + "'>删除</button><input type='hidden' value='" + id + "'/></td>");
						
						$tr.find("button").eq(0).click(function(){
							//alert($(this).text());
							update(this);
							return false;
						});
						$tr.find("button").eq(1).click(function(){
							//alert($(this).text());
							remove(this);
							return false;
						});
						
						$("#planTable").append($tr);
						$("#date").val("");
						$("#todo").val("");
						
						alert("添加成功!");
					}
				}
				
			});
			
			return false;
		});
		
		$("button[id^='updateButton-']").click(function(){
			update(this);
			return false;
		});
		
		function update(button){
			var id = $(button).next(":hidden").val();
			var todo = $("#todo-" + id).val();
			
			var url = "${ctp}/plan/update";
			var args = {"id":id, "todo":todo, "time":new Date()};
			
			$.post(url, args, function(data){
				if(data = "1"){
					alert("修改成功!");
					return false;
				}
			});
		}
		
		$("button[id^='removeButton-']").click(function(){
			remove(this);
			//alert($(this).parent().parent().html());
			//$(this).parent().parent().remove();
			return false;
		});
		
		function remove(button){
			
			var id = $(button).next(":hidden").val();
			
			var url = "${ctp}/plan/remove";
			var args = {"id":id, "time":new Date()};
			
			$.post(url, args, function(data){
				if(data = "1"){
					$(button).parent().parent().remove();
					alert("删除成功!");
					return false;
				}
				
			});
		}
		
		$("#execute").click(function(){
			var id = $(":hidden[name='chance.id']").val();
			window.location.href = "${ctx}/plan/execution/" + id;
			return false;
		});
	})
</script>
</head>

<body class="main">
	
	<span class="page_title">制定计划</span>
	
	<div class="button_bar">
		<button class="common_button" onclick="window.location.href='${ctp}/plan/execution/${chance.id}'">
			执行计划
		</button>
		<button class="common_button" onclick="javascript:history.go(-1);">
			返回
		</button>
	</div>
	
		<form action="${ctp}/plan/make" method="post">
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>
					编号
				</th>

				<td>
					${chance.id}
				</td>
				<th>
					机会来源
				</th>

				<td>
					${chance.source}
				</td>
			</tr>
			<tr>
				<th>
					客户名称
				</th>
				<td>
					${chance.custName}
				</td>
				<th>
					成功机率（%）
				</th>

				<td>
					${chance.rate}
				</td>
			</tr>
			<tr>
				<th>
					概要
				</th>
				<td colspan="3">
					${chance.title}
				</td>
			</tr>
			<tr>
				<th>
					联系人
				</th>

				<td>
					${chance.contact}
				</td>
				<th>
					联系人电话
				</th>

				<td>
					${chance.contactTel}
				</td>
			</tr>
			<tr>
				<th>
					机会描述
				</th>
				<td colspan="3">
					${chance.description}
				</td>
			</tr>
			<tr>
				<th>
					创建人
				</th>
				<td>
					${chance.createBy.name}
				</td>
				<th>
					创建时间
				</th>
				<td>
					<fmt:formatDate value="${chance.createDate }" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th>
					指派给
				</th>
				<td>
					${user.name}
				</td>

			</tr>
		</table>

		<br />
		
		<table id="planTable" class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th width="200px">
					日期
				</th>
				<th>
					计划项
				</th>
			</tr>
			
			<c:if test="${!empty salesPlans }">
				<c:forEach items="${salesPlans }" var="plan">
					<tr id="plan-${plan.id}">
						<td class="list_data_text">
							<fmt:formatDate value="${plan.date }" pattern="yyyy-MM-dd"/>
							&nbsp;
						</td>
						<td class="list_data_ltext">
							<c:if test="${plan.result == null }">
								<input type="text" size="50"
									value="${plan.todo}" id="todo-${plan.id}"/>
								<button class="common_button" id="updateButton-${plan.id}">
									修改
								</button>
								
								<input type="hidden" value="${plan.id }"/>
								
								<button class="common_button" id="removeButton-${plan.id}">
									删除
								</button>
								
								<input type="hidden" value="${plan.id }"/>
								
							</c:if>
							<c:if test="${plan.result != null }">
								<input type="text" size="50"
									value="${plan.todo}" readonly="readonly"/>
								<input type="text" size="50"
									value="${plan.result}" readonly="readonly"/>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			
		</table>
		<div class="button_bar">
			<button id="addNew" class="common_button">
				新建
			</button>
			<input type="hidden" name="chance.id" value="${chance.id}" />
		</div>
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>
					日期
					<br />
					(格式: yyyy-mm-dd)
				</th>
				<td>
					<input type="text" name="date" id="date" />
					&nbsp;
				</td>
				<th>
					计划项
				</th>
				<td>
					<input type="text" name="todo" size="50" id="todo" />
					&nbsp;
				</td>
			</tr>
		</table>
	</form>
	
</body>
</html>