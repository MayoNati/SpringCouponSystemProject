/**
 * 
 */
package com.nati.coupons.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.configurations.ConnectionPool;
import com.nati.coupons.configurations.CreateConnection;
import com.nati.coupons.enums.CouponType;
import com.nati.coupons.enums.ErrorType;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.utils.utils;

import java.sql.*;



/**
 * @author vexxnati
 *
 */
public class CompanyDAO implements ICompanyDAO {


	private ConnectionPool con;


	public CompanyDAO(ConnectionPool connectionPool){
		this.con=connectionPool;
	}

	public CompanyDAO() throws ApplicationException{
		CreateConnection newConnection=new CreateConnection();
		try {
			ConnectionPool connectionPool= new ConnectionPool(newConnection.getDriver(),newConnection.getURL(),newConnection.getUserName(),newConnection.getPassword(),newConnection.getInitialConnections(),newConnection.getMaxConnections(),newConnection.isWaitIfBusy());
			this.con=connectionPool;
		} catch (SQLException e) {
			throw new ApplicationException(e, "Conniction failed", ErrorType.GENERAL_ERROR);
		}
	}


	@Override
	public void createCompany(Company company) throws ApplicationException {
		//function to add/create new company
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			//connection and work on DB to create company
			connection = con.getConnection();
			String sql = "INSERT INTO coupons_project.company (comp_name, password, email) VALUES (?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			preparedStatement.executeUpdate();
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "createCompany failed", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
	}

	@Override
	public void removeCompanyById(long id) throws ApplicationException {
		//function to remove company (id-?)
		String sqlCommand = "delete from coupons_project.company where id=?";
		if(!DaoUtils.removeFromDB(sqlCommand, id, this.con))
			throw new ApplicationException("removeCompanyById failed", ErrorType.GENERAL_ERROR);
	}

	public void removeAllCouponsUnderCompanyId(long companyId) throws ApplicationException{
		//function to remove all coupons under specific company (id-?)
		String sqlCommand = "delete from coupons_project.coupons where company_id=?";
		if(!DaoUtils.removeFromDB(sqlCommand, companyId, this.con))
			throw new ApplicationException("removeAllCouponsUnderCompanyId failed", ErrorType.GENERAL_ERROR);
	}


//	public void removeAllPursachesCouponsOfCompanyId(long couponId) throws ApplicationException{
//		//function to remove all coupons that purchased from specific company (id-?)
//		String sqlCommand = "delete from coupons_project.customer_coupon where Coupon_ID=?";
//		if(!DaoUtils.removeFromDB(sqlCommand, couponId, this.con))
//			throw new ApplicationException("removeAllPursachesCouponsToCompanyId failed", ErrorType.GENERAL_ERROR);	
//	}
	
	public void removeAllPursachesCouponsOfCompanyId(long couponId) throws ApplicationException{
		//function to remove all coupons that purchased from specific company (id-?)
		//String sqlCommand = "delete from coupons_project.customer_coupon where Coupon_ID=?";
		String sqlCommand="delete coupons_project.customer_coupon WHERE coupon_id IN (SELECT id from coupons_project.coupons WHERE company_id = ?)";

		if(!DaoUtils.removeFromDB(sqlCommand, couponId, this.con))
			throw new ApplicationException("removeAllPursachesCouponsToCompanyId failed", ErrorType.GENERAL_ERROR);	
	}



	@Override
	public void updateCompany(Company company) throws ApplicationException {
		//function to update company
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			//connection and work on DB to update company
			connection = con.getConnection();
			String sql= "UPDATE coupons_project.company SET COMP_NAME=?, PASSWORD=?, EMAIL=? WHERE id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			preparedStatement.setLong(4, company.getId());		             
			preparedStatement.executeUpdate();						
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "updateCompany failed", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
	}

	@Override
	public Company getCompanyById(long companyId) throws ApplicationException {
		//get all information from DB about company (id-?)
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Company company = new Company();

		try {
			//connection to DB and extract company(Id-?) data
			connection = con.getConnection();
			String sql = "select * from company where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			resultSet=preparedStatement.executeQuery();
			//if func. don't find company that id-? it will return null
			if (!resultSet.next()) {
				return null;
			}

			//absorbing data company into an object
			company = extractCompanyFromResultSet(resultSet);
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getCompanyById failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
		}
		//get object company
		return company;
	}


	@Override
	public Collection<Company> getAllCompanies() throws ApplicationException {
		//get all information from DB about all companies
		List<Company> allCompanies = new ArrayList<>();

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = con.getConnection();
			String sql = "select *from coupons_project.company";
			preparedStatement = connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();

			while (resultSet.next()) {
				allCompanies.add(extractCompanyFromResultSet(resultSet));
			}
			if(allCompanies.isEmpty())
				return null;
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
		return allCompanies;
	}


