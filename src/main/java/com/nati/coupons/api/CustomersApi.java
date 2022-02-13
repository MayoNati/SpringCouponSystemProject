/**
 * 
 */
package com.nati.coupons.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.beans.Customer;
import com.nati.coupons.beans.UserLoginDetails;
import com.nati.coupons.dao.CustomerDAO;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.logic.CompanyController;
import com.nati.coupons.logic.CouponsController;
import com.nati.coupons.logic.CustomerController;

/**
 * @author vexxnati
 *
 */

@Path("/loggedin/customers")
//Please translate the java obj returned from the server into JSONs
@Produces(MediaType.APPLICATION_JSON)

//The data that comes from the client is a JSON
@Consumes(MediaType.APPLICATION_JSON)

public class CustomersApi {
	
	private CustomerController customerController;
	
	public CustomersApi() throws ApplicationException {
		customerController=new CustomerController();
	}
	
	@POST
	@Path("/create")
	public void createCustomer(Customer newCustomer) throws ApplicationException{
		//CustomerController customerController = new CustomerController();
		customerController.createCustomer(newCustomer);
		//System.out.println(customer.toString());
	}

	//The customer's coupon history must also be deleted in this case
	@DELETE
	@Path("/{deleteCoupon}")
	public void removeCustomer(@PathParam("deleteCoupon")long id) throws ApplicationException{
		System.out.println(id);
		//CustomerController customerController = new CustomerController() ;
		customerController.removeCustomer(id);

	}

	//Update customer details except customer name
	@PUT
	@Path("/updateCustomer")
	public void updateCustomer(Customer customer) throws ApplicationException{
		//CustomerController customerController = new CustomerController() ;
		customerController.updateCustomer(customer);
	}

	
	//View all Customers details
	@GET
	@Path("/getAllCustomer")
	//public Collection<Customer> getAllCustomer() throws ApplicationException {
	public Collection<Customer> getAllCustomer() throws ApplicationException {
		//CustomerController customerController = new CustomerController();
		customerController.getAllCustomer();
		
		List<Customer> allCustomers = new ArrayList<>();

		allCustomers=(List<Customer>) customerController.getAllCustomer();
		
		return allCustomers;
//		for(int i=0;i<allCustomers.size();i++){
//			System.out.println(allCustomers.get(i).toString());
//		}
		
		//System.out.println("All customers");
		//return customerDao.getAllCustomers();
	}

	//View specific Customer details
	@GET
	@Path("/{customerId}")
	public Customer getCustomerById(@PathParam("customerId") long id) throws ApplicationException {
	//	CustomerController customerController = new CustomerController();
		Customer cuostomer = new Customer();
		cuostomer=customerController.getCustomerById(id);
		return cuostomer;
//		System.out.println(cuostomer.toString());
	}
	
	//View company by sending company name and company password details
	@GET
	@Path("/getCustomerByNameAndPassword")
	public Customer getCustomerByNameAndPassword(@QueryParam("custName") String name,@QueryParam("password") String password) throws ApplicationException{			

		System.out.println("The name is:" + name +", the password is :"+password);

			Customer customer = new Customer();
			customer.setCustName(name);
			customer.setPassword(password);
			//CustomerController customerController;
			//customerController = new CustomerController();
			customer=customerController.getCustomerByNameAndPassword(customer.getCustName(),customer.getPassword());	
			System.out.println("getCustomerByNameAndPassword Succses");
			System.out.println(customer.toString());

			return customer;							
	}

//	@GET
//	@Path("/login")
//	public String login(@Context HttpServletRequest request,
//			@Context HttpServletResponse response,
//			UserLoginDetails loginData) 
//	{
//		if(loginData.getUser().equals("Admin") && loginData.getPassword().equals("1234")) {
////			System.out.println("Succses");		
//
//			request.getSession();
//			Cookie cookie=new Cookie("user",loginData.getUser());
//			cookie.setPath("/");
//			response.addCookie(cookie);
//			return "Succses";
//
//		}
//		else
//			return "Ouch....";
//			//System.out.println("Ouch...");
//	}
//	
//	//Checks if the username and password match the information in DB if yes return true 
//	public boolean login(String userName,String userPassword) throws ApplicationException{
//		if(!isPasswordValid(userPassword))
//			throw new ApplicationException("The password is invalid");
//		return customerDao.login(userName, userPassword);		
//	}

	
	

}
