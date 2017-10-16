<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-Index</title>
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
			<jsp:include page="menu.jsp" flush="true" />
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>使用说明</div>
	        </div>
	        <div class="result-wrap">
	        	<div class="result-content">
	        		<pre style="font-size: 16px;">通过该Portal可以实现添加API到KONG服务器上，创建消费者，以及添加必要的插件！功能会逐步完善！系统大体功能包含：</pre>
					<pre><font color="red"><B>提示：</B></font>添加的API默认增加Key Authentication认证插件和ACL安全控制插件，所以新增完API后是不能直接访问的，需要创建消费者，并且为该API分配消费者才可以访问！</pre>
					<pre>          用户只能修改、删除自己创建的API，但可以查看所有的API！  消费者是私有的，同一系统的用户能查询到自己系统用户创建的消费者！</pre>
					<pre style="font-size: 15px;color: green;"><B>操作示例：</B></pre>
					<pre>1： 用户登录（没有账号的可以通过注册功能注册账号，需要选择-系统类型）</pre>
					<pre>2： 添加API：</pre>
					<pre>    2.1： 点击"API列表"菜单——添加API按钮</pre>
					<pre>    2.2： 输入API名称、服务地址、请求域名或者请求路径： 如 名称=<font color="red">myTestApi</font>, 服务地址=<font color="red">http://gt.analysys.cn/yourservices</font>, 请求域名=<font color="red">test.api</font></pre>
					<pre>    2.3： 点击提交按钮，创建成功后会有提示</pre>
					<pre>3： 添加消费者：</pre>
					<pre>    3.1： 点击"消费者列表"菜单——添加消费者 按钮</pre>
					<pre>    3.2： 输入消费者名称</pre>
					<pre>    3.3： 点击提交按钮，创建成功后会有提示，创建成功也会为该消费者创建好 API Key</pre>
					<pre>4： 为API分配消费者</pre>
					<pre>    4.1： 点击"API列表"菜单,在列表的"消费者数量"一列列出了当前API的消费者个数，点击个数</pre>
					<pre>    4.2： 在跳转的页面点击 添加消费者 按钮</pre>
					<pre>    4.3： 选择消费者，比如选择消费者<font color="red">Consumer1</font>， 点击提交按钮</pre>
					<pre>    4.4： 下面列表中会显示添加的消费者test1的信息，其中有Consumer1对应的 API Key，假设该API Key=<font color="red">141436jsfasfkalsfaasf</font></pre>
					<pre>通过以上步骤就可以添加一个API，并且为该API分配消费者了。也可以通过"IP限制"菜单为API增加访问IP的限制！</pre>
					<pre><B>具体访问服务(http://ip:port/yourservices)的方式为（具体GET/POST需要根据具体要访问的API服务决定）：</B></pre>
					<pre><B>  <font color="red">curl -i -X GET --url http://gt.analysys.cn --header "Host: test.api" --header "apikey: 141436jsfasfkalsfaasf"</font></B> 或者 <B>  <font color="red">curl -i -X GET --url http://gt.analysys.cn?apikey=141436jsfasfkalsfaasf --header "Host: test.api"</font></B></pre>
				</div>
	        </div>
	    </div>
	</div>
	<jsp:include page="bottom.jsp" />
</body>
</html>