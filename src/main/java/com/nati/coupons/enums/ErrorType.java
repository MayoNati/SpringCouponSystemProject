/**
 * 
 */
package com.nati.coupons.enums;

/**
 * @author vexxnati
 *
 */
public enum ErrorType {
	GENERAL_ERROR, 
	BAD_LOGIN, 
	INVALID_PASSWORD_FORMAT, 
	NO_USER_EXIST;

	/**
	 * @return
	 */
	public int getInternalErrorCode() {
		return 0;
	}

	/**
	 * @return
	 */
	public String getInternalMessage() {
		return null;
	}

	//GENERAL_ERROR1(600, "General error")


}
