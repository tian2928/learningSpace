package com.itheima.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {

	//添加购物车
	public String addCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1、根据pid，得到指定的商品对象
		String pid = req.getParameter("pid");
		int num = Integer.parseInt(req.getParameter("num")); 
		ProductService ps = new ProductServiceImpl();
		Product p = ps.findByPid(pid);
		//2、从session域对象中获得购物车（map）对象
		HttpSession session = req.getSession();
		Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
		if(cart==null){
			cart = new HashMap<>();
		}
		//3、把商品信息添加到购物车（map）
		if(cart.containsKey(p)){   //要保证比较的是pid，需要重写p对象中pid的hashCode和equals方法
			int count = cart.get(p);//获得当前p对象所对应的value值
			num+=count;
		}
		
		cart.put(p, num);
		//4、把购物车添加到session域对象中
		session.setAttribute("cart", cart);
		
		
		/*for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
			Product p = entry.getKey();
			Integer count = entry.getValue();
		}
*/		
		return "/user/cart.jsp";
	}

	//删除购物车商品
	public String delProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Product p = new Product();
		p.setPid(req.getParameter("pid"));
		
		Map<Product, Integer> cart = (Map<Product, Integer>) req.getSession().getAttribute("cart");
		cart.remove(p);
		return "/user/cart.jsp";
	}
	
	//清空购物车
	public String delAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("cart");
		return "/user/cart.jsp";
	}
	
	
	
}
