<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>锆云API</title>
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
			<jsp:include page="menu.jsp" flush="true"><jsp:param name="tag" value="gyapi"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	       <div class="result-wrap">
	        	<iframe id="gyiframe" src="http://api.analysys.cn/" style="width: 100%; border: 0px;"></iframe>
	        </div>
	    </div>
	</div>
	<jsp:include page="bottom.jsp" />
	<script>
		$(document).ready(function(){
			document.getElementById("gyiframe").style.height = document.documentElement.clientHeight - 30 - document.getElementById("mheight").offsetTop + "px";
		});
	</script>
	</body>
</html>
