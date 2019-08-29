package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/page/login")
	public String login(String redicturl,Model model){
		model.addAttribute("redirect", redicturl);
		return "login";
		
	}
	@RequestMapping("/page/register")
	public String register(){
		return "register";
	}
	
}
