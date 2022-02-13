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
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.logic.CompanyController;
import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.UserLoginDetails;

/**
 * @author vexxnati
 *
 */

@Path("/loggedin/companies")
//Please translate the java obj returned from the server into JSONs
@Produces(MediaType.APPLICATION_JSON)

//The data that comes from the client is a JSON
@Consumes(MediaType.APPLICATION_JSON)

public class CompaniesApi {
	
	private CompanyController companyController;

	
	public CompaniesApi() throws ApplicationException{
		this.companyController = new CompanyController();
	}
 
	
	//Add new company, not possible to add new company if the name of the new company already exist.	
		@POST
		@Path("/create")
		public void createCompany(Company newCompany) throws ApplicationException {
			companyController.createCompany(newCompany);
		}

		//Company and the coupons under the company we want to delete must be deleted.	
		@DELETE
		@Path("/{deleteCompany}")
		public void removeCompanyById(@PathParam("deleteCompany")long companyId) throws ApplicationException {
			//CompanyController companyController = new CompanyController() ;
			companyController.removeCompanyById(companyId);
			//System.out.println("Remove "+companyId);
		}
	
		//Update the Company's details, except for the Company's name.
		@PUT
		@Path("/updateCompany")
		public void updateCompany(Company company) throws ApplicationException {
			//CompanyController companyController = new CompanyController();
			companyController.updateCompany(company);
			//System.out.println(company);
		}

		//View all Companies details
		@GET		
		@Path("/getAllCompany")
		public Collection<Company> getAllCompanies() throws ApplicationException {
			//CompanyController companyController = new CompanyController() ;	
			List<Company> companies = new ArrayList<>();
					companies=(List<Company>) companyController.getAllCompanies();		
					
					System.out.println("ok");
					return companies;
		}

		//View specific company details
		@GET
		@Path("/{companyId}")
		public Company getCompanyById(@PathParam("companyId")long id) throws ApplicationException{			
						
				Company company = new Company();
				//CompanyController companyController;
				//companyController = new CompanyController();
				company=this.companyController.getCompanyById(id);	
				System.out.println("GetCompanyById works");
				return company;
								
		}
		
		//View company by sending company name and company password details
		@GET
		@Path("/getCompanyByNameAndPassword")
		public Company getCompanyByNameAndPassword(@QueryParam("compName") String name,@QueryParam("password") String password) throws ApplicationException{			

			System.out.println("The name is:" + name +", the password is :"+password);

				//Company company = new Company();
			//	company.setCompName(name);
			//	company.setPassword(password);
				//CompanyController companyController;
				//companyController = new CompanyController();
				//company=companyController.getCompanyByNameAndPassword(name,password);	
				System.out.println("getCompanyByNameAndPassword Succses");
				System.out.println(companyController.getCompanyByNameAndPassword(name,password).toString());

				return companyController.getCompanyByNameAndPassword(name,password);							
		}

		
//		//Checks if the username and password match the information in DB if yes return true 
//		@POST
//		@Path("/Userlogin")
//		public void login(@Context HttpServletRequest request,
//				@Context HttpServletResponse response,
//				UserLoginDetails loginData) 
//		{
//			System.out.println("into login");
//			if(loginData.getUser().equals("Admin") && loginData.getPassword().equals("1234")) {
//				System.out.println("Succses");		
//
//				request.getSession();
//				Cookie cookie=new Cookie("user",loginData.getUser());
//				cookie.setPath("/");
//				response.addCookie(cookie);
//			}
//			else {
//				//System.out.println("Error enter login");		
//				response.setStatus(401);
//			}
//			
//		}

//		//Checks if the username and password match the information in DB if yes return true 
//		@GET
//		@Path("/login")
//		public String login(@Context HttpServletRequest request,
//				@Context HttpServletResponse response,
//				UserLoginDetails loginData) 
//		{
//			if(loginData.getUser().equals("Admin") && loginData.getPassword().equals("1234")) {
//				//System.out.println(Succses);		
//
//				request.getSession();
//				Cookie cookie=new Cookie("user",loginData.getUser());
//				cookie.setPath("/");
//				response.addCookie(cookie);
//				return "Succses";
//			}
//			else
//				//System.out.println("Ouch...");	
//				return "Ouch...";
//
//		}
	
}
