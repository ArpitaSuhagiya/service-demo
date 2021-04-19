package com.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.proxy.CommonProxy;
import com.demo.proxy.LoginProxy;

import com.demo.service.UserLoginService;

@RestController
@RequestMapping("/login")
public class UserLoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductMasterController.class);

	@Autowired
	private UserLoginService userloginService;
	
	
	@PostMapping("/login")
	public ResponseEntity<CommonProxy> login(@RequestBody LoginProxy loginProxy) {
		try {
			CommonProxy login = userloginService.login(loginProxy);
			if(login != null) {
				return new ResponseEntity<>(login, HttpStatus.OK);
			}	
			return new ResponseEntity<>(new CommonProxy("login Not Successfully!!", HttpStatus.OK.value(), Boolean.FALSE), 
					HttpStatus.OK);
		}catch (Exception e) {
			logger.error("Exception while login ", e);
			return new ResponseEntity<>(new CommonProxy("Do not login Successfully!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/logout")
	public boolean logout(HttpServletRequest httpServletRequest) {
		return userloginService.logout(httpServletRequest.getHeader("token"));
	}
}
