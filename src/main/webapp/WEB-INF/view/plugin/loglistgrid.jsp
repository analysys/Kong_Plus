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
					<th nowrap>名称</th>
					<th nowrap>创建用户</th>
					<th nowrap>访问地址</th>
					<th nowrap>访问DNS</th>
					<th nowrap>访问Path</th>
					<th nowrap>添加时间</th>
					<th nowrap>日志文件路径</th>
				</tr>
				<c:if test="${empty rows}">
					<tr>
						<td colspan="8" >无符合条件的记录</td>
					</tr>
				</c:if>
				<c:forEach items="${rows}" var="item" varStatus="ges">
					<tr>
						<td nowrap width="3%"><label>${(page.currentPage-1)*page.pageSize+ges.count}</label></td>
						<td nowrap width="10%" align="left">${item.aname}</td>
						<td nowrap width="5%" align="center">${item.uname}</td>
						<td nowrap width="10%" align="center">${item.visit_url}</td>
						<td nowrap width="7%" align="center">${item.req_host}</td>
						<td nowrap width="8%" align="center">${item.req_path}</td>
						<td nowrap width="10%" align="center">${item.createtime}</td>
						<td nowrap width="35%" align="center">
							<c:if test="${myuid == item.uid}">
								<input type="text" value="${item.log_file}" placeholder="按回车键保存" class="common-text" style="width:500px; height: 27px; border-top: 0px;border-left: 0px;border-right: 0px;" onkeydown="saveFilePath(this,'${item.id}','${item.aname}')">
							</c:if>
							<c:if test="${myuid != item.uid}">
								${item.log_file}
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
			function saveFilePath(obj,ids,aname){
				var event = arguments.callee.caller.arguments[0] || window.event;
				if(event.keyCode == 13){
					var url = "${ctx}/plugin/addlog.do";
					$.ajax({
						type: "POST",
						url: url,
						data: {id:ids, logpath:$(obj).val(), apiname:aname},
						dataType: "json",
						success: function(data){
							alert(data.msg);
							browser("");
	                    }
					});
				}
			}
		</script>
	</body>
</html>
