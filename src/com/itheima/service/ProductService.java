package com.itheima.service;

import java.util.List;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;

public interface ProductService {

	/**
	 * 查询热门商品
	 * @return
	 */
	List<Product> findHotProducts();

	/**
	 * 查询最新商品
	 * @return
	 */
	List<Product> findNewProducts();

	PageBean findByPage(int currentPage, int pageSize, String cid);

	Product findByPid(String pid);

	void addProduct(Product p);

}
