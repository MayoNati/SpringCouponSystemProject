/**
 * 
 */
package com.nati.coupons.dao;

import java.util.Collection;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.exceptions.ApplicationException;

/**
 * @author vexxnati
 *
 */
public interface ICompanyDAO {
	
	
	void createCompany(Company company) throws ApplicationException;
	void removeCompanyById(long id) throws ApplicationException;
	void updateCompany(Company company) throws ApplicationException;
	Company getCompanyById(long id) throws ApplicationException;
	Collection <Company> getAllCompanies() throws ApplicationException;
	Collection <Coupon> getCoupons() throws ApplicationException;
	boolean login(String compName, String password) throws ApplicationException;
	
	
	

}
