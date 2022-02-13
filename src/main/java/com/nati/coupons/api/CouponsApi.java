/**
 * 
 */
package com.nati.coupons.api;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.nati.coupons.beans.Company;
import com.nati.coupons.beans.Coupon;
import com.nati.coupons.enums.ClientType;
import com.nati.coupons.enums.CouponType;
import com.nati.coupons.exceptions.ApplicationException;
import com.nati.coupons.logic.CompanyController;
import com.nati.coupons.logic.CouponsController;
import com.nati.coupons.logic.CustomerController;
import com.nati.coupons.utils.utils;

/**
 * @author vexxnati
 *
 */
@Path("/loggedin/coupons")
//Please translate the java obj returned from the server into JSONs
@Produces(MediaType.APPLICATION_JSON)

//The data that comes from the client is a JSON
@Consumes(MediaType.APPLICATION_JSON)
	public class CouponsApi {
		
		private CouponsController couponsController;
		
		
		public CouponsApi() throws ApplicationException{
			this.couponsController=new CouponsController();
		}
		
//		@GET
//		@PathParam("/{couponId}")
//		public Coupon getCoupon(@PathParam("couponId") long id) {	
//			return new Coupon(1,"test","start date","end date",100,CouponType.FOOD, "Desc", 9.9F,"img");
//		}	
		
		
		
	
//		@GET
//		@Path("/{couponId}")
//		public void getCoupon(@PathParam("couponId") long id) throws ApplicationException {
//			System.out.println(id);	
//			//return couponsController.getCouponById(id);
//		}
		
		//Example url : http://localhost:8080/restExampleThatWorks/rest/users/789

		//Create
		@POST
		@Path("/create")
		public void createCoupon(Coupon coupon) throws ApplicationException {
		//	System.out.println("ok");

				System.out.println("my coupon "+coupon.toString());

			//CouponsController couponsController = new CouponsController() ;	
			couponsController.createCoupon(coupon);
			
			//System.out.println(coupon.toString());
		}
		
		//Update
		@PUT
		@Path("/updateCoupon")
		public void updateCoupon(Coupon coupon) throws ApplicationException {
		//	CouponsController couponsController = new CouponsController() ;	
			couponsController.updateCoupon(coupon);
		//	System.out.println(coupon);
		}		
		
		
		@DELETE
		@Path("/removeCoupon")
		public void removeCoupon(@QueryParam("id") long id) throws ApplicationException {
			//CouponsController couponsController=new CouponsController();
			couponsController.removeCouponById(id);
			//System.out.println(id);			
		}

		//Example url : http://localhost:8080/restExampleThatWorks/rest/users/789
		@GET
		@Path("/{couponId}")
		public Coupon getCouponById(@PathParam("couponId") long id) throws ApplicationException {
				Coupon couponById = new Coupon();
				//CouponsController couponsController=new CouponsController();
				couponById=couponsController.getCouponById(id);
				return couponById;
		}
		
		
//		//Example url : http://localhost:8080/restExampleThatWorks/rest/users/byAge?age789
//		@GET
//		@Path("/byAge")
//		public void getCouponByAge(@QueryParam("age") int age) {
//			
//			System.out.println(age);			
//		}
		
		//Purchases a coupon By sending the coupon ID number and ID number of the customer
		@GET
		@Path("/purchasedCoupon")
		public boolean purchasedCoupon(@QueryParam("couponId") long couponId,@QueryParam("customerId")long customerId) throws ApplicationException {
			System.out.println("couponId"+couponId);
			System.out.println("customerId"+customerId);
	
			//	CouponsController couponsController = new CouponsController() ;	
				couponsController.purchasedCoupon(couponId, customerId);
				
				return true;
		}
		
//		//View all coupons details
//		@GET
//		@Path("/getAllCoupons")
//		public Collection<Coupon> getAllCoupons() throws ApplicationException {
//			
//			CouponsController couponsController = new CouponsController() ;	
//
//			List<Coupon> allCoupons = new ArrayList<>();
//					allCoupons=(List<Coupon>) couponsController.getAllCoupons();
//					System.out.println("getAllCoupons works");
//					return allCoupons;
//
////					for(int i=0;i<allCoupons.size();i++){
////						System.out.println(allCoupons.get(i).toString());
////					}
//		}

		//View company coupons by coupon type, up to a certain price, up to a certain date.
		@GET
		@Path("/getCouponByType")
		public Collection<Coupon> getCouponByType(@QueryParam("type") CouponType couponType) throws ApplicationException {		
			
		//	CouponsController couponsController = new CouponsController() ;	

			List<Coupon> allCoupons = new ArrayList<>();
			allCoupons=(List<Coupon>) couponsController.getCouponByType(couponType);
			return allCoupons;
			
//			for(int i=0;i<allCoupons.size();i++){
//				System.out.println(allCoupons.get(i).toString());
//			}
		
		}

		//Viewing the all purchase history
		@GET
		@Path("/getAllPurchasedCoupons")
		public Collection<Coupon> getAllPurchasedCoupons() throws ApplicationException{
		//	CouponsController couponsController = new CouponsController() ;	

			List<Coupon> allCoupons = new ArrayList<>();
			allCoupons=(List<Coupon>) couponsController.getAllPurchasedCoupons();
			return allCoupons;

//			for(int i=0;i<allCoupons.size();i++){
//				System.out.println(allCoupons.get(i).toString());
//			}		
		}

		//Viewing the all coupons purchased by coupon type
		@GET
		@Path("/getAllPurchasedCouponsByType")
		public Collection<Coupon> getAllPurchasedCouponsByType(@QueryParam("type") CouponType type) throws ApplicationException{
		//	CouponsController couponsController = new CouponsController() ;	

			List<Coupon> allCoupons = new ArrayList<>();
			allCoupons=(List<Coupon>) couponsController.getAllPurchasedCouponsByType(type);
			return allCoupons;
		
		}
		
		//View all coupons that are up to a certain price
		@GET
		@Path("/getAllPurchasedCouponsByPrice")
		public Collection<Coupon> getAllPurchasedCouponsByPrice(@QueryParam("price")long price) throws ApplicationException{
		//	CouponsController couponsController = new CouponsController() ;	

			List<Coupon> allCoupons = new ArrayList<>();
			allCoupons=(List<Coupon>) couponsController.getAllPurchasedCouponsByPrice(price);
			return allCoupons;

//			for(int i=0;i<allCoupons.size();i++){
//				System.out.println(allCoupons.get(i).toString());
//			}	
		}
		


		@GET
		@Path("/getAllCouponsUpToDate")
		public Collection<Coupon> getAllCouponsUpToDate(@QueryParam("date")String date) throws ApplicationException{
		//	CouponsController couponsController = new CouponsController() ;	

			List<Coupon> allCoupons = new ArrayList<>();
			allCoupons=(List<Coupon>) couponsController.getAllCouponsUpToDate(date);
			return allCoupons;

//			for(int i=0;i<allCoupons.size();i++){
//				System.out.println(allCoupons.get(i).toString());
//			}	
		}

		@GET
		@Path("/getAllExpireCoupons")
		public Collection<Coupon> getAllExpireCoupons() throws ApplicationException{
		//	CouponsController couponsController = new CouponsController() ;	

			List<Coupon> allCoupons = new ArrayList<>();
			allCoupons=(List<Coupon>) couponsController.getAllExpireCoupons();
			return allCoupons;

//			for(int i=0;i<allCoupons.size();i++){
//				System.out.println(allCoupons.get(i).toString());
//			}
		}
		
					
		@GET
		@Path("/getAllCouponPurchasedByCustomerId")
		public Collection<Coupon> getAllCouponPurchasedByCustomerId(@QueryParam("customerId") long customerId) throws ApplicationException{
			
			System.out.println("getAllCouponPurchasedByCustomerId works, id:"+customerId);

			//CouponsController couponsController = new CouponsController() ;	
			List<Coupon> allCoupons = new ArrayList<>();
			allCoupons=(List<Coupon>) couponsController.getAllPurchasedCouponByCustomerId(customerId);
			return allCoupons;
			//return null;
		}
		
		@GET
		@Path("/getAllCouponsByCompanyId")
		public Collection<Coupon> getAllCouponsByCompanyId(@QueryParam("companyId") long companyId) throws ApplicationException{
			
			System.out.println("getAllCouponsByCompanyId works, id:"+companyId);

		//	CouponsController couponsController = new CouponsController() ;	
			List<Coupon> allCoupons = new ArrayList<>();
			allCoupons=(List<Coupon>) couponsController.getAllCouponsByCompanyId(companyId);
			return allCoupons;
			//return null;
		}
		
		
		@GET
		@Path("/getAllTypesCoupon")
		public Collection<String> getAllTypesCoupon() throws ApplicationException {
			
//			CouponsController couponsController = new CouponsController() ;	
		//	couponsController = new CouponsController() ;	

			List<String> getAllTypesCoupon = new ArrayList<>();
			getAllTypesCoupon=(List<String>) couponsController.getAllTypesCoupon();
					System.out.println("getAllTypesCoupon works");
					return getAllTypesCoupon;					
		}
		
		@GET
		@Path("/unsercured/getAllCoupons")
		public Collection<Coupon> getAllCoupons() throws ApplicationException {
			
//			CouponsController couponsController = new CouponsController() ;	
			//couponsController = new CouponsController() ;	

			List<Coupon> allCoupons = new ArrayList<>();
					allCoupons=(List<Coupon>) couponsController.getAllCoupons();
					System.out.println("getAllCoupons works");
					return allCoupons;					
		}
		
		
		
	
		
}
