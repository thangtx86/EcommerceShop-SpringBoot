package io.github.thang86.validators;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.PhysicalProduct;
import io.github.thang86.entities.Product;
import io.github.thang86.entities.Store;
import io.github.thang86.entities.VirtualStore;
import io.github.thang86.enums.StoreStatus;
import io.github.thang86.forms.AddStoreProductForm;
import io.github.thang86.service.AuthService;
import io.github.thang86.service.ProductService;
import io.github.thang86.service.StoreService;
import io.github.thang86.utilities.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
*  AddStoreProductFormValidator.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2019-01-02    ThangTX     Create
*/

@Component
public class AddStoreProductFormValidator implements Validator {
	@Autowired
	private ProductService productService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private AuthService authService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AddStoreProductForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AddStoreProductForm form = (AddStoreProductForm) target;

		if(errors.hasErrors())
			return;

		Optional<Product> productOptional = productService.getProductById(form.getProductId());
		if(!productOptional.isPresent()) {
			errors.rejectValue("productId", "NotValid");
			return;
		}

		Optional<Store> storeOptional = storeService.getStoreById(form.getStoreId());
		if(!storeOptional.isPresent()) {
			errors.rejectValue("storeId", "NotValid");
			return;
		}

		Product product = productOptional.get();
		Store store = storeOptional.get();

		CurrentUser currentUser = AuthUtil.getCurrentUser();
		if(!authService.canAccessStore(store, currentUser))
			errors.rejectValue("storeId","msg.NotAuthorized");

		if(store.getStatus() != StoreStatus.ACCEPTED)
			errors.rejectValue("storeId","msg.AcceptedStore");

		//Kiem tra Type
		if(store instanceof VirtualStore && product instanceof PhysicalProduct)
			errors.rejectValue("productId", "msg.VirtualStoreProducts");

	}
}
