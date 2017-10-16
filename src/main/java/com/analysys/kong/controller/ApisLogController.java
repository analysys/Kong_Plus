package com.analysys.kong.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analysys.kong.model.ApiLogs;
import com.analysys.kong.model.Apis;
import com.analysys.kong.service.ApisService;
import com.analysys.kong.util.CodeUtil;
import com.analysys.kong.util.JsonUtil;
import com.analysys.kong.util.Pager;
import com.analysys.kong.util.SystemParam;

@Controller
@RequestMapping("/apilogs")
public class ApisLogController extends BaseController {
	Logger logger = Logger.getLogger(ApisLogController.class);

	@Autowired
	private ApisService apisService;

	@RequestMapping(value = "/receive", method = { RequestMethod.POST })
	public void dealLog(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			int size = request.getContentLength();
			InputStream is = request.getInputStream();
			byte[] reqBodyBytes = readBytes(is, size);
			String res = new String(reqBodyBytes);
			logger.debug(String.format("收到的Body长度[%s], 内容：%s", size, res));
			ApiLogs apiLogs = JsonUtil.parseRequest(res);
			try {
				Apis apis = new Apis();
		    	apis.setId(apiLogs.getApi_id());
		    	apis.setCall_total("1");
				if("200".equals(apiLogs.getResponse_status()))
					apis.setCall_ok("1");
				apisService.updateApiLog(apis);
			} catch (Exception e) {
				e.printStackTrace();
			}
			apisService.addLogs(apiLogs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {
					readLengthThisTime = is.read(message, readLen, contentLen - readLen);
					if (readLengthThisTime == -1) {
						break;
					}
					readLen += readLengthThisTime;
				}
				return message;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new byte[] {};
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/tochar.do")
    public String tochar(HttpServletRequest request, ModelMap map) {
		map.put("api_id", request.getParameter("api_id"));
		map.put("api_name", request.getParameter("api_name"));
		ApiLogs apiLogs = new ApiLogs();
		apiLogs.setApi_id(request.getParameter("api_id"));
		List<Map> retList = apisService.getStatusCallCount(apiLogs);
		StringBuffer dataJson = new StringBuffer();
		int callcount = 0;
        int callcount_ok = 0;
		String baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(); 
		for(Map maps : retList){
			String url = baseUrl + "/apilogs/tolist.do?api_id=" + request.getParameter("api_id") + "&api_name=" + request.getParameter("api_name") + "&response_status=" + maps.get("rstatus").toString();
			dataJson.append(",").append("{name:'状态码(").append(maps.get("rstatus").toString()).append(")',y:").append(maps.get("scnt").toString()).append(",url:'").append(url).append("'}");
			callcount = callcount + Integer.valueOf(maps.get("scnt").toString());
			if("200".equals(maps.get("rstatus").toString()))
				callcount_ok = callcount_ok + Integer.valueOf(maps.get("scnt").toString());
		}
		for(Map maps : retList)
			maps.put("rstatus", CodeUtil.getCode(maps.get("rstatus").toString()));
		map.put("retList", retList);
		map.put("dataJson", "[" + dataJson.toString().substring(1) + "]");
		map.put("callcount", callcount);
		map.put("callcount_ok", callcount_ok);
		
        List<Map> apisList = apisService.reportConsumerCallCount(apiLogs);
        List<Map> callCountList = new ArrayList<Map>();
        for(Map _maps : apisList){
        	String max_callcount = _maps.get("max_callcount") == null ? "" : _maps.get("max_callcount").toString();
            String max_callcount_ok = _maps.get("max_callcount_ok") == null ? "" : _maps.get("max_callcount_ok").toString();
            int max_callcounts = max_callcount.split(",").length == 6 && !"".equals(max_callcount.split(",")[5]) ? Integer.valueOf(max_callcount.split(",")[5]) : -1;
            int max_callcounts_ok = max_callcount_ok.split(",").length == 6 && !"".equals(max_callcount_ok.split(",")[5]) ? Integer.valueOf(max_callcount_ok.split(",")[5]) : -1;
            Map retMap = new HashMap();
            retMap.put("cname", _maps.get("cname"));
            retMap.put("max_callcounts", max_callcounts);
            retMap.put("max_callcounts_ok", max_callcounts_ok);
            int _callcount = _maps.get("cnt") == null ? 0 : Integer.valueOf(_maps.get("cnt").toString());
            int _callcount_ok = _maps.get("cnt_ok") == null ? 0 : Integer.valueOf(_maps.get("cnt_ok").toString());
            retMap.put("callcount", _callcount);
            retMap.put("callcount_ok", _callcount_ok);
            retMap.put("callrest", max_callcounts == -1 ? -1 : max_callcounts - _callcount);
            retMap.put("callrest_ok", max_callcounts_ok == -1 ? -1 : max_callcounts_ok - _callcount_ok);
            callCountList.add(retMap);
        }
        map.put("callCountList", callCountList);
        return "apis/logchar";
    }
	
	@RequestMapping("/tolist.do")
    public String tolist(HttpServletRequest request, ModelMap map) {
		map.put("api_id", request.getParameter("api_id"));
		map.put("api_name", request.getParameter("api_name"));
		map.put("response_status", request.getParameter("response_status"));
        return "apis/loglist";
    }
	
	@RequestMapping("/list.do")
    public String list(HttpServletRequest request, ModelMap map) {
        String api_id = request.getParameter("api_id");
        String x_consumer_username = request.getParameter("x_consumer_username");
        String response_status = request.getParameter("response_status");
        String pageNo = request.getParameter("pageNo");
        ApiLogs apiLogs = new ApiLogs();
        if (StringUtils.isNotEmpty(api_id))
        	apiLogs.setApi_id(api_id);
        if (StringUtils.isNotEmpty(x_consumer_username))
        	apiLogs.setX_consumer_username(x_consumer_username);
        if (StringUtils.isNotEmpty(response_status))
        	apiLogs.setResponse_status(response_status);
        logger.info(String.format("开始查询APILog: api_id[%s]", api_id));
        int total = apisService.getLogListCountByApis(apiLogs);
        Pager<ApiLogs> pager = new Pager<ApiLogs>();
        pager.setPageSize(SystemParam.PAGENUM * 2);
        pager.setTotalCount(total);
        apiLogs.setOffset(SystemParam.PAGENUM * 2 * (pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) - 1 : 0));
        apiLogs.setLimit(SystemParam.PAGENUM * 2);
        List<ApiLogs> apisLogList = apisService.getLogListByApis(apiLogs);
        int currentPage = pageNo != null && pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) : 1;
        if (pager.getTotalPage() < currentPage)
            currentPage = pager.getTotalPage() == 0 ? 1 : pager.getTotalPage();
        pager.setCurrentPage(currentPage == 0 ? 1 : currentPage);
        pager.setPageData(apisLogList);
        map.addAttribute(SystemParam.ROWS, apisLogList);
        map.addAttribute(SystemParam.TOTAL, total);
        map.addAttribute(SystemParam.PAGE, pager);
        return "apis/loglistgrid";
    }
}
