//获取配置文件信息,即web.xml中的信息
package com.iheima.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletDemo6
 */
public class ServletDemo6 extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private ServletConfig config;	
//	@Override
//	public void init(ServletConfig config) throws ServletException {
//		this.config=config;
//	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
//		String encoding=config.getInitParameter("encoding");//从配置文件中读取信息
		//第二种方法
		 String encoding=this.getServletConfig().getInitParameter("encoding");
		//第三种方法
//		 String encoding=this.getInitParameter("encoding");
		 System.out.println(encoding);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
