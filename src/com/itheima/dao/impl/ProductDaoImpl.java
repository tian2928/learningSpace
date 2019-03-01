package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findHotProducts() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "SELECT * FROM product WHERE is_hot=1 AND pflag=0 LIMIT 9";
		return qr.query(sql , new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findNewProducts() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where pflag=0  order by pdate desc limit 9";
		return qr.query(sql , new BeanListHandler<Product>(Product.class));
	}

	@Override
	public int count(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from product where 1=1";
		List list = new ArrayList<>();
		if(cid!=null){
			sql += " and cid=?";
			list.add(cid);
		}
		long count = (long) qr.query(sql, new ScalarHandler(),list.toArray());
		return (int) count;
	}

	@Override
	public List<Product> findByPage(int currentPage, int pageSize, String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "SELECT * FROM product WHERE 1=1";
		List list = new ArrayList<>();
		if(cid!=null){
			sql += " and cid=?";
			list.add(cid);
		}
		sql+=" limit ?,?";
		list.add((currentPage-1)*pageSize);
		list.add(pageSize);
		return qr.query(sql , new BeanListHandler<Product>(Product.class),list.toArray());
	}

	@Override
	public Product findByPid(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("SELECT * FROM product WHERE pid=?", new BeanHandler<Product>(Product.class),pid);
	}

	@Override
	public void addProduct(Product p) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "INSERT INTO product VALUES(?,?,?,?,?,?,?,?,0,?)";
		qr.update(sql,p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getCid());
	}

}
