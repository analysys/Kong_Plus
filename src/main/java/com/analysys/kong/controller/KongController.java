package com.analysys.kong.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.analysys.kong.util.HttpUtil;
import com.analysys.kong.util.SystemParam;

@Controller
@RequestMapping("/kong")
public class KongController extends BaseController {
    Logger logger = Logger.getLogger(KongController.class);

    @RequestMapping("/serinfo.do")
    public String serinfo(HttpServletRequest request, ModelMap map) {
    	StringBuffer infoUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
    	infoUrl.append("/");
    	String result = HttpUtil.get(infoUrl.toString(), new HashMap<String, String>());
    	map.put("info", result);
        return "kong/serinfo";
    }
    
    @RequestMapping("/serstatus.do")
    public String serstatus(HttpServletRequest request, ModelMap map) {
    	StringBuffer infoUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
    	infoUrl.append("/status");
    	String result = HttpUtil.get(infoUrl.toString(), new HashMap<String, String>());
    	map.put("info", result);
        return "kong/serstatus";
    }
    
    @RequestMapping("/sercluster.do")
    public String sercluster(HttpServletRequest request, ModelMap map) {
    	List<Map<String, String>> nodeList = new ArrayList<Map<String, String>>();
    	StringBuffer infoUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
    	infoUrl.append("/cluster");
    	String result = HttpUtil.get(infoUrl.toString(), new HashMap<String, String>());
    	JSONObject infoJsonObj = (JSONObject) JSON.parse(result);
    	if(infoJsonObj != null){
    		JSONArray array;
    		try {
    			array = infoJsonObj.getJSONArray("data");
    			for (int i = 0; i < array.size(); i++) {
    				JSONObject jsonobject = array.getJSONObject(i);
    				Map<String, String> _map = new HashMap<String, String>();
    				_map.put("address", jsonobject.getString("address"));
    				_map.put("name", jsonobject.getString("name"));
    				_map.put("status", jsonobject.getString("status"));
    				nodeList.add(_map);
    			}
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
    	}
    	map.put("infoList", nodeList);
        return "kong/sercluster";
    }
    
    @RequestMapping("/serapi.do")
    public String serapi(HttpServletRequest request, ModelMap map) {
    	List<Map<String, String>> nodeList = new ArrayList<Map<String, String>>();
    	StringBuffer infoUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
    	infoUrl.append("/apis");
    	String result = HttpUtil.get(infoUrl.toString(), new HashMap<String, String>());
    	JSONObject infoJsonObj = (JSONObject) JSON.parse(result);
    	if(infoJsonObj != null){
    		JSONArray array;
    		try {
    			array = infoJsonObj.getJSONArray("data");
    			for (int i = 0; i < array.size(); i++) {
    				JSONObject jsonobject = array.getJSONObject(i);
    				Map<String, String> _map = new HashMap<String, String>();
    				_map.put("id", jsonobject.getString("id"));
    				_map.put("name", jsonobject.getString("name"));
    				_map.put("request_host", jsonobject.getString("request_host"));
    				_map.put("upstream_url", jsonobject.getString("upstream_url"));
    				_map.put("preserve_host", jsonobject.getString("preserve_host"));
    				_map.put("request_path", jsonobject.getString("request_path"));
    				_map.put("strip_request_path", jsonobject.getString("strip_request_path"));
    				_map.put("created_at", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(jsonobject.getString("created_at")))));
    				nodeList.add(_map);
    			}
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
    	}
    	map.put("infoList", nodeList);
        return "kong/serapi";
    }
    
    @RequestMapping("/toserdiv.do")
    public String toserdiv(HttpServletRequest request, ModelMap map) {
        return "kong/serdiv";
    }
    
    @RequestMapping("/serdiv.do")
    public @ResponseBody Object serdiv(HttpServletRequest request) {
    	StringBuffer infoUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
    	String adminapi = request.getParameter("adminapi");
    	infoUrl.append(adminapi);
    	String result = HttpUtil.get(infoUrl.toString(), new HashMap<String, String>());
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("datas", result);
        map.put("code", "200");
    	map.put("msg", "操作成功.");
		return map;
    }
}
