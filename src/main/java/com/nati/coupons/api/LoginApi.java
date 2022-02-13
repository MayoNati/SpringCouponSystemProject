/**
 * 
 */
package com.nati.coupons.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.nati.coupons.beans.Customer;
import com.nati.coupons.beans.UserLoginDetails;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.logic.CompanyController;
import com.nati.coupons.logic.CouponsController;
import com.nati.coupons.logic.CustomerController;

/**
 * @author vexxnati
 *
 */

@Path("/login")
//Please translate the java obj returned from the server into JSONs
@Produces(MediaType.APPLICATION_JSON)

//The data that comes from the client is a JSON
@Consumes(MediaType.APPLICATION_JSON)
public class LoginApi {

	
	private CustomerController customerController;
	private CompanyController companyController;

	
	public LoginApi() throws ApplicationException {
		customerController= new CustomerController();
		companyController=new CompanyController();
	}
	
	//Checks if the username and password match the information in DB if yes return true 
		@POST
		@Path("/loginCustomer")
		public void loginCustomer(@Context HttpServletRequest request,
				@Context HttpServletResponse response,
				UserLoginDetails loginData) throws IOException, ApplicationException, ServletException
		{
			System.out.println("Name:"+loginData.getUser()+" Pass:"+loginData.getPassword());

			System.out.println("into login");
			
			
			//CustomerController customerController = new CustomerController();
			
			if(customerController.login(loginData.getUser(),loginData.getPassword())){
				System.out.println("Succses");	
				request.getSession();
				Cookie cookie=new Cookie("user",loginData.getUser());
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			
			else {
				response.setStatus(401);
				response.getStatus();
				//System.out.println(response.getStatus());
				throw new ApplicationException(String.valueOf(response.getStatus()));
				
				//response.setStatus(401);
				//response.getStatus();
				//System.out.println(response.getStatus());

			}		
		}
		
		
		//Checks if the username and password match the information in DB if yes return true 
		@POST
		@Path("/loginCompany")
		public void loginCompany(@Context HttpServletRequest request,
				@Context HttpServletResponse response,
				UserLoginDetails loginData) throws IOException, ApplicationException, ServletException
		{
			System.out.println("Name:"+loginData.getUser()+" Pass:"+loginData.getPassword());

			System.out.println("into login company");
			
			//CompanyController companyController = new CompanyController();
						
			if(companyController.login(loginData.getUser(),loginData.getPassword())){
				System.out.println("Succses");	
				request.getSession();
				Cookie cookie=new Cookie("user",loginData.getUser());
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			else {
				response.setStatus(401);
				
				response.getStatus();
				//System.out.println(response.getStatus());
				throw new ApplicationException(String.valueOf(response.getStatus()));
				
				//response.setStatus(401);
				//response.getStatus();
				//System.out.println(response.getStatus());

			}		
		}
		
		@DELETE
		@Path("/removeCoupon")
		public void removeCoupon(@QueryParam("id") long id) throws ApplicationException {
			CouponsController couponsController=new CouponsController();
			couponsController.removeCouponById(id);
			//System.out.println(id);			
		}
		
		
	
	
	
}
