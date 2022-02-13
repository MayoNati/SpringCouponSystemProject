/**
 * 
 */
package com.nati.coupons.threads;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.nati.coupons.beans.Coupon;
import com.nati.coupons.dao.*;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.logic.CouponsController;;

public class DailyCouponExpiredTask extends TimerTask{
	private static int SECONDS_IN_A_DAY = 24 * 60 * 60 * 1000; // *1000 is miliseconds
	//private static int SECONDS_IN_A_DAY = 1000; // *1000 is miliseconds

	
	private CompanyDAO companyDAO; 
	private CouponDAO couponDAO;  
	private CustomerDAO customerDAO;
	private CouponsController couponsController;
	private boolean running = true;


	public DailyCouponExpiredTask() throws ApplicationException {
		this.couponDAO=new CouponDAO();
		this.couponsController = new CouponsController();
		this.running=true;	
		}

	@Override
	public void run(){		
//		List<Coupon> allExpireCoupons = new ArrayList<>();
			
				try {
					couponsController.removeAllExpireCoupons();
//					allExpireCoupons=(List<Coupon>) couponsController.getAllExpireCoupons();
//					for(int i =0;i<allExpireCoupons.size();i++){
//						couponsController.removeCouponById(allExpireCoupons.get(i).getId());
//					}
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
		
		System.out.println("coupons has been removed.");
	}
	
	public void startTask() throws ApplicationException {
		DailyCouponExpiredTask task = new DailyCouponExpiredTask();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, SECONDS_IN_A_DAY);
	}
}