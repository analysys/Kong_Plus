<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-Update</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<script type="text/javascript" src="${ctx}/js/libs/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript">
	function validates() {
		$("#myform").submit();
	}
	function goback(){
		window.location.href = "${ctx}/user/tolist.do";
	}
</script>
</head>
<body>
	<div class="topbar-wrap white">
		<jsp:include page="head.jsp" flush="true" />
	</div>
	<div class="container clearfix">
		<div class="sidebar-wrap">
			<jsp:include page="menu.jsp" flush="true"><jsp:param name="tag" value="ulist"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>用户列表</span></div>
	        </div>
	        <div class="result-wrap">
	            <div class="result-content">
	                <form action="${ctx}/user/update.do" method="post" id="myform" name="myform">
	                    <table class="insert-tab" style="width: 100%">
	                    	<tr>
                                <th width="15%">用户ID：</th>
                                <td>${user.uid}
                                <input type="hidden" id="uid" value="${user.uid}" name="uid"></td>
                            </tr>
                            <tr>
                                <th>姓名：</th>
                                <td>${user.uname}
                            	<input type="hidden" id="uname" value="${user.uname}" name="uname"></td>
                            </tr>
                            <tr>
                                <th>备注：</th>
                                <td><input type="text" id="remarks" value="${user.remarks}" maxlength="500" size="85" name="remarks" class="common-text"></td>
                            </tr>
                            <c:if test="${role == '0'}">
                            	<tr>
	                                <th>状态：</th>
	                                <td>
	                                	<select name="status" id="status" style="height: 27px;width: 170px;">
		                                    <option value="0" ${user.status == "0" ? "selected='selected'" : ""}>无效</option>
		                                    <option value="1" ${user.status == "1" ? "selected='selected'" : ""}>有效</option>
		                                </select>
	                                </td>
	                            </tr>
                            </c:if>
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
	<jsp:include page="bottom.jsp" />
</body>
</html>