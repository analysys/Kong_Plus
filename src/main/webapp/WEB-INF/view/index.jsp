<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-Index</title>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
	window.location.href = "${ctx}/home";
</script>
</head>
</html>