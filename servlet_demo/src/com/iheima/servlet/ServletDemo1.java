/**
 * 
 */
package com.iheima.servlet;
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author zhilu1234
 *
 */
public class ServletDemo1 implements Servlet {
	
	public ServletDemo1(){
		System.out.println("................ServletDemo1...............");
	}
	
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("................ServletDemo1 init...............");		
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("................ServletDemo1 service...............");		
		System.out.println("Hello ServletDemo1");
		res.getWriter().write("Hello ServletDemo1,I am here!");
	}

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("................ServletDemo1 destroy...............");	
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
