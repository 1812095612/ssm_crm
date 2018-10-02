<%@ tag language="java" pageEncoding="UTF-8" description="分页"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="page" required="true" rtexprvalue="true" type="com.qiaosoftware.crm.model.Page" %>
<%@ attribute name="navigatorUrl" required="true" rtexprvalue="true" type="java.lang.String" %>

<div style="text-align:right; padding:6px 6px 0 0;">

	共 ${page.totalRecordNo } 条记录 
	&nbsp;&nbsp;
	
	当前第 ${page.pageNo } 页/共 ${page.totalPageNo } 页
	&nbsp;&nbsp;

	<c:if test="${page.hasPrev }">
		<a href='${navigatorUrl}&pageNoStr=1'>首页</a>
		&nbsp;&nbsp;
		<a href='${navigatorUrl}&pageNoStr=${page.prev}'>上一页</a>
		&nbsp;&nbsp;
	</c:if>
	
	<c:if test="${page.hasNext }">
		<a href='${navigatorUrl}&pageNoStr=${page.next}'>下一页</a>
		&nbsp;&nbsp;
		<a href='${navigatorUrl}&pageNoStr=${page.totalPageNo}'>末页</a>
		&nbsp;&nbsp;
	</c:if>
	
	转到 <input id="pageNo" size='1'/> 页
	&nbsp;&nbsp;

</div>


<script type="text/javascript">

	$(function(){
		
		$("#pageNo").change(function(){
			var pageNo = $(this).val();
			var reg = /^\d+$/;
			if(!reg.test(pageNo)){
				$(this).val("");
				alert("输入的页码不合法");
				return;
			}
			
			var pageNo2 = parseInt(pageNo);
			if(pageNo2 < 1 || pageNo2 > parseInt("${page.totalPageNo}")){
				$(this).val("");
				alert("输入的页码不合法");
				return;
			}
			
			//查询条件需要放入到 class='condition' 的隐藏域中. 
			//window.location.href = window.location.pathname + "&pageNoStr=" + pageNo2;
			window.location.href = "${navigatorUrl}&pageNoStr=" + pageNo2;
			
		});
	})
</script>