package com.analysys.kong.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.analysys.kong.model.BaseBean;
import com.analysys.kong.model.User;

public class BaseController {
    Logger logger = Logger.getLogger(BaseController.class);

    public void setUserSysType(HttpServletRequest request, BaseBean bean) {
    	User _user = (User)request.getSession().getAttribute("user_login");
    	if(_user != null){
    		if(!"0".equals(_user.getUser_role())){
    			bean.setSys_type(_user.getSys_type());
    		}
    	}
    }
    
    public User getUser(HttpServletRequest request) {
    	User _user = (User)request.getSession().getAttribute("user_login");
    	return _user;
    }
}
