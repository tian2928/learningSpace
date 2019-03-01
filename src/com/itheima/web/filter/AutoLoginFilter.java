package com.itheima.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;

public class AutoLoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//1、强转
		HttpServletRequest req = (HttpServletRequest)request;
		//2、处理业务
			//判断用户是否已经登录
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute("user");
			if(user==null){
				//自动登录
				Cookie[] cookies = req.getCookies();
				String username = "";
				String password = "";
				for (Cookie cookie : cookies) {
					if("autoLogin".equals(cookie.getName())){
						String[] values = cookie.getValue().split("-");
						username = values[0];
						password = values[1];
						break;
					}
				}
				
				user = new User();
				user.setUsername(username);
				user.setPassword(password);
				
				UserService us = new UserServiceImpl();
				User existUser = us.login(user);
				
				session.setAttribute("user", existUser);
			}
		//3、放行
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
