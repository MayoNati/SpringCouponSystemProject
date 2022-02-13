/**
 * 
 */
package com.nati.coupons.threads;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.nati.coupons.exceptions.ApplicationException;
//import com.sun.corba.se.spi.activation.Server;


/**
 * @author vexxnati
 *
 */
public class TestThreads {

	public static void main(String[] args) throws ApplicationException{

		Scanner scan = new Scanner(System.in);
		
	//	DailyCouponExpirationTask t = new DailyCouponExpirationTask();
//		//Thread t=new Thread(new DailyCouponExpirationTask());
//		t.start();
//		//t.Stop();
//		//t.destroy();
//
//		int exit=scan.nextInt();
//		if(exit==0)
//			t.Stop();
		
		
		DailyCouponExpiredTask dailyCoupon = new DailyCouponExpiredTask();
		dailyCoupon.startTask();
		
		
		// Creating a task 
//				TimerTask timerTask = new DailyCouponTask();
//				
//				// Creating a timer
//				Timer timer = new Timer();
//				
//				// Tell the timer to run the task every 10 seconds, starting of now
//				timer.scheduleAtFixedRate(timerTask, 0, 1000);
//				
//				System.out.println("TimerTask started");
//
//				try {
//					// 10 seconds delay before canceling the task
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//				// Removing the task
//				timer.cancel();
//				System.out.println("TimerTask cancelled");
	}
}
