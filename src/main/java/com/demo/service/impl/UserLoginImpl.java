package com.demo.service.impl;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.domain.User;
import com.demo.domain.UserLoginAudit;
import com.demo.proxy.CommonProxy;
import com.demo.proxy.LoginProxy;
import com.demo.proxy.UserLoginAuditProxy;
import com.demo.repository.UserLoginRepo;
import com.demo.repository.UserRepo;
import com.demo.service.UserLoginService;

@Service
@Transactional
public class UserLoginImpl implements UserLoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginImpl.class);

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserLoginRepo userLoginRepo;
	
	@Override
	public CommonProxy login(LoginProxy loginProxy) {
		try {
			User user = userRepo.findByEmailId(loginProxy.getEmail());
			if(user == null) {
				return new CommonProxy("Please Enter Registered Email Addrss", HttpStatus.BAD_REQUEST.value(), Boolean.FALSE);
			}
			
			if(!user.getPassword().equals(loginProxy.getPassword())) {
				return new CommonProxy("Please Enter Correct Password", HttpStatus.BAD_REQUEST.value(), Boolean.FALSE);
			}
			
			String token = generateString();
			
			UserLoginAudit loginAudit = new UserLoginAudit();
			loginAudit.setUserId(user.getId());
			loginAudit.setToken(token);
			loginAudit.setCreatedDate(new Date());
			loginAudit.setCreatedBy(user.getId());
			loginAudit.setIsActive(Boolean.TRUE);
			loginAudit = userLoginRepo.save(loginAudit);
			
			UserLoginAuditProxy auditProxy = new UserLoginAuditProxy();
			BeanUtils.copyProperties(loginAudit, auditProxy);
			
			return new CommonProxy("Successfully Login !!", auditProxy, HttpStatus.OK.value(), Boolean.TRUE);
		} catch (Exception e) {
			logger.error("Exception while Login ", e);
			return new CommonProxy("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE);
		}
	}
	
	public String generateString() {
        return UUID.randomUUID().toString();
    }
	
	public UserLoginAuditProxy checkTokenValidOrNot(String token) {
		UserLoginAudit userLoginAudit = userLoginRepo.findByTokenAndIsActive(token, Boolean.TRUE);
		if(userLoginAudit == null) {
			return null;
		}
		UserLoginAuditProxy auditProxy = new UserLoginAuditProxy();
		BeanUtils.copyProperties(userLoginAudit, auditProxy);
		return auditProxy;
	}
	@Override
	public boolean logout(String token) {
		UserLoginAudit userLoginAudit = userLoginRepo.findByTokenAndIsActive(token, Boolean.TRUE);
		if(userLoginAudit == null) {
			return false;
		}
		userLoginAudit.setIsActive(Boolean.FALSE);
		userLoginAudit.setModifiedDate(new Date());
		userLoginRepo.save(userLoginAudit);
		return true;
	}
	
	
}
