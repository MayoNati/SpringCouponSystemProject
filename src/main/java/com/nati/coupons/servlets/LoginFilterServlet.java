//package com.nati.coupons.servlets;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//
//@WebFilter("/LoginFilterServlet")
//public class LoginFilterServlet implements Filter {
//
//	public void destroy() {
//	}
//
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		
//		HttpServletRequest req = (HttpServletRequest) request;
//		
//		
//		
//		
//		// Checking if the user has a cookie named login status with the value success
//				// means - the user has made a successful login
//				Cookie[] cookies = req.getCookies();
//				if (cookies!=null){
//					for (Cookie cookie : cookies) {
//						if (cookie.getName().equals("loginStatus") && cookie.getValue().equals("success")){
//							chain.doFilter(request, response);
//							return;
//						}
//					}
//				}
//				
////		if (req.getSession(false) != null) {
////
////			chain.doFilter(request, response);
////			return;
////		}
//
//		HttpServletResponse res = (HttpServletResponse) response;
//		res.setStatus(401);
//		res.setHeader("Error", "no login session found");
//		
//	}
//
//	public void init(FilterConfig fConfig) throws ServletException {
//
//	}
//
//}