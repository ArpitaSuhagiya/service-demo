package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.domain.UserLoginAudit;

public interface UserLoginRepo extends JpaRepository<UserLoginAudit, Long>{

	public UserLoginAudit findByTokenAndIsActive(String token, Boolean isActive);
}
