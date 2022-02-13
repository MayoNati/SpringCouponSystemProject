/**
 * 
 */
package com.nati.coupons.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.nati.coupons.beans.ErrorBean;

/**
 * @author vexxnati
 *
 */
@Provider
public class ExceptionsHandler implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable exception) 
	{
		if (exception instanceof ApplicationException){
			ApplicationException e = (ApplicationException) exception;

			int internalErrorCode = e.getErrorType().getInternalErrorCode();
			String internalMessage = e.getMessage();		
//			System.out.println("internalMessage:"+internalMessage);
			String externalMessage = e.getErrorType().getInternalMessage();	
//			System.out.println("externalMessage:"+externalMessage);
			ErrorBean errorBean = new ErrorBean(internalErrorCode, internalMessage, externalMessage);
			return Response.status(internalErrorCode).entity(errorBean).build();
			
		}
		else if(exception instanceof Exception) {
			String internalMessage = exception.getMessage();
			ErrorBean errorBean = new ErrorBean(601, internalMessage, "General error");
			return Response.status(601).entity(errorBean).build();
		}
		return Response.status(500).entity(null).build();
	}


}
