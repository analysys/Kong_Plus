<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-UpdateConsumers</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<script type="text/javascript" src="${ctx}/js/libs/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript">
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
		$("#myform").attr("action", "${ctx}/consumers/update.do");
		$("#myform").submit();
	}
	function reloadkey() {
		$("#myform").attr("action", "${ctx}/consumers/updatekey.do");
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
	                <form action="" method="post" id="myform" name="myform">
	                    <table class="insert-tab" style="width: 100%">
	                    	<tr>
                                <th width="15%">Consumers ID：</th>
                                <td>${consumers.id}
                                <input type="hidden" id="id" value="${consumers.id}" name="id"></td>
                            </tr>
                            <tr>
                                <th width="15%">Consumers Key：</th>
                                <td>${consumers.keys}</td>
                            </tr>
                            <tr>
                                <th><font color="red">*</font>名称：</th>
                                <td><input type="text" id="name" value="${consumers.cname}" maxlength="30" size="85" name="name" class="common-text"></td>
                            </tr>
                            <tr>
                                <th>备注：</th>
                                <td><input type="text" id="remarks" value="${consumers.remarks}" maxlength="200" size="85" name="remarks" class="common-text"></td>
                            </tr>
                            <tr>
                                <th></th>
                                <td>
                                    <input type="hidden" value="siteConf.inc.php" name="file">
                                    <input type="button" onclick="validates()" value="提交" class="btn btn6 mr10">&nbsp;
                                    <input type="button" onclick="reloadkey()" value="重新分配KEY" class="btn btn6 mr10">&nbsp;
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