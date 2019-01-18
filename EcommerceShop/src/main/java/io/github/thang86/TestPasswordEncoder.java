package io.github.thang86;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
*  TestPasswordEncoder.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2019-01-17    ThangTX     Create
*/

public class TestPasswordEncoder {

	public static void main(String[] args) {
		String password = "123456";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String  pass = passwordEncoder.encode(password);
		System.err.println(pass);

	}

}
