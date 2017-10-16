<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-ApiLogList</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<script type="text/javascript" src="${ctx}/js/libs/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
</head>
	<body>
		<div class="topbar-wrap white">
		<jsp:include page="../head.jsp" flush="true" />
	</div>
	<div class="container clearfix">
		<div class="sidebar-wrap">
			<jsp:include page="../menu.jsp" flush="true"><jsp:param name="tag" value="alist"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>API列表</span><span class="crumb-step">&gt;</span>API调用日志</div>
	        </div>
	        <div class="result-wrap">
	            <div class="result-content">
	                <form action="" method="post">
	                	<input type="hidden" id="api_id" value="${api_id}" name="api_id" />
	                	<input type="hidden" id="api_name" value="${api_name}" name="api_name" />
			             <table class="search-tab">
			                 <tr>
			                 	 <%-- <td nowrap>
                                 	API： <input type="text" id="api_name" value="${api_name}" maxlength="50" name="api_name" class="common-text" style="width: 200px;" placeholder="输入API名称" />
                                 </td> --%>
			                 	 <td nowrap>
                                 	消费者： <input type="text" id="x_consumer_username" value="${x_consumer_username}" maxlength="50" name="x_consumer_username" class="common-text" style="width: 200px;" placeholder="输入消费者" />
                                 </td>
                                 <td nowrap>
                                 	状态： <input type="text" id="response_status" value="${response_status}" maxlength="10" name="response_status" class="common-text" style="width: 50px;" placeholder="状态码" />
                                 </td>
			                     <td nowrap>
			                     	<input class="btn btn2" name="sub1" value="查询" type="button" onclick="browsers();">&nbsp;&nbsp;
			                     	<input class="btn btn6" name="sub1" value="返回" type="button" onclick="go_back();">
			                     </td>
			                 </tr>
			             </table>
			         </form>
	            </div><br>
	            <div id="detalists"></div>
	        </div>
	    </div>
	</div>
	<jsp:include page="../bottom.jsp" />
	<script>
		browser("");
		function browsers() {
			browser(1);
		}
		function browser(pageNo) {
		    var url = "${ctx}/apilogs/list.do";
			var params ={
				pageNo: pageNo,
				api_id: $("#api_id").val(),
				//api_name: $("#api_name").val(),
				x_consumer_username: $("#x_consumer_username").val(),
				response_status: $("#response_status").val(),
				anticache: Math.floor(Math.random()*10000)
			};
			$("#detalists").load(url, params);
		}
		function go_back(){
			//window.location.href = "${ctx}/apis/tolist.do";
			window.location.href = "${ctx}/apilogs/tochar.do?api_id=" + $("#api_id").val() + "&api_name=" + $("#api_name").val();
		}
	</script>
	</body>
</html>
