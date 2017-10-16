package com.analysys.kong.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.analysys.kong.dao.ApisDao;
import com.analysys.kong.model.ApiConsumers;
import com.analysys.kong.model.ApiLogs;
import com.analysys.kong.model.ApioAuth;
import com.analysys.kong.model.Apis;
import com.analysys.kong.service.ApisService;
import com.analysys.kong.util.HttpUtil;
import com.analysys.kong.util.SystemParam;

@Service("apisService")
public class ApisServiceImpl implements ApisService {

	@Resource
	private ApisDao apisDao;
	
	@Override
	public Integer getListCountByParam(Apis apis) {
		return apisDao.getListCountByParam(apis);
	}

	@Override
	public List<Apis> getListByApis(Apis apis) {
		return apisDao.getListByApis(apis);
	}

	@Override
	public Integer getListCountByApis(Apis apis) {
		return apisDao.getListCountByApis(apis);
	}

	@Override
	public String addApi(Apis apis, ApioAuth apioAuth) {
		String result = "";
		StringBuffer getUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		getUrl.append("/apis/" + apis.getAname());
    	String aresult = HttpUtil.get(getUrl.toString(), new HashMap<String, String>());
    	JSONObject getRetJsonObj = (JSONObject) JSON.parse(aresult);
		String id = getRetJsonObj.getString("id");
		if(StringUtils.isEmpty(id)){
			//添加API
			StringBuffer addUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
			addUrl.append("/apis/");
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode dataNode = mapper.createObjectNode();
			dataNode.put("name", apis.getAname());
			dataNode.put("upstream_url", apis.getVisit_url());
			if(StringUtils.isNotEmpty(apis.getReq_host())){
				dataNode.put("request_host", apis.getReq_host());
			}
			if(StringUtils.isNotEmpty(apis.getReq_path())){
				dataNode.put("request_path", apis.getReq_path());
				dataNode.put("strip_request_path", "true");
			}
	    	result = HttpUtil.postJson(addUrl.toString(), new HashMap<String, String>(), dataNode);
	    	JSONObject addRetJsonObj = (JSONObject) JSON.parse(result);
			id = addRetJsonObj.getString("id");
		}
		if(StringUtils.isNotEmpty(id)){
			if(SystemParam.keyAuth.equals(apis.getAuth_type()))
				addKeyAuthPlugin(apis);
			if(SystemParam.basicAuth.equals(apis.getAuth_type()))
				addBasicAuthPlugin(apis);
			if(SystemParam.oAuth2.equals(apis.getAuth_type()))
				addoAuth2Plugin(apis, apioAuth);
			//添加白名单
			apis.setWhitelist(UUID.randomUUID().toString().replaceAll("-", ""));
			StringBuffer aclUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
			aclUrl.append("/apis/" + apis.getAname() + "/plugins");
			ObjectMapper _mapper = new ObjectMapper();
			ObjectNode _dataNode = _mapper.createObjectNode();
			_dataNode.put("name", "acl");
			_dataNode.put("config.whitelist", apis.getWhitelist());
	    	HttpUtil.postJson(aclUrl.toString(), new HashMap<String, String>(), _dataNode);
	    	//默认日志路径
	    	File file = new File(apis.getLog_file());
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) { }
			}
	    	StringBuffer addFilePathUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
			addFilePathUrl.append("/apis/" + apis.getAname() + "/plugins");
			ObjectNode ldataNode = new ObjectMapper().createObjectNode();
			ldataNode.put("name", "file-log");
			ldataNode.put("config.path", apis.getLog_file());
	    	HttpUtil.postJson(addFilePathUrl.toString(), new HashMap<String, String>(), ldataNode);
	    	//写入日志到HTTP
	    	StringBuffer addHttpLogUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
			addHttpLogUrl.append("/apis/" + apis.getAname() + "/plugins");
			ObjectNode hdataNode = new ObjectMapper().createObjectNode();
			hdataNode.put("name", "http-log");
			hdataNode.put("config.http_endpoint", SystemParam.KONG_API_LOG_HTTP);
			hdataNode.put("config.method", "POST");
			hdataNode.put("config.timeout", "1000");
			hdataNode.put("config.keepalive", "3000");
	    	HttpUtil.postJson(addHttpLogUrl.toString(), new HashMap<String, String>(), hdataNode);
	    	//入库
	    	apis.setId(id);
	    	apisDao.insert(apis);
	    	if(SystemParam.oAuth2.equals(apis.getAuth_type())){
	    		apioAuth.setApi_id(apis.getId());
	    		apisDao.addOauth(apioAuth);
	    	}
	    	return result;
		}
		return null;
	}
	
	private void addKeyAuthPlugin(Apis apis){
		//添加Key认证
		StringBuffer keyAuthUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		keyAuthUrl.append("/apis/" + apis.getAname() + "/plugins");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode dataNode = mapper.createObjectNode();
		dataNode.put("name", "key-auth");
    	HttpUtil.postJson(keyAuthUrl.toString(), new HashMap<String, String>(), dataNode);
	}
	
	private void addBasicAuthPlugin(Apis apis){
		//添加Basic认证
		StringBuffer basicAuthUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		basicAuthUrl.append("/apis/" + apis.getAname() + "/plugins");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode dataNode = mapper.createObjectNode();
		dataNode.put("name", "basic-auth");
		dataNode.put("config.hide_credentials", "true");
    	HttpUtil.postJson(basicAuthUrl.toString(), new HashMap<String, String>(), dataNode);
	}

	private void addoAuth2Plugin(Apis apis, ApioAuth apioAuth){
		//添加oAuth2认证
		StringBuffer oAuth2Url = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		oAuth2Url.append("/apis/" + apis.getAname() + "/plugins");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode dataNode = mapper.createObjectNode();
		dataNode.put("name", "oauth2");
		dataNode.put("config.accept_http_if_already_terminated", "true");
		if(StringUtils.isNotEmpty(apioAuth.getScope()))
			dataNode.put("config.scopes", apioAuth.getScope());
		if("1".equals(apioAuth.getScope_need()))
			dataNode.put("config.mandatory_scope", "true");
		if(StringUtils.isNotEmpty(apioAuth.getGrant_type())){
			for(String type : apioAuth.getGrant_type().split(",")){
				if("pwd".equals(type))
					dataNode.put("config.enable_password_grant", "true");
				if("client".equals(type))
					dataNode.put("config.enable_client_credentials", "true");
			}
		}
    	String result = HttpUtil.postJson(oAuth2Url.toString(), new HashMap<String, String>(), dataNode);
    	apioAuth.setExtend(result);
    	JSONObject addRetJsonObj = (JSONObject) JSON.parse(result);
		String plugin_id = addRetJsonObj.getString("id");
		apioAuth.setPlugin_id(plugin_id);
		String provisionStr = addRetJsonObj.getString("config");
		JSONObject provisionJsonObj = (JSONObject) JSON.parse(provisionStr);
		String provision_key = provisionJsonObj.getString("provision_key");
		apioAuth.setProvision_key(provision_key);
		if(StringUtils.isNotEmpty(apioAuth.getGrant_type()))
			apioAuth.setGrant_type("code," + apioAuth.getGrant_type());
	}
	
	@Override
	public String updateApi(Apis apis) {
		//修改API
		StringBuffer updateUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		updateUrl.append("/apis/" + apis.getAname());
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", apis.getAname());
		param.put("upstream_url", apis.getVisit_url());
		if(StringUtils.isNotEmpty(apis.getReq_host())){
			param.put("request_host", apis.getReq_host());
		}
		if(StringUtils.isNotEmpty(apis.getReq_path())){
			param.put("request_path", apis.getReq_path());
			param.put("strip_request_path", "true");
		}
    	String result = HttpUtil.patch(updateUrl.toString(), new HashMap<String, String>(), param);
    	//修改数据库
		apisDao.update(apis);
		return result;
	}

	@Override
	public void deleteApi(Apis apis) {
		//删除API
		StringBuffer deleteUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		deleteUrl.append("/apis/" + apis.getAname());
    	HttpUtil.delete(deleteUrl.toString(), new HashMap<String, String>());
		apisDao.delete(apis);
	}

	@Override
	public List<ApiConsumers> getConsumersByApis(ApiConsumers capi) {
		List<ApiConsumers> retList = apisDao.getConsumersByApis(capi);
		if(SystemParam.oAuth2.equals(capi.getAuth_type())){
			String provision_key = apisDao.getProvisionKeyForoAuth(capi);
			for(ApiConsumers ac : retList)
				ac.setProvision_key(provision_key);
		}
		return retList;
	}
	
	@Override
	public Integer insertConsumers(ApiConsumers capi) {
		//添加到ACL
		StringBuffer aclwUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		aclwUrl.append("/consumers/" + capi.getConsumer_name() + "/acls");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode dataNode = mapper.createObjectNode();
		//白名单
		Apis apis = new Apis();
        apis.setId(capi.getApi_id());
        apis.setOffset(0);
        apis.setLimit(1);
        List<Apis> apiList = getListByApis(apis);
		dataNode.put("group", apiList.get(0).getWhitelist());
    	HttpUtil.postJson(aclwUrl.toString(), new HashMap<String, String>(), dataNode);
		return apisDao.insertConsumers(capi);
	}
	
	@Override
	public Integer updateConsumers(ApiConsumers capi) {
		if(StringUtils.isNotEmpty(capi.getMax_callcount()) && StringUtils.isEmpty(capi.getMax_callcount_ok())){
			//添加Rate Limiting
			Apis apis = new Apis();
			apis.setId(capi.getApi_id());
			apis.setOffset(0);
	        apis.setLimit(1);
	        List<Apis> apisList = getListByApis(apis);
			List<ApiConsumers> retAcList = apisDao.getConsumersByApis(capi);
			for(ApiConsumers ac : retAcList){
				if(capi.getConsumer_id().equals(ac.getConsumer_id())){
					if(StringUtils.isNotEmpty(ac.getExtend())){
						StringBuffer delIpUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
						delIpUrl.append("/apis/" + apisList.get(0).getAname() + "/plugins/" + ac.getExtend());
				    	HttpUtil.delete(delIpUrl.toString(), new HashMap<String, String>());
				    	capi.setExtend("");
					}
					break;
				}
			}
			StringBuffer rateLimitUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
			rateLimitUrl.append("/apis/" + apisList.get(0).getAname() + "/plugins");
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode dataNode = mapper.createObjectNode();
			dataNode.put("name", "rate-limiting");
			dataNode.put("consumer_id", capi.getConsumer_id());
			String[] max_callcounts = capi.getMax_callcount().split(",", 6);
			boolean isDel = false;
			if(StringUtils.isNotEmpty(max_callcounts[0]) && !"0".equals(max_callcounts[0]))
				dataNode.put("config.second", max_callcounts[0]);
			if(StringUtils.isNotEmpty(max_callcounts[1]) && !"0".equals(max_callcounts[1]))
				dataNode.put("config.minute", max_callcounts[1]);
			if(StringUtils.isNotEmpty(max_callcounts[2]) && !"0".equals(max_callcounts[2]))
				dataNode.put("config.hour", max_callcounts[2]);
			if(StringUtils.isNotEmpty(max_callcounts[3]) && !"0".equals(max_callcounts[3]))
				dataNode.put("config.day", max_callcounts[3]);
			if(StringUtils.isNotEmpty(max_callcounts[4]) && !"0".equals(max_callcounts[4]))
				dataNode.put("config.month", max_callcounts[4]);
			if(StringUtils.isNotEmpty(max_callcounts[5]) && !"0".equals(max_callcounts[5]))
				dataNode.put("config.year", max_callcounts[5]);
			if(StringUtils.isNotEmpty(max_callcounts[5]) && "-1".equals(max_callcounts[5]))
				isDel = true;
			if(!isDel){
				String result = HttpUtil.postJson(rateLimitUrl.toString(), new HashMap<String, String>(), dataNode);
		    	JSONObject getRetJsonObj = (JSONObject) JSON.parse(result);
				String id = getRetJsonObj.getString("id");
				if(StringUtils.isNotEmpty(id))
					capi.setExtend(id);
			}
		}
		return apisDao.updateConsumers(capi);
	}

	@Override
	public Integer deleteConsumers(ApiConsumers capi) {
		return apisDao.deleteConsumers(capi);
	}

	@Override
	public Integer addLogs(ApiLogs apiLogs) {
		return apisDao.addLogs(apiLogs);
	}

	@Override
	public List<ApiLogs> getLogListByApis(ApiLogs apiLogs) {
		return apisDao.getLogListByApis(apiLogs);
	}

	@Override
	public Integer getLogListCountByApis(ApiLogs apiLogs) {
		return apisDao.getLogListCountByApis(apiLogs);
	}

	@Override
	public List<ApioAuth> getApioAuthList(ApioAuth apioAuth) {
		return apisDao.getApioAuthList(apioAuth);
	}

	@Override
	public Integer addOauth(ApioAuth apioAuth) {
		return apisDao.addOauth(apioAuth);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getStatusCallCount(ApiLogs apiLogs) {
		return apisDao.getStatusCallCount(apiLogs);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> reportConsumerCallCount(ApiLogs apiLogs) {
		return apisDao.reportConsumerCallCount(apiLogs);
	}

	@Override
	public void updateApiLog(Apis apis) {
		apisDao.update(apis);
	}
}