	public Company getCompanyByNameAndPassword(String name,String password) throws ApplicationException {
	
		//get all information from DB about all companies

				Company company = new Company();
				PreparedStatement preparedStatement = null;
				Connection connection = null;
				ResultSet resultSet = null;

				try {
					connection = con.getConnection();
					String sql = "select *from coupons_project.company where comp_name=? and password=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, password);

					resultSet=preparedStatement.executeQuery();

					while (resultSet.next()) {
						company=extractCompanyFromResultSet(resultSet);
						//allCompanies.add(extractCompanyFromResultSet(resultSet));
					}
				
				}
				//catch exception that gone wrong for any reason throws up to upper classes to not loose it
				catch (SQLException e) {
					throw new ApplicationException(e, "getCompanyByNameAndPassword failed", ErrorType.GENERAL_ERROR);
				} 
				finally {
					//to make sure that in the end of process we disconnect from DB  
					con.closeDatabaseResources(connection, preparedStatement);
				}
				//get list of all companies
				return company;	
	}

	@Override
	//get all Coupons
	public Collection<Coupon> getCoupons() throws ApplicationException {	
		String sqlCommand = "select *from coupons_project.coupons";
		return DaoUtils.getCoupons(sqlCommand, con);
	}

	//get coupon id by company id 
	public long getCouponIdByCompanyId(long companyId) throws ApplicationException{
		String valueName=String.valueOf(companyId);
		long couponId;
		String sqlCommand = "select id from coupons_project.coupons where company_id=?";
		couponId=Long.valueOf(DaoUtils.getIdOfDatafromColuomnName(sqlCommand, valueName, this.con));
		return couponId;
	}

	public boolean isCompanyExistById(long companyId) throws ApplicationException{
		if(getCompanyById(companyId)==null)
			return false;
		return true;
	}

	public boolean isCompanyIdExistInCoupons(long companyId) throws ApplicationException{
		String sqlCommand = "SELECT COUNT(*) FROM coupons_project.coupons where company_id=?";
		return DaoUtils.isIdExist(sqlCommand,companyId,con);
	}


	public boolean isCompanyNameExist(String companyName) throws ApplicationException{
		String sqlCommand = "SELECT COUNT(*) FROM coupons_project.company WHERE comp_name=?";

		if(DaoUtils.isNameExist(sqlCommand, companyName, this.con))
			return true;
		return false;		
	}



	@Override
	public boolean login(String requiredUserName, String requiredUserPassword) throws ApplicationException {

		String sqlCommand = "SELECT comp_name,password from coupons_project.company where COMP_NAME=? and PASSWORD=? ";
		return DaoUtils.login(requiredUserName, requiredUserPassword, sqlCommand, this.con);

	}	



	public void updatePassword(long companyId,String newPassword) throws ApplicationException{

		String sqlUpdatePassword="PASSWORD";
		if(!updatePasswordOrEmail(companyId,newPassword,sqlUpdatePassword))
			throw new ApplicationException("updatePassword failed", ErrorType.GENERAL_ERROR);

	}
	public void updateEmail(long companyId,String newEmail) throws ApplicationException{
		String sqlUpdateEmail="EMAIL";
		if(!updatePasswordOrEmail(companyId,newEmail,sqlUpdateEmail))
			throw new ApplicationException("updateEmail failed", ErrorType.GENERAL_ERROR);


	}

	private boolean updatePasswordOrEmail(long companyId,String newValue,String updatePassOrEmail){

		//function to update company
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			//connection and work on DB to update company
			connection = con.getConnection();
			String sql= "UPDATE coupons_project.company SET "+ updatePassOrEmail +"=? WHERE id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newValue);
			preparedStatement.setLong(2, companyId);		             
			preparedStatement.executeUpdate();	
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			return false;
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
		return true;

	}

	private Company extractCompanyFromResultSet(ResultSet resultSet) throws ApplicationException {

		Company company = new Company();      
		try {
			company.setId(resultSet.getLong("id"));
			//company.setId(Long.valueOf(resultSet.getString(1)));
			company.setCompName(resultSet.getString("comp_name"));
			//company.setCompName(resultSet.getString(2));
			company.setPassword(resultSet.getString("password"));
			//company.setPassword(resultSet.getString(3));
			company.setEmail(resultSet.getString("email"));
			//company.setEmail(resultSet.getString(4));

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			throw new ApplicationException(e, "extractCompanyFromResultSet failed ", ErrorType.GENERAL_ERROR);
		}	
		return company;
	}

	//	private boolean removeFromDB(String sql,long id) throws ApplicationException{
	//		//function to remove company (id-?)
	//		
	//				PreparedStatement preparedStatement = null;
	//				Connection connection = null;
	//				try{
	//						//connection and work on DB to remove company from coupon 
	//						connection = con.getConnection();
	//						preparedStatement = connection.prepareStatement(sql);
	//						preparedStatement.setLong(1, id);
	//						preparedStatement.executeUpdate();
	//					}
	//					//catch exception that gone wrong for any reason throws up to upper classes to not loose it
	//					catch (SQLException e) {
	//						throw new ApplicationException(e, "removeFromDB failed", ErrorType.GENERAL_ERROR);
	//					} 
	//				//to make sure that in the end of process we disconnect from DB  
	//				finally {
	//					con.closeDatabaseResources(connection, preparedStatement);
	//				}
	//				return true;
	//	}







}
