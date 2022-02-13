package com.nati.coupons.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebFilter("/rest/loggedin/*")
//@WebFilter("/Login/*")
public class LoginFilter implements Filter {
	
	private static final String RESOURSE_PATH="/unsecured/";
	public void destroy() {
		 //TODO Auto-generated method stub
	}

// ------------------------------ The method that actually filters-------------------------------------

	// This method makes sure that only a logged-in user can access certain URLs
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		String path = ((HttpServletRequest)request).getPathInfo();
		if(path.contains(RESOURSE_PATH)) {
			chain.doFilter(request, response);
			return;
		}
		
		// Checking if the user has a cookie named login status with the value success
		// means - the user has made a successful login
//		Cookie[] cookies = req.getCookies();
//		if (cookies!=null){
//			for (Cookie cookie : cookies) {
//				System.out.println(cookie.getName());
//				System.out.println(cookie.getValue());
//
//				if (cookie.getName().equals("user") && cookie.getValue().equals("Admin")){
//					chain.doFilter(request, response);
//					return;
//				}
//			}
//		}
		
		
		if (req.getSession(false) != null) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpServletResponse res = (HttpServletResponse) response;
        //System.err.println(((HttpServletResponse)res).getStatus()) ;

		res.setStatus(401);
		res.setHeader("ErrorCause", "Couldn't find a login session");

	}
	
// ---------------------------------------------------------------------------------------------------

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
	
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
//		if (req.getSession(false) != null) {
//
//			chain.doFilter(request, response);
//			return;
//		}
//
//		HttpServletResponse res = (HttpServletResponse) response;
//		System.err.println(((HttpServletResponse)res).getStatus()) ;
//		res.setStatus(402);
//		res.setHeader("Error", "no login session found");
//		
//	}
//
//	public void init(FilterConfig fConfig) throws ServletException {
//
//	}
//}