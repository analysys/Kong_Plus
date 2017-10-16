package com.analysys.kong.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.analysys.kong.dao.ApisDao;
import com.analysys.kong.dao.ConsumersDao;
import com.analysys.kong.model.ApiConsumers;
import com.analysys.kong.model.Consumers;
import com.analysys.kong.service.ConsumersService;
import com.analysys.kong.util.HttpUtil;
import com.analysys.kong.util.SystemParam;

@Service("consumersService")
public class ConsumersServiceImpl implements ConsumersService {

	@Resource
	private ConsumersDao consumersDao;
	
	@Resource
	private ApisDao apisDao;
	
	@Override
	public Integer getListCountByParam(Consumers consumers) {
		return consumersDao.getListCountByParam(consumers);
	}

	@Override
	public List<Consumers> getApiConsumersList(ApiConsumers aconsumers) {
		return consumersDao.getApiConsumersList(aconsumers);
	}
	
	@Override
	public List<Consumers> getListByConsumers(Consumers consumers) {
		return consumersDao.getListByConsumers(consumers);
	}

	@Override
	public Integer getListCountByConsumers(Consumers consumers) {
		return consumersDao.getListCountByConsumers(consumers);
	}

	@Override
	public String addConsumers(Consumers consumers) {
		//添加Consumer
		StringBuffer addUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		addUrl.append("/consumers/");
		Map<String, String> param = new HashMap<String, String>();
		param.put("username", consumers.getCname());
    	String result = HttpUtil.post(addUrl.toString(), new HashMap<String, String>(), param);
    	JSONObject addRetJsonObj = (JSONObject) JSON.parse(result);
		String id = addRetJsonObj.getString("id");
		if(StringUtils.isNotEmpty(id)){
			consumers.setId(id);
			if(SystemParam.keyAuth.equals(consumers.getAuth_type()))
				grantKeyToConsumer(consumers);
			if(SystemParam.basicAuth.equals(consumers.getAuth_type()))
				grantUPwdToConsumer(consumers);
			if(SystemParam.oAuth2.equals(consumers.getAuth_type()))
				grantoAuth2ToConsumer(consumers);
	    	return result;
		}
		return null;
	}
	
	private void grantKeyToConsumer(Consumers consumers){
		//分配KEY值
		StringBuffer getUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		getUrl.append("/consumers/" + consumers.getCname() + "/key-auth/");
    	String aresult = HttpUtil.post(getUrl.toString(), new HashMap<String, String>(), new HashMap<String, String>());
    	JSONObject getRetJsonObj = (JSONObject) JSON.parse(aresult);
		String key = getRetJsonObj.getString("key");
		if(StringUtils.isNotEmpty(key)){
			consumers.setKeys(key);
			consumersDao.insert(consumers);
		}
	}
	
	private void grantUPwdToConsumer(Consumers consumers){
		//分配Username/Password值
		StringBuffer getUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		getUrl.append("/consumers/" + consumers.getCname() + "/basic-auth/");
		ObjectMapper _mapper = new ObjectMapper();
		ObjectNode _dataNode = _mapper.createObjectNode();
		_dataNode.put("username", consumers.getClient_id());
		_dataNode.put("password", consumers.getClient_secret());
    	String aresult = HttpUtil.postJson(getUrl.toString(), new HashMap<String, String>(), _dataNode);
    	JSONObject getRetJsonObj = (JSONObject) JSON.parse(aresult);
		String password = getRetJsonObj.getString("password");
		if(StringUtils.isNotEmpty(password)){
			consumers.setExtend(password);
			consumersDao.insert(consumers);
		}
	}
	
	private void grantoAuth2ToConsumer(Consumers consumers){
		//分配client_id/client_secret值
		StringBuffer getUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		getUrl.append("/consumers/" + consumers.getCname() + "/oauth2/");
		ObjectMapper _mapper = new ObjectMapper();
		ObjectNode _dataNode = _mapper.createObjectNode();
		_dataNode.put("name", consumers.getCname() + "_application");
		_dataNode.put("redirect_uri", consumers.getExtend());
    	String aresult = HttpUtil.postJson(getUrl.toString(), new HashMap<String, String>(), _dataNode);
    	JSONObject getRetJsonObj = (JSONObject) JSON.parse(aresult);
    	String client_id = getRetJsonObj.getString("client_id");
		String client_secret = getRetJsonObj.getString("client_secret");
		if(StringUtils.isNotEmpty(client_id) && StringUtils.isNotEmpty(client_secret)){
			consumers.setClient_id(client_id);
			consumers.setClient_secret(client_secret);
			consumersDao.insert(consumers);
		}
	}

	@Override
	public String updateConsumers(Consumers consumers) {
		//修改Consumer
		StringBuffer updateUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		updateUrl.append("/consumers/" + consumers.getCname());
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", consumers.getCname());
    	String result = HttpUtil.patch(updateUrl.toString(), new HashMap<String, String>(), param);
		consumersDao.update(consumers);
		return result;
	}
	
	@Override
	public String updateConsumersKey(Consumers consumers) {
		//分配KEY值
		StringBuffer getUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		getUrl.append("/consumers/" + consumers.getCname() + "/key-auth/");
    	String aresult = HttpUtil.post(getUrl.toString(), new HashMap<String, String>(), new HashMap<String, String>());
    	JSONObject getRetJsonObj = (JSONObject) JSON.parse(aresult);
		String key = getRetJsonObj.getString("key");
		if(StringUtils.isNotEmpty(key)){
			consumers.setKeys(key);
			Consumers _consumers = new Consumers();
			_consumers.setId(consumers.getId());
			_consumers.setKeys(key);
			consumersDao.update(_consumers);
		}
		return "";
	}

	@Override
	public void deleteConsumers(Consumers consumers) {
		//删除Consumer
		StringBuffer deleteUrl = new StringBuffer(SystemParam.KONG_SERVER_API_ADMIN);
		deleteUrl.append("/consumers/" + consumers.getCname());
    	HttpUtil.delete(deleteUrl.toString(), new HashMap<String, String>());
		consumersDao.delete(consumers);
		ApiConsumers apis = new ApiConsumers();
        apis.setConsumer_id(consumers.getId());
		apisDao.deleteConsumers(apis);
	}
}
