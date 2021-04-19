package com.demo.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.proxy.CommonProxy;
import com.demo.proxy.UserProxy;
import com.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<CommonProxy> save(@RequestBody UserProxy userProxy) {
		try {
			if(userProxy.getEmailId() == null) {
				return new ResponseEntity<>(new CommonProxy("Request Email Address Is null !!", HttpStatus.BAD_REQUEST.value(), Boolean.FALSE), 
						HttpStatus.OK);
			} 
			
			if(userProxy.getPassword() == null) {
				return new ResponseEntity<>(new CommonProxy("Request Password Is null !!", HttpStatus.BAD_REQUEST.value(), Boolean.FALSE), 
						HttpStatus.OK);
			}
			if(userProxy.getMobile() == null) {
				return new ResponseEntity<>(new CommonProxy("Request Mobile Is null !!", HttpStatus.BAD_REQUEST.value(), Boolean.FALSE), 
						HttpStatus.OK);
			}
			
			return new ResponseEntity<>(userService.save(userProxy), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception while save Registered details", e);
			return new ResponseEntity<>(new CommonProxy("Exception while save user data !!", HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
