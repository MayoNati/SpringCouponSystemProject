package com.nati.coupons;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.beans.Customer;
import com.nati.coupons.dao.CompanyDAO;
import com.nati.coupons.dao.CouponDAO;
import com.nati.coupons.dao.CustomerDAO;
import com.nati.coupons.dao.DaoUtils;
import com.nati.coupons.enums.ClientType;
import com.nati.coupons.enums.CouponType;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.logic.CompanyController;
import com.nati.coupons.logic.CouponsController;
import com.nati.coupons.logic.CustomerController;
import com.nati.coupons.threads.DailyCouponExpiredTask;
import com.nati.coupons.utils.EmailValidator;


/**
 * @author vexxnati
 *
 */
public class Test {

	public static void main(String[] args) throws ApplicationException{

		Scanner scan = new Scanner(System.in);
		//DailyCouponExpirationTask t = new DailyCouponExpirationTask();
		//Thread t=new Thread(new DailyCouponExpirationTask());
		//t.start();
		//t.Stop();
		//t.destroy();

//		int exit=scan.nextInt();
//		if(exit==0)
//			t.Stop();

		
		// --------------Company functions -------------//
		CompanyDAO companydao = new CompanyDAO();
		CompanyController companyController=new CompanyController();

		Company newCompany = new Company("users8","pass","mayo1@gmail.com");
		Company newCompanyWithEmailFailed = new Company("user","pass","@gmail.com");
		Company newCompanyWithPassFailed = new Company("user","p","mayo@gmail.com");

		Company updateCompanyWithIdFailed = new Company(1,"UpdateUser","UpdatePass","UpdateEmail@gmail.com");
		Company updateCompanyWithEmailFailed = new Company(2,"UpdateUser","UpdatePass","UpdateEmailgmail.com");
		Company updateCompanyWithPassFailed = new Company(2,"UpdateUser","Up","UpdateEmail@gmail.com");
		Company updateCompany = new Company(3,"UpdateUser","UpdatePass","Update.Email@gmail.com");

		//
		//		
		//		
				companyController.createCompany(newCompany);
		//		companyController.createCompany(newCompanyWithPassFailed);
		//		companyController.createCompany(newCompany);
		//
		//		companyController.createCompany(newCompany);//need getting failed because i'm not allowed to add company with the same name 
		//		
		//		companyController.removeCompanyById(5);
		//		companyController.updateCompany(updateCompany);

		//		List<Company> companies = new ArrayList<>();
		//		companies=(List<Company>) companyController.getAllCompanies();
		//
		//		for(int i=0;i<companies.size();i++){
		//			System.out.println(companies.get(i).getId());
		//			System.out.println(companies.get(i).getCompName());
		//			System.out.println(companies.get(i).getPassword());
		//			System.out.println(companies.get(i).getEmail());
		//		}


		//		Company companyById = new Company();
		//		companyById=companyController.getCompanyById(1);
		//		System.out.println(companyById.getId());
		//		System.out.println(companyById.getCompName());
		//		System.out.println(companyById.getEmail());
		//		System.out.println(companyById.getPassword());




		//		System.out.println(companyController.login("user", "pass"));//need to get True
		//		System.out.println(companyController.login("Test", "123"));//need to get True


		// --------------Customer functions -------------//
		CustomerDAO customerdao = new CustomerDAO();
		CustomerController customerController = new CustomerController();


		//		Customer newCustomer = new Customer ("user7","pass");
		//		Customer updateCustomer = new Customer(1,"Update","123");
		//		
		//		customerController.createCustomer(newCustomer);
		//		customerController.removeCustomer(1);
		//		customerController.updateCustomer(newCustomer);
		//		customerController.getAllCustomer();
		//		customerController.getCustomerById(1);
		//		System.out.println(customerController.login("user", "1234567"));
		//		System.out.println(customerController.login("mayo", "12377"));




		// --------------Coupon functions -------------//
		CouponDAO coupondao = new CouponDAO();
		CouponsController couponsController = new CouponsController();

		Coupon newCoupon = new Coupon(2,"TestCreate2","11/2/2019","12/2/2020",1,CouponType.RESTURANS,"test message",1,"IMG");
		//Coupon newCoupon2 = new Coupon(6,"TestCreate7","11/2/2019","12/2/2020",1,CouponType.RESTURANS,"test message",1,"IMG");

		Coupon newCoupon1 = new Coupon(4,"Test4","11/2/2018","12/2/2020",1,CouponType.RESTURANS,"test message",1,"IMG");
		Coupon newCoupon2 = new Coupon(5,"Test123","11/5/2018","11/11/2018",1,CouponType.RESTURANS,"test message",1,"IMG");
		Coupon newCoupon3 = new Coupon(6,"Test6","11/7/2017","12/11/2018",1,CouponType.RESTURANS,"test message",1,"IMG");

		
		Coupon updateCoupon = new Coupon(4,1,"Update","12/2/2018","31/2/2020",123,CouponType.FOOD,"Udate",123,"IMG");
		Coupon updateCouponWithFailedDates = new Coupon(4,1,"Update","12/2/2021","12/2/2019",123,CouponType.FOOD,"Udate",123,"IMG");
		Coupon updateCouponWithNoCouponsInStock= new Coupon(1,1,"Update","12/2/2018","12/2/2020",0,CouponType.FOOD,"Udate",123,"IMG");
		Coupon updateCouponWithPriceInValid= new Coupon(1,1,"Update","12/2/2018","12/2/2020",10,CouponType.FOOD,"Udate",-1,"IMG");



		//		couponsController.createCoupon(newCoupon2);
				//couponsController.createCoupon(newCoupon1);
		//		couponsController.createCoupon(newCoupon2);
		//		couponsController.createCoupon(newCoupon3);


		//		couponsController.removeCouponById(4);
		//		couponsController.updateCoupon(updateCoupon);

		//couponsController.getCouponById(100);
		
//				List<Coupon> allExpiredCoupons = new ArrayList<>();
//
//				allExpiredCoupons=(List<Coupon>) coupondao.getAllExpireCoupons();
//				for(int i=0;i<allExpiredCoupons.size();i++){
//					System.out.println(allExpiredCoupons.get(i).toString());
//				}		
				
				
		Coupon couponById = new Coupon();
		couponById=couponsController.getCouponById(2);
		System.out.println(couponById.toString());
//
//		
//		List<Coupon> allCoupons = new ArrayList<>();
//
//		allCoupons=(List<Coupon>) couponsController.getAllCoupons();
//		for(int i=0;i<allCoupons.size();i++){
//			System.out.println(allCoupons.get(i).toString());
//		}
//
//
//		List<Coupon> allCouponsPurchased = new ArrayList<>();
//		allCouponsPurchased=(List<Coupon>) couponsController.getAllPurchasedCoupons();
//		for(int i=0;i<allCouponsPurchased.size();i++){
//			System.out.println(allCouponsPurchased.get(i).toString());
//		}
//		
//		List<Coupon> couponsAllPurchasedCouponsByPrice = new ArrayList<>();
//		couponsAllPurchasedCouponsByPrice=(List<Coupon>) couponsController.getAllPurchasedCouponsByPrice(10);
//		for(int i=0;i<couponsAllPurchasedCouponsByPrice.size();i++){
//			System.out.println(couponsAllPurchasedCouponsByPrice.get(i).toString());
//		}
//		
//		List<Coupon> couponsAllPurchasedCouponsByType = new ArrayList<>();
//		couponsAllPurchasedCouponsByType=(List<Coupon>) couponsController.getAllPurchasedCouponsByType(CouponType.FOOD);
//		for(int i=0;i<couponsAllPurchasedCouponsByType.size();i++){
//			System.out.println(couponsAllPurchasedCouponsByType.get(i).toString());
//		}
				
		Customer customerPurchased = new Customer(1,"natiUser","1233");
				//customerController.createCustomer(customerPurchased);

		//		couponsController.purchasedCoupon(newCoupon,customerPurchased);
		//		couponsController.login("", "", ClientType.ADMIN);



		
	


	}


	
}
