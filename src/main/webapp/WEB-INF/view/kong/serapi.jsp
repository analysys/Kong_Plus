<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-Serapi</title>
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
			<jsp:include page="../menu.jsp" flush="true"><jsp:param name="tag" value="serapi"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>服务器API列表</span></div>
	        </div>
	        <div class="result-wrap">
	            <div class="result-content">
					<table class="result-tab" style="width: 100%">
						<tr class="table_top">
							<th nowrap><label>&nbsp;</label></th>
							<th nowrap>ID</th>
							<th nowrap>Name</th>
							<th nowrap>request_host</th>
							<th nowrap>request_path</th>
							<th nowrap>upstream_url</th>
							<th nowrap>strip_request_path</th>
							<th nowrap>preserve_host</th>
							<th nowrap>created_at</th>
						</tr>
						<c:if test="${empty infoList}">
							<tr>
								<td colspan="9" >无符合条件的记录</td>
							</tr>
						</c:if>
						<c:forEach items="${infoList}" var="item" varStatus="ges">
							<tr>
								<td nowrap width="3%"><label>${(page.currentPage-1)*page.pageSize+ges.count}</label></td>
								<td nowrap width="7%" align="left">${item.id}</td>
								<td nowrap width="10%" align="left">${item.name}</td>
								<td nowrap width="10%" align="left">${item.request_host}</td>
								<td nowrap width="10%" align="left">${item.request_path}</td>
								<td nowrap width="10%" align="left">${item.upstream_url}</td>
								<td nowrap width="10%" align="left">${item.strip_request_path}</td>
								<td nowrap width="10%" align="left">${item.preserve_host}</td>
								<td nowrap width="15%" align="center">${item.created_at}</td>
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
