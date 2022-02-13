/**
 * 
 */
package com.nati.coupons.dao;

import java.util.Collection;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.beans.Customer;
import com.nati.coupons.exceptions.ApplicationException;

/**
 * @author vexxnati
 *
 */
public interface ICustomerDAO {

	
	void createCustomer(Customer customer) throws ApplicationException;
	void removeCustomerById(long id) throws ApplicationException;
	void updateCustomer(Customer customer) throws ApplicationException;
	Customer getCustomerById(long id) throws ApplicationException;
	Collection <Customer> getAllCustomers() throws ApplicationException;
	Collection <Coupon> getCoupons() throws ApplicationException;
	boolean login(String custName, String passord) throws ApplicationException;
	
	
	
}
