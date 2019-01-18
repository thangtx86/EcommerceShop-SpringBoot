package io.github.thang86.service;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.Store;

/**
*  AuthService.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-20    ThangTX     Create
*/

public interface AuthService {

	boolean canAccessUser(CurrentUser currentUser, int userId);


	boolean canViewStore(Store store, CurrentUser currentUser);

	boolean canAccessStore(Store store, CurrentUser currentUser);
}
