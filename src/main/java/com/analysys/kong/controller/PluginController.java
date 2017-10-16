package com.analysys.kong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.analysys.kong.model.Apis;
import com.analysys.kong.service.ApisService;
import com.analysys.kong.service.PluginService;
import com.analysys.kong.util.Pager;
import com.analysys.kong.util.SystemParam;

@Controller
@RequestMapping("/plugin")
public class PluginController extends BaseController {
    Logger logger = Logger.getLogger(PluginController.class);

    @Autowired
    private ApisService apisService;
    @Autowired
    private PluginService pluginService;
    
    @RequestMapping("/tolist.do")
    public String tolist(HttpServletRequest request, ModelMap map) {
    	map.put("uid", request.getParameter("uid"));
    	map.put("myuid", getUser(request).getUid());
        return "plugin/list";
    }

    @RequestMapping("/list.do")
    public String list(HttpServletRequest request, ModelMap map) {
    	String name = request.getParameter("name");
        String uid = request.getParameter("uid");
        String pageNo = request.getParameter("pageNo");
        Apis apis = new Apis();
        if (StringUtils.isNotEmpty(name))
        	apis.setAname(name);
        if (StringUtils.isNotEmpty(uid)){
        	apis.setUid(uid);
        }
        setUserSysType(request, apis);
        logger.info(String.format("开始查询用户API: uid[%s] name[%s] sys_type[%s]", uid, name, apis.getSys_type()));
        int total = apisService.getListCountByApis(apis);
        Pager<Apis> pager = new Pager<Apis>();
        pager.setPageSize(SystemParam.PAGENUM);
        pager.setTotalCount(total);
        apis.setOffset(SystemParam.PAGENUM * (pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) - 1 : 0));
        apis.setLimit(SystemParam.PAGENUM);
        List<Apis> apisList = apisService.getListByApis(apis);
        int currentPage = pageNo != null && pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) : 1;
        if (pager.getTotalPage() < currentPage)
            currentPage = pager.getTotalPage() == 0 ? 1 : pager.getTotalPage();
        pager.setCurrentPage(currentPage == 0 ? 1 : currentPage);
        pager.setPageData(apisList);
        map.addAttribute(SystemParam.ROWS, apisList);
        map.addAttribute(SystemParam.TOTAL, total);
        map.addAttribute(SystemParam.PAGE, pager);
        map.addAttribute("myuid", getUser(request).getUid());
        return "plugin/listgrid";
    }

	@RequestMapping(value = "/addblack.do", method = { RequestMethod.POST })
	public @ResponseBody Object addblack(HttpServletRequest request) {
        String id = request.getParameter("id");
        String apiname = request.getParameter("apiname");
        String ips = request.getParameter("ips");
        String extend = request.getParameter("extend");
        Apis apis = new Apis();
    	apis.setId(id);
    	apis.setAname(apiname);
    	apis.setIp_whitelist(ips);
    	apis.setExtend(extend);
    	pluginService.addIpRestriction(apis);
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "200");
    	map.put("msg", "操作成功.");
		return map;
    }
	
	@RequestMapping("/tologlist.do")
    public String tologlist(HttpServletRequest request, ModelMap map) {
    	map.put("uid", request.getParameter("uid"));
    	map.put("myuid", getUser(request).getUid());
        return "plugin/loglist";
    }

    @RequestMapping("/loglist.do")
    public String loglist(HttpServletRequest request, ModelMap map) {
    	String name = request.getParameter("name");
        String uid = request.getParameter("uid");
        String pageNo = request.getParameter("pageNo");
        Apis apis = new Apis();
        if (StringUtils.isNotEmpty(name))
        	apis.setAname(name);
        if (StringUtils.isNotEmpty(uid)){
        	apis.setUid(uid);
        }
        setUserSysType(request, apis);
        logger.info(String.format("开始查询用户API: uid[%s] name[%s] sys_type[%s]", uid, name, apis.getSys_type()));
        int total = apisService.getListCountByApis(apis);
        Pager<Apis> pager = new Pager<Apis>();
        pager.setPageSize(SystemParam.PAGENUM);
        pager.setTotalCount(total);
        apis.setOffset(SystemParam.PAGENUM * (pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) - 1 : 0));
        apis.setLimit(SystemParam.PAGENUM);
        List<Apis> apisList = apisService.getListByApis(apis);
        int currentPage = pageNo != null && pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) : 1;
        if (pager.getTotalPage() < currentPage)
            currentPage = pager.getTotalPage() == 0 ? 1 : pager.getTotalPage();
        pager.setCurrentPage(currentPage == 0 ? 1 : currentPage);
        pager.setPageData(apisList);
        map.addAttribute(SystemParam.ROWS, apisList);
        map.addAttribute(SystemParam.TOTAL, total);
        map.addAttribute(SystemParam.PAGE, pager);
        map.addAttribute("myuid", getUser(request).getUid());
        return "plugin/loglistgrid";
    }

	@RequestMapping(value = "/addlog.do", method = { RequestMethod.POST })
	public @ResponseBody Object addlog(HttpServletRequest request) {
        String id = request.getParameter("id");
        String apiname = request.getParameter("apiname");
        String logpath = request.getParameter("logpath");
        Apis apis = new Apis();
    	apis.setId(id);
    	apis.setAname(apiname);
    	if(StringUtils.isNotEmpty(logpath)){
    		String _logpath = logpath.substring(logpath.lastIndexOf("/"));
    		_logpath = SystemParam.KONG_API_LOG_FILE.substring(0, SystemParam.KONG_API_LOG_FILE.lastIndexOf("/")) + _logpath;
    		apis.setLog_file(_logpath);
    	} else {
    		apis.setLog_file(SystemParam.KONG_API_LOG_FILE);
    	}
    	pluginService.addFilePath(apis);
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "200");
    	map.put("msg", "操作成功.");
		return map;
    }
}
