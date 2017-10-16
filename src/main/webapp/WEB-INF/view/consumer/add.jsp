<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-AddConsumers</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<script type="text/javascript" src="${ctx}/js/libs/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$(":radio[name='auth_type']").click(function () {
			$(".auth_type").hide();
			$("._" + $(this).val()).show();
	    });
	})
	function validates() {
		var pattern = /^[a-zA-Z0-9_]+$/;
		if($("#name").val() == ""){
			alert("名称不能为空.");
			$("#name").focus();
			return false;
		} else {
			if(!pattern.test($("#name").val())){
				alert("名称只能包含数字、字母、下划线!");
				$("#name").focus();
				return false;
			}
		}
		var atype = $(":radio[name='auth_type']:checked").val();
		if(atype == null || atype == ""){
			alert("请选择认证类型!");
		    return false;
		} else if(atype == "oauth2"){
			var _callback = $("#_callback").val();
			if(_callback == "") {
			    alert("回调地址不能为空!");
			    $("#_callback").focus();
			    return false;
			}
		} else if(atype == "basic"){
			var _username = $("#_username").val();
			if(_username == "") {
			    alert("用户名不能为空!");
			    $("#_username").focus();
			    return false;
			} else {
				if(!pattern.test(_username)){
					alert("用户名只能包含数字、字母、下划线!");
					$("#_username").focus();
					return false;
				}
			}
			var _password = $("#_password").val();
			if(_password == "") {
			    alert("密码不能为空!");
			    $("#_password").focus();
			    return false;
			} else {
				if(!pattern.test(_password)){
					alert("密码只能包含数字、字母、下划线!");
					$("#_password").focus();
					return false;
				}
			}
		}
		$("#myform").submit();
	}
	function goback(){
		window.location.href = "${ctx}/consumers/tolist.do";
	}
</script>
</head>
<body>
	<div class="topbar-wrap white">
		<jsp:include page="../head.jsp" flush="true" />
	</div>
	<div class="container clearfix">
		<div class="sidebar-wrap">
			<jsp:include page="../menu.jsp" flush="true"><jsp:param name="tag" value="clist"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>消费者列表</span></div>
	        </div>
	        <div class="result-wrap">
	            <div class="result-content">
	                <form action="${ctx}/consumers/add.do" method="post" id="myform" name="myform">
	                    <table class="insert-tab" style="width: 100%">
                            <tr>
                                <th><font color="red">*</font>消费者名称：</th>
                                <td><input type="text" id="name" value="${name}" maxlength="30" size="85" name="name" class="common-text"></td>
                            </tr>
                            <tr>
                                <th><font color="red">*</font>认证类型：</th>
                                <td>
                                	<input type="radio" name="auth_type" id="key_auth_type" value="key">&nbsp;&nbsp;Key Authentication&nbsp;&nbsp;&nbsp;&nbsp;
                                	<input type="radio" name="auth_type" id="oauth2_auth_type" value="oauth2">&nbsp;&nbsp;OAuth2.0 Authentication&nbsp;&nbsp;&nbsp;&nbsp;
                                	<input type="radio" name="auth_type" id="basic_auth_type" value="basic">&nbsp;&nbsp;Basic Authentication
                                </td>
                            </tr>
                            
                            <tr class="auth_type _oauth2" style="display: none;">
                                <th>认证参数：</th>
                                <td><font color="red">*</font>&nbsp;回调地址：<input type="text" id="_callback" maxlength="100" size="76" name="_callback" class="common-text"></td>
                            </tr>
                            
                            <tr class="auth_type _basic" style="display: none;">
                                <th rowspan="2">认证参数：</th>
                                <td>用户名：<input type="text" id="_username" maxlength="30" size="15" name="_username" class="common-text"></td>
                            </tr>
                            <tr class="auth_type _basic" style="display: none;">
                                <td>密&#12288;码：<input type="text" id="_password" maxlength="30" size="15" name="_password" class="common-text"></td>
                            </tr>
                            <tr>
                                <th>邮箱：</th>
                                <td><input type="text" id="email" value="${email}" maxlength="200" size="85" name="email" class="common-text"></td>
                            </tr>
                            <tr>
                                <th>备注：</th>
                                <td><input type="text" id="remarks" value="${remarks}" maxlength="200" size="85" name="remarks" class="common-text"></td>
                            </tr>
                            <tr>
                                <th></th>
                                <td>
                                    <input type="hidden" value="siteConf.inc.php" name="file">
                                    <input type="button" onclick="validates()" value="提交" class="btn btn6 mr10">
                                    <input type="button" onclick="goback()" value="返回" class="btn btn6 mr10">&nbsp;&nbsp;
                                    <font color="red">${msg}</font>
                                </td>
                            </tr>
	                    </table>
	                </form>
	            </div>
	        </div>
	    </div>
	</div>
	<jsp:include page="../bottom.jsp" />
</body>
</html>