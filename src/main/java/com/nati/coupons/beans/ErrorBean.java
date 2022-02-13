/**
 * 
 */
package com.nati.coupons.beans;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author vexxnati
 *
 */
//@XmlRootElement
public class ErrorBean {

	/**
	 * @param internalErrorCode
	 * @param internalMessage
	 * @param externalMessage
	 */
	
	private int internalErrorCode;
	private String internalMessage;
	private String externalMessage;
	
	public ErrorBean(int internalErrorCode, String internalMessage, String externalMessage) {
		
		this.internalErrorCode=internalErrorCode;
		this.externalMessage=externalMessage;
		this.internalMessage=internalMessage;
	}
	
	public ErrorBean() {	
	}
	
	public int getInternalErrorCode() {
		return internalErrorCode;		
	}

	public String getInternalMessage() {
		return internalMessage;
	}

	public void setInternalMessage(String internalMessage) {
		this.internalMessage = internalMessage;
	}

	public String getExternalMessage() {
		return externalMessage;
	}

	public void setExternalMessage(String externalMessage) {
		this.externalMessage = externalMessage;
	}

	public void setInternalErrorCode(int internalErrorCode) {
		this.internalErrorCode = internalErrorCode;
	}
	
	

}
