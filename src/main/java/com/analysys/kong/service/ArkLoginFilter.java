package com.analysys.kong.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.analysys.kong.model.User;

public class ArkLoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String uri = request.getRequestURI();
		if(uri.toLowerCase().indexOf("login") != -1 || uri.toLowerCase().indexOf("register") != -1 || uri.toLowerCase().indexOf("logout") != -1 || uri.toLowerCase().indexOf("index") != -1)
			chain.doFilter(request, response);
		else {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user_login");
			if(user == null){
				RequestDispatcher dispatcher = request.getRequestDispatcher("/");
				dispatcher.forward(request, response);
				return;
			}
			chain.doFilter(request, response);
		}
	}
	
	@Override
	public void destroy() { }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException { }
}
