<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var totalpage, cpage, count, outstr, liststep, totalrows;
	cpage = parseInt('${page.currentPage}');
	totalpage = parseInt('${page.totalPage}');
	totalrows = parseInt('${page.totalCount}');
	liststep = 5;
	outstr = "";
	function setpage() {
		if (totalpage > 1) {
			if (cpage == 1) 
				outstr = "<span style='width:54px; height:24px;'></span> ";
			if (cpage > 1) 
				outstr = "<a href='javaScript:browser(cpage-1)' class='next'>上一页</a> ";
			var listbegin;
			var listend;
			if (cpage < liststep) {
				listbegin = 1;
				listend = liststep + 1;
			} else {
				listbegin = cpage - Math.floor(liststep / 2);
				listend = cpage + liststep / 2;
			}
			if (listend > totalpage) 
				listend = totalpage + 1;
			for (count = listbegin; count < listend; count++) {
				if (count != cpage) 
					outstr = outstr + "<a href='javaScript:browser(" + count + ")'>" + count + "<\/a>&nbsp;";
				else 
					outstr = outstr + "<span class='current'>" + count + "</span>&nbsp;";
			}
			if (cpage == totalpage) 
				outstr = outstr + "<span style='width:54px; height:24px;'></span>";
			if (cpage < totalpage) 
				outstr = outstr + "<a href='javaScript:browser(cpage+1)' class='next'>下一页</a>";
			outstr += "&nbsp;<span>共<i>"+totalpage+"</i>页&nbsp&nbsp&nbsp/&nbsp&nbsp&nbsp"+totalrows+"条</span>";
			document.getElementById("setpage").innerHTML = "<div id='setpage'>"+outstr+"</div>";
			outstr = "";
		}
	}
</script>
