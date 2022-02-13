/**
 * 
 */
package com.nati.coupons.exceptions;

import com.nati.coupons.beans.ErrorBean;
import com.nati.coupons.enums.ErrorType;

/**
 * @author vexxnati
 *
 */
public class ApplicationException extends Exception {

	private ErrorType errorType;

	  public ApplicationException() { super(); }
	  public ApplicationException(String message) { super(message); }
	  public ApplicationException(String message, Throwable cause) { super(message, cause); }
	  public ApplicationException(Throwable cause, String string, ErrorType generalError) { super(cause); }
	  public ApplicationException(String string, ErrorType isNotExist) { super(); }

	  public ErrorType getErrorType() {
			return errorType;
		}

	  
	
	
	

}
