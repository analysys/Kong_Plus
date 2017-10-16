<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="topbar-inner clearfix">
	<div class="topbar-logo-wrap clearfix">
		<ul class="navbar-list clearfix">
			<li><a href="#" onclick="gohome();">首页</a></li>
		</ul>
	</div>
	<div class="top-info-wrap">
		<ul class="top-info-list clearfix">
			<li>
				<a href="#" onclick="changepwd();">修改密码</a>
			</li>
			<li>
				<a href="#" onclick="log_out();">退出</a>
			</li>
		</ul>
	</div>
</div>
<script>
	function log_out(){
		window.location.href = "${ctx}/logout";
	}
	function changepwd(){
		window.location.href = "${ctx}/user/tocpwd.do";
	}
	function gohome(){
		window.location.href = "${ctx}/home";
	}
	function syntaxHighlight(json) {
	    if (typeof json != 'string') {
	        json = JSON.stringify(json, undefined, 2);
	    }
	    json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
	    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
	        var cls = 'number';
	        if (/^"/.test(match)) {
	            if (/:$/.test(match)) {
	                cls = 'key';
	            } else {
	                cls = 'string';
	            }
	        } else if (/true|false/.test(match)) {
	            cls = 'boolean';
	        } else if (/null/.test(match)) {
	            cls = 'null';
	        }
	        return '<span class="' + cls + '">' + match + '</span>';
	    });
	}
</script>
