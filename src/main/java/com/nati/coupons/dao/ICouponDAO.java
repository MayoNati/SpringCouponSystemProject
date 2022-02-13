/**
 * 
 */
package com.nati.coupons.dao;

import java.text.ParseException;
import java.util.Collection;

import com.nati.coupons.beans.Coupon;
import com.nati.coupons.enums.CouponType;
import com.nati.coupons.exceptions.ApplicationException;

/**
 * @author vexxnati
 *
 */
public interface ICouponDAO {

		void createCoupon(Coupon coupon) throws ApplicationException;
		void removeCouponById(long id) throws ApplicationException;
		void updateCoupon(Coupon coupon) throws ApplicationException;
		Coupon getCouponById(long id) throws ApplicationException;
		Collection <Coupon> getAllCoupons() throws ApplicationException;
		Collection <Coupon> getCouponsByType(CouponType couponType) throws ApplicationException;

	
	
	
	
	
	
}
