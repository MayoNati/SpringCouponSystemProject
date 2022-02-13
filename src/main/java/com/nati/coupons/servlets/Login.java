//package com.nati.coupons.servlets;
//
//import java.io.IOException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Calendar;
//import java.util.Date;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//
//@WebServlet("/Login")
//public class Login extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String user = request.getParameter("User");
//		String password = request.getParameter("Password");
//		
//		String nextPage = null;
//		if (user!=null && user.equals("admin") &&
//			password!=null && password.equals("1234")){
//			
//			Cookie cookie = new Cookie("loginStatus", "success");
//			cookie.setPath("/");
//			response.addCookie(cookie);
//			
//			nextPage = "/CreateCoupon.html";
//		}
//		else{
//			nextPage = "/BadLogin.html";
//		}
//		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPage);
//		dispatcher.forward(request,response);
//	}
//
//}
