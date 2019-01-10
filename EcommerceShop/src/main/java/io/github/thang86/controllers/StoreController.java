package io.github.thang86.controllers;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.Order;
import io.github.thang86.entities.Store;
import io.github.thang86.entities.StoreOwner;
import io.github.thang86.entities.StoreProduct;
import io.github.thang86.enums.Role;
import io.github.thang86.forms.*;
import io.github.thang86.service.*;
import io.github.thang86.utilities.AuthUtil;
import io.github.thang86.utilities.FlashMessages;
import io.github.thang86.validators.*;
import io.github.thang86.viewmodels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

/**
*
*  StoreController.java
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
public class StoreController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private StoreProductService storeProductService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private AuthService authService;

	@Autowired
	private StoreHistoryService storeHistoryService;

	@Autowired
	private AddStoreProductViewModel addStoreProductViewModel;

	@Autowired
	private AddStoreCollaboratorViewModel addStoreCollaboratorViewModel;

	@Autowired
	private StoreProductViewModel storeProductViewModel;

	@Autowired
	private AddCollaboratorFormValidator addCollaboratorFormValidator;

	@Autowired
	private RemoveCollaboratorFormValidator removeCollaboratorFormValidator;

	@Autowired
	private AddStoreProductFormValidator addStoreProductFormValidator;

	@Autowired
	private StoreOwnerDashboardViewModel storeOwnerDashboardViewModel;

	@Autowired
	private StoreOwnerStatisticsViewModel storeOwnerStatisticsViewModel;

	@Autowired
	private AddStoreFormValidator addStoreFormValidator;

	@Autowired
	private UndoHistoryValidator undoHistoryValidator;

	@Autowired
	private AddOrderViewModel addOrderViewModel;

	@Autowired
    private StoreOrdersViewModel storeOrdersViewModel;

	@Autowired
	private StoreHistoryViewModel storeHistoryViewModel;


	@InitBinder("addStoreProductForm")
	public void addBrandFormInitBinder(WebDataBinder binder) {
		binder.addValidators(addStoreProductFormValidator);
	}

	@InitBinder("addStoreCollaboratorForm")
	public void addCollaboratorFormInitBinder(WebDataBinder binder) {
		binder.addValidators(addCollaboratorFormValidator);
	}

	@InitBinder("removeStoreCollaboratorForm")
	public void removeCollaboratorFormInitBinder(WebDataBinder binder) { binder.addValidators(removeCollaboratorFormValidator); }

	@InitBinder("addStoreForm")
	public void addStoreFormInitBinder(WebDataBinder binder) {
		binder.addValidators(addStoreFormValidator);
	}

	@InitBinder("undoHistoryForm")
	public void UndoHistoryInitBinder(WebDataBinder binder) {
		binder.addValidators(undoHistoryValidator);
	}


	@RequestMapping(value = "/store/add", method = RequestMethod.GET)
	public ModelAndView addStore(@ModelAttribute("addStoreForm") AddStoreForm addStoreForm) {
		return new ModelAndView("store/add", "addStoreForm", addStoreForm);
	}

	@RequestMapping(value = "/store/add", method = RequestMethod.POST)
	public ModelAndView addStore(@Valid @ModelAttribute("addStoreForm") AddStoreForm addStoreForm, BindingResult bindingResult, CurrentUser currentUser, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors())
			return new ModelAndView("store/add", "AddStoreForm", addStoreForm);

		Store store = storeService.add(addStoreForm, currentUser.getUser());

		AuthUtil.addRoleAtRuntime(Role.STORE_OWNER);

		FlashMessages.info(store.getName() + " đã được thêm và đang chờ Admin phê duyệt", redirectAttributes);

		return new ModelAndView("redirect:/store/view/" + store.getId());
	}

	@RequestMapping(value = "/store/view/{id}", method = RequestMethod.GET)
	public ModelAndView viewProduct(@PathVariable("id") Long id, CurrentUser currentUser) {
		Optional<Store> storeTmp = storeService.getStoreById(id);

		if (!storeTmp.isPresent())
			return new ModelAndView("error/404");

		Store store = storeTmp.get();

		if (authService.canViewStore(store, currentUser))
			return new ModelAndView("store/view", "store", store);
		else
			return new ModelAndView("error/403");
	}

	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/store/addproduct", method = RequestMethod.GET)
	public ModelAndView addStoreProduct(@ModelAttribute("addStoreProductForm") AddStoreProductForm addStoreProductForm, CurrentUser currentUser) {
		return new ModelAndView("store/addproduct", addStoreProductViewModel.create(addStoreProductForm, currentUser.getId()));
	}


	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/store/addproduct", method = RequestMethod.POST)
	public ModelAndView addStoreProduct(@Valid @ModelAttribute("addStoreProductForm") AddStoreProductForm addStoreProductForm, BindingResult bindingResult, CurrentUser currentUser, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors())
			return new ModelAndView("store/addproduct", addStoreProductViewModel.create(addStoreProductForm, currentUser.getId()));

		StoreProduct storeProduct = storeService.addProductToStore(addStoreProductForm, currentUser.getUser());

		FlashMessages.success("Thành công! " + storeProduct.getProduct().getName() + " đã được thêm vào cửa hàng của bạn!", redirectAttributes);

		return new ModelAndView("redirect:/store/products/" + storeProduct.getId());
	}

	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/store/addcollaborator", method = RequestMethod.GET)
	public ModelAndView addCollaborator(@ModelAttribute("addStoreCollaboratorForm") AddStoreCollaboratorForm addStoreCollaboratorForm, CurrentUser currentUser) {
		return new ModelAndView("store/addcollaborator", addStoreCollaboratorViewModel.create(addStoreCollaboratorForm, currentUser.getId()));
	}


	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/store/addcollaborator", method = RequestMethod.POST)
	public ModelAndView addCollaborator(@Valid @ModelAttribute("addStoreCollaboratorForm") AddStoreCollaboratorForm addStoreCollaboratorForm, BindingResult bindingResult, CurrentUser currentUser, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors())
			return new ModelAndView("store/addcollaborator", addStoreCollaboratorViewModel.create(addStoreCollaboratorForm, currentUser.getId()));

		StoreOwner collaborator = storeService.addCollaboratorToStore(addStoreCollaboratorForm,currentUser.getId());

		if(collaborator!=null)
			FlashMessages.success("Thành công! " + collaborator.getUser().getName() + " đã thêm thành cộng tác viên cho cửa hàng của bạn!", redirectAttributes);

		return new ModelAndView("redirect:/user/storeowner/dashbaord" );
	}

	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/store/removecollaborator", method = RequestMethod.GET)
	public ModelAndView removeCollaborator(@ModelAttribute("removeStoreCollaboratorForm") AddStoreCollaboratorForm addStoreCollaboratorForm, CurrentUser currentUser) {
		return new ModelAndView("store/removecollaborator",addStoreCollaboratorViewModel.create(addStoreCollaboratorForm, currentUser.getId()));
	}


	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/store/removecollaborator", method = RequestMethod.POST)
	public ModelAndView removeCollaborator(@Valid @ModelAttribute("removeStoreCollaboratorForm") AddStoreCollaboratorForm addStoreCollaboratorForm, BindingResult bindingResult, CurrentUser currentUser, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors())
			return new ModelAndView("store/removecollaborator", addStoreCollaboratorViewModel.create(addStoreCollaboratorForm, currentUser.getId()));

		storeService.removeCollaboratorToStore(addStoreCollaboratorForm, currentUser.getId());

		FlashMessages.success("Thành công! " + addStoreCollaboratorForm.getUsername() + " đã xóa khỏi cộng tác viên của cửa hàng!", redirectAttributes);

		return new ModelAndView("redirect:/store/removecollaborator" );
	}

	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/store/statistics", method = RequestMethod.GET)
	public ModelAndView viewStatistics(CurrentUser currentUser) {
		return new ModelAndView("store/statistics", storeOwnerStatisticsViewModel.create(currentUser.getId()));
	}

	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/store/orders", method = RequestMethod.GET)
	public ModelAndView viewOrders(CurrentUser currentUser) {
		return new ModelAndView("store/orders", storeOrdersViewModel.create(currentUser.getId()));
	}

	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value="/store/history",method = RequestMethod.GET)
	public ModelAndView viewHistory(CurrentUser currentUser){
		return new ModelAndView("store/history",storeHistoryViewModel.create(currentUser.getId()));
	}

	@RequestMapping(value = "/store/products/{id}", method = RequestMethod.GET)
	public ModelAndView viewStoreProduct(@PathVariable("id") Long id) {
		Optional<StoreProduct> product = storeProductService.getProductById(id);
		if (!product.isPresent())
			return new ModelAndView("error/404");

		StoreProduct storeProduct = product.get();

		storeProductService.incrementViews(storeProduct);
		productService.incrementViews(storeProduct.getProduct());

		return new ModelAndView("store/storeprodcutview", storeProductViewModel.create(storeProduct));
	}

	@RequestMapping(value = "/store/products/{id}/buy", method = RequestMethod.GET)
	public ModelAndView addOrder(@PathVariable("id") Long id, @ModelAttribute("addOrderForm") AddOrderForm addOrderForm) {
		Optional<StoreProduct> product = storeProductService.getProductById(id);

		if (!product.isPresent())
			return new ModelAndView("error/404");

		return new ModelAndView("store/addorder", addOrderViewModel.create(addOrderForm, product.get()));
	}

	@RequestMapping(value = "/store/products/{id}/buy", method = RequestMethod.POST)
	public ModelAndView addOrder(@PathVariable("id") Long id, @Valid @ModelAttribute("addOrderForm") AddOrderForm addOrderForm, BindingResult bindingResult, CurrentUser currentUser, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors())
			return new ModelAndView("store/addorder", addOrderViewModel.create(addOrderForm, id));

		Optional<StoreProduct> product = storeProductService.getProductById(id);
		Order order = orderService.addOrder(currentUser.getUser(), product.get(), addOrderForm);

		AuthUtil.updateOrders(currentUser.getOrdersCount() + 1);

		FlashMessages.success(product.get().getProduct().getName() + " đã được thêm vào giở hàng", redirectAttributes);
		return new ModelAndView("redirect:/");
	}

	@PreAuthorize("hasAuthority('STORE_OWNER')")
	@RequestMapping(value = "/store/history/undo", method = RequestMethod.POST)
	public ModelAndView acceptStore(@Valid @ModelAttribute("undoHistoryForm") UndoHistoryForm undoHistoryForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, CurrentUser currentUser) {
		if(bindingResult.hasErrors())
			FlashMessages.danger("Lỗi!", redirectAttributes);
		else if(storeHistoryService.undo(undoHistoryForm.getId(), currentUser))
			FlashMessages.success("Đã hoàn tác!", redirectAttributes);
		else {
			FlashMessages.danger("Failed!", redirectAttributes);
			FlashMessages.info("Lỗi hoàn tác vì đã thay đổi thông tin trước .", redirectAttributes);
		}
		return new ModelAndView("redirect:/store/history");
	}

}
