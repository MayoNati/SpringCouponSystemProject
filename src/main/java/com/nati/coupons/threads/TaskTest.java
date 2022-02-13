/**
 * 
 */
package com.nati.coupons.threads;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import com.nati.coupons.beans.Coupon;
import com.nati.coupons.dao.CompanyDAO;
import com.nati.coupons.dao.CouponDAO;
import com.nati.coupons.dao.CustomerDAO;
import com.nati.coupons.enums.ErrorType;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.logic.CouponsController;
import com.nati.coupons.utils.utils;

/**
 * @author vexxnati
 *
 */
public class TaskTest extends Thread implements Runnable {

	private CompanyDAO companyDAO; 
	private CouponDAO couponDAO;  
	private CustomerDAO customerDAO;
	private CouponsController couponsController;
	private boolean running = true;

	public TaskTest() throws ApplicationException{
		this.couponDAO=new CouponDAO();
		this.couponsController = new CouponsController();
		this.running=true;
	}


	@Override
	public void run(){
		List<Coupon> allExpireCoupons = new ArrayList<>();

		
		while(running){

			try {
				System.out.println("Cheaks the DB");
				System.out.println("Enter 0 if you want to exit");

				allExpireCoupons=(List<Coupon>) couponsController.getAllExpireCoupons();
				for(int i =0;i<allExpireCoupons.size();i++){
					couponsController.removeCouponById(allExpireCoupons.get(i).getId());
				}
			} catch (ApplicationException e1) {
				System.out.println("Remove dailyCoupon failed: "+e1.getMessage());
			}
			try{
				//Thread.sleep(86400000);//Execute action once per day  
				Thread.sleep(1000);//Execute action every second
			}
			catch(Exception e ){
				System.out.println("Thread sleep failed: "+e.getMessage());
			}
		}	
	}

	

	public void Stop(){
		this.running=false;
	}

}
