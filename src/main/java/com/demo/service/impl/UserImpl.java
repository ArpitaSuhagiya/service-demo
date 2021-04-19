package com.demo.service.impl;


import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.domain.User;
import com.demo.proxy.CommonProxy;
import com.demo.proxy.UserProxy;
import com.demo.repository.UserRepo;
import com.demo.service.UserService;

@Service
@Transactional
public class UserImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public CommonProxy save(UserProxy userProxy) {
		User user = userRepo.findByEmailId(userProxy.getEmailId());
		if(user != null) {
			return new CommonProxy("This email address is already exists, please enter different email address!!", HttpStatus.BAD_REQUEST.value(), Boolean.TRUE);
		}
		user = new User();
		BeanUtils.copyProperties(userProxy, user);
		user.setIsActive(Boolean.TRUE);
		user.setCreatedDate(new Date());
		userRepo.save(user);
		return new CommonProxy("Successfuly Registered Your Data", HttpStatus.OK.value(), Boolean.TRUE);
	}
}

