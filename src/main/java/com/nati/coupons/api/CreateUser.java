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
import com.nati.coupons.beans.Customer;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.logic.CompanyController;
import com.nati.coupons.logic.CouponsController;
import com.nati.coupons.logic.CustomerController;

/**
 * @author vexxnati
 *
 */
@Path("/createUser")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreateUser {

//@Path("/coupons")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

	
	//@GET
	//@PathParam("/{couponId}")
	//public Coupon getCoupon(@PathParam("couponId") long id) {	
	//}
	
	
	//Add new company, not possible to add new company if the name of the new company already exist.	
			@POST
			@Path("/createCompany")
			public void createCompany(Company newCompany) throws ApplicationException {
				System.out.println(newCompany.toString());				
				CompanyController companyController = new CompanyController() ;
				companyController.createCompany(newCompany);
			}
			
			@POST
			@Path("/createCustomer")
			public void createCustomer(Customer newCustomer) throws ApplicationException{
				System.out.println(newCustomer.toString());
				CustomerController customerController = new CustomerController();
				customerController.createCustomer(newCustomer);
			}
	
}
