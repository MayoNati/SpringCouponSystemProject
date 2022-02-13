/**
 * 
 */
package com.nati.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nati.coupons.beans.Coupon;
import com.nati.coupons.configurations.ConnectionPool;
import com.nati.coupons.enums.CouponType;
import com.nati.coupons.enums.ErrorType;
import com.nati.coupons.exceptions.ApplicationException;

/**
 * @author vexxnati
 *
 */
public class DaoUtils {

	

	public static boolean removeFromDB(String sqlCommand,long id,ConnectionPool con) throws ApplicationException{
		//function to remove company (id-?)

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try{
			//connection and work on DB to remove company from coupon 
			connection = con.getConnection();
			preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "DaoUtiles->removeFromDB failed", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
		return true;
	}


	public static boolean isNameExist(String sqlCommand,String nameForCheck,ConnectionPool con) throws ApplicationException{
		//get information from DB about specific name (name-?)
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			//connection to DB and extract the name(Name-?) 
			connection = con.getConnection();
			preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, nameForCheck);
			resultSet=preparedStatement.executeQuery();
			//if func. don't find number bigger than 0 it will return false it's mean the name not exist
			while(resultSet.next()){
				String numOfRows=resultSet.getString(1);
				if (numOfRows.equals("0")) {
					return false;	                
				}
			}
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "DaoUtiles->isNameExist failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
		}
		//return true if we found name already exist
		return true;
	}

	
	public static boolean isIdExist(String sqlCommand,long checkId,ConnectionPool con) throws ApplicationException{
		//get information from DB about specific name (name-?)
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			//connection to DB and extract the name(Name-?) 
			connection = con.getConnection();
			preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setLong(1, checkId);
			resultSet=preparedStatement.executeQuery();
			//if func. don't find number bigger than 0 it will return false it's mean the name not exist
			while(resultSet.next()){
				String numOfRows=resultSet.getString(1);
				if (numOfRows.equals("0")) {
					return false;	                
				}
			}
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "DaoUtiles->isNameExist failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
		}
		//return true if we found name already exist
		return true;
	}
	
	
	
	
	
	public static boolean login(String requiredUserName, String requiredUserPassword,String sqlCommand,ConnectionPool con) throws ApplicationException{

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		String userName="";
		String userPassword="";

		try {
			connection = con.getConnection();
			//String sql = "SELECT comp_name,password from coupons_project.company where COMP_NAME=? and PASSWORD=? ";
			preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1,requiredUserName);
			preparedStatement.setString(2,requiredUserPassword);
			resultSet=preparedStatement.executeQuery();

			while(resultSet.next()){

				userName = resultSet.getString(1);
				userPassword= resultSet.getString(2);

				if(requiredUserName.equals(userName) && requiredUserPassword.equals(userPassword))
					return true;
			}

		}//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "DaoUtiles->login", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
		return false;
	}	
	
	
		public static String getIdOfDatafromColuomnName(String sqlCommand,String valueName,ConnectionPool con) throws ApplicationException{
			
				PreparedStatement preparedStatement = null;
				Connection connection = null;
				ResultSet resultSet = null;
				String idOfData="";
					
				try{
						connection = con.getConnection();
						preparedStatement = connection.prepareStatement(sqlCommand);
						preparedStatement.setString(1, valueName);
						resultSet=preparedStatement.executeQuery();
							
						while(resultSet.next()){
							idOfData=resultSet.getString(1);
						}				
					}
					//catch exception that gone wrong for any reason throws up to upper classes to not loose it
					catch (SQLException e) {
						throw new ApplicationException(e, "DaoUtiles->getIdOfDatafromColuomnName failed", ErrorType.GENERAL_ERROR);
					} 
				//to make sure that in the end of process we disconnect from DB  
				finally {
					con.closeDatabaseResources(connection, preparedStatement);
				}
				return idOfData;
		}
		
		
		public static Collection<Coupon> getCoupons(String sqlCommand,ConnectionPool con) throws ApplicationException {	

			List<Coupon> allCoupons = new ArrayList<>();

			PreparedStatement preparedStatement = null;
			Connection connection = null;
			ResultSet resultSet = null;
			Coupon coupon = null;

			try {
				connection = con.getConnection();
				String sql = sqlCommand;
				preparedStatement = connection.prepareStatement(sql);
				resultSet=preparedStatement.executeQuery();

				while (resultSet.next()) {
					coupon = new Coupon();

					coupon.setId(resultSet.getLong("id"));
					coupon.setCompanyId(resultSet.getLong("company_id"));
					coupon.setTitle(resultSet.getString("title"));
					coupon.setStartDate(resultSet.getString("start_date"));
					coupon.setEndDate(resultSet.getString("end_date"));
					coupon.setAmount(resultSet.getInt("amount"));
					coupon.setType(CouponType.valueOf(resultSet.getString("type")));
					coupon.setMessage(resultSet.getString("message"));
					coupon.setPrice(resultSet.getFloat("price"));
					coupon.setImage(resultSet.getString("image"));

					allCoupons.add(coupon);
				}	
			}
			//catch exception that gone wrong for any reason throws up to upper classes to not loose it
			catch (SQLException e) {
				throw new ApplicationException(e, "getAllCompanies failed", ErrorType.GENERAL_ERROR);
			} 
			finally {
				//to make sure that in the end of process we disconnect from DB  
				con.closeDatabaseResources(connection, preparedStatement);
			}
			//get list of all companies
			return allCoupons;		
		}



}
