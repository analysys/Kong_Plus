package com.analysys.kong.service;

import com.analysys.kong.model.Apis;

public interface PluginService {
	
	public String addIpRestriction(Apis apis);
	
	public String addFilePath(Apis apis);
}
