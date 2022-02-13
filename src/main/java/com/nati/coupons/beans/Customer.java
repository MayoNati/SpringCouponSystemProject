/**
 * 
 */
package com.nati.coupons.beans;

import java.util.ArrayList;
import java.util.Collection;

//import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

/**
 * @author vexxnati
 *
 */
//@XmlRootElement
public class Customer implements java.io.Serializable {
	
	private long id;
	private String custName;
	private String password;
//	private Collection<Coupon> couponsOfCustomer;

	
	
	public Customer(long id,String custName,String password){
		this.id=id;
		this.custName=custName;
		this.password=password;
//        this.couponsOfCustomer = new ArrayList<>();
	}
	
	
	public Customer(String custName,String password){
		this.custName=custName;
		this.password=password;
//        this.couponsOfCustomer = new ArrayList<>();
	}
	public Customer(String custName){
		this.custName=custName;	
	}
	
	public Customer(long id){
		this.id=id;
	}
	public Customer(){	
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
//	public Collection<Coupon> getAllCouponsOfCustomer() {
//		return this.couponsOfCustomer;
//	}
//	public void setCouponsOfCustomer(Collection<Coupon> couponsOfCustomer) {
//		this.couponsOfCustomer = couponsOfCustomer;
//	}
	
	
//	public void setCouponOfCoupons(Coupon coupons) {	
//		this.couponsOfCustomer.add(coupons);
//	}
	
	public String toString(){

		return "id: "+this.id + 
				", customer name: "+this.custName+
				", password: "+this.password;
	}

}
