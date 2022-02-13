/**
 * 
 */
package com.nati.coupons.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.Date;


import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.configurations.ConnectionPool;
import com.nati.coupons.configurations.CreateConnection;
import com.nati.coupons.utils.*;
import com.nati.coupons.enums.*;
import com.nati.coupons.exceptions.ApplicationException;


/**
 * @author vexxnati
 *
 */
public class CouponDAO implements ICouponDAO {

	private ConnectionPool con;


	public CouponDAO() throws ApplicationException{

		CreateConnection newConnection=new CreateConnection();
		try {
			ConnectionPool connectionPool= new ConnectionPool(newConnection.getDriver(),newConnection.getURL(),newConnection.getUserName(),newConnection.getPassword(),newConnection.getInitialConnections(),newConnection.getMaxConnections(),newConnection.isWaitIfBusy());
			this.con=connectionPool;
		} catch (SQLException e) {
			throw new ApplicationException(e, "Conniction failed", ErrorType.GENERAL_ERROR);

		}

	}



	@Override
	public void createCoupon(Coupon coupon) throws ApplicationException {
		//function to add/create new coupon
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		//Date start_date,end_date;
		
		try {
			//connection and work on DB to create coupon
			connection = con.getConnection();
			String sql = "INSERT INTO coupons_project.coupons (company_id,title,start_date,end_date,amount,type,message,price,image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, coupon.getCompanyId());
			preparedStatement.setString(2, coupon.getTitle());
			preparedStatement.setString(3, coupon.getStartDate());
			preparedStatement.setString(4, coupon.getEndDate());
			preparedStatement.setInt(5, coupon.getAmount());
			preparedStatement.setString(6, coupon.getType().name());
			preparedStatement.setString(7, coupon.getMessage());
			preparedStatement.setFloat(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImage());
			preparedStatement.executeUpdate();
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "createCoupon failed", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
	}


	@Override
	public void removeCouponById(long id) throws ApplicationException {

		//function to remove coupon (id-?)
		String sqlCommand = "delete from coupons_project.coupons where id=?";
		if(!DaoUtils.removeFromDB(sqlCommand, id, this.con))
			throw new ApplicationException("removeCouponById failed", ErrorType.GENERAL_ERROR);
	}

	public void removeAllCouponsUnderCompanyId(long id) throws ApplicationException{

		//function to remove all coupons under specific company (id-?)
		String sqlCommand = "delete from coupons_project.coupons where company_id=?";

		if(!DaoUtils.removeFromDB(sqlCommand, id, this.con))
			throw new ApplicationException("removeAllCouponsUnderCompanyId failed", ErrorType.GENERAL_ERROR);
	}


	public void removeAllPursachesCouponsToCompanyId(long id) throws ApplicationException{
		//function to remove all coupons that purchased from specific company (id-?)

		String sqlCommand = "delete from coupons_project.customer_coupon where Coupon_ID=?";
		if(!DaoUtils.removeFromDB(sqlCommand, id, this.con))
			throw new ApplicationException("removeAllPursachesCouponsToCompanyId failed", ErrorType.GENERAL_ERROR);	
	}


	public void removeAllExpireCoupons() throws ApplicationException{

		//function to remove all Expire Coupons 

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try{
			//connection and work on DB to remove company from coupon 
			connection = con.getConnection();
			String sqlCommand = "delete from coupons_project.coupons where str_to_date(end_date,'%d/%m/%Y') < NOW()";
			preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.executeUpdate();
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "Delete all ExpireCoupons faild", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
		
		
	}
	
	@Override
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		//function to update coupon
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			//connection and work on DB to update coupon
			connection = con.getConnection();
			String sql= "UPDATE coupons_project.coupons SET  title=?, start_date=? ,end_date=? ,amount=? ,type=? ,message=? ,price=? ,image=? WHERE id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, coupon.getTitle());
			preparedStatement.setString(2, coupon.getStartDate());
			preparedStatement.setString(3, coupon.getEndDate());
			preparedStatement.setInt(4, coupon.getAmount());
			preparedStatement.setString(5, coupon.getType().name());
			preparedStatement.setString(6, coupon.getMessage());
			preparedStatement.setFloat(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImage());	
			preparedStatement.setLong(9, coupon.getId());	

			preparedStatement.executeUpdate();						
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "updateCoupon failed", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
	}


	@Override
	public Coupon getCouponById(long couponId) throws ApplicationException {
		//get all information from DB about coupon (id-?)
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Coupon coupon = new Coupon();

		try {
			//connection to DB and extract coupon(Id-?) data
			connection = con.getConnection();
			String sql = "select * from coupons_project.coupons where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			resultSet=preparedStatement.executeQuery();

			//if func. don't find coupon that id-? it will return null
			if (!resultSet.next()) {
				return null;
			}

			//absorbing data coupon into an object
			coupon = extractCouponFromResultSet(resultSet);
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getCouponById failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
		}
		//get object coupon
		return coupon;
	}

	public boolean isCouponExistById(long couponId) throws ApplicationException{
		if(getCouponById(couponId)==null)
			return false;
		return true;
	}

	private Coupon extractCouponFromResultSet(ResultSet resultSet) throws ApplicationException {

		Coupon coupon = new Coupon();      
		try {
			//coupon.setId(resultSet.getLong("id"));
			coupon.setId(Long.valueOf(resultSet.getString(1)));
			//coupon.setCompanyId(resultSet.getLong("company_id"));
			coupon.setCompanyId(Long.valueOf(resultSet.getString(2)));
			//coupon.setTitle(resultSet.getString("title"));
			coupon.setTitle(resultSet.getString(3));
			//coupon.setStartDate(resultSet.getString("start_date"));
			coupon.setStartDate(resultSet.getString(4));
			//coupon.setEndDate(resultSet.getString("end_date"));
			coupon.setEndDate(resultSet.getString(5));
			//coupon.setAmount(resultSet.getInt("amount"));
			coupon.setAmount(Integer.valueOf(resultSet.getString(6)));
			//coupon.setType(resultSet.getString("type"));
			coupon.setType(CouponType.valueOf(resultSet.getString(7)));
			coupon.setMessage(resultSet.getString(8));
			coupon.setPrice(Float.valueOf(resultSet.getString(9)));
			coupon.setImage(resultSet.getString(10));


		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			throw new ApplicationException(e, "extractCouponFromResultSet failed ", ErrorType.GENERAL_ERROR);
		}	
		return coupon;
	}


	@Override
	public Collection<Coupon> getAllCoupons() throws ApplicationException {

		List<Coupon> allCoupons = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = con.getConnection();
			String sql = "select * from coupons_project.coupons where amount>0";
			preparedStatement = connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();


			while(resultSet.next()){
				allCoupons.add(extractCouponFromResultSet(resultSet));
			}

		}//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getAllCoupons failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
			//con.closeAllConnections();
			
		}
		//get list of all coupons
		return allCoupons;
	}

	
	public Collection<String> getAllTypesCoupon() throws ApplicationException {

		List<String> getAllTypesCoupon = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = con.getConnection();
			String sql = "Select distinct type from coupons_project.coupons";
			preparedStatement = connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();


			while(resultSet.next()){
				getAllTypesCoupon.add(resultSet.getString(1));
				//allTypeCoupons.add(extractCouponFromResultSet(resultSet));
			}

		}//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getAllTypesCoupon failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
			//con.closeAllConnections();
			
		}
		//get list of all coupons
		return getAllTypesCoupon;
	}

	public Collection<Coupon> getAllCouponsByCompanyId(Long companyId) throws ApplicationException {

		List<Coupon> allCoupons = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = con.getConnection();
			String sql = "select * from coupons_project.coupons where company_id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,companyId);

			resultSet=preparedStatement.executeQuery();


			while(resultSet.next()){
				allCoupons.add(extractCouponFromResultSet(resultSet));
			}

		}//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getAllCoupons failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
			//con.closeAllConnections();
			
		}
		//get list of all coupons
		return allCoupons;
	}
	
	@Override
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException {

		//get all information from DB about coupon (id-?)
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		List<Coupon> allCouponsType = new ArrayList<>();


		try {
			//connection to DB and extract coupon(type-?) data
			connection = con.getConnection();
			String sql = "select *from coupons_project.coupons where type=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, couponType.name());
			resultSet=preparedStatement.executeQuery();

			while(resultSet.next()){
				allCouponsType.add(extractCouponFromResultSet(resultSet));
			}


		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getCouponsByType failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
		}
		//get list of coupons
		return allCouponsType;

	}



	public Collection<Coupon> getAllPurchasedCoupons() throws ApplicationException{
		//get all information from DB about coupons are Purchased
		List<Coupon> allCouponsPurchased = new ArrayList<>();


		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			//connection to DB and extract customer_coupon data
			connection = con.getConnection();
			String sql = "select coupon_id from coupons_project.customer_coupon";
			preparedStatement = connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();


			while(resultSet.next()){
				allCouponsPurchased.add(getCouponById(resultSet.getLong("COUPON_ID")));
			}
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getAllPurchasedCoupons failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
		}
		//get object coupon
		return allCouponsPurchased;

	}

	//Get all Purchased Coupon by customer ID
	public Collection<Coupon> getAllPurchasedCouponsByCusomerId(long customerId) throws ApplicationException{
		
		List<Coupon> allPurchasedCoupons = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = con.getConnection();
			 
			
			String sql ="SELECT coupons.* FROM coupons_project.coupons coupons LEFT JOIN coupons_project.customer_coupon couponsl ON  coupons.id = couponsl.coupon_id WHERE couponsl.cust_id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			resultSet=preparedStatement.executeQuery();


			while(resultSet.next()){
				allPurchasedCoupons.add(extractCouponFromResultSet(resultSet));
			}

		}//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getAllCoupons failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
			//con.closeAllConnections();
			
		}
		//get list of all coupons Purchased by customer ID
		return allPurchasedCoupons;
	}
	
