/**
 * 
 */
package com.nati.coupons.configurations;

/**
 * @author vexxnati
 *
 */
public class Tester {

	public static void main(String[] args){
	
		String driver="com.mysql.jdbc.Driver";
		String URL="jdbc:mysql://localhost:3306/coupons_project";
		String userName="root";
		String password="root";
		int ini=5;
		int maxCon=10;
		Boolean waitForBusy=true;
		
		try {
			ConnectionPool connPoll = new ConnectionPool(driver,URL,userName,password,ini,maxCon,waitForBusy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
