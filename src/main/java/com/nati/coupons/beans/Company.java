/**
 * 
 */
package com.nati.coupons.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

/**
 * @author vexxnati
 *
 */
//@XmlRootElement
public class Company implements java.io.Serializable {


	private long id;
	private String compName;
	private String password;
	private String email;
//	private Collection<Coupon> couponsOfCompnay; 

	private static int test=4;

	public Company(String compName,String password,String email){
		this.compName=compName;
		this.password=password;
		this.email=email;
//		this.couponsOfCompnay = new ArrayList<>();
	}
	
	public Company(String compName,String password){
		this.compName=compName;
		this.password=password;
	}

	public Company(long id,String compName,String password,String email){
		this.id=id;
		this.compName=compName;
		this.password=password;
		this.email=email;
//		this.couponsOfCompnay = new ArrayList<>();
	}

	public Company(long id){
		this.id=id;
	}
	public Company(){
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String validate(String s1,String s2){
		if(s1.equals(this.compName) && s2.equals(this.password))
			return "VALID";
		else
			return "INVALID";
	}


//	public void setCouponOfCompnay(Coupon coupons) {	
//		this.couponsOfCompnay.add(coupons);
//	}
//
//
//	public Collection<Coupon> getAllCouponsOfCompnay() {//Show list of all the coupons that under company
//		return this.couponsOfCompnay;
//	}
//
//
//
//	public void setAllCouponsOfCompnay(Collection<Coupon> couponsOfCompnay) {
//		this.couponsOfCompnay = couponsOfCompnay;
//	}

	public String toString(){

		return "id:"+this.id + 
				", company name: "+this.compName+
				", password:"+this.password+
				", email:"+this.email;
	}


}
