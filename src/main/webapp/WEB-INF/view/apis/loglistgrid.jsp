<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../paging.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<body>
		 <div class="result-content">
			<table class="result-tab" style="width: 100%">
				<tr class="table_top">
					<th nowrap><label>&nbsp;</label></th>
					<th nowrap>开始时间</th>
					<th nowrap>HTTP方法</th>
					<th nowrap>请求包大小</th>
					<th nowrap>请求消费者</th>
					<th nowrap>客户端IP</th>
					<th nowrap>返回包大小</th>
					<th nowrap>返回状态码</th>
					<th nowrap>转发耗时</th>
					<th nowrap>请求耗时</th>
					<th nowrap>XFF头</th>
					<th nowrap>请求路径</th>
				</tr>
				<c:if test="${empty rows}">
					<tr>
						<td colspan="12" >无符合条件的记录</td>
					</tr>
				</c:if>
				<c:forEach items="${rows}" var="item" varStatus="ges">
					<tr>
						<td nowrap width="3%"><label>${(page.currentPage-1)*page.pageSize+ges.count}</label></td>
						<td nowrap width="10%" align="left">${item.started_at}</td>
						<td nowrap width="5%" align="center">${item.request_method}</td>
						<td nowrap width="5%" align="center">${item.request_size}&nbsp;字节</td>
						<td nowrap width="10%" align="center">${item.x_consumer_username}</td>
						<td nowrap width="10%" align="left">${item.client_ip}</td>
						<td nowrap width="5%" align="center">${item.response_size}&nbsp;字节</td>
						<td nowrap width="5%" align="center">${item.response_status}</td>
						<td nowrap width="5%" align="center">${item.latencies_proxy}&nbsp;ms</td>
						<td nowrap width="5%" align="center">${item.latencies_request}&nbsp;ms</td>
						<td nowrap width="10%" align="center">${item.x_forwarded_for}</td>
						<td nowrap width="20%" align="left">${item.request_real_uri}</td>
					</tr>
				</c:forEach>
			</table>
			<div class="list-page">
				<div id="setpage"></div>
			</div>
		</div>
		<script type="text/javascript">
			setpage();
		</script>
	</body>
</html>
