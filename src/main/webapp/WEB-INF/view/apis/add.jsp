<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-AddApi</title>
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
		var auth_type = '${auth_type}';
		$("#" + auth_type + "_auth_type").attr("checked","checked");
		$(".auth_type").hide();
		$("._" + auth_type).show();
		if(auth_type == "oauth2"){
			$("#_scopes").val('${scopes}');
			var scopes_need = '${scopes_need}';
			if(scopes_need == "1"){
				$("#_scopes_need").attr("checked","checked");
			}
			$("#_expiration").val('${expiration}');
			var grant_type = '${grant_type}';
			if(grant_type != null && grant_type != ''){
				var grant_types = grant_type.split(",");
				for (i=0; i<grant_types.length; i++ ){
					$("#" + grant_types[i] + "_grant_type").attr("checked","checked");
				}
			}
		}
	})

	function validates() {
		if($("#name").val() == ""){
			alert("名称不能为空.");
			$("#name").focus();
			return false;
		} else {
			var pattern = /^[a-zA-Z0-9_]+$/;
			if(!pattern.test($("#name").val())){
				alert("名称只能包含数字、字母、下划线!");
				$("#name").focus();
				return false;
			}
		}
		if($("#visit_url").val() == ""){
			alert("服务地址不能为空.");
			$("#visit_url").focus();
			return false;
		}
		if($("#req_host").val() == "" && $("#req_path").val() == ""){
			alert("请求域名与请求路径不能同时为空.");
			return false;
		}
		if($("#req_host").val() != ""){
			var dnspattern = /^([a-zA-Z0-9]([a-zA-Z0-9\-]{0,61}[a-zA-Z0-9])?\.)+[a-zA-Z]{2,6}$/;
			var dnsstr = $("#req_host").val();
			if(!dnspattern.test(dnsstr)){
				alert("请求域名格式不正确!");
				$("#req_host").focus();
				return false;
			}
		}
		if($("#req_path").val() != ""){
			var pathstr = $("#req_path").val();
			if(!(pathstr.length > 0 && pathstr.substring(0,1) == "\/")){
				alert("请求路径格式不正确!");
				$("#req_path").focus();
				return false;
			}
		}
		var atype = $(":radio[name='auth_type']:checked").val();
		if(atype == null || atype == ""){
			alert("请选择认证类型!");
		    return false;
		} else if(atype == "oauth2"){
			var _scopes = $("#_scopes").val();
			if(_scopes == "") {
			    alert("作用域不能为空!");
			    $("#_scopes").focus();
			    return false;
			}
			var _expiration = $("#_expiration").val();
			var reg = /^[0-9]{1,10}$/; 
			if(!reg.test(_expiration)) {
			    alert("有效期必须输入数字型!");
			    $("#_expiration").focus();
			    return false;
			}
		}
		$("#myform").submit();
	}
	function goback(){
		window.location.href = "${ctx}/apis/tolist.do";
	}
</script>
</head>
<body>
	<div class="topbar-wrap white">
		<jsp:include page="../head.jsp" flush="true" />
	</div>
	<div class="container clearfix">
		<div class="sidebar-wrap">
			<jsp:include page="../menu.jsp" flush="true"><jsp:param name="tag" value="alist"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>API列表</span></div>
	        </div>
	        <div class="result-wrap">
	            <div class="result-content">
	                <form action="${ctx}/apis/add.do" method="post" id="myform" name="myform">
	                    <table class="insert-tab" style="width: 100%">
                            <tr>
                                <th><font color="red">*</font>名称：</th>
                                <td><input type="text" id="name" value="${name}" maxlength="20" size="85" name="name" class="common-text"></td>
                            </tr>
                            <tr>
                                <th><font color="red">*</font>服务地址：</th>
                                <td><input type="text" id="visit_url" value="${visit_url}" maxlength="300" size="85" name="visit_url" class="common-text"></td>
                            </tr>
                            <tr>
                                <th rowspan="2"><font color="red">*</font>请求域名：</th>
                                <td>
                                	<input type="text" id="req_host" value="${req_host}" maxlength="50" size="85" name="req_host" class="common-text">&nbsp;
                                	根据该请求域名访问服务地址,与请求路径二选一,  域名形式为：<font color="red">xxx.xx</font>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                	<font color="green">通过KONG根据域名访问服务地址方式为(假设域名为：test.api)： curl -i -X GET --url http://gt.analysys.cn --header "Host: test.api"</font> 
                                </td>
                            </tr>
                            <tr>
                                <th rowspan="2"><font color="red">*</font>请求路径：</th>
                                <td>
                                	<input type="text" id="req_path" value="${req_path}" maxlength="50" size="85" name="req_path" class="common-text">&nbsp;
                                	根据该请求路径访问服务地址,与请求域名二选一,路径形式为：<font color="red">/xxx</font>,  必须以  <font color="red">/</font>  开始
                                </td>
                            </tr>
                            <tr>
                                <td>
                                	<font color="green">通过KONG根据路径访问服务地址方式为(假设路径为：/test)： curl -i -X GET --url http://gt.analysys.cn/test</font>
                                </td>
                            </tr>
                            <tr>
                                <th>服务描述：</th>
                                <td><input type="text" id="remarks" value="${remarks}" maxlength="300" size="85" name="remarks" class="common-text"></td>
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
                                <th rowspan="3">配置参数：</th>
                                <td><font color="red">*</font>作用域(Scopes)：<input type="text" id="_scopes" maxlength="30" size="15" name="_scopes" class="common-text">&nbsp;<input type="checkbox" name="_scopes_need" id="_scopes_need" value="1">&nbsp;访问必须传递&nbsp;<font color="green">(多个Scopes以逗号分隔)</font></td>
                            </tr>
                            <tr class="auth_type _oauth2" style="display: none;">
                                <td>&nbsp;有效期(aToken)：<input type="text" id="_expiration" maxlength="30" size="15" value="7200" name="_expiration" class="common-text">&nbsp;秒&nbsp;<font color="green">(默认7200秒)</font></td>
                            </tr>
                            <tr class="auth_type _oauth2" style="display: none;">
                                <td>授权方式：
                                	<br/>&#12288;&#12288;<input type="checkbox" name="_grant_type" checked disabled="disabled" value="code">&nbsp;授权码模式 (Authorization Code)&nbsp;&nbsp;
                                	<br/>&#12288;&#12288;<input type="checkbox" name="_grant_type" id="pwd_grant_type" value="pwd">&nbsp;密码模式 (Resource Owner Password Credentials)&nbsp;&nbsp;
                                	<br/>&#12288;&#12288;<input type="checkbox" name="_grant_type" id="client_grant_type" value="client">&nbsp;客户端模式 (Client Credentials)
                                </td>
                            </tr>
                            
                            <tr>
                                <th><font color="red">*</font>安全策略：</th>
                                <td><input type="radio" name="" checked value="acl">&nbsp;&nbsp;ACL</td>
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