package com.analysys.kong.controller;

import java.util.ArrayList;
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

import com.analysys.kong.model.ApiConsumers;
import com.analysys.kong.model.ApioAuth;
import com.analysys.kong.model.Apis;
import com.analysys.kong.service.ApisService;
import com.analysys.kong.util.Pager;
import com.analysys.kong.util.SystemParam;
import com.fasterxml.jackson.databind.util.JSONPObject;

@Controller
@RequestMapping("/apis")
public class ApisController extends BaseController {
    Logger logger = Logger.getLogger(ApisController.class);

    @Autowired
    private ApisService apisService;
    
    @RequestMapping("/tolist.do")
    public String tolist(HttpServletRequest request, ModelMap map) {
    	map.put("uid", request.getParameter("uid"));
    	map.put("myuid", getUser(request).getUid());
        return "apis/list";
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
        //setUserSysType(request, apis);
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
        return "apis/listgrid";
    }
    
    @RequestMapping("/toadd.do")
    public String toadd(HttpServletRequest request, ModelMap map) {
        return "apis/add";
    }
    
    @RequestMapping("/add.do")
    public String add(HttpServletRequest request, ModelMap map) {
    	String name = request.getParameter("name");
    	String visit_url = request.getParameter("visit_url");
    	String req_host = request.getParameter("req_host");
    	String req_path = request.getParameter("req_path");
    	String auth_type = request.getParameter("auth_type");
    	String remarks = request.getParameter("remarks");
    	if(StringUtils.isEmpty(name) || StringUtils.isEmpty(visit_url) || StringUtils.isEmpty(auth_type) || (StringUtils.isEmpty(req_host) && StringUtils.isEmpty(req_path))){
         	map.addAttribute("msg", "必选参数不能为空.");
         	return "apis/add";
    	}
    	
    	String scopes = request.getParameter("_scopes");
    	String scopes_need = request.getParameter("_scopes_need");
    	String expiration = request.getParameter("_expiration");
    	String grant_type = "";
    	String[] _grant_type = request.getParameterValues("_grant_type");
    	if(_grant_type != null && _grant_type.length > 0){
    		grant_type = StringUtils.join(_grant_type, ",");
    	}
    	ApioAuth apioAuth = null;
    	if(SystemParam.oAuth2.equals(auth_type)){
    		apioAuth = new ApioAuth();
    		apioAuth.setScope(scopes);
    		apioAuth.setScope_need(scopes_need);
    		apioAuth.setExpiration(expiration);
    		apioAuth.setGrant_type(grant_type);
    	}
    	map.addAttribute("name", name);
		map.addAttribute("visit_url", visit_url);
		map.addAttribute("req_host", req_host);
		map.addAttribute("req_path", req_path);
		map.addAttribute("remarks", remarks);
		map.addAttribute("auth_type", auth_type);
		map.addAttribute("scopes", scopes);
		map.addAttribute("scopes_need", scopes_need);
		map.addAttribute("expiration", expiration);
		map.addAttribute("grant_type", grant_type);
    	Apis apis = new Apis();
    	apis.setAname(name);
    	Integer retNum = apisService.getListCountByParam(apis);
    	if(retNum > 0){
         	map.addAttribute("msg", "该名称已经存在.");
         	return "apis/add";
    	}
    	if(StringUtils.isNotEmpty(req_host)){
    		apis = new Apis();
        	apis.setReq_host(req_host);
        	retNum = apisService.getListCountByParam(apis);
        	if(retNum > 0){
             	map.addAttribute("msg", "该访问DNS已经存在.");
             	return "apis/add";
        	}
    	}
    	if(StringUtils.isNotEmpty(req_path)){
    		apis = new Apis();
        	apis.setReq_path(req_path);
        	retNum = apisService.getListCountByParam(apis);
        	if(retNum > 0){
             	map.addAttribute("msg", "该访问Path已经存在.");
             	return "apis/add";
        	}
    	}
    	apis = new Apis();
    	apis.setAname(name);
    	apis.setVisit_url(visit_url);
    	apis.setReq_host(req_host);
    	apis.setReq_path(req_path);
    	apis.setAuth_type(auth_type);
    	apis.setRemarks(remarks);
    	apis.setUid(getUser(request).getUid());
    	apis.setLog_file(SystemParam.KONG_API_LOG_FILE);
    	String retStr = apisService.addApi(apis, apioAuth);
    	if(retStr == null){
        	map.addAttribute("msg", "添加失败.");
    	} else {
    		map.addAttribute("retkong", retStr);
        	map.addAttribute("msg", "添加成功.");
    	}
        return "apis/add";
    }
    
