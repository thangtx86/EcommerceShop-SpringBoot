package io.github.thang86.controllers;

import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.*;
import io.github.thang86.forms.*;
import io.github.thang86.service.*;
import io.github.thang86.utilities.FlashMessages;
import io.github.thang86.validators.*;
import io.github.thang86.viewmodels.AddProductViewModel;
import io.github.thang86.viewmodels.DemoteAdminViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

/**
*
*  AdminController.java
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
public class AdminController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

    @Autowired
    private AddBrandFormValidator brandFormValidator;

    @Autowired
    private AddProductFormValidator addProductFormValidator;

	@Autowired
	private AddCompanyFormValidator addCompanyFormValidator;

	@Autowired
	private PromoteAdminFormValidator promoteAdminFormValidator;

	@Autowired
	private DemoteAdminFormValidator demoteAdminFormValidator;

	@Autowired
	private AddProductViewModel addProductViewModel;

	@Autowired
	private DemoteAdminViewModel demoteAdminViewModel;


    @InitBinder("addBrandForm")
    public void addBrandFormInitBinder(WebDataBinder binder) {
        binder.addValidators(brandFormValidator);
    }

    @InitBinder("addProductForm")
    public void AddProductFormInitBinder(WebDataBinder binder){
        binder.addValidators(addProductFormValidator);
    }

	@InitBinder("addCompanyForm")
	public void AddCompanyFormInitBinder(WebDataBinder binder){
		binder.addValidators(addCompanyFormValidator);
	}

	@InitBinder("promoteAdminForm")
	public void promoteAdminFormInitBinder(WebDataBinder binder){
		binder.addValidators(promoteAdminFormValidator);
	}

	@InitBinder("demoteAdminForm")
	public void demoteAdminFormInitBinder(WebDataBinder binder){
		binder.addValidators(demoteAdminFormValidator);
	}



    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin/addbrand", method = RequestMethod.GET)
    public ModelAndView addBrand(@ModelAttribute("addBrandForm") AddBrandForm addBrandForm) {
        return new ModelAndView("admin/addbrand", "addBrandForm", addBrandForm);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin/addbrand", method = RequestMethod.POST)
    public ModelAndView addBrand(@Valid @ModelAttribute("addBrandForm")AddBrandForm addBrandForm, BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors())
            return new ModelAndView("admin/addbrand","addBrandForm",addBrandForm);

        Brand brand = brandService.addBrand(addBrandForm);

	    FlashMessages.success("Thành công! Thương hiệu: " + brand.getName() + " Đã được thêm!", redirectAttributes);


	    return new ModelAndView("redirect:/admin/addbrand");
    }

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/addcompany", method = RequestMethod.GET)
	public ModelAndView addCompany(@ModelAttribute("addCompanyForm") AddCompanyForm addCompanyForm) {
		return new ModelAndView("admin/addcompany", "addCompanyForm", addCompanyForm);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/addcompany", method = RequestMethod.POST)
	public ModelAndView addCompany(@Valid @ModelAttribute("addCompanyForm")AddCompanyForm addCompanyForm, BindingResult bindingResult, RedirectAttributes redirectAttributes)
	{
		if(bindingResult.hasErrors())
			return new ModelAndView("admin/addcompany","addCompanyForm",addCompanyForm);

		Company company = companyService.addCompany(addCompanyForm);

		FlashMessages.success("Thành công! Công ty: " + company.getName() + " đã được thêm!", redirectAttributes);

		return new ModelAndView("redirect:/admin/addcompany");
	}

	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin/addproduct", method = RequestMethod.GET)
    public ModelAndView addProduct(@ModelAttribute("addProductForm") AddProductForm addProductForm) {
	   return new ModelAndView("admin/addproduct", addProductViewModel.create(addProductForm));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin/addproduct", method = RequestMethod.POST)
    public ModelAndView addProduct(@Valid @ModelAttribute("addProductForm") AddProductForm addProductForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
	        return new ModelAndView("admin/addproduct", addProductViewModel.create(addProductForm));

        Product product = productService.addProduct(addProductForm);

	    FlashMessages.success("Thành công! Sản phẩm " + product.getName() + " đã được thêm thành công!", redirectAttributes);

	    return new ModelAndView("redirect:/product/view/"+product.getId());
    }

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/acceptstores", method = RequestMethod.GET)
	public ModelAndView viewAppliedStore() {
		Collection<Store> stores = storeService.getAllAppliedStores();
		return new ModelAndView("admin/acceptStoreList", "stores", stores);
	}

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin/acceptstores/{id}", method = RequestMethod.GET)
    public ModelAndView viewAndAcceptStore(@PathVariable("id") Long id) {
        Optional<Store> store = storeService.getStoreById(id);  
        if (!store.isPresent()) {
            return new ModelAndView("error/404");
        }

        return new ModelAndView("admin/acceptstore", "store", store);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin/acceptstores", method = RequestMethod.POST)
    public ModelAndView acceptStore(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {

        Store store = storeService.acceptStore(id);

	    FlashMessages.success("Thành công! Cửa hàng: " + store.getName() + " đã được thêm thành công!", redirectAttributes);

        return new ModelAndView("redirect:/admin/acceptstores");
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin/rejectstores/{id}", method = RequestMethod.GET)
    public ModelAndView viewAndRejectStore(@PathVariable("id") Long id) {
        Optional<Store> store = storeService.getStoreById(id);

        if (!store.isPresent()) {
            return new ModelAndView("error/404");
        }

        return new ModelAndView("admin/rejectstore", "store", store);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin/rejectstores", method = RequestMethod.POST)
    public ModelAndView rejectStore(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {

        Store store = storeService.rejectStore(id);

        FlashMessages.success("Thành công! Cửa hàng: " + store.getName() + " đã bị từ chối!", redirectAttributes);

        return new ModelAndView("redirect:/admin/acceptstores");
    }
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/promote", method = RequestMethod.GET)
	public ModelAndView promoteAdmin(@ModelAttribute("promoteAdminForm") PromoteAdminForm promoteAdminForm, CurrentUser currentUser) {
		return new ModelAndView("admin/promote");
	}


	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/promote", method = RequestMethod.POST)
	public ModelAndView promoteAdmin(@Valid @ModelAttribute("promoteAdminForm") PromoteAdminForm promoteAdminForm, BindingResult bindingResult, CurrentUser currentUser, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors())
			return new ModelAndView("admin/promote");

		User user = userService.promoteAdmin(promoteAdminForm,currentUser.getUser());

		if(user != null) {
			FlashMessages.success("Thành công! " + user.getName() + " đang là Quản trị viên!", redirectAttributes);

		}

		return new ModelAndView("redirect:/admin/promote" );
	}


	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/demote", method = RequestMethod.GET)
	public ModelAndView demoteAdmin(@ModelAttribute("demoteAdminForm") DemoteAdminForm demoteAdminForm, CurrentUser currentUser) {
		return new ModelAndView("admin/demote", demoteAdminViewModel.create(demoteAdminForm, currentUser.getId()));
	}


	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/demote", method = RequestMethod.POST)
	public ModelAndView demoteAdmin(@Valid @ModelAttribute("demoteAdminForm") DemoteAdminForm demoteAdminForm, BindingResult bindingResult, CurrentUser currentUser, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors())
			return new ModelAndView("admin/demote", demoteAdminViewModel.create(demoteAdminForm, currentUser.getId()));

		User user = userService.demoteAdmin(demoteAdminForm,currentUser.getUser());

		if(user != null)
			FlashMessages.success("Thành công! " + user.getName() + " không còn là Admin!", redirectAttributes);

		return new ModelAndView("redirect:/admin/demote" );
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/dashbaord", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		return new ModelAndView("admin/dashboard");
	}

}
