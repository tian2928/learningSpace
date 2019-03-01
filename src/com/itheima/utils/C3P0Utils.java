package com.itheima.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {
	//�õ�һ�������Դ
	static DataSource ds = new ComboPooledDataSource();
	
	public static DataSource getDataSource(){
		return ds;
	}
	//�õ����ӵķ���
	public static Connection getConn() throws SQLException{
		return ds.getConnection();
	}
	//�ر���Դ
	public static void closeAll(ResultSet rs,Statement stmt,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs= null;
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt= null;
		}
		if(conn!=null){
			try {
				conn.close();//���ĵĹء��Ƿ�ر�ȡ����conn�Ǵ��������ġ�
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn= null;
		}
	}
}
