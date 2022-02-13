
package com.nati.coupons.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.dao.CompanyDAO;
import com.nati.coupons.dao.CouponDAO;
import com.nati.coupons.dao.DaoUtils;
import com.nati.coupons.dao.ICompanyDAO;
import com.nati.coupons.enums.ClientType;
import com.nati.coupons.enums.CouponType;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.utils.EmailValidator;
import com.nati.coupons.utils.utils;
import com.nati.coupons.*;

/**
 * @author vexxnati
 *
 */
public class CompanyController {

	private CompanyDAO companyDao; 
	private CouponDAO couponDAO; 


	private static final int MAX_PASSWORD_LENGTH=12;
	private static final int MIN_PASSWORD_LEGTH=3;

	public CompanyController() throws ApplicationException{
		this.companyDao = new CompanyDAO();	
		this.couponDAO=new CouponDAO();
	}

	//Add new company, not possible to add new company if the name of the new company already exist.						
	public void createCompany(Company company) throws ApplicationException {
		if(isCompanyDetailsValid(company))
			companyDao.createCompany(company);
	}

	//Checks the legality of values(name,email,password), return true if Everything ok.
	private boolean isCompanyDetailsValid(Company company) throws ApplicationException{
		boolean chackAllDetailes=true;
		if(validateNameExistOfCompany(company.getCompName()))
			throw new ApplicationException("Can't add- "+company.getCompName()+ ", the company because the name of the company is already exist");
		if(!isEmailValid(company.getEmail()))
			throw new ApplicationException("The Email "+ company.getEmail() + ", is invalid");
		if(!isPasswordValid(company.getPassword()))
			throw new ApplicationException("The Password is invalid");
		return chackAllDetailes;
	}

	//Company and the coupons under the company we want to delete must be deleted.	
	public void removeCompanyById(long companyId) throws ApplicationException {
		if(!isIdExist(companyId)){
			throw new ApplicationException("ID NOT EXIST");
		}	
				
		this.companyDao.removeAllPursachesCouponsOfCompanyId(companyId);
		if(isCompanyExistInCoupons(companyId))
			this.companyDao.removeAllCouponsUnderCompanyId(companyId);
		this.companyDao.removeCompanyById(companyId);

//		
//		List<Long> allCouponIdToRemove = new ArrayList<>();
//		//Checks if a company coupon has been purchased, if yes the func will delete all purchased coupons belonging to the company
//		allCouponIdToRemove=listOfCouponIdToRemoveFromPursachesCoupons(companyId);
//		if(allCouponIdToRemove.size()>0){
//			for(int i=0;i<allCouponIdToRemove.size();i++)
//				this.companyDao.removeAllPursachesCouponsOfCompanyId(allCouponIdToRemove.get(i));
//		}
//		//Checks if company coupon exist,if yes remove it.
//		if(isCompanyExistInCoupons(companyId))
//			this.companyDao.removeAllCouponsUnderCompanyId(companyId);
//
//		this.companyDao.removeCompanyById(companyId);
	}

	//return list of all company coupon purchased 
	private List<Long> listOfCouponIdToRemoveFromPursachesCoupons(long companyId) throws ApplicationException{
		List<Long> allCouponIdToRemove = new ArrayList<>();
		List<Coupon> allPurchasedCoupons = new ArrayList<>();

		allPurchasedCoupons=(List<Coupon>) couponDAO.getAllPurchasedCoupons();
		for(int i=0;i<allPurchasedCoupons.size();i++){
			if(allPurchasedCoupons.get(i).getCompanyId()==companyId)			
				allCouponIdToRemove.add(allPurchasedCoupons.get(i).getId());
		}
		return allCouponIdToRemove;
	}

	//Checks if company coupon exist.
	private boolean isCompanyExistInCoupons(long companyId) throws ApplicationException{
		return companyDao.isCompanyIdExistInCoupons(companyId);
	}

	//Update the Company's details, except for the Company's name.
	public void updateCompany(Company company) throws ApplicationException {
		if(isIdExist(company.getId())){
			if(isCompanyDetailsValid(company)){
				Company updateCompany = new Company();
				updateCompany=companyDao.getCompanyById(company.getId());
				updateCompany.setEmail(company.getEmail());
				updateCompany.setPassword(company.getPassword());
				companyDao.updateCompany(updateCompany);
			}
		}
		else
			throw new ApplicationException("Can't update the company because the id of the company is not exist");
	}

	//View all Companies details
	public Collection<Company> getAllCompanies() throws ApplicationException {
		return companyDao.getAllCompanies();
	}

	//View specific company details
	public Company getCompanyById(long id) throws ApplicationException {
		if(!isIdExist(id))
			throw new ApplicationException("ID NOT EXIST");
		return companyDao.getCompanyById(id);		
	}

	//Checks into DB if this name already exists , return true if exists and false if not.
	private boolean validateNameExistOfCompany(String name) throws ApplicationException{
		return companyDao.isCompanyNameExist(name);	
	}

	//Checks into DB if this id already exists , return true if exists and false if not.
	private boolean isIdExist(long id) throws ApplicationException{	
		if(companyDao.getCompanyById(id)==null)
			return false;
		else 
			return true;
	}
	
	public Company getCompanyByNameAndPassword(String name ,String password) throws ApplicationException {
		if(!login(name,password)) 
			throw new ApplicationException("Login failed");
		return companyDao.getCompanyByNameAndPassword(name, password);
	
	}

//	//Checks if the username and password match the information in DB if yes return true 
//	public boolean login(String userName, String userPassword) throws ApplicationException{	
//		if(userName.equals("Admin")&& userPassword.equals("1234"))
//			return true;
//		return false;
//		//if(!isPasswordValid(userPassword))
//		//	throw new ApplicationException("The password is invalid");
//		//return companyDao.login(userName, userPassword);			
//	}
	
	//Checks if the username and password match the information in DB if yes return true 
	public boolean login(String userName,String userPassword) throws ApplicationException{

		if(!isPasswordValid(userPassword))
			throw new ApplicationException("The password is invalid");
		return companyDao.login(userName, userPassword);		
	}

	//Checks if the password is valid
	private boolean isPasswordValid(String password) {
		if(password.length()>MAX_PASSWORD_LENGTH || password.length()<MIN_PASSWORD_LEGTH)
			return false;	
		return true;
	}

	//Checks if the email is valid
	private boolean isEmailValid(String userEmail){
		EmailValidator emailValidator = new EmailValidator();
		return emailValidator.validateEmail(userEmail);
	}

}