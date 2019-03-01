package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.Order;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import com.itheima.utils.PaymentUtil;

/**
 * Servlet implementation class PayServlet
 */
public class PayServlet extends BaseServlet {

	/**
	 * 此方法要做2件事：
	 * 1、更新order表数据，提交收货人信息
	 * 2、完成支付
	 */
	public String pay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*String address = req.getParameter("address");
		String name = req.getParameter("name");
		String telephone = req.getParameter("telephone");
		String oid = req.getParameter("oid");*/
		try {
			Order order = new Order();
			BeanUtils.populate(order, req.getParameterMap());
			
			//更新order表数据，提交收货人信息
			OrderService os = new OrderServiceImpl();
			os.updateOrder(order);
			
			//完成支付
			String p0_Cmd = "Buy";
			String p1_MerId = "10001126856";   // 商户编号
			String p2_Order = order.getOid();  //商户订单号
			String p3_Amt = order.getTotal()+"";  //支付金额
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = "http://localhost:8080/products60/payServlet?method=callback";  //第三方返回结果数据所跳转本网站的路径
			String p9_SAF = "";
			String pa_MP = "";
			String pd_FrpId = req.getParameter("pd_FrpId");  // 支付通道编码(即：用户所选的银行)
			String pr_NeedResponse = "1";
			String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid , p6_Pcat , p7_Pdesc , p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
			
//			resp.sendRedirect("https://www.yeepay.com/app-merchant-proxy/node?p0_Cmd="+p0_Cmd+"&p1_MerId="+p1_MerId+"&");
	
			req.setAttribute("p0_Cmd", p0_Cmd);
			req.setAttribute("p1_MerId", p1_MerId);
			req.setAttribute("p2_Order", p2_Order);
			req.setAttribute("p3_Amt", p3_Amt);
			req.setAttribute("p4_Cur", p4_Cur);
			req.setAttribute("p5_Pid", p5_Pid);
			req.setAttribute("p6_Pcat", p6_Pcat);
			req.setAttribute("p7_Pdesc", p7_Pdesc);
			req.setAttribute("p8_Url", p8_Url);
			req.setAttribute("p9_SAF", p9_SAF);
			req.setAttribute("pa_MP", pa_MP);
			req.setAttribute("pd_FrpId", pd_FrpId);
			req.setAttribute("pr_NeedResponse", pr_NeedResponse);
			req.setAttribute("hmac", hmac);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/user/confirm.jsp";
	}

	//修改订单状态
	public	String callback(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String hmac = req.getParameter("hmac");
		String p1_MerId = req.getParameter("p1_MerId");
		String r0_Cmd = req.getParameter("r0_Cmd");
		String r1_Code = req.getParameter("r1_Code"); // 支付结果
		String r2_TrxId = req.getParameter("r2_TrxId");
		String r3_Amt = req.getParameter("r3_Amt");
		String r4_Cur = req.getParameter("r4_Cur");
		String r5_Pid = req.getParameter("r5_Pid");
		String r6_Order = req.getParameter("r6_Order"); //商户订单号
		String r7_Uid = req.getParameter("r7_Uid");
		String r8_MP = req.getParameter("r8_MP");
		String r9_BType = req.getParameter("r9_BType");
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		boolean verifyCallback = PaymentUtil.verifyCallback(hmac , p1_MerId , r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if(verifyCallback){ //表示返回数据是安全
			if("1".equals(r1_Code)){ //支付成功
				//修改支付状态
				OrderService os = new OrderServiceImpl();
				os.updateState(r6_Order);
			}
		}
		return "/orderServlet?method=findByPage";
	}
	
	

}
