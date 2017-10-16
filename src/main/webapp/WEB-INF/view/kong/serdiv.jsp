<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-Serdiv</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<script type="text/javascript" src="${ctx}/js/libs/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
</head>
<body>
	<div class="topbar-wrap white">
		<jsp:include page="../head.jsp" flush="true" />
	</div>
	<div class="container clearfix">
		<div class="sidebar-wrap">
			<jsp:include page="../menu.jsp" flush="true"><jsp:param name="tag" value="serdiv"></jsp:param></jsp:include>
		</div>
		<div class="main-wrap">
	        <div class="crumb-wrap">
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>自定义查询</span></div>
	        </div>
	        <div class="result-wrap" style="overflow: scroll;">
	        	<div class="result-content">
	                <form action="" method="post">
			             <table class="search-tab">
			                 <tr>
                                 <td nowrap>
                                 	KONG Admin API (<font color="red">仅限于GET请求</font>): &nbsp;&nbsp;<input type="text" id="name" maxlength="50" name="name" class="common-text" style="width: 600px; border-top: 0px;border-left: 0px;border-right: 0px;" placeholder="eg: /apis/{api name or id}/plugins" />
                                 </td>
			                     <td nowrap>
			                     	<input class="btn btn2" name="sub1" value="查询" type="button" onclick="showjson();">
			                     </td>
			                 </tr>
			             </table>
			         </form>
	            </div><br/>
	            <pre id="infores"></pre>
	        </div>
	    </div>
	</div>
	<jsp:include page="../bottom.jsp" />
	<script type="text/javascript">
		function showjson(){
			if($("#name").val() == ""){
				alert("输入要查询的AdminApi");
				return false;
			} else {
				if(!($("#name").val().substring(0,1) == "\/")){
					alert("请求AdminApi路径格式不正确!");
					$("#name").focus();
					return false;
				} else {
					$("#infores").html("");
					$.ajax({
						type: "GET",
						url: "${ctx}/kong/serdiv.do",
						data: {adminapi: $("#name").val()},
						dataType: "json",
						success: function(data){
							$("#infores").html(syntaxHighlight(eval("("+data.datas+")")));
		                }
					});
				}
			}
		}
	</script>
	</body>
</html>
