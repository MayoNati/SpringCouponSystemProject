/**
 * 
 */
package com.nati.coupons.beans;

import java.sql.Date;

//import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import com.nati.coupons.enums.*;


/**
 * @author vexxnati
 *
 */

//@XmlRootElement
public class Coupon implements java.io.Serializable {
	
	private CouponType type; 
	private long id;
	private long companyId;
	private String title;
	private String startDate;
	private String endDate;
	private int amount;
	private String message;
	private float price;
	private String image;

	
	public Coupon(long id,long companyId,String title,String startDate,String endDate,int amount,CouponType type,String message,float price,String image){
		this.id=id;
		this.companyId=companyId;
		this.title=title;
		this.startDate=startDate;
		this.endDate=endDate;
		this.amount=amount;
		this.type=type;
		this.message=message;
		this.price=price;
		this.image=image;	
		
		
	}
	
	

	public Coupon(long compId,String title,String startDate,String endDate,int amount,CouponType type,String message,float price,String image){
		this.title=title;
		this.companyId=compId;
		this.startDate=startDate;
		this.endDate=endDate;
		this.amount=amount;
		this.type=type;
		this.message=message;
		this.price=price;
		this.image=image;	
	}
	
	public Coupon(long compId,CouponType type,String message){
		this.companyId=compId;
		this.type=type;
		this.message=message;
	}
	

	public Coupon(long id,CouponType type){
		this.id=id;
		this.type=type;
	}
	public Coupon(String message){
		this.message=message;
	}
	public Coupon(long compId){
		this.companyId=compId;
	}
	
	public Coupon(){
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String start_date) {
		this.startDate = start_date;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String start_end) {
		this.endDate = start_end;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String toString(){
		
		
		return "id:"+this.id + 
			   ", company_id: "+this.companyId+
			   ", title: "+this.title+
			   ", start date: "+this.startDate +
			   ", end date: "+ this.endDate +
			   ", amount: "+this.amount+
			   ", type: "+this.type+
			   ", message: "+this.message+
			   ", price: "+this.price+
			   ", image: "+this.image;
	}
	
	

}
