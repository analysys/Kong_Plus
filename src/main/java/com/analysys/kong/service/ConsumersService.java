package com.analysys.kong.service;

import java.util.List;

import com.analysys.kong.model.ApiConsumers;
import com.analysys.kong.model.Consumers;

public interface ConsumersService {
	
	public Integer getListCountByParam(Consumers consumers);
	
	public List<Consumers> getListByConsumers(Consumers consumers);
	
	public List<Consumers> getApiConsumersList(ApiConsumers aconsumers);
	
	public Integer getListCountByConsumers(Consumers consumers);

	public String addConsumers(Consumers consumers);

	public String updateConsumers(Consumers consumers);
	
	public String updateConsumersKey(Consumers consumers);
	
	public void deleteConsumers(Consumers consumers);
}