//	   SELECT coupons.*
//	     FROM coupons_project.coupons coupons
//	LEFT JOIN coupons_project.customer_coupon couponsl ON  coupons.id = couponsl.coupon_id 
//	    WHERE couponsl.cust_id=6 ;

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType coupontype) throws ApplicationException{
		List<Coupon> allPurchasedCouponsByType = new ArrayList<>();
		List<Coupon> allCouponsPurchased = new ArrayList<>();

		allCouponsPurchased=(List<Coupon>) getAllPurchasedCoupons();

		for(int i = 0; i < allCouponsPurchased.size(); i++) {

			if(allCouponsPurchased.get(i).getType().name().equals(coupontype.name()))
				allPurchasedCouponsByType.add(allCouponsPurchased.get(i));
		}

		return allPurchasedCouponsByType;
	}

	public Collection<Coupon> getAllPurchasedCouponsByPrice(long price) throws ApplicationException{
		List<Coupon> allPurchasedCouponsByPrice = new ArrayList<>();
		List<Coupon> allCouponsPurchased = new ArrayList<>();


		allCouponsPurchased=(List<Coupon>) getAllPurchasedCoupons();

		for(int i = 0; i < allCouponsPurchased.size(); i++) {

			if(allCouponsPurchased.get(i).getPrice()<=price)
				allPurchasedCouponsByPrice.add(allCouponsPurchased.get(i));
		}

		return allCouponsPurchased;
	}

	public Collection<Coupon> getAllExpireCoupons() throws ApplicationException{		
		//get all expire coupons date
		List<Coupon> allCoupons = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = con.getConnection();
			String sql = "select *from coupons_project.coupons where str_to_date(end_date,'%d/%m/%Y') < NOW()";
			preparedStatement = connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();

				while(resultSet.next()){
					allCoupons.add(extractCouponFromResultSet(resultSet));
				}
			

		}//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getAllExpireCoupons failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);	
			con.free(connection);
		}
		//get list of all coupons
		return allCoupons;
	}
	
	
	
	public Collection<Coupon> getAllCouponsUpToDate(String upToDate) throws ApplicationException
	{
		//get all coupons that will end till the date the user send
		List<Coupon> allCoupons = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = con.getConnection();
			String sql = "select *from coupons_project.coupons where  str_to_date(end_date,'%d/%m/%Y') < str_to_date(?,'%d/%m/%Y')";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, upToDate);
			resultSet=preparedStatement.executeQuery();

				while(resultSet.next()){
					allCoupons.add(extractCouponFromResultSet(resultSet));
				}
			
		}//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getAllExpireCoupons failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);			
		}
		//get list of all coupons
		return allCoupons;
	}
	
