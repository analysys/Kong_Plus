package com.analysys.kong.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analysys.kong.dao.ApisDao;
import com.analysys.kong.model.ApiConsumers;
import com.analysys.kong.service.ApisService;
import com.analysys.kong.util.EmailUtil;
import com.analysys.kong.util.SystemParam;

@Service("queryConditionJob")
public class QueryConditionJob {
	Logger logger = Logger.getLogger(QueryConditionJob.class);

	@Resource
	private ApisDao apisDao;
	
	@Autowired
    private ApisService apisService;

	@SuppressWarnings("rawtypes")
	public boolean monitorCallsCount() {
		try {
			List<Map> callNumList = apisDao.getConsumerCallCount();
			logger.info(String.format("======开始统计调用次数[%s]===",callNumList != null ? callNumList.size() : -1));
			if(callNumList != null && callNumList.size() > 0){
				for(Map retMap : callNumList){
					String cnt = retMap.get("cnt") != null ? retMap.get("cnt").toString() : "";
					String call_ok = retMap.get("call_ok") != null ? retMap.get("call_ok").toString() : "";
					String api_id = retMap.get("api_id") != null ? retMap.get("api_id").toString() : "";
					String x_consumer_id = retMap.get("x_consumer_id") != null ? retMap.get("x_consumer_id").toString() : "";
					String max_callcount = retMap.get("max_callcount") != null ? retMap.get("max_callcount").toString() : "";
					String max_callcount_ok = retMap.get("max_callcount_ok") != null ? retMap.get("max_callcount_ok").toString() : "";
					String[] max_callcounts = max_callcount.split(",", 6);
					String[] max_callcounts_ok = max_callcount_ok.split(",", 6);
					if((max_callcounts[5] == null || max_callcounts[5].trim().length() == 0) && (max_callcounts_ok[5] == null || max_callcounts_ok[5].trim().length() == 0))
						continue;
					//总调用次数
					if(StringUtils.isNotEmpty(cnt) && !"0".equals(max_callcounts[5]) && Integer.valueOf(max_callcounts[5]) - Integer.valueOf(cnt) <= Integer.valueOf(SystemParam.KONG_API_CALL_SURPLUS)){
						//发邮件
						String toAddresse = (String)retMap.get("email");
						if(toAddresse == null || toAddresse.trim().length() == 0)
							toAddresse = "";
						String subject = "总调用次数即将用完!";
						String content = generalHtmlMailBody(retMap, true);
						EmailUtil.sendMail(toAddresse, subject, content);
						logger.info(String.format("======[%s(%s)]-[%s(%s)]总调用次数[%s]-[%s]即将用完===",api_id,retMap.get("aname"),x_consumer_id,retMap.get("cname"),max_callcounts[5],cnt));
					}
					//成功调用次数
					if(StringUtils.isNotEmpty(call_ok) && !"0".equals(max_callcounts_ok[5]) && Integer.valueOf(max_callcounts_ok[5]) - Integer.valueOf(call_ok) <= Integer.valueOf(SystemParam.KONG_API_CALL_SURPLUS)){
						//发邮件
						String toAddresse = (String)retMap.get("email");
						if(toAddresse == null || toAddresse.trim().length() == 0)
							toAddresse = "";
						String subject = "成功调用次数即将用完!";
						String content = generalHtmlMailBody(retMap, false);
						EmailUtil.sendMail(toAddresse, subject, content);
						logger.info(String.format("------[%s(%s)]-[%s(%s)]成功调用次数[%s]-[%s]即将用完---",api_id,retMap.get("aname"),x_consumer_id,retMap.get("cname"),max_callcounts_ok[5],cnt));
					}
					if(Integer.valueOf(max_callcounts_ok[5]) <= Integer.valueOf(call_ok)){
						//设置调用次数
						ApiConsumers apis = new ApiConsumers();
				        apis.setApi_id(api_id);
				        apis.setConsumer_id(x_consumer_id);
				        apis.setMax_callcount("0,0,0,0,0," + max_callcounts_ok[5]);
				        try {
				        	logger.info(String.format("======[%s(%s)]-[%s(%s)]重置次数[%s]---",api_id,retMap.get("aname"),x_consumer_id,retMap.get("cname"),max_callcounts[5]));
				        	apisService.updateConsumers(apis);
						} catch (Exception e) {}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	private String generalHtmlMailBody(Map retMap, boolean isTotal){
		StringBuffer buffer = new StringBuffer();
		buffer.append("客户端: ");
		buffer.append(retMap.get("cname")).append("(").append(retMap.get("cr")).append(")");
		buffer.append("<br>调用服务: ");
		buffer.append(retMap.get("aname")).append("(").append(retMap.get("ar")).append(")");
		buffer.append("<br>次数为: ");
		buffer.append(retMap.get("cnt"));
		buffer.append("<br>设置总调用次数为: ");
		if(isTotal){
			buffer.append(retMap.get("max_callcount").toString().split(",", 6)[5]);
		} else {
			buffer.append(retMap.get("max_callcount_ok").toString().split(",", 6)[5]);
		}
		return buffer.toString();
	}
}
