/**
 * 
 */
package com.nati.coupons.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.logic.CompanyController;
import com.nati.coupons.logic.CouponsController;

/**
 * @author vexxnati
 *
 */
@Path("/allcoupons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CouponSystem {

	private CouponsController couponsController;
	private CompanyController companyController;
	
	public CouponSystem() throws ApplicationException {
		couponsController=new CouponsController();
		companyController=new CompanyController();
	}
	
	
	@GET
	@Path("/getAllCoupons")
	public Collection<Coupon> getAllCoupons() throws ApplicationException {
		
	//	CouponsController couponsController = new CouponsController() ;	

		List<Coupon> allCoupons = new ArrayList<>();
				allCoupons=(List<Coupon>) couponsController.getAllCoupons();
				System.out.println("getAllCoupons works");
				return allCoupons;

//				for(int i=0;i<allCoupons.size();i++){
//					System.out.println(allCoupons.get(i).toString());
//				}
	}
	

//	@POST
//	@Path("/createCompany")
//	public void createCompany(Company newCompany) throws ApplicationException {
//		companyController.createCompany(newCompany);
//	}
	
	@GET
	@Path("/getAllTypesCoupon")
	public Collection<String> getAllTypesCoupon() throws ApplicationException {
		
//		CouponsController couponsController = new CouponsController() ;	
		CouponsController couponsController = new CouponsController() ;	

		List<String> getAllTypesCoupon = new ArrayList<>();
		getAllTypesCoupon=(List<String>) couponsController.getAllTypesCoupon();
				System.out.println("getAllTypesCoupon works");
				return getAllTypesCoupon;					
	}
	
}
