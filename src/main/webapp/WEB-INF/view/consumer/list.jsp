<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-ConsumersList</title>
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
			<jsp:include page="../menu.jsp" flush="true"><jsp:param name="tag" value="clist"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>消费者列表</span></div>
	        </div>
	        <div class="result-wrap">
	            <div class="result-content">
	                <form action="" method="post">
			             <table class="search-tab">
			                 <tr>
                                 <td nowrap>
                                 	<input type="text" id="name" value="${name}" maxlength="50" name="name" class="common-text" style="width: 200px;" placeholder="输入消费者名称" />
                                 </td>
			                     <td nowrap>
			                     	<input class="btn btn2" name="sub1" value="查询" type="button" onclick="browsers();">&nbsp;&nbsp;
			                     	<input class="btn btn6" name="sub1" value="添加消费者" type="button" onclick="addconsumers();">
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
		    var url = "${ctx}/consumers/list.do";
			var params ={
				pageNo: pageNo,
				name: $("#name").val(),
				anticache: Math.floor(Math.random()*10000)
			};
			$("#detalists").load(url, params);
		}
		function addconsumers() {
			window.location.href = "${ctx}/consumers/toadd.do";
		}
	</script>
	</body>
</html>
