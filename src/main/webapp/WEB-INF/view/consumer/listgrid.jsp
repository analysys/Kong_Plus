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
					<th nowrap>ID</th>
					<th nowrap>认证类型</th>
					<th nowrap>API Key</th>
					<th nowrap>Client_id/Username</th>
					<th nowrap>Client_secret/Password</th>
					<th nowrap>名称</th>
					<th nowrap>创建用户</th>
					<th nowrap>添加时间</th>
					<th nowrap>备注</th>
					<th nowrap>操作</th>
				</tr>
				<c:if test="${empty rows}">
					<tr>
						<td colspan="11" >无符合条件的记录</td>
					</tr>
				</c:if>
				<c:forEach items="${rows}" var="item" varStatus="ges">
					<tr>
						<td nowrap width="3%"><label>${(page.currentPage-1)*page.pageSize+ges.count}</label></td>
						<td nowrap width="7%" align="left">&nbsp;
							<c:if test="${myuid == item.uid}">
								<a href="#" onclick="updates('${item.id}')">${item.id}</a>
							</c:if>
							<c:if test="${myuid != item.uid}">
								${item.id}
							</c:if>
						</td>
						<td nowrap width="10%" align="left">${item.auth_type}</td>
						<td nowrap width="10%" align="left">${item.keys}</td>
						<td nowrap width="10%" align="left">${item.client_id}</td>
						<td nowrap width="10%" align="left">${item.client_secret}</td>
						<td nowrap width="10%" align="left">${item.cname}</td>
						<td nowrap width="5%" align="center">${item.uname}</td>
						<td nowrap width="10%" align="center">${item.createtime}</td>
						<td nowrap width="20%" align="left">${item.remarks}</td>
						<td nowrap width="5%" align="center">
							<c:if test="${myuid == item.uid}">
								<a href="#" onclick="deletes('${item.id}','${item.cname}')">删除</a>
							</c:if>
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
			function updates(aid){
				window.location.href = "${ctx}/consumers/toupdate.do?id=" + aid;
			}
			function deletes(aid,aname){
				if(confirm("确定要删除该消费者?")){
					var url = "${ctx}/consumers/delete.do";
					$.ajax({
						type: "POST",
						url: url,
						data: {id:aid, name:aname},
						dataType: "json",
						success: function(data){
							browsers();
	                    },
	                    error:function(XMLHttpRequest, textStatus, errorThrown) {
	                    	browsers();
	                    }
					});
				}
			}
		</script>
	</body>
</html>
