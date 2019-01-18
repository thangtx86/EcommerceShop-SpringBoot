package io.github.thang86;

import java.io.UnsupportedEncodingException;

/**
*  M.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2019-01-13    ThangTX     Create
*/

public class Mainss {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String czech = "HoÃng Anh Tuáº¥n VÅ©";
        String japanese = "日本語";
        System.out.println(czech);
        System.out.println("UTF-8 czech: " + new String(czech.getBytes("UTF-8")));
        System.out.println("UTF-8 japanese: " + new String(japanese.getBytes("UTF-8")));

        System.out.println("ISO-8859-1 czech: " + new String(czech.getBytes("ISO-8859-1")));
        System.out.println("ISO-8859-1 japanese: " + new String(japanese.getBytes("ISO-8859-1")));

	}

}
