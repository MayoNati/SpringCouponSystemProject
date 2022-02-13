/**
 * 
 */
package com.nati.coupons.utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.nati.coupons.exceptions.ApplicationException;

/**
 * @author vexxnati
 *
 */
public class utils {
	
	
	
	
	
	
	public static boolean splitDateAndCompareRequestedDateBiggerSqlDate(String requestedDate,String sqlDate){
		
		String[] sqlDateParts = sqlDate.split("/");
		String sqlDay = sqlDateParts[0]; 
		String sqlMonth = sqlDateParts[1]; 
		String sqlYear = sqlDateParts[2]; 

		String[] requestedDateParts = requestedDate.split("/");
		String day = requestedDateParts[0]; 
		String month = requestedDateParts[1]; 
		String year = requestedDateParts[2]; 
		
		if(Integer.valueOf(year) > Integer.valueOf(sqlYear))
			return true;
		else if(Integer.valueOf(year) < Integer.valueOf(sqlYear))
			return false;
		
		else
			if(Integer.valueOf(month) > Integer.valueOf(sqlMonth))
				return true;
			else if(Integer.valueOf(month) < Integer.valueOf(sqlMonth))
				return false;
			else
				if(Integer.valueOf(day) > Integer.valueOf(sqlDay))
					return true;
				else if(Integer.valueOf(day) < Integer.valueOf(sqlDay))
					return false;
				else 
					return true;
		
	}
	
	public static boolean isDateFormatValid(String date) throws ApplicationException{
		
		String[] dateFormat = date.split("/");
		String day = dateFormat[0]; 
		String month = dateFormat[1]; 
		String year = dateFormat[2]; 
		
		for(int i=0;i<day.length();i++) {
			if(i==0 && (day.charAt(i)<'0' || day.charAt(i)>'9'))
				throw new ApplicationException("The day value is invalid-The day value must be have with numbers");
			if(i==1 && (day.charAt(i)<'1' || day.charAt(i)>'9'))
				throw new ApplicationException("The day value is invalid-The day value must be have with numbers");
		}
		for(int i=0;i<month.length();i++) {
			if(i==0 && (month.charAt(i)<'0' || month.charAt(i)>'9'))
				throw new ApplicationException("The month value is invalid-The month value must be have with numbers");
			if(i==1 && (month.charAt(i)<'1' || month.charAt(i)>'9')) {
				throw new ApplicationException("The month value is invalid-The month value must be have with numbers");
			}
		}
		for(int i=0;i<year.length();i++) {
			if(i==0 && year.charAt(i)!='2' )
				throw new ApplicationException("The year value is invalid-The year value must be strat like: 2*** ");
			if(i==1 && year.charAt(i)!='0')
				throw new ApplicationException("The year value is invalid-The year value must be strat like: 20**");
			if(i==2 && (year.charAt(i)<'0' || year.charAt(i)>'9'))
				throw new ApplicationException("The year value is invalid-The year value must be strat like: 200* to 209*");
			if(i==3 && (year.charAt(i)<'0' || year.charAt(i)>'9'))
				throw new ApplicationException("The year value is invalid-The year value must be strat like: 2000 to 2099");
		}
			
			
		if(Integer.valueOf(day)<0 || Integer.valueOf(day)>31)
			throw new ApplicationException("The day value is invalid");
		if(Integer.valueOf(month)<1 || Integer.valueOf(month)>12  )
			throw new ApplicationException("The month value is invalid");
		if(Integer.valueOf(year)<2000 || Integer.valueOf(year)>2100  )
			throw new ApplicationException("The year value is invalid");
		

		return true;
	}
	
	
	public static String getCurrentDate(){
		
		LocalDate localDate = LocalDate.now();		
		return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate);
	}
	

}
