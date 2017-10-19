package com.analysys.kong.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.analysys.kong.model.User;
import com.analysys.kong.service.UserService;
import com.analysys.kong.util.Md5Util;
import com.analysys.kong.util.SystemParam;

@Controller
@RequestMapping("/")
public class IndexController {
	@Autowired
    private UserService userService;

    @RequestMapping("/")
    public String tologin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "login";
    }
    
    @RequestMapping("/login")
    public String login(HttpServletRequest request, ModelMap map) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setOffset(0);
        user.setLimit(1);
        user.setUname(username);
        user.setEpwd(Md5Util.MD5(password));
        user.setStatus(SystemParam.VALID);
        List<User> userList = userService.getListByName(user);
        if(userList != null && userList.size() == 1){
        	recordSession(request,userList);
        	return "index";
        }
        map.put("username", username);
        map.put("index", "1");
        map.put("msg", "用户名/密码错误！");
        return "login";
    }
    
    @RequestMapping("/register")
    public String add(HttpServletRequest request, ModelMap map) {
        String name = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        String sys_type = request.getParameter("sys_type");
        String user_role = request.getParameter("user_role");
        String remarks = request.getParameter("remarks");
        map.put("uname", name);
        map.put("index", "2");
        User user = new User();
        user.setUname(name);
        List<User> _userList = userService.getListByName(user);
        if(_userList != null && _userList.size() > 0){
        	map.put("rmsg", "该用户名已经被占用.");
        	return "login";
        }
        user.setEpwd(Md5Util.MD5(pwd));
        user.setRemarks(remarks);
        user.setSys_type(sys_type);
        user.setUser_role(user_role);
        userService.addUser(user);
        user.setOffset(0);
        user.setLimit(1);
        List<User> userList = userService.getListByUser(user);
        if(userList != null && userList.size() == 1){
        	recordSession(request,userList);
        	return "index";
        }
        map.put("rmsg", "注册失败");
        return "login";
    }
    
    @RequestMapping("/home")
    public String tohome(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "home";
    }
    
    @RequestMapping("/gyapi")
    public String gyapi(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "gyapi";
    }
    
    private void recordSession(HttpServletRequest request, List<User> userList){
    	request.getSession().setAttribute("user_login", userList.get(0));
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    	request.getSession().removeAttribute("user_login");
        request.getSession().invalidate();
        return "login";
    }
}
