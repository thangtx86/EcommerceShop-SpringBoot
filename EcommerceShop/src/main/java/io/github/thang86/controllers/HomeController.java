package io.github.thang86.controllers;


import io.github.thang86.repository.UserRepository;
import io.github.thang86.viewmodels.HomePageViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
*
*  HomeController.java
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
public class HomeController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HomePageViewModel homePageModel;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("home/index", homePageModel.create());
	}

}
