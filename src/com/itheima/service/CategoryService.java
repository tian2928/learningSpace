package com.itheima.service;

import java.util.List;

import com.itheima.domain.Category;

public interface CategoryService {

	/**
	 * 查询所有类别信息
	 * @return
	 */
	List<Category> findAll();

	/**
	 * 从redis数据库中获得数据
	 * @return
	 */
	String findAllByRedis();

	void addCategory(Category c);

	void delCategory(String cid);

	Category findByCid(String cid);

	void update(Category c);

}
