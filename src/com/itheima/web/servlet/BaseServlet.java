package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=UTF-8");
			//得到请求方法名
			String method = req.getParameter("method");
			//得到当前类的字节码对象
			Class clazz = this.getClass();
			//根据方法名得到指定的方法对象
			Method m = clazz.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			//执行方法
			String path = (String) m.invoke(this,req,resp);
			if(path!=null){
				req.getRequestDispatcher(path).forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
