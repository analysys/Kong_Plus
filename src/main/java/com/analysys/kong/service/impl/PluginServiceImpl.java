package com.analysys.kong.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.analysys.kong.dao.ApisDao;
import com.analysys.kong.model.Apis;
import com.analysys.kong.service.PluginService;
import com.analysys.kong.util.HttpUtil;
import com.analysys.kong.util.SystemParam;

@Service("pluginService")
public class PluginServiceImpl implements PluginService {
	@Resource
	private ApisDao apisDao;

	@Override
	public String addIpRestriction(Apis apis) {
		String result = "";
		if(StringUtils.isNotEmpty(apis.getExtend())){
			StringBuffer delIpUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
			delIpUrl.append("/apis/" + apis.getAname() + "/plugins/" + apis.getExtend());
	    	HttpUtil.delete(delIpUrl.toString(), new HashMap<String, String>());
		}
		Apis _apis = new Apis();
		_apis.setId(apis.getId());
		_apis.setIp_whitelist("");
    	_apis.setExtend("");
		if(StringUtils.isNotEmpty(apis.getIp_whitelist())){
			StringBuffer addIpUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
			addIpUrl.append("/apis/" + apis.getAname() + "/plugins");
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode dataNode = mapper.createObjectNode();
			dataNode.put("name", "ip-restriction");
			dataNode.put("config.whitelist", apis.getIp_whitelist());
	    	result = HttpUtil.postJson(addIpUrl.toString(), new HashMap<String, String>(), dataNode);
	    	JSONObject getRetJsonObj = (JSONObject) JSON.parse(result);
			String id = getRetJsonObj.getString("id");
			if(StringUtils.isNotEmpty(id)){
		    	_apis.setIp_whitelist(apis.getIp_whitelist());
		    	_apis.setExtend(id);
			}
		}
		apisDao.update(_apis);
		return result;
	}
	
	@Override
	public String addFilePath(Apis apis) {
		String result = "";
		File file = new File(apis.getLog_file());
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) { }
		}
		StringBuffer addFilePathUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		addFilePathUrl.append("/apis/" + apis.getAname() + "/plugins");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode dataNode = mapper.createObjectNode();
		dataNode.put("name", "file-log");
		dataNode.put("config.path", apis.getLog_file());
    	result = HttpUtil.putJson(addFilePathUrl.toString(), new HashMap<String, String>(), dataNode);
		apisDao.update(apis);
		return result;
	}
}
