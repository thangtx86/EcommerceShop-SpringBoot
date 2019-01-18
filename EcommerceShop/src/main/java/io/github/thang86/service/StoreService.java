package io.github.thang86.service;


import io.github.thang86.entities.Store;
import io.github.thang86.entities.StoreOwner;
import io.github.thang86.entities.StoreProduct;
import io.github.thang86.entities.User;
import io.github.thang86.forms.AddStoreCollaboratorForm;
import io.github.thang86.forms.AddStoreForm;
import io.github.thang86.forms.AddStoreProductForm;

import java.util.Collection;
import java.util.Optional;

/**
*  StoreService.java
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

public interface StoreService {
	Optional<Store> getStoreById(Long id);

	Optional<Store> getStoreByName(String name);

	Store acceptStore(Long storeId);

	Store rejectStore(Long storeId);

	Collection<Store> getAllStores();

	Collection<Store> getAllAppliedStores();

	Collection<Store> getAllUserAndCollabStores(Long storeOwnerId);

	Collection<Store> getAllAcceptedUserStores(Long storeOwnerId);

	Collection<Store> getAllPendingUserStores(Long storeOwnerId);

	Collection<Store> getAllNotAcceptedUserStores(Long storeOwnerId);

	Collection<Store> getAllCollaboratedUserStores(Long storeOwnerId);

	Store add(AddStoreForm form, User user);

	StoreProduct addProductToStore(AddStoreProductForm form, User user);

	Boolean removeProductFromStore(Long storeProductId, User user);

	StoreOwner addCollaboratorToStore(AddStoreCollaboratorForm form, Long userId);

	void removeCollaboratorToStore(AddStoreCollaboratorForm form, Long userId);
}
