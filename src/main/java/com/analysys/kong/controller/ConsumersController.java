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

import com.analysys.kong.model.ApiConsumers;
import com.analysys.kong.model.Consumers;
import com.analysys.kong.service.ConsumersService;
import com.analysys.kong.util.Pager;
import com.analysys.kong.util.SystemParam;
import com.fasterxml.jackson.databind.util.JSONPObject;

@Controller
@RequestMapping("/consumers")
public class ConsumersController extends BaseController {
    Logger logger = Logger.getLogger(ConsumersController.class);

    @Autowired
    private ConsumersService consumersService;
    
    @RequestMapping("/tolist.do")
    public String tolist(HttpServletRequest request, ModelMap map) {
        return "consumer/list";
    }

    @RequestMapping("/list.do")
    public String list(HttpServletRequest request, ModelMap map) {
        String name = request.getParameter("name");
        String pageNo = request.getParameter("pageNo");
        Consumers consumers = new Consumers();
        setUserSysType(request, consumers);
        //consumers.setUid(getUser(request).getUid());
        if (StringUtils.isNotEmpty(name))
        	consumers.setCname(name);
        logger.info(String.format("开始查询消费者: name[%s]", name));
        int total = consumersService.getListCountByConsumers(consumers);
        Pager<Consumers> pager = new Pager<Consumers>();
        pager.setPageSize(SystemParam.PAGENUM);
        pager.setTotalCount(total);
        consumers.setOffset(SystemParam.PAGENUM * (pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) - 1 : 0));
        consumers.setLimit(SystemParam.PAGENUM);
        List<Consumers> consumersList = consumersService.getListByConsumers(consumers);
        int currentPage = pageNo != null && pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) : 1;
        if (pager.getTotalPage() < currentPage)
            currentPage = pager.getTotalPage() == 0 ? 1 : pager.getTotalPage();
        pager.setCurrentPage(currentPage == 0 ? 1 : currentPage);
        pager.setPageData(consumersList);
        map.addAttribute(SystemParam.ROWS, consumersList);
        map.addAttribute(SystemParam.TOTAL, total);
        map.addAttribute(SystemParam.PAGE, pager);
        map.addAttribute("myuid", getUser(request).getUid());
        return "consumer/listgrid";
    }
    
    @RequestMapping(value = "/getconsumer.do", method = { RequestMethod.GET })
	public @ResponseBody Object getconsumer(HttpServletRequest request, ModelMap map) {
    	String id = request.getParameter("id");
    	String atype = request.getParameter("atype");
    	ApiConsumers apis = new ApiConsumers();
        apis.setApi_id(id);
        apis.setAuth_type(atype);
        setUserSysType(request, apis);
    	List<Consumers> consumersList = consumersService.getApiConsumersList(apis);
		return consumersList;
	}
    
    @RequestMapping("/toadd.do")
    public String toadd(HttpServletRequest request, ModelMap map) {
        return "consumer/add";
    }
    
    @RequestMapping("/add.do")
    public String add(HttpServletRequest request, ModelMap map) {
    	String name = request.getParameter("name");
    	String auth_type = request.getParameter("auth_type");
    	String email = request.getParameter("email");
    	String remarks = request.getParameter("remarks");
    	String callback = request.getParameter("_callback");
    	String username = request.getParameter("_username");
    	String password = request.getParameter("_password");
    	if(StringUtils.isEmpty(name) || StringUtils.isEmpty(auth_type)){
         	map.addAttribute("msg", "必选参数不能为空.");
         	return "consumer/add";
    	}
    	map.addAttribute("name", name);
    	map.addAttribute("auth_type", auth_type);
    	map.addAttribute("callback", callback);
    	map.addAttribute("username", username);
    	map.addAttribute("password", password);
    	map.addAttribute("email", email);
		map.addAttribute("remarks", remarks);
		Consumers consumers = new Consumers();
		consumers.setCname(name);
    	Integer retNum = consumersService.getListCountByParam(consumers);
    	if(retNum > 0){
         	map.addAttribute("msg", "该名称已经存在.");
         	return "consumer/add";
    	}
    	consumers.setAuth_type(auth_type);
    	consumers.setExtend(callback);
    	consumers.setClient_id(username);
    	consumers.setClient_secret(password);
    	consumers.setEmail(email);
    	consumers.setRemarks(remarks);
    	consumers.setUid(getUser(request).getUid());
    	String retStr = consumersService.addConsumers(consumers);
    	if(retStr == null){
        	map.addAttribute("msg", "添加失败.");
    	} else {
    		map.addAttribute("retkong", retStr);
        	map.addAttribute("msg", "添加成功.");
    	}
        return "consumer/add";
    }
    
    @RequestMapping("/toupdate.do")
    public String toupdate(HttpServletRequest request, ModelMap map) {
    	String id = request.getParameter("id");
    	Consumers consumers = new Consumers();
    	consumers.setId(id);
        logger.info(String.format("开始查询消费者: id[%s] ", id));
        consumers.setOffset(0);
        consumers.setLimit(1);
        List<Consumers> consumersList = consumersService.getListByConsumers(consumers);
        map.addAttribute("consumers", consumersList.get(0));
        return "consumer/update";
    }
    
    @RequestMapping("/update.do")
    public String update(HttpServletRequest request, ModelMap map) {
    	String id = request.getParameter("id");
    	String name = request.getParameter("name");
    	String remarks = request.getParameter("remarks");
    	if(StringUtils.isEmpty(name)){
         	map.addAttribute("msg", "必选参数不能为空.");
         	return "consumer/update";
    	}
		Consumers consumers = new Consumers();
		consumers.setId(id);
		consumers.setCname(name);
    	Integer retNum = consumersService.getListCountByParam(consumers);
    	if(retNum > 0){
    		consumers.setRemarks(remarks);
    		map.addAttribute("consumers", consumers);
         	map.addAttribute("msg", "该名称已经存在.");
         	return "consumer/update";
    	}
    	consumers.setRemarks(remarks);
    	consumers.setUid(getUser(request).getUid());
    	String retStr = consumersService.updateConsumers(consumers);
    	map.addAttribute("consumers", consumers);
    	map.addAttribute("retkong", retStr);
    	map.addAttribute("msg", "修改成功.");
        return "consumer/update";
    }
    
    @RequestMapping("/updatekey.do")
    public String updatekey(HttpServletRequest request, ModelMap map) {
    	String id = request.getParameter("id");
    	Consumers consumers = new Consumers();
    	consumers.setId(id);
        logger.info(String.format("开始查询消费者: id[%s] ", id));
        consumers.setOffset(0);
        consumers.setLimit(1);
        List<Consumers> consumersList = consumersService.getListByConsumers(consumers);
        consumers = consumersList.get(0);
    	String retStr = consumersService.updateConsumersKey(consumers);
    	map.addAttribute("consumers", consumers);
    	map.addAttribute("retkong", retStr);
    	map.addAttribute("msg", "重新分配成功.");
        return "consumer/update";
    }
    
    @RequestMapping(value = "/delete.do", method = { RequestMethod.POST })
	@ResponseBody
	public JSONPObject delete(HttpServletRequest request) {
    	String id = request.getParameter("id");
    	String name = request.getParameter("name");
    	Consumers consumers = new Consumers();
    	consumers.setId(id);
    	consumers.setCname(name);
    	consumersService.deleteConsumers(consumers);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("code", 200);
    	map.put("msg", "删除成功.");
		return new JSONPObject("", map);
	}
}
