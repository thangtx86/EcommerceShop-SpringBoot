package io.github.thang86.controllers;


import io.github.thang86.entities.Order;
import io.github.thang86.forms.FinishOrderForm;
import io.github.thang86.service.OrderService;
import io.github.thang86.utilities.FlashMessages;
import io.github.thang86.validators.FinishOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
*
*  OrderController.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2019-01-04    ThangTX     Create
*
*/

@Controller
public class OrderController {
	/////////////////////////*  SERVICES, REPOSITORIES AND VALIDATORS SECTION  */////////////////////////////
	@Autowired
	private OrderService orderService;


	@Autowired
	private FinishOrderValidator finishOrderValidator;
	
	@InitBinder("finishOrderForm")
	public void addFormInitBinder(WebDataBinder binder) {
		binder.addValidators(finishOrderValidator);
	}



	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/order/finish", method = RequestMethod.POST)
	public ModelAndView acceptStore(@Valid @ModelAttribute("finishOrderForm") FinishOrderForm finishOrderForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
			FlashMessages.danger("Lỗi!", redirectAttributes);

		} else {
			Order order = orderService.finishOrder(finishOrderForm.getOrderId());
			FlashMessages.success("Đơn hàng đã được giao!", redirectAttributes);
		}
		return new ModelAndView("redirect:/store/orders");
	}

}
