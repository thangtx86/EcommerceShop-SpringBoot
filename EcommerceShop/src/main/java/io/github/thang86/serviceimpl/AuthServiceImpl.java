package io.github.thang86.serviceimpl;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.Store;
import io.github.thang86.enums.Role;
import io.github.thang86.enums.StoreStatus;
import io.github.thang86.service.AuthService;
import org.springframework.stereotype.Service;

/**
*  AuthServiceImpl.java
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

@Service
public class AuthServiceImpl implements AuthService {

	@Override
	public boolean canAccessUser(CurrentUser currentUser, int userId) {
		return currentUser != null
				&& (currentUser.getRole().contains(Role.ADMIN) || currentUser.getId() == userId);
	}

	@Override
	public boolean canViewStore(Store store, CurrentUser currentUser) {
		return (store.getStatus() == StoreStatus.ACCEPTED
				|| currentUser.getRole().contains(Role.ADMIN)
				|| store.getStoreOwner().getId() == currentUser.getId());
	}

	@Override
	public boolean canAccessStore(Store store, CurrentUser currentUser) {
		return store.getStoreOwner().getId() == currentUser.getId()
				|| (currentUser.getUser().getStoreOwner() != null && store.getCollaborators().contains(currentUser.getUser().getStoreOwner()));
	}

}
