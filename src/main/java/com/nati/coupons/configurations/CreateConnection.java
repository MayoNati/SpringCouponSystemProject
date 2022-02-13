/**
 * 
 */
package com.nati.coupons.configurations;

import java.sql.SQLException;

import com.nati.coupons.dao.CompanyDAO;

/**
 * @author vexxnati
 *
 */
public class CreateConnection {
	

	private String driver="com.mysql.jdbc.Driver";
	private String URL="jdbc:mysql://localhost:3306/coupons_project?allowPublicKeyRetrieval=true&useSSL=false";
	private String userName="root";
	private String password="root";
	private int initialConnections=1; 
	private int maxConnections=100;
	private boolean waitIfBusy=true;
	
	public CreateConnection(){
		
	}
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public ConnectionPool createNewConncetionPool(){
		
		try {
			ConnectionPool connectionPool = new ConnectionPool(this.driver,this.URL,this.userName,this.password,this.initialConnections, this.maxConnections, this.waitIfBusy);
			return connectionPool;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getInitialConnections() {
		return initialConnections;
	}

	public void setInitialConnections(int initialConnections) {
		this.initialConnections = initialConnections;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public boolean isWaitIfBusy() {
		return waitIfBusy;
	}

	public void setWaitIfBusy(boolean waitIfBusy) {
		this.waitIfBusy = waitIfBusy;
	}
	
	

}
