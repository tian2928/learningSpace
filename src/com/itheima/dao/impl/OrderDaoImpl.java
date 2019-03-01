package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void insertOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "INSERT INTO orders VALUES(?,?,?,0,NULL,NULL,NULL,?)";
		qr.update(sql,order.getOid(),order.getOrdertime(),order.getTotal(),order.getUser().getUid());
	}

	@Override
	public void insertOrderItems(Order order) throws SQLException {
		List<OrderItem> orderItems = order.getOrderItems();
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		for (OrderItem oi : orderItems) {
			String sql = "INSERT INTO orderitem VALUES(?,?,?,?)";
			qr.update(sql,oi.getItemid(),oi.getCount(),oi.getP().getPid(),order.getOid());
		}
	}

	@Override
	public int count(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		long count = (long) qr.query("SELECT COUNT(*) FROM orders WHERE uid=?", new ScalarHandler(),uid);
		return (int) count;
	}

	@Override
	public List<Order> findByPage(int currentPage, int pageSize, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "SELECT * FROM orders WHERE uid=? LIMIT ?,?";
		List<Order> orders = qr.query(sql, new BeanListHandler<Order>(Order.class),uid,(currentPage-1)*pageSize,pageSize);
		//给每个Order对象添加集合数据（List<orderItems>）
		for (Order order : orders) {
			//声明一个集合，用于保存当前order对应的子表数据
			List<OrderItem> orderItems = new ArrayList<>();
			//得到当前订单对应的子表数据
			String sql2 = "SELECT  p.pimage,p.pname,p.shop_price,oi.count FROM orderitem oi,product p WHERE oi.pid=p.pid AND oid=?";
			List<Map<String, Object>> list = qr.query(sql2, new MapListHandler(),order.getOid());
			//循环给每个OrderItem对应赋值，并添加到orderItems集合中
			for (Map<String, Object> map : list) {
				//创建Product对象
				Product p = new Product();
				BeanUtils.populate(p, map); //此时把结果集中前3列数据封装到P对象中
				//创建OrderItem对象
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi, map); //此时把结果集中第4个列数据封装到oi中
				oi.setP(p);
				
				orderItems.add(oi);
			}
			//给当前order对象添加子表数据
			order.setOrderItems(orderItems);
		}
		
		return orders;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "SELECT * FROM orders WHERE oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		
		//声明一个集合，用于保存当前order对应的子表数据
		List<OrderItem> orderItems = new ArrayList<>();
		//得到当前订单对应的子表数据
		String sql2 = "SELECT  p.pimage,p.pname,p.shop_price,oi.count FROM orderitem oi,product p WHERE oi.pid=p.pid AND oid=?";
		List<Map<String, Object>> list = qr.query(sql2, new MapListHandler(),order.getOid());
		//循环给每个OrderItem对应赋值，并添加到orderItems集合中
		for (Map<String, Object> map : list) {
			//创建Product对象
			Product p = new Product();
			BeanUtils.populate(p, map); //此时把结果集中前3列数据封装到P对象中
			//创建OrderItem对象
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi, map); //此时把结果集中第4个列数据封装到oi中
			oi.setP(p);
			
			orderItems.add(oi);
		}
		//给当前order对象添加子表数据
		order.setOrderItems(orderItems);
		return order;
	}

	@Override
	public void updateOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "UPDATE orders SET address=?, NAME=?,telephone=? WHERE oid=?";
		qr.update(sql,order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}

	@Override
	public void updateState(String oid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		qr.update("UPDATE orders SET state=1 WHERE oid=?",oid);
	}

}
