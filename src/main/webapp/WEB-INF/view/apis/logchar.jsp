<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-ApiLogList</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<script type="text/javascript" src="${ctx}/js/libs/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
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
	            <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>菜单<span class="crumb-step">&gt;</span><span>API列表</span><span class="crumb-step">&gt;</span>API调用日志</div>
	        </div>
	        <div class="result-wrap">
	            <div class="result-content">
	                <form action="" method="post">
	                	<input type="hidden" id="api_id" value="${api_id}" name="api_id" />
	                	<input type="hidden" id="api_name" value="${api_name}" name="api_name" />
			             <table class="search-tab">
			                 <tr>
			                     <td nowrap>
			                     	<input class="btn btn6" name="sub1" value="返回" type="button" onclick="go_back();">
			                     </td>
			                 </tr>
			             </table>
			         </form>
	            </div><br>
	            <div class="result-content">
					<table class="result-tab" style="width: 100%">
						<tr>
							<td align="left" width="30%">
								<table class="result-tab" style="width: 100%">
									<tr class="table_top">
										<td align="center" colspan="2">总共调用:${callcount} &nbsp;&nbsp;&nbsp; 成功调用:${callcount_ok}</td>
									</tr>
									<tr class="table_top">
										<th nowrap>状态</th>
										<th nowrap>数量</th>
									</tr>
									<c:forEach items="${retList}" var="item" varStatus="ges">
										<tr>
											<td nowrap width="10%" align="left">${item.rstatus}</td>
											<td nowrap width="5%" align="right">${item.scnt}</td>
										</tr>
									</c:forEach>
								</table>
								<br/>
								<table class="result-tab" style="width: 100%">
									<tr class="table_top">
										<th nowrap>消费者</th>
										<th nowrap>调用次数</th>
										<th nowrap>已调用</th>
										<th nowrap>剩余次数</th>
									</tr>
									<c:forEach items="${callCountList}" var="item" varStatus="ges">
										<tr>
											<td rowspan="2">${item.cname}</td>
											<td nowrap>总共: ${item.max_callcounts}&nbsp;</td>
											<td nowrap>${item.callcount}&nbsp;</td>
											<td nowrap>${item.callrest}&nbsp;</td>
										</tr>
										<tr>
											<td nowrap>成功: ${item.max_callcounts_ok}&nbsp;</td>
											<td nowrap>${item.callcount_ok}&nbsp;</td>
											<td nowrap>${item.callrest_ok}&nbsp;</td>
										</tr>
									</c:forEach>
								</table>
							</td>
							<td align="center" width="70%">
								<div id="container" style="min-width:600px;height:600px"></div>
							</td>
						</tr>
					</table>
				</div>
	        </div>
	    </div>
	</div>
	<jsp:include page="../bottom.jsp" />
	<script>
		function go_back(){
			window.location.href = "${ctx}/apis/tolist.do";
		}
		$(function () {
			var dataJson = ${dataJson};
			Highcharts.getOptions().plotOptions.pie.colors = (function () {
		        var colors = [],
		            base = Highcharts.getOptions().colors[0],
		            i;
		        for (i = 0; i < 10; i += 1) {
		            colors.push(Highcharts.Color(base).brighten((i - 3) / 7).get());
		        }
		        return colors;
		    }());
		    $('#container').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: '${api_name}——调用次数占比'
		        },
		        tooltip: {
		            headerFormat: '占比: <br>',
		            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    }
		                },
		                showInLegend: true
		            },
		            series: {
	                     point: {
	                         events: {
	                             click: function () {
	                            	 window.location.href = this.options.url;
	                             }
	                         }
	                     }
		            }
		        },
		        series: [{
		            type: 'pie',
		            data: dataJson
		        }]
		    });
		});
	</script>
	</body>
</html>
