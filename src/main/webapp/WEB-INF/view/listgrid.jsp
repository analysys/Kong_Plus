<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="paging.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<body>
		 <div class="result-content">
			<table class="result-tab" style="width: 100%">
				<tr class="table_top">
					<th nowrap><label>&nbsp;</label></th>
					<th nowrap>用户ID</th>
					<th nowrap>用户名称</th>
					<th nowrap>系统类型</th>
					<th nowrap>状态</th>
					<th nowrap>API个数</th>
					<th nowrap>注册时间</th>
					<th nowrap>备注</th>
				</tr>
				<c:if test="${empty rows}">
					<tr>
						<td colspan="8" >无符合条件的记录</td>
					</tr>
				</c:if>
				<c:forEach items="${rows}" var="item" varStatus="ges">
					<tr>
						<td nowrap width="3%"><label>${(page.currentPage-1)*page.pageSize+ges.count}</label></td>
						<td nowrap width="7%" align="left">${item.uid}</td>
						<td nowrap width="10%" align="left"><a href="#" onclick="updates(${item.uid})">${item.uname}</a></td>
						<td nowrap width="10%" align="center">
							${item.sys_type == "1" ? "方舟" : (item.sys_type == "2" ? "官网" : (item.sys_type == "3" ? "千帆" : (item.sys_type == "4" ? "博阅" : (item.sys_type == "5" ? "数据" : "--"))))}
						</td>
						<td nowrap width="10%" align="center">${item.status == "0" ? "无效" : "有效"}</td>
						<td nowrap width="10%" align="center">
							<a href="#" onclick="showapi(${item.uid})">${item.apicnt == "" or item.apicnt == null ? "0" : item.apicnt}</a>
						</td>
						<td nowrap width="15%" align="center">${item.createtime}</td>
						<td nowrap width="30%" align="center">
							${item.remarks}
						</td>
					</tr>
				</c:forEach>
			</table>
			<div class="list-page">
				<div id="setpage"></div>
			</div>
		</div>
		<script type="text/javascript">
			setpage();
			function updates(uid){
				window.location.href = "${ctx}/user/toupdate.do?uid=" + uid;
			}
			function showapi(uid){
				window.location.href = "${ctx}/apis/tolist.do?uid=" + uid;
			}
		</script>
	</body>
</html>
