/**
 * 
 */
package com.nati.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.beans.Customer;
import com.nati.coupons.configurations.ConnectionPool;
import com.nati.coupons.configurations.CreateConnection;
import com.nati.coupons.enums.CouponType;
import com.nati.coupons.enums.ErrorType;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.utils.utils;

/**
 * @author vexxnati
 *
 */
public class CustomerDAO implements ICustomerDAO {


	private ConnectionPool con;

	public CustomerDAO() throws ApplicationException{
		CreateConnection newConnection=new CreateConnection();
		try {
			ConnectionPool connectionPool= new ConnectionPool(newConnection.getDriver(),newConnection.getURL(),newConnection.getUserName(),newConnection.getPassword(),newConnection.getInitialConnections(),newConnection.getMaxConnections(),newConnection.isWaitIfBusy());
			this.con=connectionPool;
		} catch (SQLException e) {
			throw new ApplicationException(e, "Conniction failed", ErrorType.GENERAL_ERROR);

		}
	}


	@Override
	public void createCustomer(Customer customer) throws ApplicationException {
		//function to add/create new customer
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			//connection and work on DB to create customer
			connection = con.getConnection();
			String sql = "INSERT INTO coupons_project.customers (customer_name,password) VALUES (?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getCustName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.executeUpdate();
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "createCustomer failed", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
	}


	@Override
	public void removeCustomerById(long id) throws ApplicationException {

		String sqlCommand = "delete from coupons_project.customers where id=?";
		if(!DaoUtils.removeFromDB(sqlCommand, id, this.con))
			throw new ApplicationException("removeCustomerById failed", ErrorType.GENERAL_ERROR);		
	}


	public void removeAllPursachesCoupons(long customerId) throws ApplicationException{
		//function to remove all coupons that purchased from specific company (id-?)

		String sqlCommand = "delete from coupons_project.customer_coupon where id=?";
		if(!DaoUtils.removeFromDB(sqlCommand, customerId, this.con))
			throw new ApplicationException("removeAllPursachesCoupons failed", ErrorType.GENERAL_ERROR);	
	}

	@Override
	public void updateCustomer(Customer customer) throws ApplicationException {
		//function to update customer
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			//connection and work on DB to update customer
			connection = con.getConnection();
			String sql= "UPDATE coupons_project.customers SET  customer_name=?, password=? WHERE id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getCustName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setLong(3, customer.getId());
			preparedStatement.executeUpdate();						
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "updateCustomer failed", ErrorType.GENERAL_ERROR);
		} 
		//to make sure that in the end of process we disconnect from DB  
		finally {
			con.closeDatabaseResources(connection, preparedStatement);
		}
	}



	@Override
	public Customer getCustomerById(long customerId)  throws ApplicationException {
		//get all information from DB about customer (id-?)
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Customer customer = new Customer();

		try {
			//connection to DB and extract customer(Id-?) data
			connection = con.getConnection();
			String sql = "select * from coupons_project.customers where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			resultSet=preparedStatement.executeQuery();

			//if func. don't find customer that id-? it will return null
			if (!resultSet.next()) {
				return null;
			}

			//absorbing data coupon into an object
			customer = extractCustomerFromResultSet(resultSet);
		}
		//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getCustomerById failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
		}
		//get object coupon
		return customer;
	}


	private Customer extractCustomerFromResultSet(ResultSet resultSet) throws ApplicationException {

		Customer customer = new Customer();      
		try {
			customer.setId(resultSet.getLong("id"));
			customer.setCustName(resultSet.getString("customer_name"));
			customer.setPassword(resultSet.getString("password"));

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			throw new ApplicationException(e, "extractCustomerFromResultSet failed ", ErrorType.GENERAL_ERROR);
		}	
		return customer;
	}



	@Override
	public Collection<Customer> getAllCustomers() throws ApplicationException {

		List<Customer> allCustomers = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = con.getConnection();
			String sql = "select * from coupons_project.customers";
			preparedStatement = connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();


			while(resultSet.next()){
				allCustomers.add(extractCustomerFromResultSet(resultSet));
			}


		}//catch exception that gone wrong for any reason throws up to upper classes to not loose it
		catch (SQLException e) {
			throw new ApplicationException(e, "getAllCustomers failed", ErrorType.GENERAL_ERROR);
		} 
		finally {
			//to make sure that in the end of process we disconnect from DB  
			con.closeDatabaseResources(connection, preparedStatement);
		}
		//get list of all customer
		return allCustomers;
	}


	@Override
	public Collection<Coupon> getCoupons() throws ApplicationException {
		String sqlCommand = "select *from coupons_project.coupons";
		return DaoUtils.getCoupons(sqlCommand, con);
	}


	@Override
	public boolean login(String requiredCustomerName, String requiredCustomerPassword) throws ApplicationException {		
		String sqlCommand = "SELECT customer_name,password from coupons_project.customers where customer_name=? and password=?  ";
		return DaoUtils.login(requiredCustomerName, requiredCustomerPassword, sqlCommand, this.con);	
	}	


	public boolean isCustomerNameExist(String customerName) throws ApplicationException{
		String sqlCommand = "SELECT COUNT(*) FROM coupons_project.customers WHERE customer_name=?";
		if(DaoUtils.isNameExist(sqlCommand, customerName, this.con))
			return true;
		return false;
	}

	public boolean isIdExist(long id) throws ApplicationException{
		if(getCustomerById(id)==null)
			return false;
		return true;
	}
	
	
	
	public Customer getCustomerByNameAndPassword(String name,String password) throws ApplicationException {
		

				Customer customer = new Customer();
				PreparedStatement preparedStatement = null;
				Connection connection = null;
				ResultSet resultSet = null;

				try {
					connection = con.getConnection();
					String sql = "select *from coupons_project.customers where customer_name=? and password=?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, password);

					resultSet=preparedStatement.executeQuery();

					while (resultSet.next()) {
						customer=extractCustomerFromResultSet(resultSet);
					}
				
				}
				//catch exception that gone wrong for any reason throws up to upper classes to not loose it
				catch (SQLException e) {
					throw new ApplicationException(e, "getCustomerByNameAndPassword failed", ErrorType.GENERAL_ERROR);
				} 
				finally {
					//to make sure that in the end of process we disconnect from DB  
					con.closeDatabaseResources(connection, preparedStatement);
				}
				return customer;
		
	}

	
}
