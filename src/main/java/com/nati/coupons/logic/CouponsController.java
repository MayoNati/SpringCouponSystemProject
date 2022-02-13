/**
 * 
 */
package com.nati.coupons.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.beans.Customer;
import com.nati.coupons.dao.CompanyDAO;
import com.nati.coupons.dao.CouponDAO;
import com.nati.coupons.dao.CustomerDAO;
import com.nati.coupons.dao.DaoUtils;
import com.nati.coupons.enums.ClientType;
import com.nati.coupons.enums.CouponType;
import com.nati.coupons.enums.ErrorType;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.utils.utils;
import com.nati.coupons.logic.*;

/**
 * @author vexxnati
 *
 */
public class CouponsController {


	private CouponDAO couponDao;
	private CompanyController companyController;
	private CustomerController customerController;


	private boolean adminPermission=false;
	private boolean customerPermission=false;
	private boolean companyPermission=false;

	private final String ADMIN_USER_NAME="ADMIN";
	private final String ADMIN_USER_PASSWORD="1234";




	public CouponsController() throws ApplicationException{
		this.couponDao=new CouponDAO();
	}



	//Add new coupon, not possible to add new coupon if the title of the coupon already exist.						
	public void createCoupon(Coupon coupon) throws ApplicationException {

		if(isCouponDetailsValid(coupon))
			if(isTitleNameExists(coupon.getTitle()))
				throw new ApplicationException("Can't add the coupon because the name of the coupon is already exist");

		couponDao.createCoupon(coupon);

	}

	//You must also delete coupons purchased by customers
	public void removeCouponById(long id) throws ApplicationException {
		if(!isIdExist(id))
			throw new ApplicationException("Can't remove the coupon because the id:"+id +" coupon not exist");		
		couponDao.removeAllPursachesCouponsToCompanyId(id);
		couponDao.removeCouponById(id);
	}
	
	public void removeAllExpireCoupons() throws ApplicationException {
		couponDao.removeAllExpireCoupons();
	}

