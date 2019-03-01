package com.itheima.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {

	public String findProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//调用业务
		ProductService ps = new ProductServiceImpl();
		List<Product> hotProducts = ps.findHotProducts();//查询热门商品
		List<Product> newProducts = ps.findNewProducts();//查询热门商品
		//响应数据
		/*List arr = new ArrayList<>();
		arr.add(newProducts);
		arr.add(hotProducts);
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"market_price","pdate","is_hot","pdesc","pflag","cid"});
		String json = JSONArray.fromObject(arr, config ).toString();
		resp.getWriter().write(json); //[[{"pid":"1","pname":"dell"}],[{"pid":"3","pname":"ihpone10"}]]
*/
		req.setAttribute("hotProducts", hotProducts);
		req.setAttribute("newProducts", newProducts);
		return "/user/index.jsp";
	}

	//查询当前类别商品的分页数据
	public String findByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//获取请求参数
		int currentPage = 1;
		int pageSize = 12;
		String currPage = req.getParameter("currentPage");
		if(currPage!=null){
			currentPage = Integer.parseInt(currPage);
		}
		String cid = req.getParameter("cid"); //得到当前类别id
		//调用业务
		ProductService ps = new ProductServiceImpl();
		PageBean<Product> pb = ps.findByPage(currentPage,pageSize,cid);
		//分发转向
		req.setAttribute("pb", pb);
		return "/user/product_list.jsp";
	}

	//根据pid查询指定商品对象
	public String findByPid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pid = req.getParameter("pid");
		
		ProductService ps = new ProductServiceImpl();
		Product p = ps.findByPid(pid);
		
		//保存历史记录
		String historyid = organizeId(pid,req); 
		Cookie cookie = new Cookie("historyId", historyid);
		cookie.setPath(req.getContextPath());  //  /products60
		cookie.setMaxAge(Integer.MAX_VALUE); // 设置保存时间
		
		resp.addCookie(cookie); //把Cookie数据写入客户端浏览器
		
		
		req.setAttribute("p", p);
		return "/user/product_info.jsp";
	}

	//组织pid
	/**
	 * 浏览器缓存					url？pid=1                            服务器
	        没有Cookie				pid=1				historyid=1
	       有Cookie，但不是想要的                  pid=1				historyid=1
	   historyid=1				pid=2				historyid=2-1
	   historyid=2-1			pid=3				historyid=3-2-1
	   historyid=3-2-1			pid=2				historyid=2-3-1
	   historyid=2-3-1          pid=4				historyid=4-2-3
	 */
	private String organizeId(String pid, HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		
		if(cookies==null){
			return pid;
		}
		
		Cookie historyCookie = null;
		for (Cookie cookie : cookies) {
			if("historyId".equals(cookie.getName())){
				historyCookie = cookie;
				break;
			}
		}
		
		if(historyCookie==null){
			return pid;
		}
		
		String pids = historyCookie.getValue();  // 2-1
		String[] split = pids.split("-");
		LinkedList llist = new LinkedList<>(Arrays.asList(split));
		
		if(llist.size()<3){
			if(llist.contains(pid)){
				llist.remove(pid);
			}
		}else{
			if(llist.contains(pid)){
				llist.remove(pid);
			}else{
				llist.removeLast();
			}
		}
		llist.addFirst(pid);  // 4 3 1 
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < llist.size(); i++) {
			if(i>0){
				sb.append("-");
			}
			sb.append(llist.get(i)); // 4-3-1
		}
		
		return sb.toString();
	}
	
	
	
	
}
