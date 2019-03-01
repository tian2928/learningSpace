package com.itheima.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import com.itheima.utils.UUIDUtils;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {

	public String createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1、判断用户是否登录
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "/user/login.jsp";
		}
		//2、创建Order对象，并封装基本数据
		Order order = new Order();
		order.setOid(UUIDUtils.getUUID());
		order.setOrdertime(new Date());
		order.setUser(user);
		
		//3、从session中获得购物车（Map<Product,Integer>）信息
		Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
		//4、循环遍历购物车数据，添加到每个OrderItem对象中，并保存到List集合中
		List<OrderItem> orderItems = new ArrayList<>();
		double total = 0;
		for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
			Product p = entry.getKey();
			Integer count = entry.getValue();
			//创建OrderItem对象，并封装数据
			OrderItem oi = new OrderItem();
			oi.setItemid(UUIDUtils.getUUID());
			oi.setP(p);
			oi.setCount(count);
			//把每个oi对象保存到list集合中
			orderItems.add(oi);
			total+= p.getShop_price()*count;//求总金额
		}
		//5、把list封装到Order对象中
		order.setTotal(total);
		order.setOrderItems(orderItems);
		
		//6、调用业务
		OrderService os = new OrderServiceImpl();
		os.createOrder(order);
		
		req.setAttribute("o", order);
		
		return "/user/order_info.jsp";
	}

	//分页查询我的订单
	public String findByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		
		int currentPage = 1;//设置当前页码
		int pageSize = 3; //每页显示条数
		String currPage = req.getParameter("currentPage");
		if(currPage!=null){
			currentPage = Integer.parseInt(currPage);
		}
		
		OrderService os = new OrderServiceImpl();
		PageBean<Order> pb = os.findByPage(currentPage,pageSize,user.getUid());
		
		req.setAttribute("pb", pb);
		return "/user/order_list.jsp";
	}

	//根据oid查询指定订单
	public String findByOid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		OrderService os = new OrderServiceImpl();
		Order o = os.findByOid(oid);
		
		req.setAttribute("o", o);
		return "/user/order_info.jsp";
	}

	
	

}
