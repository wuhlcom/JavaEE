package com.iheima.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
 *适配器模式
 */
public class ServletDemo2 extends GenericServlet{

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Hello ServletDemo2");
		arg1.getWriter().write("Hello ServletDemo2,I am here!");
	}

}