    @RequestMapping("/toupdate.do")
    public String toupdate(HttpServletRequest request, ModelMap map) {
    	String id = request.getParameter("id");
        Apis apis = new Apis();
        apis.setId(id);
        logger.info(String.format("开始查询用户API: id[%s] ", id));
        apis.setOffset(0);
        apis.setLimit(1);
        List<Apis> apiList = apisService.getListByApis(apis);
        map.addAttribute("apis", apiList.get(0));
        return "apis/update";
    }
    
    @RequestMapping("/update.do")
    public String update(HttpServletRequest request, ModelMap map) {
    	String id = request.getParameter("id");
    	String name = request.getParameter("name");
    	String visit_url = request.getParameter("visit_url");
    	String req_host = request.getParameter("req_host");
    	String req_path = request.getParameter("req_path");
    	String remarks = request.getParameter("remarks");
    	if(StringUtils.isEmpty(name) || StringUtils.isEmpty(visit_url) || (StringUtils.isEmpty(req_host) && StringUtils.isEmpty(req_path))){
         	map.addAttribute("msg", "必选参数不能为空.");
         	return "apis/update";
    	}
    	Apis apis = new Apis();
    	apis.setId(id);
    	apis.setAname(name);
    	Integer retNum = apisService.getListCountByParam(apis);
    	if(retNum > 0){
    		apis.setAname(name);
    		apis.setReq_host(req_host);
        	apis.setReq_path(req_path);
    		apis.setVisit_url(visit_url);
    		apis.setRemarks(remarks);
    		map.addAttribute("apis", apis);
         	map.addAttribute("msg", "该名称已经存在.");
         	return "apis/update";
    	}
    	if(StringUtils.isNotEmpty(req_host)){
    		apis = new Apis();
    		apis.setId(id);
        	apis.setReq_host(req_host);
        	retNum = apisService.getListCountByParam(apis);
        	if(retNum > 0){
        		apis.setAname(name);
        		apis.setReq_host(req_host);
            	apis.setReq_path(req_path);
        		apis.setVisit_url(visit_url);
        		apis.setRemarks(remarks);
        		map.addAttribute("apis", apis);
             	map.addAttribute("msg", "该访问DNS已经存在.");
             	return "apis/update";
        	}
    	}
    	if(StringUtils.isNotEmpty(req_path)){
    		apis = new Apis();
        	apis.setId(id);
        	apis.setReq_path(req_path);
        	retNum = apisService.getListCountByParam(apis);
        	if(retNum > 0){
        		apis.setAname(name);
        		apis.setReq_host(req_host);
            	apis.setReq_path(req_path);
        		apis.setVisit_url(visit_url);
        		apis.setRemarks(remarks);
        		map.addAttribute("apis", apis);
             	map.addAttribute("msg", "该访问Path已经存在.");
             	return "apis/update";
        	}
    	}
    	apis = new Apis();
    	apis.setId(id);
    	apis.setAname(name);
    	apis.setVisit_url(visit_url);
    	apis.setReq_host(req_host);
    	apis.setReq_path(req_path);
    	apis.setRemarks(remarks);
    	apis.setUid(getUser(request).getUid());
    	String retStr = apisService.updateApi(apis);
    	map.addAttribute("retkong", retStr);
    	map.addAttribute("apis", apis);
    	map.addAttribute("msg", "修改成功.");
        return "apis/update";
    }
    
    @RequestMapping(value = "/delete.do", method = { RequestMethod.POST })
	@ResponseBody
	public JSONPObject delete(HttpServletRequest request) {
    	String id = request.getParameter("id");
    	String name = request.getParameter("name");
    	Apis apis = new Apis();
    	apis.setId(id);
    	apis.setAname(name);
    	apisService.deleteApi(apis);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("code", 200);
    	map.put("msg", "删除成功.");
		return new JSONPObject("", map);
	}
    
    @RequestMapping("/consumers.do")
    public String consumers(HttpServletRequest request, ModelMap map) {
        String id = request.getParameter("id");
        String cuid = request.getParameter("cuid");
        String atype = request.getParameter("atype");
        ApiConsumers apis = new ApiConsumers();
        apis.setApi_id(id);
        apis.setAuth_type(atype);
        logger.info(String.format("开始查询用户API的消费者: id[%s]", id));
        List<ApiConsumers> apiConsumersList = apisService.getConsumersByApis(apis);
        map.addAttribute(SystemParam.ROWS, apiConsumersList);
        map.addAttribute("apiid", id);
        map.addAttribute("cuid", cuid);
        map.addAttribute("atype", atype);
        map.addAttribute("sys_type", getUser(request).getSys_type());
        map.addAttribute("add", getUser(request).getUid().equals(cuid) ? "1" : "0");
        return "apis/consumers";
    }
    