//	need to finish! 
//	public Collection<Coupon> getAllCouponsUpToDate(String date) throws ApplicationException{
//		List<Coupon> allCouponsUpToDate = new ArrayList<>();
//		List<Coupon> allCoupons = new ArrayList<>();
//		
//		allCoupons=(List<Coupon>) getAllCoupons();
//		
//
//		return null;
//	}



	public void purchasedCoupon(long companyId,long customerId) throws ApplicationException{
		//function to purchase new coupon 
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			//connection and work on DB to purchase coupon
			connection = con.getConnection();
			String sql = "INSERT INTO coupons_project.customer_coupon (cust_id,coupon_id) VALUES (?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, companyId);
			preparedStatement.executeUpdate();
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "purchasedCoupon failed", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
	}

	public void updateCouponAmountAfterPurchase(Coupon coupon) throws ApplicationException{
		//function to update the amount of the coupon

		coupon.setAmount(coupon.getAmount()-1);

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			//connection and work on DB to update amount coupon
			connection = con.getConnection();
			String sql= "UPDATE coupons_project.coupons SET  amount=? WHERE id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, coupon.getAmount());
			preparedStatement.setLong(2, coupon.getId());
			preparedStatement.executeUpdate();						
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "updateAmountCoupon failed", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}

	}


	public long getCouponIdByComapnyId(long companyId) throws ApplicationException{

		String valueName=String.valueOf(companyId);
		long couponId;
		String sqlCommand = "select id from coupons_project.coupons where company_id=?";
		couponId=Long.valueOf(DaoUtils.getIdOfDatafromColuomnName(sqlCommand, valueName, this.con));
		return couponId;
	}

	public boolean isCustomerAlreadyPurcheasedCoupon(long customerId) throws ApplicationException{
		String sqlCommand = "SELECT COUNT(*) FROM coupons_project.customer_coupon WHERE cust_id=?";
		return DaoUtils.isIdExist(sqlCommand, customerId, this.con);
	}

	public boolean isTitleNameExist(String name) throws ApplicationException{
		String commandToSQL = "SELECT COUNT(*) FROM coupons_project.coupons WHERE TITLE=?";
		return DaoUtils.isNameExist(commandToSQL,name,con); 
	}



	/**
	 * @param id
	 */
	public void buyCoupon(long id) {
	
		
//		//function to add/create new coupon
//				PreparedStatement preparedStatement = null;
//				Connection connection = null;
//				
//		        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//				//Date start_date,end_date;
//				
//				try {
//					//connection and work on DB to create coupon
//					connection = con.getConnection();
//					String sql = "select INTO coupons_project.coupons ";
//					preparedStatement = connection.prepareStatement(sql);
//					preparedStatement.setLong(1, coupon.getCompanyId());
//					preparedStatement.executeUpdate();
//				}
//				//catch exception that gone wrong for any reason throws up to upper classes to not loose it
//				catch (SQLException e) {
//					throw new ApplicationException(e, "createCoupon failed", ErrorType.GENERAL_ERROR);
//				} 
//				//to make sure that in the end of process we disconnect from DB  
//				finally {
//					con.closeDatabaseResources(connection, preparedStatement);
//				}
//		
	}



	/**
	 * @param customerID
	 * @return
	 * @throws ApplicationException 
	 */
	public boolean isCustomerAlreadyBoughtCoupon(long customerID) throws ApplicationException {
		String sqlCommand = "SELECT COUNT(*) FROM coupons_project.customer_coupon WHERE cust_id=?";
		return DaoUtils.isIdExist(sqlCommand, customerID, this.con);
	}

	//	public boolean isIdExist(long id) throws ApplicationException{
	//		String commandToSQL = "SELECT COUNT(*) FROM coupons_project.coupons WHERE id=?";
	//		return DaoUtils.isIdExist(commandToSQL, id, con); 
	//	}






}
