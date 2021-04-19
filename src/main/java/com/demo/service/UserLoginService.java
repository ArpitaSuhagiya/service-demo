package com.demo.service;

import com.demo.proxy.CommonProxy;
import com.demo.proxy.LoginProxy;
import com.demo.proxy.UserLoginAuditProxy;

public interface UserLoginService {

	CommonProxy login(LoginProxy loginProxy);

	boolean logout(String token);
	
	public UserLoginAuditProxy checkTokenValidOrNot(String token);

}
