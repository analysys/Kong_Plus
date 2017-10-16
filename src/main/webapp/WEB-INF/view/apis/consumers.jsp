<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Portal-ApiConsumers</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<script type="text/javascript" src="${ctx}/js/libs/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<style type="text/css">
	#bg{background-color:#666;position:absolute;z-index:99;left:0;top:0;display:none;width:100%;height:100%;opacity:0.5;filter: alpha(opacity=50);-moz-opacity: 0.5;}
	.box{position:absolute;width:600px;left:50%;height:auto;z-index:100;background-color:#fff;border:1px #ddd solid;padding:1px;}
	.box h2{height:35px;font-size:16px;background-color:#37b7f5;position:relative;padding-left:10px;padding-top:10px;line-height:25px;color:#fff;}
	.box h2 a.close{position:absolute;right:5px;font-size:16px;color:#fff;}
	.box h2 a.adds{position:absolute;right:50px;font-size:16px;color:#fff;}
	.box .list{padding:10px;}
	.box .list li{height:24px;line-height:24px;}
	.box .list li span{margin:0 5px 0 0;font-family:"宋体";font-size:12px;font-weight:400;color:#ddd;}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$(".close").click(function () {
	        $("#bg,.box").css("display", "none");
	    });
		$(".adds").click(function () {
			$("#consumer_id").val($(".previewlist input[name='itemcheck']:checked").val());
			$("#chosecon").text($(".previewlist input[name='itemcheck']:checked").next("span").text());
			$("#consumer_name").val($(".previewlist input[name='itemcheck']:checked").next("span").text());
	        $("#bg,.box").css("display", "none");
	    });
	})
	function addcons() {
		gocancle();
		$("#addConsumerTbl").show();
	}
	function addcalls(cid,max_callcount,max_callcount_ok) {
		gocancle();
		$("#call_consumer_id").val(cid);
		var max_callcounts = new Array(6);
		max_callcounts[0] = "0";
		max_callcounts[1] = "0";
		max_callcounts[2] = "0";
		max_callcounts[3] = "0";
		max_callcounts[4] = "0";
		max_callcounts[5] = "0";
		if(max_callcount != ""){
			_max_callcounts = max_callcount.split(',');
			for (var i=0; i<_max_callcounts.length; i++) {
				if(_max_callcounts[i] != "")
					max_callcounts[i] = _max_callcounts[i];
			}
		}
		$("#max_callcount_second").val(max_callcounts[0]);
		$("#max_callcount_minute").val(max_callcounts[1]);
		$("#max_callcount_hour").val(max_callcounts[2]);
		$("#max_callcount_day").val(max_callcounts[3]);
		$("#max_callcount_month").val(max_callcounts[4]);
		$("#max_callcount_year").val(max_callcounts[5]);
		
		var max_callcounts_ok = new Array(6);
		max_callcounts_ok[0] = "0";
		max_callcounts_ok[1] = "0";
		max_callcounts_ok[2] = "0";
		max_callcounts_ok[3] = "0";
		max_callcounts_ok[4] = "0";
		max_callcounts_ok[5] = "0";
		if(max_callcount_ok != ""){
			_max_callcounts_ok = max_callcount_ok.split(',');
			for (var i=0; i<_max_callcounts_ok.length; i++) {
				if(_max_callcounts_ok[i] != "")
					max_callcounts_ok[i] = _max_callcounts_ok[i];
			}
		}
		$("#max_callcount_second_success").val(max_callcounts_ok[0]);
		$("#max_callcount_minute_success").val(max_callcounts_ok[1]);
		$("#max_callcount_hour_success").val(max_callcounts_ok[2]);
		$("#max_callcount_day_success").val(max_callcounts_ok[3]);
		$("#max_callcount_month_success").val(max_callcounts_ok[4]);
		$("#max_callcount_year_success").val(max_callcounts_ok[5]);
		
		$("#addCallTimeTbl").show();
	}
	function goback(){
		window.location.href = "${ctx}/apis/tolist.do";;
	}
	function gocancle(){
		$("#addCallTimeTbl").hide();
		$("#addConsumerTbl").hide();
		$("#consumer_id").val("");
		$("#consumer_name").val("");
		$("#chosecon").text("");
	}
	function addconsumers(){
		if($("#consumer_id").val() == ""){
			alert("请选择一个消费者!");
			return false;
		} else {
			$.ajax({
	            type: "POST",
	            url: "${ctx}/apis/addconsumers.do",
	            data: {
	            	api_id: $("#api_id").val(), 
	            	consumer_id: $("#consumer_id").val(),
	            	consumer_name: $("#consumer_name").val(),
	            	max_callcount: $("#max_callcount").val(),
	            	max_callcount_ok: $("#max_callcount_ok").val(),
	            	sys_type: $("#sys_type").val(),
	            	remarks: $("#remarks").val()
	            },
	            dataType: "json",
	            success: function(data){
	            	alert(data.msg);
	            	window.location.href = "${ctx}/apis/consumers.do?id=" + $("#api_id").val() + "&cuid=" + $("#cuid").val() + "&atype=" + $("#atype").val();
	            }
	        });
		}
	}
	function delconsumers(ids){
		$.ajax({
            type: "POST",
            url: "${ctx}/apis/delconsumers.do",
            data: {
            	id: ids
            },
            dataType: "json",
            success: function(data){
            	alert(data.msg);
            	window.location.href = "${ctx}/apis/consumers.do?id=" + $("#api_id").val();
            }
        });
	}
	function showConsumer(){
		$(".previewlist").html("");
		$.ajax({
            type: "GET",
            url: "${ctx}/consumers/getconsumer.do?id=" + $("#api_id").val() + "&atype=" + $("#atype").val(),
            dataType: "json",
            success: function(data){
            	var previewHtml = "<div class='result-content'>";
        		previewHtml += "<table class='result-tab' width='100%'><tr><td align='left'>";
				$.each(data,function(idx,item){
					previewHtml += "<input type='radio' name='itemcheck' value='"+item.id+"'/>&nbsp;<span>"+item.cname+"</span>&nbsp;&nbsp;&nbsp;";
				});
				previewHtml += "</td></tr></table></div>";
				$(".previewlist").html(previewHtml);
				$("#ctitle").text("选择消费者");
				showDiv();
            }
        });
	}
	function showDiv(){
		$("#bg").css({
            display: "block", height: $(document).height()
        });
        var $box = $('.box');
        $box.css({
            left: ($("body").width() - $box.width()) / 2 - 20 + "px",
            top: ($(window).height() - $box.height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
	}
	function addconsumercalls(){
		$.ajax({
            type: "POST",
            url: "${ctx}/apis/addcalls.do",
            data: {
            	api_id: $("#api_id").val(), 
            	call_consumer_id: $("#call_consumer_id").val(),
            	max_callcount_second: $("#max_callcount_second").val(),
            	max_callcount_minute: $("#max_callcount_minute").val(),
            	max_callcount_hour: $("#max_callcount_hour").val(),
            	max_callcount_day: $("#max_callcount_day").val(),
            	max_callcount_month: $("#max_callcount_month").val(),
            	max_callcount_year: $("#max_callcount_year").val()
            },
            dataType: "json",
            success: function(data){
            	alert(data.msgs);
            	window.location.href = "${ctx}/apis/consumers.do?id=" + $("#api_id").val() + "&cuid=" + $("#cuid").val() + "&atype=" + $("#atype").val();
            }
        });
	}
	function addconsumercalls_ok(){
		$.ajax({
            type: "POST",
            url: "${ctx}/apis/addcalls.do",
            data: {
            	api_id: $("#api_id").val(),
            	flag: "addOk",
            	call_consumer_id: $("#call_consumer_id").val(),
            	max_callcount_second: $("#max_callcount_second_success").val(),
            	max_callcount_minute: $("#max_callcount_minute_success").val(),
            	max_callcount_hour: $("#max_callcount_hour_success").val(),
            	max_callcount_day: $("#max_callcount_day_success").val(),
            	max_callcount_month: $("#max_callcount_month_success").val(),
            	max_callcount_year: $("#max_callcount_year_success").val()
            },
            dataType: "json",
            success: function(data){
            	alert(data.msgs);
            	window.location.href = "${ctx}/apis/consumers.do?id=" + $("#api_id").val() + "&cuid=" + $("#cuid").val() + "&atype=" + $("#atype").val();
            }
        });
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
	        		<input type="hidden" id="api_id" value="${apiid}" name="api_id" />
	        		<input type="hidden" id="consumer_id" name="consumer_id" value="" />
	        		<input type="hidden" id="consumer_name" name="consumer_name" value="" />
	        		<input type="hidden" id="cuid" name="cuid" value="${cuid}" />
	        		<input type="hidden" id="atype" name="atype" value="${atype}" />
		            <table class="search-tab">
		                 <tr>
		                     <td nowrap>
		                     	<c:if test="${add == '1'}">
									<input class="btn btn6" name="sub1" value="添加消费者" type="button" onclick="addcons();">
								</c:if>
		                     	<input type="button" onclick="goback()" value="返回" class="btn btn2 mr10">&nbsp;&nbsp;
		                     	<font color="red">${msg}</font>
		                     </td>
		                 </tr>
		            </table><br/>
	        		<table class="insert-tab" id="addConsumerTbl" style="width: 100%; display: none;">
                          <tr>
                              <th><font color="red">*</font>消费者：</th>
                              <td><span id="chosecon"></span>&nbsp;<input type="button" onclick="showConsumer()" value="选择消费者" class="btn btn6 mr10"></td>
                          </tr>
                          <tr>
                              <th><font color="red">*</font>分配给：</th>
                              <td>
							  	<select name="sys_type" id="sys_type" style="height: 27px;width: 170px;">
                                    <option value="1">方舟</option>
                                    <option value="2">官网</option>
                                    <option value="3">千帆</option>
                                    <option value="4">博阅</option>
                                    <option value="5">数据</option>
                                </select>
							  </td>
                          </tr>
                          <tr>
                              <th>备注：</th>
                              <td><input type="text" id="remarks" value="${remarks}" maxlength="500" size="85" name="remarks" class="common-text"></td>
                          </tr>
                          <tr>
                              <th>调用次数(总共)：</th>
                              <td><input type="text" id="max_callcount" value="${max_callcount}" maxlength="500" size="85" name="max_callcount" class="common-text"></td>
                          </tr>
                          <tr>
                              <th>调用次数(成功)：</th>
                              <td><input type="text" id="max_callcount_ok" value="${max_callcount_ok}" maxlength="500" size="85" name="max_callcount_ok" class="common-text"></td>
                          </tr>
                          <tr>
                              <th></th>
                              <td>
                                  <input type="hidden" value="siteConf.inc.php" name="file">
                                  <input type="button" onclick="addconsumers()" value="提交" class="btn btn6 mr10">
                                  <input type="button" onclick="gocancle()" value="取消" class="btn btn6 mr10">&nbsp;&nbsp;
                                  <font color="red">${msg}</font>
                              </td>
                          </tr>
                    </table>
                    <table class="insert-tab" id="addCallTimeTbl" style="width: 100%; display: none;">
                          <tr>
                              <td width="50%">
                                  <table class="insert-tab" style="width: 100%">
			                          <tr>
			                              <th width="15%" rowspan="6" align="right">总体次数：<input type="hidden" id="call_consumer_id" name="call_consumer_id" value="" /></th>
			                              <td><input type="text" id="max_callcount_second" maxlength="50" size="25" name="max_callcount_second" class="common-text">次 / 秒</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_minute" maxlength="50" size="25" name="max_callcount_minute" class="common-text">次 / 分钟</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_hour" maxlength="50" size="25" name="max_callcount_hour" class="common-text">次 / 小时</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_day" maxlength="50" size="25" name="max_callcount_day" class="common-text">次 / 天</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_month" maxlength="50" size="25" name="max_callcount_month" class="common-text">次 / 月</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_year" maxlength="50" size="25" name="max_callcount_year" class="common-text">次 / 年</td>
			                          </tr>
			                          <tr>
			                              <th></th>
			                              <td>
			                                  <input type="hidden" value="siteConf.inc.php" name="file">
			                                  <input type="button" onclick="addconsumercalls()" value="提交" class="btn btn6 mr10">
			                                  <input type="button" onclick="gocancle();" value="取消" class="btn btn6 mr10">&nbsp;&nbsp;
			                                  <font color="red">${msgs}</font>
			                              </td>
			                          </tr>
			                      </table>
                              </td>
                              <td width="50%">
                                  <table class="insert-tab" style="width: 100%">
			                          <tr>
			                              <th width="15%" rowspan="6" align="right">成功次数：<input type="hidden" id="call_consumer_id" name="call_consumer_id" value="" /></th>
			                              <td><input type="text" id="max_callcount_second_success" maxlength="50" size="25" name="max_callcount_second_success" class="common-text">次 / 秒</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_minute_success" maxlength="50" size="25" name="max_callcount_minute_success" class="common-text">次 / 分钟</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_hour_success" maxlength="50" size="25" name="max_callcount_hour_success" class="common-text">次 / 小时</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_day_success" maxlength="50" size="25" name="max_callcount_day_success" class="common-text">次 / 天</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_month_success" maxlength="50" size="25" name="max_callcount_month_success" class="common-text">次 / 月</td>
			                          </tr>
			                          <tr>
			                              <td><input type="text" id="max_callcount_year_success" maxlength="50" size="25" name="max_callcount_year_success" class="common-text">次 / 年</td>
			                          </tr>
			                          <tr>
			                              <th></th>
			                              <td>
			                                  <input type="hidden" value="siteConf.inc.php" name="file">
			                                  <input type="button" onclick="addconsumercalls_ok()" value="提交" class="btn btn6 mr10">
			                                  <input type="button" onclick="gocancle();" value="取消" class="btn btn6 mr10">&nbsp;&nbsp;
			                                  <font color="red">${msgs}</font>
			                              </td>
			                          </tr>
			                      </table>
                              </td>
                          </tr>
                    </table>
                    <br/>
                    <c:if test="${atype == 'key'}">
                    	<table class="result-tab" style="width: 100%">
							<tr class="table_top">
								<th nowrap><label>&nbsp;</label></th>
								<th nowrap>ID</th>
								<th nowrap>名称</th>
								<th nowrap>API Key</th>
								<th nowrap>调用次数</th>
								<th nowrap>描述</th>
								<th nowrap>添加时间</th>
							</tr>
							<c:if test="${empty rows}">
								<tr>
									<td colspan="6" >无符合条件的记录</td>
								</tr>
							</c:if>
							<c:forEach items="${rows}" var="item" varStatus="ges">
								<tr>
									<td nowrap width="3%"><label>${ges.count}</label></td>
									<td nowrap width="5%" align="center">${item.id}</td>
									<td nowrap width="15%" align="left">${item.consumer_name}</td>
									<td nowrap width="15%" align="center">
										<c:if test="${add == '1' or sys_type == item.sys_type}">
											${item.keys}
										</c:if>
										<c:if test="${add == '0' and sys_type != item.sys_type}">
											*** *** *** *** *** ***
										</c:if>
									</td>
									<td nowrap width="10%" align="right">
										<a href="#" onclick="addcalls('${item.consumer_id}','${item.max_callcount}','${item.max_callcount_ok}');">
											${item.max_callcount == "" or item.max_callcount == null ? "-1" : item.max_callcount}<br/>
											${item.max_callcount_ok == "" or item.max_callcount_ok == null ? "-1" : item.max_callcount_ok}
										</a>
									</td>
									<td nowrap width="20%" align="center">${item.remarks}</td>
									<td nowrap width="15%" align="center">${item.createtime}</td>
								</tr>
							</c:forEach>
						</table>
                    </c:if>
                    
                    <c:if test="${atype == 'basic'}">
                    	<table class="result-tab" style="width: 100%">
							<tr class="table_top">
								<th nowrap><label>&nbsp;</label></th>
								<th nowrap>ID</th>
								<th nowrap>名称</th>
								<th nowrap>用户名</th>
								<th nowrap>密码</th>
								<th nowrap>调用次数</th>
								<th nowrap>描述</th>
								<th nowrap>添加时间</th>
							</tr>
							<c:if test="${empty rows}">
								<tr>
									<td colspan="7" >无符合条件的记录</td>
								</tr>
							</c:if>
							<c:forEach items="${rows}" var="item" varStatus="ges">
								<tr>
									<td nowrap width="3%"><label>${ges.count}</label></td>
									<td nowrap width="5%" align="center">${item.id}</td>
									<td nowrap width="15%" align="left">${item.consumer_name}</td>
									<c:if test="${add == '1' or sys_type == item.sys_type}">
										<td nowrap width="15%" align="center">${item.client_id}</td>
										<td nowrap width="15%" align="center">${item.client_secret}</td>
									</c:if>
									<c:if test="${add == '0' and sys_type != item.sys_type}">
										<td nowrap width="15%" align="center">*** *** *** *** *** ***</td>
										<td nowrap width="15%" align="center">*** *** *** *** *** ***</td>
									</c:if>
									<td nowrap width="10%" align="right">
										<a href="#" onclick="addcalls('${item.consumer_id}','${item.max_callcount}','${item.max_callcount_ok}');">
											${item.max_callcount == "" or item.max_callcount == null ? "-1" : item.max_callcount}<br/>
											${item.max_callcount_ok == "" or item.max_callcount_ok == null ? "-1" : item.max_callcount_ok}
										</a>
									</td>
									<td nowrap width="20%" align="center">${item.remarks}</td>
									<td nowrap width="15%" align="center">${item.createtime}</td>
								</tr>
							</c:forEach>
						</table>
                    </c:if>
                    
                    <c:if test="${atype == 'oauth2'}">
                    	<table class="result-tab" style="width: 100%">
							<tr class="table_top">
								<th nowrap><label>&nbsp;</label></th>
								<th nowrap>ID</th>
								<th nowrap>名称</th>
								<th nowrap>Provision_Key</th>
								<th nowrap>Client_Id</th>
								<th nowrap>Client_Secret</th>
								<th nowrap>调用次数</th>
								<th nowrap>描述</th>
								<th nowrap>添加时间</th>
							</tr>
							<c:if test="${empty rows}">
								<tr>
									<td colspan="8" >无符合条件的记录</td>
								</tr>
							</c:if>
							<c:forEach items="${rows}" var="item" varStatus="ges">
								<tr>
									<td nowrap width="3%"><label>${ges.count}</label></td>
									<td nowrap width="5%" align="center">${item.id}</td>
									<td nowrap width="15%" align="left">${item.consumer_name}</td>
									<c:if test="${add == '1' or sys_type == item.sys_type}">
										<td nowrap width="15%" align="center">${item.provision_key}</td>
										<td nowrap width="15%" align="center">${item.client_id}</td>
										<td nowrap width="15%" align="center">${item.client_secret}</td>
									</c:if>
									<c:if test="${add == '0' and sys_type != item.sys_type}">
										<td nowrap width="15%" align="center">*** *** *** *** *** ***</td>
										<td nowrap width="15%" align="center">*** *** *** *** *** ***</td>
										<td nowrap width="15%" align="center">*** *** *** *** *** ***</td>
									</c:if>
									<td nowrap width="5%" align="right">
										<a href="#" onclick="addcalls('${item.consumer_id}','${item.max_callcount}','${item.max_callcount_ok}');">
											${item.max_callcount == "" or item.max_callcount == null ? "-1" : item.max_callcount}<br/>
											${item.max_callcount_ok == "" or item.max_callcount_ok == null ? "-1" : item.max_callcount_ok}
										</a>
									</td>
									<td nowrap width="20%" align="center">${item.remarks}</td>
									<td nowrap width="15%" align="center">${item.createtime}</td>
								</tr>
							</c:forEach>
						</table>
                    </c:if>
					
				</div>
	        </div>
	    </div>
	</div>
	<jsp:include page="../bottom.jsp" />
	</body>
	<div id="bg"></div>
	<div class="box" style="display:none; overflow-y: auto;">
	    <h2><span id="ctitle"></span><a href="#" class="adds">添加</a>&nbsp;<a href="#" class="close">关闭</a></h2>
	    <div class="previewlist"></div>
	</div>
</html>
