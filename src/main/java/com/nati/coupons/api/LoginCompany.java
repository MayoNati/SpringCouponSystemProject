///**
// * 
// */
//package com.nati.coupons.api;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//
//import com.nati.coupons.beans.UserLoginDetails;
//import com.nati.coupons.exceptions.ApplicationException;
//import com.nati.coupons.exceptions.ExceptionsHandler;
//import com.nati.coupons.logic.CompanyController;
//
///**
// * @author vexxnati
// *
// */
//@Path("/login1")
////Please translate the java obj returned from the server into JSONs
//@Produces(MediaType.APPLICATION_JSON)
//
////The data that comes from the client is a JSON
//@Consumes(MediaType.APPLICATION_JSON)
//public class LoginCompany {
//	
//	
//	
//	//Checks if the username and password match the information in DB if yes return true 
//	@POST
//	@Path("/LoginCompany")
//	public void login(@Context HttpServletRequest request,
//			@Context HttpServletResponse response,
//			UserLoginDetails loginData) throws IOException, ApplicationException, ServletException
//	{
//		System.out.println("Name:"+loginData.getUser()+" Pass:"+loginData.getPassword());
//
//		System.out.println("into login");
//		if(loginData.getUser().equals("Admin") && loginData.getPassword().equals("1234")) {
//			System.out.println("Succses");		
//
//			request.getSession();
//			Cookie cookie=new Cookie("user",loginData.getUser());
//			cookie.setPath("/");
//			response.addCookie(cookie);
//			
//			//System.out.println(response.getStatus());
//		}
//		else {
//			response.setStatus(401);
//			
//			response.getStatus();
//			//System.out.println(response.getStatus());
//			throw new ApplicationException(String.valueOf(response.getStatus()));
//			
//			//response.setStatus(401);
//			//response.getStatus();
//			//System.out.println(response.getStatus());
//
//		}		
//	}
//	
//
////	
////	// -------------------------Checks if customer login details are true----------------------
////
////		@POST
////		@Path("/logincustomer")
////		public void loginCustomer (@Context HttpServletRequest request, 
////				@Context HttpServletResponse response, 
////				UserDetails loginData)  throws Throwable {
////
////			String username = loginData.getUsername();
////			String password = loginData.getPassword();
////
////			CustomerController customerController = new CustomerController();
////
////			boolean isUserLegitimate = customerController.login(username, password);
////
////
////			if (isUserLegitimate) {
////
////				request.getSession();
////				Cookie cookie = new Cookie("customerEmail", loginData.getUsername());
////				cookie.setPath("/");
////				response.addCookie(cookie);
////				
////				HttpServletResponse res = (HttpServletResponse) response;
////				res.setHeader("LoginStatus", "Customer : " + username + ", has logged in successfully");
////			}
////
////		}
//
//		// --------------------------------Checks if company details are valid----------------------------
//
////		@POST
////		@Path("/Userlogin")
////		public void loginCompany (@Context HttpServletRequest request, 
////				@Context HttpServletResponse response, 
////				UserLoginDetails loginData)  throws Throwable {
////
////			String username = loginData.getUser();
////			String password = loginData.getPassword();
////
////			CompanyController companyController = new CompanyController();
////
////			boolean isUserLegitimate = companyController.login(username, password);
////
////
////			if (isUserLegitimate) {
////
////				request.getSession();
////				Cookie cookie = new Cookie("companyName", loginData.getUser());
////				cookie.setPath("/");
////				response.addCookie(cookie);
////
////				HttpServletResponse res = (HttpServletResponse) response;
////				res.setHeader("LoginStatus", "Company : " + username + ", has logged in successfully");
////			}
////			else
////				response.setStatus(401);
////		}	
////	
////	
//	
//	
//	
//
//	
//	
//
//}
