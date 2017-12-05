package com.myssm.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myssm.model.User;
import com.myssm.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {

    @Resource  
    private UserService userService; 
    
	public ModelAndView index(){
        ModelAndView mav = new ModelAndView("index"); 
        System.out.println("index");
        User user = userService.selectUserById(1); 
        mav.addObject("user", user); 
		return mav;
	}
	
	@RequestMapping("/l")
	public ModelAndView selectUserById(){
        ModelAndView mav = new ModelAndView("userlist"); 
        User user = userService.selectUserById(1); 
        mav.addObject("user", user); 
		return mav;
	}
	
}