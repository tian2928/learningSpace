package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findHotProducts() {
		ProductDao pd = new ProductDaoImpl();
		List<Product> list = null;
		try {
			list = pd.findHotProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Product> findNewProducts() {
		ProductDao pd = new ProductDaoImpl();
		List<Product> list = null;
		try {
			list = pd.findNewProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PageBean findByPage(int currentPage, int pageSize, String cid) {
		PageBean<Product> pb = null;
		try {
			ProductDao pd = new ProductDaoImpl();
			int count = pd.count(cid);
			int totalPage = (int) Math.ceil(count*1.0/pageSize);
			List<Product> list = pd.findByPage(currentPage,pageSize,cid);
			
			pb = new PageBean<>();
			pb.setCount(count);
			pb.setCurrentPage(currentPage);
			pb.setList(list);
			pb.setPageSize(pageSize);
			pb.setTotalPage(totalPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pb;
	}

	@Override
	public Product findByPid(String pid) {
		ProductDao pd = new ProductDaoImpl();
		Product p = null;
		try {
			p = pd.findByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public void addProduct(Product p) {
		ProductDao pd = new ProductDaoImpl();
		try {
			pd.addProduct(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
