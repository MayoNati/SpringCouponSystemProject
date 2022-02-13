/**
 * 
 */
package com.nati.coupons.logic;

import java.util.Collection;
import java.util.List;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.beans.Customer;
import com.nati.coupons.dao.CouponDAO;
import com.nati.coupons.dao.CustomerDAO;
import com.nati.coupons.enums.ClientType;
import com.nati.coupons.enums.CouponType;
import com.nati.coupons.exceptions.ApplicationException;

/**
 * @author vexxnati
 *
 */
public class CustomerController {

	private CustomerDAO customerDao;

	private static final int MAX_PASSWORD_LENGTH=12;
	private static final int MIN_PASSWORD_LEGTH=3;

	public CustomerController() throws ApplicationException{
		this.customerDao = new CustomerDAO();
	}

	//Add new customer, not possible to add new customer if the name of the new customer already exist.						
	public void createCustomer(Customer customer) throws ApplicationException{
		if(isCustomerDetailsValid(customer))
			customerDao.createCustomer(customer);	
	}

	//Checks the legality of values(name,email,password), return true if Everything ok.
	private boolean isCustomerDetailsValid(Customer customer) throws ApplicationException{
		boolean chackAllDetailes=true;
		if(validateNameExistOfCustomer(customer.getCustName()))
			throw new ApplicationException("Can't add the company because the name of the customer is already exist");
		if(!isPasswordValid(customer.getPassword()))
			throw new ApplicationException("The Password is invalid");
		return chackAllDetailes;
	}


	//The customer's coupon history must also be deleted in this case
	public void removeCustomer(long id) throws ApplicationException{
		if(!isIdExist(id)){
			throw new ApplicationException("ID NOT EXIST");
		}
		customerDao.removeAllPursachesCoupons(id);
		customerDao.removeCustomerById(id);
	}

	//Update customer details except customer name
	public void updateCustomer(Customer customer) throws ApplicationException{
		if(isIdExist(customer.getId())){
			if(isCustomerDetailsValid(customer)){
				Customer updateCustomer = new Customer();
				updateCustomer=customerDao.getCustomerById(customer.getId());
				updateCustomer.setPassword(customer.getPassword());
				customerDao.updateCustomer(updateCustomer);
			}
		}
		else
			System.out.println("Can't update the customer because the name of the customer not exist");	
	}

	//Checks into DB if this id already exists , return true if exists and false if not.
	private boolean isIdExist(long id) throws ApplicationException{	
		if(customerDao.getCustomerById(id)==null)
			return false;
		else 
			return true;
	}

	//View all Customers details
	public Collection<Customer> getAllCustomer() throws ApplicationException {
		return customerDao.getAllCustomers();
	}

	//View specific Customer details
	public Customer getCustomerById(long id) throws ApplicationException {
		return customerDao.getCustomerById(id);
	}

	//Checks into DB if this name already exists , return true if exists and false if not.
	private boolean validateNameExistOfCustomer(String name) throws ApplicationException{
		return customerDao.isCustomerNameExist(name);
	}



	//Checks if the username and password match the information in DB if yes return true 
	public boolean login(String userName,String userPassword) throws ApplicationException{
		if(!isPasswordValid(userPassword))
			throw new ApplicationException("The password is invalid");
		return customerDao.login(userName, userPassword);		
	}

	//Checks if the password is valid
	private boolean isPasswordValid(String password) {	
		if(password.length()>MAX_PASSWORD_LENGTH || password.length()<MIN_PASSWORD_LEGTH){
			return false;	
		}
		return true;
	}


	public Customer getCustomerByNameAndPassword(String name ,String password) throws ApplicationException {
		if(!login(name,password)) 
			throw new ApplicationException("Login failed");
		return customerDao.getCustomerByNameAndPassword(name, password);
	
	}


}
