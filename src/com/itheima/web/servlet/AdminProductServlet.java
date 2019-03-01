package com.itheima.web.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.UUIDUtils;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class AdminProductServlet
 */
public class AdminProductServlet extends BaseServlet {

	public void findByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int currentPage = 1;
		int pageSize = 3;
		String page = req.getParameter("page");
		if(page!=null){
			currentPage = Integer.parseInt(page);
		}
		
		String rows = req.getParameter("rows");
		if(rows!=null){
			pageSize = Integer.parseInt(rows);
		}
		
		ProductService ps = new ProductServiceImpl();
		PageBean<Product> pb = ps.findByPage(currentPage, pageSize, null);
		
		Map<String, Object> map = new HashMap<>();
		map.put("total", pb.getCount());
		map.put("rows", pb.getList());
		
		String json = JSONObject.fromObject(map).toString();
		resp.getWriter().write(json);
	}

	/**
	 * 此方法做2件事：
	 * 	1、保存数据到表中
	 *  2、上传文件
	 */
	public void addProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Product p = new Product();
			Map<String,String> map = new HashMap<>();
			
			//创建一个工厂类
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//通过工厂得到一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request
			List<FileItem> items = upload.parseRequest(req);
			
			for (FileItem fileItem : items) {
				if(fileItem.isFormField()){//普通表单项
					String name = fileItem.getFieldName();
					String value = fileItem.getString("utf-8");
					
					map.put(name, value);
				}else{ //文件上传表单项
					String name = fileItem.getFieldName();
					String filename = fileItem.getName(); //得到文件名
					
					
					//文件上传操作
					//创建目录
					String realPath = this.getServletContext().getRealPath("/products/1");
					File dirStore = new File(realPath);
					if(!dirStore.exists()){
						dirStore.mkdirs();
					}
					
					filename = UUIDUtils.getUUID()+"."+FilenameUtils.getExtension(filename);
					//创建一个文件对象
					File file = new File(realPath+"/"+filename);
					
					//写入磁盘
					fileItem.write(file);
					fileItem.delete();
					
					
					map.put(name, "products/1/"+filename);
				}
			}
			
			
			BeanUtils.populate(p, map);
			p.setPid(UUIDUtils.getUUID());
			p.setPdate(new Date());
			
			//调用业务
			ProductService ps = new ProductServiceImpl();
			ps.addProduct(p);
			
			Map<String, String> msg = new HashMap<>();
			msg.put("msg", "添加成功！");
			String json = JSONObject.fromObject(msg).toString();
			resp.getWriter().write(json); //{"msg":"添加成功！"}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
