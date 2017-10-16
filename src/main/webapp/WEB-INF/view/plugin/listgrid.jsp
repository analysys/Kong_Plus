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
					<th nowrap>IP地址白名单</th>
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
								<c:if test="${item.ip_whitelist == '' or item.ip_whitelist == null}">
									<a href="#" onclick="showblack(this,'${item.id}','${item.aname}','')">添加</a>
								</c:if>
								<c:if test="${item.ip_whitelist != '' and item.ip_whitelist != null}">
									<a href="#" onclick="showblack(this,'${item.id}','${item.aname}','${item.extend}')">${item.ip_whitelist}</a>
								</c:if>
							</c:if>
							<c:if test="${myuid != item.uid}">
								${item.ip_whitelist}
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
			function showblack(obj,ids,aname,extend){
				var trHtml = "<tr><td colspan='10'>";
				if(extend == ""){
					trHtml += "<input type='text' class='common-text' style='width: 93%;' placeholder='输入IP地址(CIDR)：xx.xx.xx.xx, xx.x.x.xxx/xx' />";
				} else {
					trHtml += "<input type='text' value='"+$(obj).text()+"' class='common-text' style='width: 93%;' placeholder='输入IP地址(CIDR)：xx.xx.xx.xx, xx.x.x.xxx/xx' />";
				}
				trHtml += "&nbsp;&nbsp;<input class='btn btn2' value='添加' type='button' onclick=\"addblack(this,'"+ids+"','"+aname+"','"+extend+"');\">";
				trHtml += "</td></tr>";
				$(obj).parent().parent().after(trHtml);
			}
			function addblack(obj,ids,aname,extend){
				var url = "${ctx}/plugin/addblack.do";
				$.ajax({
					type: "POST",
					url: url,
					data: {id:ids, apiname:aname, ips:$(obj).prev("input").val(), extend:extend},
					dataType: "json",
					success: function(data){
						alert(data.msg);
						browser("");
                    }
				});
			}
		</script>
	</body>
</html>
