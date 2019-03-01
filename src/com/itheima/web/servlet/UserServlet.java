package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.UUIDUtils;

import cn.dsna.util.images.ValidateCode;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	
	public String register(HttpServletRequest request,HttpServletResponse response){
		try {
			//获取请求参数
			User user = new User();
			ConvertUtils.register(new DateLocaleConverter(), Date.class); // yyyy-MM-dd
			BeanUtils.populate(user, request.getParameterMap());//此方法可以自动转换4类8种数据类型
			user.setUid(UUIDUtils.getUUID());
			user.setCode(UUIDUtils.getUUID());
			//调用业务
			UserService us = new UserServiceImpl();
			us.regist(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//分发转向
		return "/user/login.jsp";
	}

	//用户激活
	public String active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获得请求参数
		String code = req.getParameter("code");
		//调用业务
		UserService us = new UserServiceImpl();
		us.activeCode(code);
		
		//分发转向
		return "/user/login.jsp";
	}
	
	//用户登录
	public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			//执行验证操作
			String code = req.getParameter("code");
			String  sess_code = (String) req.getSession().getAttribute("sess_code");
			if(!code.equalsIgnoreCase(sess_code)){
				req.setAttribute("msg", "验证码错误！");
				return "/user/login.jsp";
			}
			
			//获得请求参数
			User user = new User();
			BeanUtils.populate(user, req.getParameterMap());
			//调用业务
			UserService us = new UserServiceImpl();
			User existUser = us.login(user);
			
			if(existUser==null){
				req.setAttribute("msg", "用户名或密码错误！");
				return "/user/login.jsp";
			}
			
			if(existUser.getState()==0){
				req.setAttribute("msg", "用户未激活！");
				return "/user/login.jsp";
			}
			
			//************自动登录功能模块***************
			String autoLogin = req.getParameter("autologin");
			
			Cookie cookie = new Cookie("autoLogin", existUser.getUsername()+"-"+existUser.getPassword());
			cookie.setPath(req.getContextPath()); //  /products
			
			if(autoLogin!=null){
				cookie.setMaxAge(Integer.MAX_VALUE);
			}else{
				cookie.setMaxAge(0);
			}
			
			resp.addCookie(cookie);
			
			
			//保存用户到session域对象中
			req.getSession().setAttribute("user", existUser);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//分发转向
		return "/user/index.jsp";
	}

	//用户注销
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("user");
		
		Cookie cookie = new Cookie("autoLogin", "");
		cookie.setPath(req.getContextPath()); //  /products
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		
		return "/user/index.jsp";
	}
	
	//生成验证码
	public void code(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//参数1：宽度   参数2：调试  参数3：字符个数　　参数4：干扰线的条数
		ValidateCode vc = new ValidateCode(125, 35, 4, 19);
		String code = vc.getCode(); //返回验证码字符
		req.getSession().setAttribute("sess_code", code);
		vc.write(resp.getOutputStream());
		
	}
	
	//验证用户是否存在
	public void ckname(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		UserService us = new UserServiceImpl();
		boolean b = us.ckName(username);
		
		resp.getWriter().println(b);  
	}
	
	
	
}
