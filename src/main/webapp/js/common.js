$(document).ready(function() {
	//菜单选中状态
	var _current_href = window.location.href;
	var _menu_li_id = _current_href.slice(_current_href.lastIndexOf('\/')+1,_current_href.lastIndexOf(".do"));
	$(".sidebar-list .selected").removeClass("selected");
	$('#'+_menu_li_id).addClass("selected");


	$('ul.sidebar-list > li').each(function() {
		$(this).find("a").click(function() {
			$(this).next("ul").toggle();
		});
	});
	
	var host = $(".host").val();
	$(".prePage").click(function() {
		var pageNum =  parseInt($(".pageNum").val())-1;
		var check_status = $(":selected").val();
		window.location.href = host+"/errorCheckApp/checkErrorCheckAppInfo.do?pageNumber="+pageNum+"&check_status="+check_status;
	});
	
	$(".nextPage").click(function() {
		var pageNum =  parseInt($(".pageNum").val())+1;
		var check_status = $(":selected").val();
		window.location.href = host+"/errorCheckApp/checkErrorCheckAppInfo.do?pageNumber="+pageNum+"&check_status="+check_status;
	});
	
	$(".goPage").click(function(){
		var pageNum = $(".inputPage").val();
		var check_status = $(":selected").val();
		var pageTotal = $(".pageTotal").val();
		if(isNaN(pageNum)||pageNum<=0||!(/^\d+$/.test(pageNum))){
			 alert("请输入正确的页数！");
			 return false;
		 }else{
			 
			 if(pageNum>pageTotal){
				 alert("您输入的页数超过总页数！");
			 }else{
				 window.location.href = host+"/errorCheckApp/checkErrorCheckAppInfo.do?pageNumber="+pageNum+"&check_status="+check_status;
			 }
		 }
	});
	
	var host = $(".host").val();
	$(".reviewSuccess").click(function(){
		var id = $(".errorCheckAppId").val();
		var check_status = 1;
		var url = host+"/errorCheckApp/reviewErrorCheckAppInfo.do?id="+id+"&check_status="+check_status;
		window.location.href = url;
		return false;
	});
	
	$(".reviewFail").click(function(){
		var id = $(".errorCheckAppId").val();
		var check_status = -1;
		var url = host+"/errorCheckApp/reviewErrorCheckAppInfo.do?id="+id+"&check_status="+check_status;
		window.location.href = url;
		return false;
	});
});