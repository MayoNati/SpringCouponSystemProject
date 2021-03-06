/**
 * 
 */
package com.nati.coupons.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author vexxnati
 *
 */
public class EmailValidator {
	
	
	// Email Regex java
		//private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,7})$";
		private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\."+
		        								  "[a-zA-Z0-9_+&*-]+)*@" +
		        								  "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
		        								  "A-Z]{2,7}$";
	
		

		// static Pattern object, since pattern is fixed
		private static Pattern pattern;

		// non-static Matcher object because it's created from the input String
		private Matcher matcher;

		public EmailValidator() {
			// initialize the Pattern object
			pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		}

		/**
		 * This method validates the input email address with EMAIL_REGEX pattern
		 * 
		 * @param email
		 * @return boolean
		 */
		public boolean validateEmail(String email) {
			matcher = pattern.matcher(email);
			return matcher.matches();
		}
	}