    @RequestMapping(value = "/addconsumers.do", method = { RequestMethod.POST })
	public @ResponseBody Object addconsumers(HttpServletRequest request) {
    	Map<String, String> map = new HashMap<String, String>();
    	String api_id = request.getParameter("api_id");
        String consumer_id = request.getParameter("consumer_id");
        String consumer_name = request.getParameter("consumer_name");
        String remarks = request.getParameter("remarks");
        String max_callcount = request.getParameter("max_callcount");
        String max_callcount_ok = request.getParameter("max_callcount_ok");
        String sys_type = request.getParameter("sys_type");
        if(StringUtils.isEmpty(api_id) || StringUtils.isEmpty(consumer_id) || StringUtils.isEmpty(consumer_name)){
         	map.put("msg", "必选参数不能为空.");
         	return map;
    	}
        ApiConsumers apis = new ApiConsumers();
        apis.setApi_id(api_id);
        apis.setConsumer_id(consumer_id);
        apis.setConsumer_name(consumer_name);
        apis.setRemarks(remarks);
        apis.setMax_callcount(",,,,," + max_callcount);
        apis.setMax_callcount_ok(",,,,," + max_callcount_ok);
        apis.setSys_type(sys_type);
        logger.info(String.format("开始添加API[%s]的消费者: consumer_id[%s]", api_id, consumer_id));
        apisService.insertConsumers(apis);
        map.put("code", "200");
    	map.put("msg", "添加成功.");
		return map;
	}
    
    @RequestMapping(value = "/delconsumers.do", method = { RequestMethod.POST })
	public @ResponseBody Object delconsumers(HttpServletRequest request) {
    	String id = request.getParameter("id");
        ApiConsumers apis = new ApiConsumers();
        apis.setId(id);
        logger.info(String.format("开始删除API的消费者: id[%s]", id));
        apisService.deleteConsumers(apis);
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "200");
    	map.put("msg", "删除成功.");
		return map;
	}
    
    @RequestMapping(value = "/addcalls.do", method = { RequestMethod.POST })
	public @ResponseBody Object addcalls(HttpServletRequest request) {
    	Map<String, String> map = new HashMap<String, String>();
    	String api_id = request.getParameter("api_id");
    	String flag = request.getParameter("flag");
        String consumer_id = request.getParameter("call_consumer_id");
        String max_callcount_second = request.getParameter("max_callcount_second");
        String max_callcount_minute = request.getParameter("max_callcount_minute");
        String max_callcount_hour = request.getParameter("max_callcount_hour");
        String max_callcount_day = request.getParameter("max_callcount_day");
        String max_callcount_month = request.getParameter("max_callcount_month");
        String max_callcount_year = request.getParameter("max_callcount_year");
        if(StringUtils.isEmpty(max_callcount_second) || StringUtils.isEmpty(max_callcount_minute) || StringUtils.isEmpty(max_callcount_hour) || StringUtils.isEmpty(max_callcount_day) || StringUtils.isEmpty(max_callcount_month) || StringUtils.isEmpty(max_callcount_year)){
         	map.put("msg", "必选参数不能为空.");
         	return map;
    	}
        List<String> callList = new ArrayList<String>();
        callList.add(StringUtils.isNotEmpty(max_callcount_second)?max_callcount_second:"0");
        callList.add(StringUtils.isNotEmpty(max_callcount_minute)?max_callcount_minute:"0");
        callList.add(StringUtils.isNotEmpty(max_callcount_hour)?max_callcount_hour:"0");
        callList.add(StringUtils.isNotEmpty(max_callcount_day)?max_callcount_day:"0");
        callList.add(StringUtils.isNotEmpty(max_callcount_month)?max_callcount_month:"0");
        callList.add(StringUtils.isNotEmpty(max_callcount_year)?max_callcount_year:"0");
        String max_callcount = StringUtils.join(callList.toArray(), ",");
        ApiConsumers apis = new ApiConsumers();
        apis.setApi_id(api_id);
        apis.setConsumer_id(consumer_id);
        if("addOk".equals(flag))
        	apis.setMax_callcount_ok(max_callcount);
        else 
        	apis.setMax_callcount(max_callcount);
        logger.info(String.format("开始修改API[%s]的消费者[%s]调用次数: flag[%s] max_callcount[%s]", api_id, consumer_id, flag, max_callcount));
        try {
        	apisService.updateConsumers(apis);
		} catch (Exception e) {}
        map.put("code", "200");
    	map.put("msgs", "设置成功.");
		return map;
	}
}
