package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.OrderDao;
import com.itheima.dao.impl.OrderDaoImpl;
import com.itheima.domain.Order;
import com.itheima.domain.PageBean;
import com.itheima.service.OrderService;

public class OrderServiceImpl implements OrderService {

	@Override
	public void createOrder(Order order) {
		OrderDao od = new OrderDaoImpl();
		try {
			od.insertOrder(order);
			od.insertOrderItems(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PageBean<Order> findByPage(int currentPage, int pageSize, String uid) {
		PageBean<Order> pb = null;
		try {
			OrderDao od = new OrderDaoImpl();
			int count = od.count(uid);
			int totalPage = (int) Math.ceil(count*1.0/pageSize);
			List<Order> list = od.findByPage(currentPage,pageSize,uid);
			
			pb = new PageBean();
			pb.setCount(count);
			pb.setCurrentPage(currentPage);
			pb.setList(list);
			pb.setPageSize(pageSize);
			pb.setTotalPage(totalPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pb;
	}

	@Override
	public Order findByOid(String oid) {
		OrderDao od = new OrderDaoImpl();
		Order o = null;
		try {
			o = od.findByOid(oid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public void updateOrder(Order order) {
		OrderDao od = new OrderDaoImpl();
		try {
			od.updateOrder(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateState(String oid) {
		OrderDao od = new OrderDaoImpl();
		try {
			od.updateState(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
