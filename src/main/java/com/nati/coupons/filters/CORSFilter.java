
//package com.nati.coupons.login;
//import java.io.IOException;
//import java.io.*;
//import javax.servlet.*;
//import javax.servlet.http.*;
//
//public class CORSFilter implements Filter {
//
//	public CORSFilter() { }
//
//	public void init(FilterConfig fConfig) throws ServletException { }
//
//	public void destroy() {	}
//
//	public void doFilter(
//		ServletRequest request, ServletResponse response, 
//		FilterChain chain) throws IOException, ServletException {
//
//		((HttpServletResponse)response).addHeader(
//			"Access-Control-Allow-Origin", "*"
//		);
//		chain.doFilter(request, response);
//	}
//}


//
package com.nati.coupons.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
/**
 * Servlet Filter implementation class CORSFilter
 */
// Enable it for Servlet 3.x implementations
@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class CORSFilter implements Filter {
 
    /**
     * Default constructor.
     */
    public CORSFilter() {
        // TODO Auto-generated constructor stub
    }
 
    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }
 
    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
 
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        System.out.println("CORSFilter HTTP Request: " + request.getMethod());
        
 
        // Authorize (allow) all domains to consume the content
//        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials","true");
//        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin","http://localhost:4200");
//        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST,DELETE");
//        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers","Origin, Accept, 
//        x-auth-token, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

//        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
      ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin","http://localhost:4200");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST, DELETE");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "Cache-Control, Pragma,Authorization,X-PINGOTHER,Origin, X-Requested-With, Content-Type, Accept, x-auth-token,Access-Control-Request-Method,Access-Control-Request-Headers");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Max-Age", "1728000");

        	
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With,x-auth-token, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        		
        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
 
        // pass the request along the filter chain
        chain.doFilter(request, servletResponse);        

    }
 
    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }
 
}

//
///**
// * 
// */
//package com.nati.coupons.login;
//import java.io.IOException;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerResponseContext;
//import javax.ws.rs.container.ContainerResponseFilter;
//import javax.ws.rs.ext.Provider;
//
//
///**
// * @author vexxnati
// *
// */
//@Provider
//public class CORSFilter implements ContainerResponseFilter {
//
//    @Override
//    public void filter(ContainerRequestContext request,
//            ContainerResponseContext response) throws IOException {
//        response.getHeaders().add("Access-Control-Allow-Origin", "*");
//        response.getHeaders().add("Access-Control-Allow-Headers",
//                "origin, content-type, accept, authorization");
//        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
//        response.getHeaders().add("Access-Control-Allow-Methods",
//                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//    }
//}