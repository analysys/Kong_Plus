<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-Login</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<script type="text/javascript" src="${ctx}/js/libs/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<style type="text/css">
	a:link, a:visited {
		font-size: 12px;
		color: #666;
		text-decoration: none;
	}
	
	a:hover {
		color: #ff0000;
		text-decoration: underline;
	}
	
	#Tab {
		margin: 10% 55%;
		width: 500px;
		border: 1px solid #BCE2F3;
	}
	
	.Menubox {
		height: 50px;
		border-bottom: 1px solid #37b7f5;
		background: #E4F2FB;
	}
	
	.Menubox ul {
		list-style: none;
		margin: 7px 40px;
		padding: 0;
		position: absolute;
	}
	
	.Menubox ul li {
		float: left;
		background: #37b7f5;
		line-height: 42px;
		display: block;
		cursor: pointer;
		width: 200px;
		text-align: center;
		color: #fff;
		font-weight: bold;
		border-top: 1px solid #37b7f5;
		border-left: 1px solid #37b7f5;
		border-right: 1px solid #37b7f5;
	}
	
	.Menubox ul li.hover {
		background: #fff;
		border-bottom: 1px solid #fff;
		color: #147AB8;
	}
	
	.Contentbox {
		clear: both;
		margin-top: 0px;
		border-top: none;
		height: 400px;
		padding-top: 8px;
		height: 100%;
	}
	
	.Contentbox ul {
		list-style: none;
		margin: 7px;
		padding: 0;
	}
	
	.Contentbox ul li {
		line-height: 24px;
		border-bottom: 1px dotted #ccc;
	}
</style>
<script>
	$(function(){
		var index = '${index}';
		if(index == '1' || index == '2')
			setTab('menu',index,2);
	});
	function setTab(name,cursel,n){
		for(i=1; i<=n; i++){
			var menu=document.getElementById(name+i);
			var con=document.getElementById("con_"+name+"_"+i);
			menu.className=i==cursel?"hover":"";
			con.style.display=i==cursel?"block":"none";
		}
	}
	function validates() {
		if($("#pwd").val() == "" || $("#pwds").val() == ""){
			alert("密码不能为空.");
			return false;
		} else if($("#pwd").val() != $("#pwds").val()){
			alert("两次密码输入不一致.");
			return false;
		} else {
			$("#registerform").submit();
		}
	}
</script>
</head>
<body>
	<div class="topbar-wrap white">
		<div class="topbar-inner clearfix">
			<div class="topbar-logo-wrap clearfix">
				<ul class="navbar-list clearfix">
					<li><img src="${ctx}/images/logos.png" width="20%" /></li>
				</ul>
			</div>
		</div>
	</div>
	<div id="Tab">
		<div class="Menubox">
			<ul>
				<li id="menu1" onmouseover="setTab('menu',1,2)" class="hover">登录</li>
				<li id="menu2" onmouseover="setTab('menu',2,2)">注册</li>
			</ul>
		</div>
		<div class="Contentbox">
			<div id="con_menu_1" class="hover">
				<form action="${ctx}/login" method="post" id="loginform">
					<table style="margin: 20px auto;">
						<tr height="60px">
							<th align="right">用户名：</th>
							<td align="left">
								<input type="text" id="username" maxlength="50" name="username" value="${username}" class="common-text" width="60px;">
							</td>
						</tr>
						<tr height="60px">
							<th align="right">密码：</th>
							<td align="left">
								<input type="password" id="password" maxlength="60" width="50px" name="password" class="common-text">
							</td>
						</tr>
						<tr>
							<td align="left">&nbsp;</td>
							<td align="right">
								<input class="btn btn4" name="sub1" value="登录" type="submit"> 
								<input class="btn btn2" value="重置" type="reset">
							</td>
						</tr>
						<tr>
							<td align="left">&nbsp;</td>
							<td align="right">
								<br /><font color='red'>${msg}</font>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="con_menu_2" style="display: none">
				<form action="${ctx}/register" method="post" id="registerform">
					<table style="margin: 20px auto;">
						<tr height="60px">
							<th align="right">用户名：</th>
							<td align="left">
								<input type="text" id="uname" maxlength="50" name="uname" value="${uname}" class="common-text" width="60px;">
							</td>
						</tr>
						<tr height="60px">
							<th align="right">密码：</th>
							<td align="left">
								<input type="password" id="pwd" maxlength="60" width="50px" name="pwd" class="common-text">
							</td>
						</tr>
						<tr height="60px">
							<th align="right">确认密码：</th>
							<td align="left">
								<input type="password" id="pwds" maxlength="60" width="50px" name="pwds" class="common-text">
							</td>
						</tr>
						<tr height="60px">
							<th align="right">服务提供方：</th>
							<td align="left">
								<select name="sys_type" id="sys_type" style="height: 27px;width: 206px;">
                                    <option value="1">方舟</option>
                                    <option value="2">官网</option>
                                    <option value="3">千帆</option>
                                    <option value="4">博阅</option>
                                    <option value="5">数据</option>
                                </select>
							</td>
						</tr>
						<tr>
							<td align="left">&nbsp;</td>
							<td align="right">
								<input class="btn btn4" name="sub1" value="注册" onclick="validates()" type="button"> 
								<input class="btn btn2" value="重置" type="reset">
							</td>
						</tr>
						<tr>
							<td align="left">&nbsp;</td>
							<td align="right">
								<br /><font id="rmsg" color='red'>${rmsg}</font>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>