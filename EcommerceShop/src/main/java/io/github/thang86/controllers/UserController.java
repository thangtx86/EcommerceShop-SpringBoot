package io.github.thang86.controllers;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.User;
import io.github.thang86.forms.UserCreateForm;
import io.github.thang86.service.OrderService;
import io.github.thang86.service.UserService;
import io.github.thang86.utilities.AuthUtil;
import io.github.thang86.utilities.FlashMessages;
import io.github.thang86.validators.UserCreateFormValidator;
import io.github.thang86.viewmodels.ProfileViewModel;
import io.github.thang86.viewmodels.ShoppingCartModel;
import io.github.thang86.viewmodels.StoreOwnerDashboardViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
*
*  UserController.java
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
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserCreateFormValidator userCreateFormValidator;

	@Autowired
	private StoreOwnerDashboardViewModel storeOwnerDashboardViewModel;

	@Autowired
	private ShoppingCartModel shoppingCartModel;

	@Autowired
	private OrderService orderService;

	@Autowired
    private ProfileViewModel profileViewModel;

	
	@InitBinder("registerForm")
	public void registerFormInitBinder(WebDataBinder binder) {
	
		binder.addValidators(userCreateFormValidator);
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET,produces = "text/plain;charset=UTF-8")
	public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
		return new ModelAndView("user/login", "error", error.isPresent() ? error : null);
	}

	//@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #pathVariable)")
	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/register", method = RequestMethod.GET,produces = "text/plain;charset=UTF-8")
	public ModelAndView register(@ModelAttribute("registerForm") UserCreateForm registerForm) {
		return new ModelAndView("user/register", "registerForm", registerForm);
	}

	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/register", method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public ModelAndView register(@Valid @ModelAttribute("registerForm") UserCreateForm registerForm, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors())
			return new ModelAndView("user/register", "registerForm", registerForm);
	
		/*registerForm.setName("Trần Xuân Thắng");*/
		System.out.println("------------------------------------------");
		System.out.println(registerForm.getName());
		System.out.println("------------------------------------------");
		userService.register(registerForm);
	
		try {
			request.changeSessionId();
			request.login(registerForm.getUsername(), registerForm.getPassword());
		} catch (ServletException e) {
			e.printStackTrace();
		}

		FlashMessages.info("Chào mừng bạn đến với E-Shop! E-Shop rất hân hạnh được phục vụ bạn !", redirectAttributes);


		return new ModelAndView("redirect:/");
	}



	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/user/storeowner/dashbaord", method = RequestMethod.GET)
	public ModelAndView addStoreProduct(CurrentUser currentUser) {
		return new ModelAndView("store/dashboard", storeOwnerDashboardViewModel.create(currentUser.getId()));
	}

    @RequestMapping(value = "/user/view/{username}", method = RequestMethod.GET)
    public ModelAndView viewUser(@PathVariable("username") String username ,CurrentUser currentUser) {
	    Optional<User> targetUser = userService.getUserByUsername(username);

	    if(!targetUser.isPresent())
            return new ModelAndView("error/404");

        return new ModelAndView("user/viewprofile",profileViewModel.create(targetUser.get()));
	}

	@RequestMapping(value = "/user/shoppingcart", method = RequestMethod.GET)
	public ModelAndView shoppingCart(CurrentUser currentUser) {
		return new ModelAndView("user/shoppingcart",shoppingCartModel.create(currentUser.getId()));
	}

	@RequestMapping(value = "/user/shoppingcart", method = RequestMethod.POST)
	public ModelAndView shoppingCart(CurrentUser currentUser,RedirectAttributes redirectAttributes) {
		Integer ordersProcessed = orderService.checkout(currentUser.getId());
		if(ordersProcessed > 0){
			FlashMessages.success("Kiểm tra thành công "+ ordersProcessed + " đơn đặt hàng", redirectAttributes);
			AuthUtil.updateOrders(0);
		}
		else
			FlashMessages.warning("Không có đơn hàng", redirectAttributes);

		return new ModelAndView("redirect:/user/shoppingcart");
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public ModelAndView profile(CurrentUser currentUser) {
		Optional<User> user = userService.getUserById(currentUser.getId());
		return new ModelAndView("user/profile",profileViewModel.create(user.get()));
	}

}
