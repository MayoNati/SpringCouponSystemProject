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
//import com.nati.coupons.*;
//import com.nati.coupons.beans.Coupon;
//import com.nati.coupons.enums.CouponType;
//import com.nati.coupons.exceptions.ApplicationException;
//import com.nati.coupons.logic.CouponsController;
//
//
//@WebServlet("/CreateCoupon")
//public class CreateCoupon extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		
//		String nextPage = "/BadLogin.html";
//					
//		try {
//			CouponsController couponsController = new CouponsController();
//			
//			String companyID = request.getParameter("CompanyID");
//			String title = request.getParameter("Title");		
//			String startDate = request.getParameter("StartDate");
//			String endDate = request.getParameter("EndDate");
//			String amount = request.getParameter("Amount");
//			String type = request.getParameter("CouponType");
////			CouponType couponTypeEnum = CouponType.valueOf(request.getParameter("CouponType"));
//			String message = request.getParameter("Message");
//			String price = request.getParameter("Price");
//			String image = request.getParameter("Image");	
//			
//			CouponType typeNew = null;
//			typeNew=typeNew.valueOf(type);
//			long companyId = Long.parseLong(companyID);
//			int amountInt = Integer.parseInt(amount);			
//			float priceF = Float.parseFloat(price);
//			
//			Coupon coupon = new Coupon(companyId,title,startDate,endDate,amountInt,typeNew,message,priceF,image);
//			String companyName = (String)request.getSession(false).getAttribute("Mother Company");
//
//			couponsController.createCoupon(coupon);
//			nextPage = "/Success.html";
//
//		} catch (ApplicationException e) {
//
//			e.printStackTrace();
//		
//		}
//				
//					
//
//		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPage);
//		dispatcher.forward(request,response);
//	}
//
//}
