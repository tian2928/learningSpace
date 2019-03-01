package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {

    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //调用业务
        CategoryService cs = new CategoryServiceImpl();
        List<Category> list = cs.findAll();
        //响应数据
        // [{"cid":"1","cname":"手机数码"}，{"cid":"2","cname":"电脑办公"}]
        String json = JSONArray.fromObject(list).toString();

        //从redis数据库中获得数据
        //String json = cs.findAllByRedis();
        resp.getWriter().write(json);
    }

}
