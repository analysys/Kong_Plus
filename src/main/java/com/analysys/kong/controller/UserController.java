package com.analysys.kong.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.analysys.kong.model.User;
import com.analysys.kong.service.UserService;
import com.analysys.kong.util.Md5Util;
import com.analysys.kong.util.Pager;
import com.analysys.kong.util.SystemParam;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @RequestMapping("/tolist.do")
    public String tolist(HttpServletRequest request, ModelMap map) {
        return "list";
    }

    @RequestMapping("/list.do")
    public String list(HttpServletRequest request, ModelMap map) {
        String name = request.getParameter("name");
        String pageNo = request.getParameter("pageNo");
        User user = new User();
        if (StringUtils.isNotEmpty(name))
            user.setUname(name);
        setUserSysType(request, user);
        logger.info(String.format("开始查询用户: name[%s] sys_type[%s]", name, user.getSys_type()));
        int total = userService.getListCountByUser(user);
        Pager<User> pager = new Pager<User>();
        pager.setPageSize(SystemParam.PAGENUM);
        pager.setTotalCount(total);
        user.setOffset(SystemParam.PAGENUM * (pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) - 1 : 0));
        user.setLimit(SystemParam.PAGENUM);
        List<User> userList = userService.getListByUser(user);
        int currentPage = pageNo != null && pageNo.trim().length() > 0 ? Integer.valueOf(pageNo) : 1;
        if (pager.getTotalPage() < currentPage)
            currentPage = pager.getTotalPage() == 0 ? 1 : pager.getTotalPage();
        pager.setCurrentPage(currentPage == 0 ? 1 : currentPage);
        pager.setPageData(userList);
        map.addAttribute(SystemParam.ROWS, userList);
        map.addAttribute(SystemParam.TOTAL, total);
        map.addAttribute(SystemParam.PAGE, pager);
        return "listgrid";
    }

    @RequestMapping("/toupdate.do")
    public String toupdate(HttpServletRequest request, ModelMap map) {
        String uid = request.getParameter("uid");
        User user = new User();
        user.setUid(uid);
        user.setOffset(0);
        user.setLimit(1);
        List<User> userList = userService.getListByUser(user);
        User _user = userList.get(0);
        map.addAttribute("user", _user);
        map.addAttribute("role", getUser(request).getUser_role());
        return "update";
    }
    
    @RequestMapping("/tocpwd.do")
    public String tocpwd(HttpServletRequest request, ModelMap map) {
        User user = getUser(request);
        map.addAttribute("user", user);
        return "cpwd";
    }

    @RequestMapping("/update.do")
    public String update(HttpServletRequest request, ModelMap map) {
        String uid = request.getParameter("uid");
        String password = request.getParameter("password");
        String oldpwd = request.getParameter("pwd");
        String remarks = request.getParameter("remarks");
        String status = request.getParameter("status");
        User user = new User();
        user.setUid(uid);
        if (StringUtils.isNotEmpty(oldpwd) && StringUtils.isNotEmpty(password)) {
        	user.setEpwd(Md5Util.MD5(oldpwd));
        	user.setUname(request.getParameter("uname"));
        	List<User> userList = userService.getListByName(user);
            if(userList == null || userList.size() == 0){
                map.addAttribute("user", user);
            	map.addAttribute("msg", "原密码错误");
            	return "cpwd";
            }
            user.setUname(null);
            user.setEpwd(Md5Util.MD5(password));
        }
        if (StringUtils.isNotEmpty(status)) {
        	user.setStatus(status);
        }
        user.setRemarks(remarks);
        logger.info(String.format("开始修改用户: uid[%s] pwd[%s] status[%s] remarks[%s]", uid, password, status, remarks));
        userService.updateUser(user);
        user.setUname(request.getParameter("uname"));
        map.addAttribute("user", user);
        map.addAttribute("code", "200");
        map.addAttribute("msg", "修改成功");
        map.addAttribute("role", getUser(request).getUser_role());
        if (StringUtils.isNotEmpty(oldpwd) && StringUtils.isNotEmpty(password)) {
        	return "cpwd";
        } else {
        	return "update";
        }
    }
}