	//Update the only expired date and price of the Coupon details.
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		if(isCouponDetailsValid(coupon))
			if(isIdExist(coupon.getId())){
				Coupon updateCoupon = new Coupon();
				updateCoupon=couponDao.getCouponById(coupon.getId());
				updateCoupon.setPrice(coupon.getPrice());
				updateCoupon.setEndDate(coupon.getEndDate());
				couponDao.updateCoupon(updateCoupon);
			}
			else
				throw new ApplicationException("Can't update the coupon because the id not exist");
	}



	//Checks into DB if this name already exists , return true if exists and false if not.
	private boolean isTitleNameExists(String name) throws ApplicationException{
		return couponDao.isTitleNameExist(name);
	}

	//Checks into DB if this id already exists , return true if exists and false if not.
	//private boolean isIdExist(long id) throws ApplicationException{
	//	return couponDao.isCouponExistById(id);
	//}

	private boolean isIdExist(long id) throws ApplicationException{	
		if(couponDao.getCouponById(id)==null)
			return false;
		else 
			return true;
	}

	//Checks the legality of values(Amount,Price,Date,Expired Date), return true if Everything ok.
	private boolean isCouponDetailsValid(Coupon coupon) throws ApplicationException{
		boolean chackAllDetailes=true;

		if(utils.isDateFormatValid(coupon.getStartDate()) && utils.isDateFormatValid(coupon.getEndDate()))
		{	
			if(!isAmountAvailable(coupon.getAmount()))
				throw new ApplicationException("Amount value is invalid, Amount must be bigger than 0");
			if(!cheackLegalPrice(coupon.getPrice()))
				throw new ApplicationException("Price value is invalid, Price must be bigger than 0");
			if(utils.splitDateAndCompareRequestedDateBiggerSqlDate(coupon.getStartDate(),coupon.getEndDate()))
				throw new ApplicationException("Dates values is invalid-The Coupon End date must come after the Start date of the coupon");
			if(utils.splitDateAndCompareRequestedDateBiggerSqlDate(utils.getCurrentDate(),coupon.getEndDate()))
				throw new ApplicationException("Dates values is invalid-You can't add a coupon with already expired date");
		}
		return chackAllDetailes;
	}

	//View specific coupon details
	public Coupon getCouponById(long id) throws ApplicationException {
		if(isIdExist(id))
			return couponDao.getCouponById(id);
		else
			throw new ApplicationException("The coupon id not exist");

	}
	
	//View all coupons details
	public Collection<Coupon> getAllCoupons() throws ApplicationException {
		return couponDao.getAllCoupons();
	}

	public Collection<String> getAllTypesCoupon() throws ApplicationException{
		return couponDao.getAllTypesCoupon();
	}
	//View company coupons by coupon type, up to a certain price, up to a certain date.
	public Collection<Coupon> getCouponByType(CouponType couponType) throws ApplicationException {		
		return couponDao.getCouponsByType(couponType);
	}

	//Viewing the all purchase history
	public Collection<Coupon> getAllPurchasedCoupons() throws ApplicationException{
		return couponDao.getAllPurchasedCoupons();
	}

	//Viewing the all coupons purchased by coupon type
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws ApplicationException{
		return couponDao.getAllPurchasedCouponsByType(type);
	}

	//View all coupons that are up to a certain price
	public Collection<Coupon> getAllPurchasedCouponsByPrice(long price) throws ApplicationException{
		if(!cheackLegalPrice(price))
			throw new ApplicationException("The price is invalid");
		return couponDao.getAllPurchasedCouponsByPrice(price);
	}

	//Checking if the price not negative
	private boolean cheackLegalPrice(float price){
		if(price>0)
			return true;
		return false;		
	}

	//buyCoupon
	//Purchases a coupon By sending the coupon ID number and ID number of the customer
	public void purchasedCoupon(long couponId,long customerId) throws ApplicationException{
		
		Coupon couponBuy = new Coupon();
		couponBuy=couponDao.getCouponById(couponId);
		if(!isAmountAvailable(couponBuy.getAmount()))
			throw new ApplicationException("You can not buy coupons that are out of stock");
		if(isCouponDateExpired(couponBuy))
			throw new ApplicationException("Can't purchase expired coupon");
//		if(isCouponAlreadyPurchasedByCustomer(customerId))
//			throw new ApplicationException("Can't be purchased more than one coupon");

		couponDao.updateCouponAmountAfterPurchase(couponBuy);
		couponDao.purchasedCoupon(couponDao.getCouponIdByComapnyId(couponBuy.getCompanyId()),customerId);
	}


	public Collection<Coupon> getAllCouponsUpToDate(String date) throws ApplicationException{
		List<Coupon> allCouponsUpToDate = new ArrayList<>();
		if(utils.isDateFormatValid(date))
			allCouponsUpToDate=(List<Coupon>) couponDao.getAllCouponsUpToDate(date);		
		else
			throw new ApplicationException("The date value is invalid");
		return allCouponsUpToDate;
	}
	
	public Collection<Coupon> getAllCouponsByCompanyId(long CompanyId) throws ApplicationException{

//		if(couponDao.isCustomerAlreadyBoughtCoupon(customerID))	
			return couponDao.getAllCouponsByCompanyId(CompanyId);
//		return null;
		
//		return null;		
	}

	public Collection<Coupon> getAllPurchasedCouponByCustomerId(long customerID) throws ApplicationException{
		if(couponDao.isCustomerAlreadyBoughtCoupon(customerID))	
			return couponDao.getAllPurchasedCouponsByCusomerId(customerID);
		return null;		
	}
	
	public Collection<Coupon> getAllExpireCoupons() throws ApplicationException{
		List<Coupon> allExpireCoupons = new ArrayList<>();
		return allExpireCoupons=(List<Coupon>) couponDao.getAllExpireCoupons();
	}
	
	//Checking if the amount not negative
	private boolean isAmountAvailable(int amount) {
		if(amount>0)
			return true;
		return false;
	}

	//Checking if the date already expired
	private boolean isCouponDateExpired(Coupon coupon){
		if(utils.splitDateAndCompareRequestedDateBiggerSqlDate(utils.getCurrentDate(),coupon.getEndDate()))
			return true;
		return false;
	}

	//Checks if the customer has already purchased a coupon
	private boolean isCouponAlreadyPurchasedByCustomer(long customerId) throws ApplicationException{
		return couponDao.isCustomerAlreadyPurcheasedCoupon(customerId);
	}	

	//Checks if the login details that are sent match the information in the system, if appropriate permission is saved
	public void login(String name,String password,ClientType client_type) throws ApplicationException{
		if(client_type.name()=="ADMIN")
			if(name.equals(ADMIN_USER_NAME) && password.equals(ADMIN_USER_PASSWORD))
				this.adminPermission=true;
			else
				throw new ApplicationException("ADMIN - Access denied ");

		if(client_type.name()=="COMPANY"){
			companyController = new CompanyController();
			if(companyController.login(name, password))
				this.companyPermission=true;
			else
				throw new ApplicationException("Company - Access denied ");
		}

		if(client_type.name()=="CLINET"){
			customerController=new CustomerController();
			if(customerController.login(name, password))
				this.customerPermission=true;
			else
				throw new ApplicationException("CLINET - Access denied ");
		}
	}
	
//	public void buyCoupon(long id,long customerId) throws ApplicationException {
//		Coupon coupon = new Coupon();
//		coupon=getCouponById(id);
//		if(coupon.getAmount()>1)
//			couponDao.purchasedCoupon(id, customerId);
//		
//	}

	//Returns the status of the permission of Admin
	public boolean getAdminPermissionStatus(){return this.adminPermission;}
	//Returns the status of the permission of Company
	public boolean getCompanyPermissionStatus(){return this.companyPermission;}
	//Returns the status of the permission of Customer
	public boolean getCustomerPermissionStatus(){return this.customerPermission;}

}