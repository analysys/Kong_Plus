<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.analysys.kong.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<% 
	User user = (User)request.getSession().getAttribute("user_login");
	String role = user.getUser_role();
%>
<div class="sidebar-title">
	<h1>菜单</h1>
</div>
<%if(role !=null && role.equalsIgnoreCase("0")){%>
	<div class="sidebar-content" id="mheight">
		<ul class="sidebar-list">
			<li><b>系统菜单</b></li>
			<li id="ulist"><a href="${ctx}/user/tolist.do" class="bigTag">用户列表</a></li>
			<li id="alist"><a href="${ctx}/apis/tolist.do" class="bigTag">API列表</a></li>
			<li id="clist"><a href="${ctx}/consumers/tolist.do" class="bigTag">消费者列表</a></li>
			<li><b>插件</b></li>
			<li id="ipre"><a href="${ctx}/plugin/tolist.do" class="bigTag">IP限制</a></li>
			<li id="logre"><a href="${ctx}/plugin/tologlist.do" class="bigTag">日志文件</a></li>
			<li><b>KONG</b></li>
			<li id="serinfo"><a href="${ctx}/kong/serinfo.do" class="bigTag">服务器信息 (Json)</a></li>
			<li id="serstatus"><a href="${ctx}/kong/serstatus.do" class="bigTag">服务器状态 (Json)</a></li>
			<li id="sercluster"><a href="${ctx}/kong/sercluster.do" class="bigTag">集群信息</a></li>
			<li id="serapi"><a href="${ctx}/kong/serapi.do" class="bigTag">服务器API列表</a></li>
			<li id="serdiv"><a href="${ctx}/kong/toserdiv.do" class="bigTag">自定义查询</a></li>
			<li><b>锆云</b></li>
			<li id="gyapi"><a href="${ctx}/gyapi.do" class="bigTag">锆云API</a></li>
		</ul>
	</div>
<%}else{%>
	<div class="sidebar-content" id="mheight">
		<ul class="sidebar-list">
			<li><b>系统菜单</b></li>
			<li id="ulist"><a href="${ctx}/user/tolist.do" class="bigTag">用户列表</a></li>
			<li id="alist"><a href="${ctx}/apis/tolist.do" class="bigTag">API列表</a></li>
			<li id="clist"><a href="${ctx}/consumers/tolist.do" class="bigTag">消费者列表</a></li>
			<li><b>插件</b></li>
			<li id="ipre"><a href="${ctx}/plugin/tolist.do" class="bigTag">IP限制</a></li>
			<li id="logre"><a href="${ctx}/plugin/tologlist.do" class="bigTag">日志文件</a></li>
			<li><b>锆云</b></li>
			<li id="gyapi"><a href="${ctx}/gyapi.do" class="bigTag">锆云API</a></li>
		</ul>
	</div>
<%}%>
<script>
	$(document).ready(function(){
		var aid = '${param.tag}';
		$("#" + aid).css({"background-color":"#C0C0C0"});
		document.getElementById("mheight").style.height = document.documentElement.clientHeight - 45 - document.getElementById("mheight").offsetTop + "px";
	});
</script>
