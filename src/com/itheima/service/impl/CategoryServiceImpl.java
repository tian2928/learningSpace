package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CateogryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.JedisUtils;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<Category> findAll() {
        CategoryDao cd = new CateogryDaoImpl();
        List<Category> list = null;
        try {
            list = cd.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String findAllByRedis() {
        String json = null;
        try {
            Jedis jedis = JedisUtils.getJedis();
            json = jedis.get("category_list");

            if (json == null) {//如果redis数据库中没有此数据，则从mysql数据库中查询
                List<Category> list = findAll();
                json = JSONArray.fromObject(list).toString();
                jedis.set("category_list", json);
                System.out.println("从mysql数据库中获得的数据：" + json);
            }
        } catch (Exception e) {
            e.printStackTrace();
            List<Category> list = findAll();
            json = JSONArray.fromObject(list).toString();
        }
        return json;
    }

    @Override
    public void addCategory(Category c) {
        CategoryDao cd = new CateogryDaoImpl();
        try {
            cd.addCategory(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delCategory(String cid) {
        CategoryDao cd = new CateogryDaoImpl();
        try {
            cd.delCategory(cid);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Category findByCid(String cid) {
        CategoryDao cd = new CateogryDaoImpl();
        Category c = null;
        try {
            c = cd.findByCid(cid);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void update(Category c) {
        CategoryDao cd = new CateogryDaoImpl();
        try {
            cd.update(c);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
