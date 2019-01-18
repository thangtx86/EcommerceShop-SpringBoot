package io.github.thang86.validators;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.Order;
import io.github.thang86.enums.OrderStatus;
import io.github.thang86.forms.FinishOrderForm;
import io.github.thang86.service.AuthService;
import io.github.thang86.service.OrderService;
import io.github.thang86.utilities.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
*  FinishOrderValidator.java
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
public class FinishOrderValidator implements Validator {

	@Autowired
	OrderService orderService;

	@Autowired
	AuthService authService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(FinishOrderForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FinishOrderForm finishOrderForm = (FinishOrderForm) target;

		
		if(errors.hasErrors())
			return;

		Optional<Order> orderOptional = orderService.getOrderById(finishOrderForm.getOrderId());

		if(!orderOptional.isPresent()) {
			errors.rejectValue("orderId", "NotValid", "Đơn hàng không tồn tại!");
			return;
		}

		Order order = orderOptional.get();
		if(order.getOrderStatus() == OrderStatus.DELIVERED || order.getOrderStatus() == OrderStatus.UNPROCESSED){
			errors.rejectValue("orderId", "NotValid", "Đơn hàng không hợp lệ");
			return;
		}

		CurrentUser currentUser = AuthUtil.getCurrentUser();
		if(!authService.canAccessStore(order.getStoreProduct().getStore(), currentUser)){
			errors.rejectValue("orderId", "msg.NotAuthorized", "Bạn không được ủy quyền để làm điều này!");
		}

	}


}
