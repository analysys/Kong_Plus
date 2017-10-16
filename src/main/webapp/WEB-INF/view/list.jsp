<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-UserList</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<script type="text/javascript" src="${ctx}/js/libs/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
	</head>
	<body>
		<div class="topbar-wrap white">
		<jsp:include page="head.jsp" flush="true" />
	</div>
	<div class="container clearfix">
		<div class="sidebar-wrap">
			<jsp:include page="menu.jsp" flush="true"><jsp:param name="tag" value="ulist"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>用户列表</span></div>
	        </div>
	        <div class="result-wrap">
	            <div class="result-content">
	                <form action="" method="post">
			             <table class="search-tab">
			                 <tr>
                                 <td nowrap>
                                 	<input type="text" id="name" value="${name}" maxlength="50" name="name" class="common-text" style="width: 200px;" placeholder="输入用户名" />
                                 </td>
			                     <td nowrap>
			                     	<input class="btn btn2" name="sub1" value="查询" type="button" onclick="browsers();">
			                     </td>
			                 </tr>
			             </table>
			         </form>
	            </div><br>
	            <div id="detalists"></div>
	        </div>
	    </div>
	</div>
	<jsp:include page="bottom.jsp" />
	<script>
		browser("");
		function browsers() {
			browser(1);
		}
		function browser(pageNo) {
		    var url = "${ctx}/user/list.do";
			var params ={
				pageNo: pageNo,
				name: $("#name").val(),
				anticache: Math.floor(Math.random()*10000)
			};
			$("#detalists").load(url, params);
		}
	</script>
	</body>
</html>
