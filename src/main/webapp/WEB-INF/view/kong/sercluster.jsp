<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-Sercluster</title>
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
			<jsp:include page="../menu.jsp" flush="true"><jsp:param name="tag" value="sercluster"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>集群信息</span></div>
	        </div>
	        <div class="result-wrap">
	        	<div class="result-content">
					<table class="result-tab" style="width: 100%">
						<tr class="table_top">
							<th nowrap><label>&nbsp;</label></th>
							<th nowrap>Address</th>
							<th nowrap>Name</th>
							<th nowrap>Status</th>
						</tr>
						<c:if test="${empty infoList}">
							<tr>
								<td colspan="3" >无符合条件的记录</td>
							</tr>
						</c:if>
						<c:forEach items="${infoList}" var="item" varStatus="ges">
							<tr>
								<td nowrap width="3%"><label>${(page.currentPage-1)*page.pageSize+ges.count}</label></td>
								<td nowrap width="15%" align="center">${item.address}</td>
								<td nowrap width="15%" align="left">${item.name}</td>
								<td nowrap width="10%" align="center">${item.status}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
	        </div>
	    </div>
	</div>
	<jsp:include page="../bottom.jsp" />
	</body>
</html>
