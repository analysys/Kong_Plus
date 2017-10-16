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
					<th nowrap>API名称</th>
					<th nowrap>创建用户</th>
					<th nowrap>服务地址</th>
					<th nowrap>请求域名</th>
					<th nowrap>请求路径</th>
					<th nowrap>消费者数量</th>
					<th nowrap>调用次数</th>
					<th nowrap>添加时间</th>
					<th nowrap>认证方式</th>
					<th nowrap>服务描述</th>
					<th nowrap>操作</th>
				</tr>
				<c:if test="${empty rows}">
					<tr>
						<td colspan="13" >无符合条件的记录</td>
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
						<td nowrap width="10%" align="left">${item.aname}</td>
						<td nowrap width="5%" align="center">${item.uname}</td>
						<td nowrap width="10%" align="left">${item.visit_url}</td>
						<td nowrap width="7%" align="center">${item.req_host}</td>
						<td nowrap width="8%" align="left">${item.req_path}</td>
						<td nowrap width="5%" align="center">
							<%-- <c:if test="${myuid == item.uid}">
								<a href="#" onclick="showconsumers('${item.id}')">
									${item.consumer_cnt == "" or item.consumer_cnt == null ? "0" : item.consumer_cnt}
								</a>
							</c:if>
							<c:if test="${myuid != item.uid}">
								${item.consumer_cnt == "" or item.consumer_cnt == null ? "0" : item.consumer_cnt}
							</c:if> --%>
							<a href="#" onclick="showconsumers('${item.id}','${item.uid}','${item.auth_type}')">
								${item.consumer_cnt == "" or item.consumer_cnt == null ? "0" : item.consumer_cnt}
							</a>
						</td>
						<td nowrap width="5%" align="center">
							<a href="#" onclick="showlogs('${item.id}','${item.aname}')">
								${item.log_cnt == "" or item.log_cnt == null ? "0" : item.log_cnt}
							</a>
						</td>
						<td nowrap width="10%" align="center">${item.createtime}</td>
						<td nowrap width="5%" align="center">${item.auth_type}</td>
						<td width="20%" align="left">${item.remarks}</td>
						<td nowrap width="5%" align="center">
							<c:if test="${myuid == item.uid}">
								<a href="#" onclick="deletes('${item.id}','${item.aname}')">删除</a>
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
				window.location.href = "${ctx}/apis/toupdate.do?id=" + aid;
			}
			function showconsumers(aid,uid,atype){
				window.location.href = "${ctx}/apis/consumers.do?id=" + aid + "&cuid=" + uid + "&atype=" + atype;
			}
			function showlogs(aid,aname){
				window.location.href = "${ctx}/apilogs/tochar.do?api_id=" + aid + "&api_name=" + aname;
			}
			function deletes(aid,aname){
				if(confirm("确定要删除该API?")){
					var url = "${ctx}/apis/delete.do";
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
