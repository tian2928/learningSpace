package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.utils.UUIDUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet {

	public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CategoryService cs = new CategoryServiceImpl();
		List<Category> list = cs.findAll();
		
		String json = JSONArray.fromObject(list).toString();

		resp.getWriter().write(json);
	}

	public void addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cname = req.getParameter("cname");
		Category c = new Category();
		c.setCid(UUIDUtils.getUUID());
		c.setCname(cname);
		
		//调用业务
		CategoryService cs = new CategoryServiceImpl();
		cs.addCategory(c);
		
		//响应数据   {"msg":"添加成功"}
		Map<String, String> map = new HashMap<>();
		map.put("msg", "添加成功");
		
		String json = JSONObject.fromObject(map).toString(); //{"msg":"添加成功"}
		resp.getWriter().write(json);;
	}
	
	
	public void delCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cid = req.getParameter("cid");
		
		//调用业务
		CategoryService cs = new CategoryServiceImpl();
		cs.delCategory(cid);
		
		//响应数据   {"msg":"添加成功"}
		Map<String, String> map = new HashMap<>();
		map.put("msg", "删除成功");
		
		String json = JSONObject.fromObject(map).toString(); //{"msg":"添加成功"}
		resp.getWriter().write(json);
	}
	
	

	public void findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cid = req.getParameter("cid");
		CategoryService cs = new CategoryServiceImpl();
		Category c = cs.findByCid(cid);
		
		String json = JSONObject.fromObject(c).toString();
		resp.getWriter().write(json);  //{"cid":1,"cname":"手机数码"}
	}
	
	
	
	public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Category c = new Category();
			BeanUtils.populate(c, req.getParameterMap());
			
			//调用业务
			CategoryService cs = new CategoryServiceImpl();
			cs.update(c);
			
			//响应数据   {"msg":"添加成功"}
			Map<String, String> map = new HashMap<>();
			map.put("msg", "修改成功");
			
			String json = JSONObject.fromObject(map).toString(); //{"msg":"添加成功"}
			resp.getWriter().write(json);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
